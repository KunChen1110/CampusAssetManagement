package com.campus.asset.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.concurrent.TimeUnit;

//@SpringBootTest
@Disabled("Manual Redis smoke test; requires Redis and Spring context configuration.")
public class SpringDataRedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedisTemplate(){
        System.out.println(redisTemplate);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        HashOperations hashOperations = redisTemplate.opsForHash();
        ListOperations listOperations = redisTemplate.opsForList();
        SetOperations setOperations = redisTemplate.opsForSet();
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
    }

    @Test
    public void testString(){
        redisTemplate.opsForValue().set("city","Beijing");
        String city = (String) redisTemplate.opsForValue().get("city");
        redisTemplate.opsForValue().set("code","1234",3, TimeUnit.MINUTES);
        redisTemplate.opsForValue().setIfAbsent("lock","1");
    }

    @Test
    public void testHash(){
        redisTemplate.opsForHash().put("user:1","name","Tom");
        redisTemplate.opsForHash().put("user:1","age","18");
        String name= (String) redisTemplate.opsForHash().get("user: 1","name");
        System.out.println(name);
    }
}
