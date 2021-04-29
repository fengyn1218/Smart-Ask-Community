package com.feng.community.dto;

import com.feng.community.constant.ResultViewCode;
import com.feng.community.exception.CustomizeException;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.feng.community.constant.ResultViewCode.FAIL;
import static com.feng.community.constant.ResultViewCode.SUCCESS;

/**
 * @author fengyunan
 * Created on 2021-03-07
 */
@Data
@NoArgsConstructor
public class ResultView<T> {
    private Integer code;
    private String message;
    private T data;

    public ResultView(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

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

    public static ResultView fail(CustomizeException e) {
        return fail(e.getCode(), e.getMessage());
    }

    public static ResultView fail(Integer code, String message) {
        ResultView resultView = new ResultView();
        resultView.setCode(code);
        resultView.setMessage(message);
        return resultView;
    }

    public static ResultView fail(ResultViewCode viewCode) {
        return fail(viewCode.getCode(), viewCode.getMsg());
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
