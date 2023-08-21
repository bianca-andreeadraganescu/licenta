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
import java.util.List;

@Repository
public class DevPoliciesRepository {

    public static final String HASH_KEY = "DevPolicy";
    private static final Logger log = LoggerFactory.getLogger(DevPoliciesRepository.class);

    @Autowired
    @Qualifier("template_dev")
    private RedisTemplate redisTemplateDev;

    @PostConstruct
    public void init() {
        RedisSerializer<?> serializer = new Jackson2JsonRedisSerializer<>(FirewallPolicy.class);
        redisTemplateDev.setKeySerializer(new StringRedisSerializer());
        redisTemplateDev.setHashKeySerializer(new StringRedisSerializer());
        redisTemplateDev.setValueSerializer(serializer);
        redisTemplateDev.setHashValueSerializer(serializer);
        log.info("RedisTemplate configured successfully: {}", redisTemplateDev);
    }

    public FirewallPolicy save(FirewallPolicy policy) throws IOException {
        redisTemplateDev.opsForHash().put(HASH_KEY, policy.getId(), policy);
        return policy;
    }

    public void deleteById(String id) {
        Long deleted = redisTemplateDev.opsForHash().delete(HASH_KEY, id);
        if (deleted == null || deleted == 0) {
            throw new IllegalArgumentException("Policy not found with ID: " + id);
        }
    }


}