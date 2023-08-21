package com.madmin.policies.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.madmin.policies.object.FirewallPolicy;
import com.madmin.policies.repository.DefaultPoliciesRepository;
import com.madmin.policies.repository.DevPoliciesRepository;
import com.madmin.policies.repository.ExamPoliciesRepository;
import com.madmin.policies.repository.LabPoliciesRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/policies")
public class PoliciesController {

    @Autowired
    private DevPoliciesRepository devRepo;

    @Autowired
    private LabPoliciesRepository labRepo;

    @Autowired
    private ExamPoliciesRepository examRepo;

    @Autowired
    private DefaultPoliciesRepository defaultRepo;

    @PostMapping("/dev")
    public FirewallPolicy saveDevPolicy() throws IOException {
        File file = new File("src/main/resources/policies/devpolicy.json");
        String jsonContent = FileUtils.readFileToString(file, "UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        FirewallPolicy policy = objectMapper.readValue(jsonContent, FirewallPolicy.class);
        return devRepo.save(policy);
    }
    @PostMapping("/default")
    public FirewallPolicy saveDefaultPolicy() throws IOException{
        File file = new File("src/main/resources/policies/defaultpolicy.json");
        String jsonContent = FileUtils.readFileToString(file, "UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        FirewallPolicy policy = objectMapper.readValue(jsonContent, FirewallPolicy.class);
        return defaultRepo.save(policy);
    }
    @PostMapping("/lab")
    public FirewallPolicy saveLabPolicy() throws IOException {
        File file = new File("src/main/resources/policies/labpolicy.json");
        String jsonContent = FileUtils.readFileToString(file, "UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        FirewallPolicy policy = objectMapper.readValue(jsonContent, FirewallPolicy.class);
        return labRepo.save(policy);

    }

    @PostMapping("/exam")
    public FirewallPolicy saveExamPolicy() throws IOException{
        File file = new File("src/main/resources/policies/exampolicy.json");
        String jsonContent = FileUtils.readFileToString(file, "UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        FirewallPolicy policy = objectMapper.readValue(jsonContent, FirewallPolicy.class);
        return examRepo.save(policy);
    }
}
