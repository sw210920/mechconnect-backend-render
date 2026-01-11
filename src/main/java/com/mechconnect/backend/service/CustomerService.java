package com.mechconnect.backend.service;

import java.util.List;

import com.mechconnect.backend.dto.CustomerOrderDto;

/**
 * CustomerService
 *
 * Part of the MechConnect backend application.
 * Responsible for handling service related logic.
 */





import com.mechconnect.backend.dto.CustomerRegistrationRequest;
import com.mechconnect.backend.dto.LoginRequestDto;
import com.mechconnect.backend.entity.Customer;
import com.mechconnect.backend.entity.ServiceRequest;
import com.mechconnect.backend.entity.enums.RequestStatus;


public interface CustomerService {

	String SaveCustomer(CustomerRegistrationRequest registerCustomer);

	
	  Customer loginCustomer(LoginRequestDto loginRequest);

	    Customer findByEmail(String email);
	
	    Customer updateCustomer(Customer customer);
	    
	    Customer findById(Long id);
	
	    Customer getCustomerProfile(Long CustomerId);
	


	
    boolean changePassword(Long CustomerId, String oldPassword, String newPassword);

    boolean sendOtpForForgotPassword(String email);

    String verifyOtp(String email, String otp);

    String resetPassword(String email, String newPassword);
	String deleteCustomer(Long CustomerId);
    
//	Orders Method
	List<CustomerOrderDto> getOrdersForCustomer(Long customerId);
	
	
	List<ServiceRequest> findByCustomer_CustomerIdAndStatus(
	        Long customerId,
	        RequestStatus status
	);
	
	
	
}
