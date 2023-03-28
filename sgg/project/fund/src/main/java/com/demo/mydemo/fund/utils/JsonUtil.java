package com.demo.mydemo.fund.utils;

import com.alibaba.fastjson.JSON;

public class JsonUtil {
    public static <T> T parse(String jsonStr) {
        return (T) JSON.parse(jsonStr);
    }

    public static String toJSONString(Object obj) {
        return JSON.toJSONString(obj);
    }
}
