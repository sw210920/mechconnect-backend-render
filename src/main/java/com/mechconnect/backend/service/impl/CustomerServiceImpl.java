package com.mechconnect.backend.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * CustomerServiceImpl
 *
 * Part of the MechConnect backend application.
 * Responsible for handling impl related logic.
 */

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mechconnect.backend.dto.CustomerOrderDto;
import com.mechconnect.backend.dto.CustomerRegistrationRequest;
import com.mechconnect.backend.dto.LoginRequestDto;
import com.mechconnect.backend.entity.Customer;
import com.mechconnect.backend.entity.Mechanic;
import com.mechconnect.backend.entity.Orders;
import com.mechconnect.backend.entity.ServiceRequest;
import com.mechconnect.backend.entity.enums.OrderStatus;
import com.mechconnect.backend.entity.enums.RequestStatus;
import com.mechconnect.backend.entity.enums.ServiceMode;
import com.mechconnect.backend.repository.CustomerRepository;
import com.mechconnect.backend.repository.MechanicRepository;
import com.mechconnect.backend.repository.OrderRepository;
import com.mechconnect.backend.repository.ServiceRequestRepository;
import com.mechconnect.backend.service.CustomerService;
import com.mechconnect.backend.service.EmailService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	private MechanicRepository mechanicRepository;

	
	  @Autowired
	    private ServiceRequestRepository serviceRequestRepository;
	
	  
	  @Autowired
	  private EmailService emailService;

	  
	static Long orderNumber=1L;

	
	
//	Register Customer
	@Override
	public String SaveCustomer(CustomerRegistrationRequest registerCustomer) {
		if(registerCustomer!=null) {
			Customer customer=new Customer();
			 customer.setFirstName(registerCustomer.getFirstName());
			 customer.setLastName(registerCustomer.getLastName());
			 customer.setEmail(registerCustomer.getEmail());
			 //customer.setPassword(registerCustomer.getpassword());
			 
			 customer.setPassword(
				        passwordEncoder.encode(registerCustomer.getpassword())
				    );
			 customer.setMobailNumber(registerCustomer.getMobailNumber());
			 customer.setAddress(registerCustomer.getAddress());
			 
			 customerRepository.save(customer);
		    
		    return "User updated";
		}
		
		return "User not update";
		
	}
	
	
//	Log In Customer
	 @Override
	    public Customer loginCustomer(LoginRequestDto loginRequest) {

	        String cleanEmail = loginRequest.getEmail().trim();
	        String rawPassword = loginRequest.getpassword();

	        Customer customer = customerRepository.findByEmail(cleanEmail);

	        if (customer == null) {
	            return null;
	        }

	        if (!passwordEncoder.matches(rawPassword, customer.getPassword())) {
	            return null;
	        }

	        return customer;
	    }
//     Method Used For  LogIn
	    @Override
	    public Customer findByEmail(String email) {
	        return customerRepository.findByEmail(email);
	    }
	
	    
	    
//	 	Fetch Customer Profile
		@Override
		public Customer getCustomerProfile(Long id) {
			return customerRepository.findById(id).orElse(null);
		}
	
	    
//		Update Customer Profile
		@Override
		public Customer updateCustomer(Customer updatedCustomer) {

		    if (updatedCustomer.getCustomerId() == null) {
		        throw new IllegalArgumentException("Customer ID cannot be null");
		    }

		    return customerRepository.findById(updatedCustomer.getCustomerId())
		            .map(existing -> {
		                existing.setFirstName(updatedCustomer.getFirstName());
		                existing.setLastName(updatedCustomer.getLastName());
		                existing.setEmail(updatedCustomer.getEmail());
		                existing.setMobailNumber(updatedCustomer.getMobailNumber());
		                existing.setAddress(updatedCustomer.getAddress());
		                return customerRepository.save(existing);
		            })
		            .orElse(null);
		}
//		Used
		@Override
	    public Customer findById(Long id) {
	        return customerRepository.findById(id).orElse(null);
	    }
	
//		Change password of cusomer ACC From Profile
		@Override
		public boolean changePassword(Long customerId, String oldPassword, String newPassword) {

		    if (customerId == null) {
		        throw new IllegalArgumentException("Customer ID is null");
		    }

		    Customer customer = customerRepository.findById(customerId)
		            .orElseThrow(() -> new RuntimeException("Customer not found"));

		    if (!passwordEncoder.matches(oldPassword, customer.getPassword())) {
		        return false;
		    }

		    customer.setPassword(passwordEncoder.encode(newPassword));
		    customerRepository.save(customer);
		    return true;
		}

		
		
		    // 1️⃣ SEND OTP
		    @Override
		    public boolean sendOtpForForgotPassword(String email) {

		        email = email.trim().toLowerCase();
		        System.out.println("Searching email: [" + email + "]");

		        Customer customer = customerRepository.findByEmail(email);

		        if (customer == null) {
		            return false;
		        }

		        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

		        customer.setOtp(otp);
		        customer.setOtpExpiry(LocalDateTime.now().plusMinutes(5));

		        customerRepository.save(customer);
		        
		        emailService.sendOtpEmail(customer.getEmail(), otp);
		        return true;
		    }

		    // 2️⃣ VERIFY OTP
		    @Override
		    public String verifyOtp(String email, String otp) {

		        email = email.trim().toLowerCase();
		        otp = otp.trim();

		        Customer customer = customerRepository.findByEmail(email);

		        if (customer == null) {
		            return "Email not registered";
		        }

		        if (!otp.equals(customer.getOtp())) {
		            return "Invalid OTP";
		        }

		        if (customer.getOtpExpiry().isBefore(LocalDateTime.now())) {
		            return "OTP expired";
		        }

		        return "SUCCESS";
		    }

		    // 3️⃣ RESET PASSWORD
		    @Override
		    public String resetPassword(String email, String newPassword) {

		        email = email.trim().toLowerCase();
		        newPassword = newPassword.trim();

		        Customer customer = customerRepository.findByEmail(email);

		        if (customer == null) {
		            return "Email not registered";
		        }

		        if (customer.getOtp() == null || customer.getOtpExpiry() == null) {
		            return "OTP verification required";
		        }

		        if (customer.getOtpExpiry().isBefore(LocalDateTime.now())) {
		            return "OTP expired";
		        }

		        customer.setPassword(passwordEncoder.encode(newPassword));
		        customer.setOtp(null);
		        customer.setOtpExpiry(null);

		        customerRepository.save(customer);

		        return "SUCCESS";
		    }
		
		
//	Delete Customer Acc	
			@Override
			public String deleteCustomer(Long id) {
				if(id!=null) {
				Optional<Customer> customerOptional=customerRepository.findById(id);
				if(customerOptional.isPresent()) {
					customerRepository.delete(customerOptional.get());
					return "User Deleted";
				}
			}
			return "User Not Deleted";
		}

	
//	Fetch Orders At Customer Log In	
			@Override
			public List<CustomerOrderDto> getOrdersForCustomer(Long customerId) {

			    List<CustomerOrderDto> result = new ArrayList<>();

			    /* =============================
			       1️⃣ Pending Requests
			    ============================== */
			    List<ServiceRequest> pendingRequests =
			        serviceRequestRepository
			            .findByCustomer_CustomerIdAndStatusOrderByCreatedAtDesc(
			                customerId,
			                RequestStatus.PENDING
			            );

			    for (ServiceRequest req : pendingRequests) {

			        CustomerOrderDto dto = new CustomerOrderDto();
			        
			        dto.setOrderNumber("REQ-" + req.getRequestId());
			        dto.setServiceType(req.getServiceType());
			        dto.setPackageName(req.getPackageName());
			        dto.setServiceMode(req.getServiceMode());
			        dto.setServiceDate(req.getServiceDate());
			        dto.setServiceTime(req.getTime());
			        dto.setVehicleMake(req.getMake());
			        dto.setVehicleModel(req.getModel());
			        dto.setVehicleRegistrationNumber(req.getRegistrationNumber());
			        dto.setStatus("PENDING");
			        // ✅ Requested mechanic (NOT assigned yet)
			        Mechanic requested = req.getRequestedMechanic();
			        if (requested != null) {
			            dto.setMechanicId(requested.getMechanicId());
			            dto.setMechanicName(
			                requested.getFirstName() + " " + requested.getLastName()
			            );
			        } else {
			            dto.setMechanicName("Waiting for mechanic");
			        }

			        result.add(dto);
			    }
			    /* =============================
			       2️⃣ Orders (Accepted / Rejected / Completed)
			    ============================== */
			    List<Orders> orders =
			        orderRepository.findOrdersWithMechanic(customerId);

			    for (Orders order : orders) {

			        CustomerOrderDto dto = new CustomerOrderDto();

			        dto.setOrderNumber(order.getOrderNumber());
			        dto.setServiceType(order.getServiceType());
			        dto.setPackageName(order.getPackageName());
			        dto.setServiceMode(order.getServiceMode());
			        dto.setServiceDate(order.getServiceDate());
			        dto.setVehicleRegistrationNumber(order.getVehicleRegistrationNumber());
			        dto.setServiceTime(order.getServiceTime());
			        dto.setVehicleMake(order.getVehicleMake());
			        dto.setVehicleModel(order.getVehicleModel());
			        dto.setStatus(order.getStatus().name());

			        Mechanic mechanic = order.getMechanic();

			        if (mechanic != null) {
			            dto.setMechanicId(mechanic.getMechanicId());
			            dto.setMechanicName(
			                mechanic.getFirstName() + " " + mechanic.getLastName()
			            );

			            // ✅ BUSINESS RULE: show address only for GARAGE + Accepted/Completed
			            if (
			            	    order.getServiceMode() == ServiceMode.GARAGE &&
			            	    (order.getStatus() == OrderStatus.ACCEPTED
			            	     || order.getStatus() == OrderStatus.COMPLETED)
			            	) {
			            	    dto.setMechanicAddress(mechanic.getAddress());
			            	}
			        }

			        result.add(dto);
			    }

			    return result;
			}

	
	
//	Extra
	@Override
	public List<ServiceRequest> findByCustomer_CustomerIdAndStatus(Long customerId, RequestStatus status) {
		// TODO Auto-generated method stub
		return null;
	}


}
