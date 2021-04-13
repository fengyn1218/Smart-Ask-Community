package com.feng.community.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.feng.community.constant.ResultViewCode;
import com.feng.community.exception.CustomizeException;
import com.feng.community.storage.LoginUserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.feng.community.annotation.NeedLoginToken;
import com.feng.community.dto.ResultView;
import com.feng.community.entity.TbUser;
import com.feng.community.utils.TokenUtils;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

/**
 * 会话拦截
 *
 * @author fengyunan
 * Created on 2021-03-10
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginUserCache loginUserCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String referer = request.getHeader("referer");
        String host = request.getHeader("host");
        //处理静态资源
        if (handler instanceof ResourceHttpRequestHandler) {
            if (referer != null && (!host.equals(referer.split("//")[1].split("/")[0]))) {//静态资源防盗链
                response.setStatus(403);
                return false;
            }
            return true;
        }

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        Method method = ((HandlerMethod) handler).getMethod();
        String token = null;
        ResultView resultView = null;
        Cookie[] cookies = request.getCookies();
        boolean hashToken = false;

        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    if (token != null) {
                        hashToken = true;
                        resultView = TokenUtils.verifyToken(token);
                        if (resultView.getCode() == 200) {
                            TbUser user = (TbUser) resultView.getData();
                            request.setAttribute("loginUser", user);
                            loginUserCache.putLoginUser(user.getId(), System.currentTimeMillis());
                        }
                    }
                    break;
                }
            }
        }
        /*
         * 拦截注解
         */
        if (method.isAnnotationPresent(NeedLoginToken.class)) {
            NeedLoginToken userLoginToken = method.getAnnotation(NeedLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证，需要登录
                if ((!hashToken) || resultView.getCode() != 200) {
                    throw new CustomizeException(ResultViewCode.NEED_LOGIN);
                }
            }
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
}
