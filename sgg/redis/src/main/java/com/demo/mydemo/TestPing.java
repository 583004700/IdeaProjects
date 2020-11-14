package com.demo.mydemo;

import redis.clients.jedis.Jedis;

public class TestPing {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("hadoop102",6379);
        System.out.println(jedis.ping());
    }
}
