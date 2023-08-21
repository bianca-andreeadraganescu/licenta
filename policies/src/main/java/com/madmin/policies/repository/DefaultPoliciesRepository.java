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
public class DefaultPoliciesRepository {

    public static final String HASH_KEY = "DefaultPolicy";
    private static final Logger log = LoggerFactory.getLogger(DefaultPoliciesRepository.class);

    @Autowired
    @Qualifier("template_default")
    private RedisTemplate redisTemplateDefault;

    @PostConstruct
    public void init() {
        RedisSerializer<?> serializer = new Jackson2JsonRedisSerializer<>(FirewallPolicy.class);
        redisTemplateDefault.setKeySerializer(new StringRedisSerializer());
        redisTemplateDefault.setHashKeySerializer(new StringRedisSerializer());
        redisTemplateDefault.setValueSerializer(serializer);
        redisTemplateDefault.setHashValueSerializer(serializer);
        log.info("RedisTemplate configured successfully: {}", redisTemplateDefault);
    }

    public FirewallPolicy save(FirewallPolicy policy) throws IOException {
        redisTemplateDefault.opsForHash().put(HASH_KEY, policy.getId(), policy);
        return policy;
    }

    public void deleteById(String id) {
        Long deleted = redisTemplateDefault.opsForHash().delete(HASH_KEY, id);
        if (deleted == null || deleted == 0) {
            throw new IllegalArgumentException("Policy not found with ID: " + id);
        }
    }

}