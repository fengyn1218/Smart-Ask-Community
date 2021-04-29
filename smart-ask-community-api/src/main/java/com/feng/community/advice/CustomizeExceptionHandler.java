package com.feng.community.advice;


import com.feng.community.constant.ResultViewCode;
import com.feng.community.dto.ResultView;
import com.feng.community.exception.CustomizeException;
import com.feng.community.utils.MapperUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultView MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 可以使用 warn 日志级别来记录用户输入参数错误的情况，避免用户投诉时，无所适从。如非必要，请不要在此场景打出 error 级别，避免频繁报警。
        //  log.warn(e.getMessage(), e);
        // 然后提取错误提示信息进行返回
        return ResultView.fail(ResultViewCode.VALIDATE_ERROR.getCode(), objectError.getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler({BindException.class})
    public ResultView MethodArgumentNotValidExceptionHandler(BindException e) {
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 可以使用 warn 日志级别来记录用户输入参数错误的情况，避免用户投诉时，无所适从。如非必要，请不要在此场景打出 error 级别，避免频繁报警。
        //log.warn(e.getMessage(), e);
        // 然后提取错误提示信息进行返回
        return ResultView.fail(ResultViewCode.VALIDATE_ERROR.getCode(), objectError.getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType) || request.getServletPath().contains("api")) {//访问接口异常时弹出异常信息，非跳转
            ResultView resultDTO;
            // 返回 JSON
            if (e instanceof CustomizeException) {//已知自定义异常
                resultDTO = ResultView.fail((CustomizeException) e);
            } else {//未知异常
                resultDTO = ResultView.fail(ResultViewCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(MapperUtils.obj2json(resultDTO));
                writer.close();
            } catch (IOException ioe) {
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        } else {
            // 访问页面错误时页面跳转
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
                model.addAttribute("errorCode", "错误:" + ((CustomizeException) e).getCode());
            } else {
                model.addAttribute("message", ResultViewCode.SYS_ERROR.getMsg());
            }
            return new ModelAndView("error");
        }
    }
}
