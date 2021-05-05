package com.feng.community.values;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: fengyunan
 * Created on: 2021-05-03
 */
@Getter
@Component
public class HTTPApplicationValues {

    @Value("${http_pool.max_total}")
    private int maxTotal;

    @Value("${http_pool.default_max_per_route}")
    private int maxPerRoute;

    @Value("${http_pool.connect_timeout}")
    private int connTimeOut;

    @Value("${http_pool.connection_request_timeout}")
    private int connReqTimeOut;

    @Value("${http_pool.socket_timeout}")
    private int socketTimeout;

    @Value("${http_pool.validate_after_inactivity}")
    private int inactivity;
}

