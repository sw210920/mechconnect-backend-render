package com.mechconnect.backend.service;

import java.util.List;

import com.mechconnect.backend.dto.AdminLoginRequestDto;
import com.mechconnect.backend.dto.AdminResponseDto;
import com.mechconnect.backend.entity.Customer;
import com.mechconnect.backend.entity.Mechanic;



public interface AdminService {

	AdminResponseDto login(AdminLoginRequestDto request);
	List<Customer> getAllCustomers();

    List<Mechanic> getAllMechanics();
    
}
