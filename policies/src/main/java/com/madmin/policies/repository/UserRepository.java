package com.madmin.policies.repository;

import com.madmin.policies.object.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    private static final String HASH_KEY = "user";
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    public User save(User user) {
        redisTemplate.opsForHash().put(HASH_KEY, user.getUsername(), user);
        return user;
    }

    public Optional<User> findByUsername(Object username) {
        UserRepositoryFunction<String, User> findByUsernameFunction = key ->
                (User) redisTemplate.opsForHash().get(HASH_KEY, key);

        return Optional.ofNullable(findByUsernameFunction.apply((String) username));    }

    public void deleteByUsername(String username) {
        redisTemplate.opsForHash().delete(HASH_KEY, username);
    }

}
