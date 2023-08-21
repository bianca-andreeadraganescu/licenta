package com.project.policies.administration.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rules implements Serializable {

    private String rule_name;
    private String domain;
    private List<String> destination;
    private String source;
    private String action;
    private String direction;

    public Rules(LinkedHashMap<String, Object> map) {
        this.rule_name = (String) map.get("rule_name");
        this.domain = (String) map.get("domain");
        this.destination = (List<String>) map.get("destination");
        this.source = (String) map.get("source");
        this.action = (String) map.get("action");
        this.direction = (String) map.get("direction");
    }

}
