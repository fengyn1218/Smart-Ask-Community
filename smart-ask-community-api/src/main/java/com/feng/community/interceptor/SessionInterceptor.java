package com.feng.community.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.feng.community.annotation.NeedLoginToken;
import com.feng.community.dto.ResultView;
import com.feng.community.entity.TbUser;
import com.feng.community.utils.TokenUtils;

/**
 * 会话拦截
 *
 * @author fengyunan
 * Created on 2021-03-10
 */
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

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
                            loginUserCache.putLoginUser(userDTO.getId(), System.currentTimeMillis());
                            //return true;
                        }
                    }
                    break;
                }
            }
        }

        /**
         * 拦截注解
         */
        if (method.isAnnotationPresent(NeedLoginToken.class)) {
            NeedLoginToken userLoginToken = method.getAnnotation(NeedLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if ((!hashToken) || resultView.getCode() != 200) {
                    throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
                }
            }
        }
        return true;


        return false;
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
