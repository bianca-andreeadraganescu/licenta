package com.madmin.policies.repository;

import com.madmin.policies.object.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
public class RoleRepository {

    private static final String HASH_KEY = "roles";
    private static final Logger log = LoggerFactory.getLogger(RoleRepository.class);

    @Autowired
    @Qualifier("template_role")
    private RedisTemplate<String, Role> redisTemplate;
    private HashOperations<String, String, Role> hashOperations;

    public RoleRepository(RedisTemplate<String, Role> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void init() {
        hashOperations = redisTemplate.opsForHash();
        log.info("RoleRepository initialized successfully.");
    }

    public Role save(Role role) {
        hashOperations.put(HASH_KEY, String.valueOf(role.getId()), role);
        return role;
    }

    public List<Role> findAll() {
        return hashOperations.values(HASH_KEY);
    }

    public Role findById(Long id) {
        return hashOperations.get(HASH_KEY, String.valueOf(id));
    }

    public Role findByName(String name) {
        return hashOperations.values(HASH_KEY)
                .stream()
                .filter(role -> role.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void deleteById(Long id) {
        hashOperations.delete(HASH_KEY, String.valueOf(id));
    }

}
