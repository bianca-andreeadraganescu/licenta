package com.madmin.policies.services;
import com.madmin.policies.repository.KeyValueRepository;
import com.madmin.policies.utils.KeyValue;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final KeyValueRepository keyValueRepository;

    public RedisService(KeyValueRepository keyValueRepository) {
        this.keyValueRepository = keyValueRepository;
    }

    public void saveValue(String key, String value) {
        KeyValue keyValue = new KeyValue();
        keyValue.setKey(key);
        keyValue.setValue(value);
        keyValueRepository.save(keyValue);
    }

    public String getValue(String key) {
        KeyValue keyValue = keyValueRepository.findByKey(key);
        return keyValue != null ? keyValue.getValue() : null;
    }
}
