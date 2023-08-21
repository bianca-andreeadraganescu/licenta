package com.madmin.policies.repository;

import com.madmin.policies.utils.KeyValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyValueRepository extends CrudRepository<KeyValue, String> {
    KeyValue findByKey(String key);
}
