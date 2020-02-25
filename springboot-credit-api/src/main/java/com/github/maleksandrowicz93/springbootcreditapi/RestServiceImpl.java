package com.github.maleksandrowicz93.springbootcreditapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestServiceImpl <T> implements RestService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void post(String url, Object requestBody) {
        restTemplate.postForObject(url, requestBody, requestBody.getClass());
    }

    @Override
    public void post(String url, Object[] requestBody) {
        restTemplate.postForObject(url, requestBody, requestBody.getClass());
    }

    @Override
    public Object get(String url, Class aClass) {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                aClass
        );
    }

}
