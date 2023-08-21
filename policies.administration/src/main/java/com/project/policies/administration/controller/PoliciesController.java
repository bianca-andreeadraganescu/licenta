package com.project.policies.administration.controller;

import com.project.policies.administration.object.FirewallPolicy;
import com.project.policies.administration.repository.DefaultPoliciesRepository;
import com.project.policies.administration.repository.DevPoliciesRepository;
import com.project.policies.administration.repository.ExamPoliciesRepository;
import com.project.policies.administration.repository.LabPoliciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @GetMapping("/dev")
    public List<FirewallPolicy> getAllDevPolicies() {
        return devRepo.findAll();
    }

    @GetMapping("/dev/{id}")
    public FirewallPolicy findDevPolicy(@PathVariable int id) {
        return devRepo.findPolicyById(id);
    }
    @GetMapping("/lab")
    public List<FirewallPolicy> getAllLabPolicies(){
        return labRepo.findAll();
    }
    @GetMapping("/lab/{id}")
    public FirewallPolicy findLabPolicy(@PathVariable String id){
        return labRepo.findPolicyById(id);
    }

    @GetMapping("/exam")
    public List<FirewallPolicy> getAllExamPolicies(){
        return examRepo.findAll();
    }

    @GetMapping("/exam/{id}")
    public FirewallPolicy findExamPolicy(@PathVariable String id){
        return examRepo.findPolicyById(id);
    }

    @GetMapping("/default")
    public List<FirewallPolicy> getAllDefaultPolicies(){
        return defaultRepo.findAll();
    }

    @GetMapping("/default/{id}")
    public FirewallPolicy findDefaultPolicy(@PathVariable String id){
        return defaultRepo.findPolicyById(Integer.parseInt(id));
    }
}
