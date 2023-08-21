package com.madmin.policies.repository;

import com.madmin.policies.object.FirewallPolicy;
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

@Repository
public class ExamPoliciesRepository {

    public static final String HASH_KEY = "ExamPolicy";
    private static final Logger log = LoggerFactory.getLogger(ExamPoliciesRepository.class);

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
        log.info("RedisTemplate configured successfully: {}", redisTemplateExam);
    }

    public FirewallPolicy save(FirewallPolicy policy) throws IOException {
        redisTemplateExam.opsForHash().put(HASH_KEY, policy.getId(), policy);
        return policy;
    }

    public void deleteById(String id) {
        Long deleted = redisTemplateExam.opsForHash().delete(HASH_KEY, id);
        if (deleted == null || deleted == 0) {
            throw new IllegalArgumentException("Policy not found with ID: " + id);
        }
    }
}
