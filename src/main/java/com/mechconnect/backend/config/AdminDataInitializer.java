package com.mechconnect.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.mechconnect.backend.entity.Admin;
import com.mechconnect.backend.repository.AdminRepository;

@Component
public class AdminDataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (adminRepository.count() == 0) {

            Admin admin = new Admin();
            admin.setName("Super Admin");
            admin.setEmail("admin@mechconnect.com");
            admin.setPassword(passwordEncoder.encode("admin@123"));

            adminRepository.save(admin);

            System.out.println("✅ Default admin created");
        }
    }
}
