package com.demo.mydemo;

import redis.clients.jedis.Jedis;

public class TestApi {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("hadoop102",6379);

        jedis.set("k1","v1");
        jedis.set("k2","v2");
        jedis.set("k3","v3");
        System.out.println(jedis.get("k3"));
        System.out.println(jedis.keys("*"));
    }
}
