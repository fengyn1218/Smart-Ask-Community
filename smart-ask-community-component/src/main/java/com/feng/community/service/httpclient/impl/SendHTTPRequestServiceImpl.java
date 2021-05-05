package com.feng.community.service.httpclient.impl;

import com.feng.community.service.httpclient.SendHTTPRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author: fengyunan
 * Created on: 2021-05-03
 */
@Service
public class SendHTTPRequestServiceImpl implements SendHTTPRequestService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String sendGetRequest(String parameters) {
        String url = "http://192.168.1.101:5001/predict/" + parameters;
        ResponseEntity responseEntity = restTemplate.getForEntity(url, String.class);
        return (String) responseEntity.getBody();
    }
}
