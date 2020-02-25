package com.github.maleksandrowicz93.springbootcreditapi;

import org.springframework.stereotype.Service;

@Service
public interface RestService <T> {

    void post(String url, Object requestBody);
    void post(String url, Object[] requestBody);
    Object get(String url, Class<T> aClass);

}
