package com.project.policies.administration.repository;

import com.project.policies.administration.object.FirewallPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
public class DevPoliciesRepository {

    public static final String HASH_KEY = "DevPolicy";

    @Autowired
    @Qualifier("template_dev")
    private RedisTemplate redisTemplateDev;

    @PostConstruct
    public void init() {
        RedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        redisTemplateDev.setKeySerializer(new StringRedisSerializer());
        redisTemplateDev.setHashKeySerializer(new StringRedisSerializer());
        redisTemplateDev.setValueSerializer(serializer);
        redisTemplateDev.setHashValueSerializer(serializer);
    }

    public FirewallPolicy save(FirewallPolicy policy) {
        redisTemplateDev.opsForHash().put(HASH_KEY, policy.getId(), policy);
        return policy;
    }

    public List<FirewallPolicy> findAll() {
        return redisTemplateDev.opsForHash().values(HASH_KEY);
    }

    public FirewallPolicy findPolicyById(int id) {
        return (FirewallPolicy) redisTemplateDev.opsForHash().get(HASH_KEY, id);
    }


}