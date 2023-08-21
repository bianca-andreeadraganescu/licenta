package com.madmin.policies.object;

import com.madmin.policies.utils.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.LinkedHashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Policy")
public class FirewallPolicy implements Serializable {
    @Id
    private String id;
    private Type type;
    private boolean active;
    private String t_start;
    private String t_stop;
    private Rules rules;
    public FirewallPolicy(LinkedHashMap<String, Object> map) {
        this.id = (String) map.get("id");
        this.type = Type.valueOf((String) map.get("type"));
        this.active = (Boolean) map.get("active");
        this.t_start = (String) map.get("t_start");
        this.t_stop = (String) map.get("t_stop");
        LinkedHashMap<String, Object> rulesMap = (LinkedHashMap<String, Object>) map.get("rules");
        this.rules = new Rules(rulesMap);

    }

    public String getId() {
        return id;
    }
}
