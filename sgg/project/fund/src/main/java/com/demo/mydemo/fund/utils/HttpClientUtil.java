package com.demo.mydemo.fund.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpClientUtil {
    @Autowired
    RestTemplate restTemplate;

    public <T> T getForEntity(String url, Class<T> clazz) {
        try {
            ResponseEntity<T> forEntity = restTemplate.getForEntity(url, clazz);
            return forEntity.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
