package com.madmin.policies.controller;

import com.madmin.policies.object.Role;
import com.madmin.policies.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
public class RoleInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RoleInitializer.class);

    private RoleRepository roleRepository;

    @Autowired
    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findAll().isEmpty()) {
            // Initialize predefined roles
            Role adminRole = new Role(1L, "Administrator");
            roleRepository.save(adminRole);

            Role userRole = new Role(2L, "User");
            roleRepository.save(userRole);

            log.info("Predefined roles initialized successfully.");
        }
    }
}
