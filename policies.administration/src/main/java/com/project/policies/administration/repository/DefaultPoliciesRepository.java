package com.project.policies.administration.repository;

import com.project.policies.administration.object.FirewallPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Repository
public class DefaultPoliciesRepository {

    public static final String HASH_KEY = "DefaultPolicy";
    @Autowired
    @Qualifier("template_default")
    private RedisTemplate redisTemplateDefault;

    @PostConstruct
    public void init() {
        RedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        redisTemplateDefault.setKeySerializer(new StringRedisSerializer());
        redisTemplateDefault.setHashKeySerializer(new StringRedisSerializer());
        redisTemplateDefault.setValueSerializer(serializer);
        redisTemplateDefault.setHashValueSerializer(serializer);
    }
    public FirewallPolicy save(FirewallPolicy policy) {
        redisTemplateDefault.opsForHash().put(HASH_KEY, policy.getId(), policy);
        return policy;
    }

    public List<FirewallPolicy> findAll() {
        return redisTemplateDefault.opsForHash().values(HASH_KEY);
    }

    public FirewallPolicy findPolicyById(int id) {
        return (FirewallPolicy) redisTemplateDefault.opsForHash().get(HASH_KEY, id);
    }

}