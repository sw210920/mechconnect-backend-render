package com.mechconnect.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mechconnect.backend.dto.AdminLoginRequestDto;
import com.mechconnect.backend.dto.AdminResponseDto;
import com.mechconnect.backend.entity.Admin;
import com.mechconnect.backend.entity.Customer;
import com.mechconnect.backend.entity.Mechanic;
import com.mechconnect.backend.repository.AdminRepository;
import com.mechconnect.backend.repository.CustomerRepository;
import com.mechconnect.backend.repository.MechanicRepository;
import com.mechconnect.backend.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MechanicRepository mechanicRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public AdminResponseDto login(AdminLoginRequestDto request) {

        Admin admin = adminRepository.findByEmail(
            request.getEmail().trim().toLowerCase()
        );

        if (admin == null) {
            throw new RuntimeException("Admin not found");
        }

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        AdminResponseDto dto = new AdminResponseDto();
        dto.setAdminId(admin.getAdminId());
        dto.setEmail(admin.getEmail());
        dto.setRole(admin.getRole());

        return dto;
    }
    
    
    
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<Mechanic> getAllMechanics() {
        return mechanicRepository.findAll();
    }
    
}