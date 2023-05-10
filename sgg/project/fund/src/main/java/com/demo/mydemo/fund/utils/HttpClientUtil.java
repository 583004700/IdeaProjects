package com.demo.mydemo.fund.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

@Component
public class HttpClientUtil {
    @Autowired
    RestTemplate restTemplate;

    int retrySleepTime = 2000;

    public <T> T getForEntity(String url, Class<T> clazz) {
        boolean success = true;
        do {
            try {
                ResponseEntity<T> forEntity = restTemplate.getForEntity(url, clazz);
                return forEntity.getBody();
            } catch (UnknownHttpStatusCodeException unknownHttpStatusCodeException) {
                success = false;
                try {
                    Thread.sleep(retrySleepTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(url + "【请求频率过快导致未获取到数据，正在重试!】");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (!success);
        return null;
    }

    public static String get(String url) {
        try {
            HttpResponse execute = HttpRequest.get(url).timeout(1000 * 30).execute();
            if(execute.getStatus() == 200){
                return execute.body();
            }else{
                System.out.println("请求异常！url:"+url);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
