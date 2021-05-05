package com.feng.community;

import com.feng.community.service.httpclient.SendHTTPRequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: fengyunan
 * Created on: 2021-05-03
 */
@SpringBootTest
public class TestHTTPClient {

    @Autowired
    private SendHTTPRequestService sendHTTPRequestService;

    @Test
    public void setSendHTTPRequest(){
        System.out.println(sendHTTPRequestService.sendGetRequest("123"));
    }
}
