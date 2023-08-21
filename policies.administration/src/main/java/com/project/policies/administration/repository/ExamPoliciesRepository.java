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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ExamPoliciesRepository {

    public static final String HASH_KEY = "ExamPolicy";

    @Autowired
    @Qualifier("template_exam")
    private RedisTemplate redisTemplateExam;

    @PostConstruct
    public void init() {
        RedisSerializer<?> serializer = new Jackson2JsonRedisSerializer<>(FirewallPolicy.class);
        redisTemplateExam.setKeySerializer(new StringRedisSerializer());
        redisTemplateExam.setHashKeySerializer(new StringRedisSerializer());
        redisTemplateExam.setValueSerializer(serializer);
        redisTemplateExam.setHashValueSerializer(serializer);
    }

    public FirewallPolicy save(FirewallPolicy policy) throws IOException {
        redisTemplateExam.opsForHash().put(HASH_KEY, policy.getId(), policy);
        return policy;
    }

    public List<FirewallPolicy> findAll() {
        return (List<FirewallPolicy>) redisTemplateExam.opsForHash().values(HASH_KEY).stream()
                .map(object -> new FirewallPolicy((LinkedHashMap<String, Object>) object))
                .collect(Collectors.toList());
    }

    public FirewallPolicy findPolicyById(String id) {
        Object object = redisTemplateExam.opsForHash().get(HASH_KEY, id);
        return object != null ? new FirewallPolicy((LinkedHashMap<String, Object>) object) : null;
    }
}
