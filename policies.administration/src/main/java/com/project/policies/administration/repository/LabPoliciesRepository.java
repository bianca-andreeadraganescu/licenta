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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LabPoliciesRepository {

    public static final String HASH_KEY = "LabPolicy";

    @Autowired
    @Qualifier("template_lab")
    private RedisTemplate redisTemplate;

    @PostConstruct
    public void init() {
        RedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
    }
    public FirewallPolicy save(FirewallPolicy policy) {
        redisTemplate.opsForHash().put(HASH_KEY, policy.getId(), policy);
        return policy;
    }

    public List<FirewallPolicy> findAll() {
        return (List<FirewallPolicy>) redisTemplate.opsForHash().values(HASH_KEY).stream()
                .map(object -> new FirewallPolicy((LinkedHashMap<String, Object>) object))
                .collect(Collectors.toList());
    }

    public FirewallPolicy findPolicyById(String id) {
        Object object = redisTemplate.opsForHash().get(HASH_KEY, id);
        return object != null ? new FirewallPolicy((LinkedHashMap<String, Object>) object) : null;
    }
}