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
        resultView.setCode(SUCCESS);
        resultView.setMessage(message);
        return resultView;
    }

    public static ResultView success(Integer code, String message) {
        ResultView resultView = new ResultView();
        resultView.setCode(code);
        resultView.setMessage(message);
        return resultView;
    }

    public static ResultView fail(String message) {
        ResultView resultView = new ResultView();
        resultView.setCode(FAIL);
        resultView.setMessage(message);
        return resultView;
    }

    public static ResultView fail(Integer code, String message) {
        ResultView resultView = new ResultView();
        resultView.setCode(code);
        resultView.setMessage(message);
        return resultView;
    }

}
