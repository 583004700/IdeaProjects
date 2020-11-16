package com.demo.mydemo;

import redis.clients.jedis.Jedis;

public class TestMS {
    public static void main(String[] args) {
        Jedis jedis_M = new Jedis("hadoop102", 6379);
        Jedis jedis_S = new Jedis("hadoop102", 6380);
        jedis_S.slaveof("hadoop102", 6379);
        jedis_M.set("class","1122");
        String result = jedis_S.get("class");
        System.out.println(result);
    }
}
