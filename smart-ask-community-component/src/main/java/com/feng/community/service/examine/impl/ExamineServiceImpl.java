package com.feng.community.service.examine.impl;

import com.feng.community.service.examine.ExamineService;
import com.feng.community.service.httpclient.SendHTTPRequestService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.feng.community.constant.ExamineConstant.SENSITIVE_LIMIT;

/**
 * @author: fengyunan
 * Created on: 2021-05-03
 */
@Service
public class ExamineServiceImpl implements ExamineService {
    @Autowired
    private SendHTTPRequestService sendHTTPRequestService;

    @Override
    public boolean isNormal(String string) {
        if (StringUtils.isBlank(string)) {
            return true;
        }
        String s = sendHTTPRequestService.sendGetRequest(string);
        double aDouble = Double.parseDouble(s);
        return !(aDouble > SENSITIVE_LIMIT);
    }
}
