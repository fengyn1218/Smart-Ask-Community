package com.feng.community.dto;

import static com.feng.community.constant.ResultViewCode.FAIL;
import static com.feng.community.constant.ResultViewCode.SUCCESS;

import lombok.Data;

/**
 * @author fengyunan
 * Created on 2021-03-07
 */
@Data
public class ResultView<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResultView success(String message) {
        ResultView resultView = new ResultView();
        resultView.setCode(SUCCESS.getCode());
        resultView.setMessage(message);
        return resultView;
    }

    public static ResultView success(Integer code, String message) {
        ResultView resultView = new ResultView();
        resultView.setCode(code);
        resultView.setMessage(message);
        return resultView;
    }

    public static <T> ResultView success(T data) {
        ResultView resultView = new ResultView();
        resultView.setCode(SUCCESS.getCode());
        resultView.setMessage("请求成功");
        resultView.setData(data);
        return resultView;
    }

    public static ResultView fail(String message) {
        ResultView resultView = new ResultView();
        resultView.setCode(FAIL.getCode());
        resultView.setMessage(message);
        return resultView;
    }

    public static ResultView fail(Integer code, String message) {
        ResultView resultView = new ResultView();
        resultView.setCode(code);
        resultView.setMessage(message);
        return resultView;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
