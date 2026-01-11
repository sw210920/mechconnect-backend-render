package com.mechconnect.backend.controller;

/**
 * CustomerController
 *
 * Part of the MechConnect backend application.
 * Responsible for handling controller related logic.
 */


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mechconnect.backend.dto.LoginRequestDto;
import com.mechconnect.backend.dto.ServiceRequestCreateDto;
import com.mechconnect.backend.dto.PasswordResetRequestDto;
import com.mechconnect.backend.dto.CustomerOrderDto;
import com.mechconnect.backend.dto.CustomerRegistrationRequest;
import com.mechconnect.backend.entity.Customer;
import com.mechconnect.backend.entity.ServiceRequest;
import com.mechconnect.backend.service.CustomerService;
import com.mechconnect.backend.service.ServiceRequestService;
import com.mechconnect.backend.service.MechanicService;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping(value="/api")
public class CustomerController {
	
	

	
	@Autowired
	CustomerService customerService;
	public String CustomerNameFromProp;
	
	@Autowired
	MechanicService mechanicService;
	@Autowired
	ServiceRequestService mechanicRequestService;
	
	
	
	@Value
	("${test.Customer.name}")
	
	@PostConstruct
	public void postConstruct() {
		System.out.println("In Post Constructor");
		
	}
		
//	Register Customer
	@CrossOrigin
	//@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value="/saveCustomer",consumes=MediaType.APPLICATION_JSON_VALUE)
	 public String saveCustomer(@RequestBody CustomerRegistrationRequest registerCustomer) {
		System.out.println("Controller Started");
		 System.out.println("In Save User");
		 
		return customerService.SaveCustomer(registerCustomer);
	}
		
	
	
//	Customer Log In
	@CrossOrigin
	@PostMapping(value = "/Customerlogin", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {

	    Customer customer = customerService.loginCustomer(loginRequest);

	    if (customer == null) {
	        return ResponseEntity.status(401).body("Invalid email or password");
	    }

	    return ResponseEntity.ok(customer);
	}

	
			
		// 1) GET Customer PROFILE 
		@CrossOrigin
		@GetMapping("/customer/profile/{customerId}")
	    public ResponseEntity<?> getCustomerProfile(@PathVariable Long customerId) {
	        Customer customer = customerService.getCustomerProfile(customerId);
	        if (customer == null) return ResponseEntity.status(404).body("Customer not found");
	        return ResponseEntity.ok(customer);
	    }

	    // 2) UPDATE CUSTOMER PROFILE
		@CrossOrigin
		@PutMapping("/customer/profile/update") // PUT method
		public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
		    // Map frontend `id` to `customerId` if necessary
		    if (customer.getCustomerId() == null && customer.getCustomerId() != null) {
		        customer.setCustomerId((Long) customer.getCustomerId());
		    }

		    if (customer.getCustomerId() == null) {
		        return ResponseEntity.badRequest().body("Customer ID is missing!");
		    }

		    Customer updated = customerService.updateCustomer(customer);
		    if (updated == null) {
		        return ResponseEntity.status(404).body("Customer not found");
		    }
		    return ResponseEntity.ok(updated);
		}


	    // 3) CHANGE Customer PASSWORD (From Profile)
		@CrossOrigin
		@PutMapping("/customer/change-password")
		public ResponseEntity<?> changePassword(@RequestBody PasswordResetRequestDto req) {

		    System.out.println("DEBUG customerId = " + req.getCustomerId());
		    System.out.println("DEBUG oldPassword = " + req.getOldPassword());

		    if (req.getCustomerId() == null) {
		        return ResponseEntity.badRequest().body("Customer ID missing");
		    }

		    boolean success = customerService.changePassword(
		            req.getCustomerId(),
		            req.getOldPassword(),
		            req.getNewPassword()
		    );

		    if (!success) {
		        return ResponseEntity.status(400).body("Incorrect current password");
		    }

		    return ResponseEntity.ok("Password updated successfully");
		}
		
		
//     for Delelete cusstomer profile (Not yet Used)
       @DeleteMapping("/deleteCustomer/{customerId}")
    	   public String deleteCustomer(@PathVariable Long customerId,@PathVariable String name,@RequestParam String email,@RequestParam String Password) {
    	   System.out.println("In Delet API "+customerId+" "+name+" "+email+" "+Password+"");
    	   return customerService.deleteCustomer(customerId);
       }
       
       
       
   
    
    
    

// 		Get Nearby Mechanics Based On Location & Specialization    
//       Get Near by Mechanics	
       @CrossOrigin
       @GetMapping("/mechanics/nearby")
       public ResponseEntity<?> getNearbyMechanics(
               @RequestParam String serviceLocation,
               @RequestParam String serviceType
       ) {
           return ResponseEntity.ok(
               mechanicService.findNearbyMechanics(serviceLocation, serviceType)
           );
       }

       




       // 1️⃣ FIND USER & SEND OTP
       @PostMapping("/customers/forgot-password/find-user")
       public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {

           String email = request.get("email");

           if (email == null || email.trim().isEmpty()) {
               return ResponseEntity.badRequest().body("Email is required");
           }

           boolean sent = customerService.sendOtpForForgotPassword(email);

           if (!sent) {
               return ResponseEntity.status(404).body("Email not registered");
           }

           return ResponseEntity.ok("OTP sent successfully");
       }

       // 2️⃣ VERIFY OTP
       @PostMapping("/customers/forgot-password/verify-otp")
       public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {

           String email = request.get("email");
           String otp = request.get("otp");

           if (email == null || email.trim().isEmpty()
                   || otp == null || otp.trim().isEmpty()) {
               return ResponseEntity.badRequest().body("Email and OTP are required");
           }

           String result = customerService.verifyOtp(email, otp);

           if (!result.equals("SUCCESS")) {
               return ResponseEntity.badRequest().body(result);
           }

           return ResponseEntity.ok("OTP verified successfully");
       }

       // 3️⃣ RESET PASSWORD
       @PostMapping("/customers/forgot-password/reset-password")
       public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {

           String email = request.get("email");
           String newPassword = request.get("newPassword");

           if (email == null || email.trim().isEmpty()
                   || newPassword == null || newPassword.trim().isEmpty()) {
               return ResponseEntity.badRequest().body("Email and new password are required");
           }

           String result = customerService.resetPassword(email, newPassword);

           if (!result.equals("SUCCESS")) {
               return ResponseEntity.badRequest().body(result);
           }

           return ResponseEntity.ok("Password reset successful");
       }




//send service request api
		@CrossOrigin
		@PostMapping(value="/sendRequest" ,consumes = MediaType.APPLICATION_JSON_VALUE)
		public String sendRequest(@RequestBody ServiceRequestCreateDto dto) {
		    return mechanicRequestService.sendRequest(dto);
		}


		
//  Get Orders 
//		@GetMapping("customer/orders")
//		public ResponseEntity<List<CustomerOrderDto>> getCustomerOrders(
//		        @RequestParam Long customerId) {
//
//		    return ResponseEntity.ok(
//		            customerService.getOrdersForCustomer(customerId)
//		    );
//		}

	       @CrossOrigin
		@GetMapping("/customer/orders")
		public ResponseEntity<List<CustomerOrderDto>> getOrders(
		        @RequestParam Long customerId) {

		    return ResponseEntity.ok(
		        customerService.getOrdersForCustomer(customerId)
		    );
		}

		
}
