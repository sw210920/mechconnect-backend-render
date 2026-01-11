package com.mechconnect.backend.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * MechanicServiceImpl
 *
 * Part of the MechConnect backend application.
 * Responsible for handling impl related logic.
 */



import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mechconnect.backend.dto.LoginRequestDto;
import com.mechconnect.backend.dto.MechanicLoginResponseDto;
import com.mechconnect.backend.dto.MechanicOrderDto;
import com.mechconnect.backend.dto.MechanicProfileResponseDto;
import com.mechconnect.backend.dto.MechanicProfileUpdateRequestDto;
import com.mechconnect.backend.dto.MechanicRegistrationRequest;
import com.mechconnect.backend.dto.NearbyMechanicCardResponseDto;
import com.mechconnect.backend.entity.Customer;
import com.mechconnect.backend.entity.Mechanic;
import com.mechconnect.backend.entity.Orders;
import com.mechconnect.backend.entity.ServiceRequest;
import com.mechconnect.backend.entity.enums.OrderStatus;
import com.mechconnect.backend.entity.enums.RequestStatus;
import com.mechconnect.backend.entity.enums.ServiceMode;
import com.mechconnect.backend.entity.enums.ServiceType;
import com.mechconnect.backend.repository.MechanicRepository;
import com.mechconnect.backend.repository.OrderRepository;
import com.mechconnect.backend.repository.ServiceRequestRepository;
import com.mechconnect.backend.service.EmailService;
import com.mechconnect.backend.service.MechanicService;

import jakarta.transaction.Transactional;

@Service
public class MechanicServiceImpl implements MechanicService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	
	 private final ServiceRequestRepository serviceRequestRepository;
	
	@Autowired
	MechanicRepository mechanicRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	
	@Autowired
	private EmailService emailService;

	
	static Long orderNumber1=1L;
	
	   public MechanicServiceImpl(
	            ServiceRequestRepository serviceRequestRepository,
	            MechanicRepository mechanicRepository,
	            OrderRepository orderRepository,
	            BCryptPasswordEncoder passwordEncoder
	    ) {
	        this.serviceRequestRepository = serviceRequestRepository;
	        this.mechanicRepository = mechanicRepository;
	        this.orderRepository = orderRepository;
	        this.passwordEncoder = passwordEncoder;
	    }
	
//	Register Mechanic
	@Override
	public String saveMechanic(MechanicRegistrationRequest registerMechanic) {
		// TODO Auto-generated method stub
		if(registerMechanic!=null) {
			Mechanic mechanic=new Mechanic();
			
			 ServiceType specialization = mapServiceType(registerMechanic.getSpecialization());

			
			 mechanic.setFirstName(registerMechanic.getFirstName());
			 mechanic.setLastName(registerMechanic.getLastName());
			 mechanic.setEmail(registerMechanic.getEmail());
			// mechanic.setPassword(registerMechanic.getpassword());
			 mechanic.setPassword(
			            passwordEncoder.encode(registerMechanic.getpassword()) );
			 mechanic.setMobailNumber(registerMechanic.getMobailNumber());
			 mechanic.setServiceLocation(registerMechanic.getServiceLocation());
			 mechanic.setAddress(registerMechanic.getAddress());
			 mechanic.setYearsOfExperience(registerMechanic.getYearsOfExperience());
			 mechanic.setSpecialization(specialization);
			
			 mechanic.setCertifications(registerMechanic.getCertifications());
			 mechanic.setBio(registerMechanic.getBio());
			 mechanicRepository.save(mechanic);
		    
		    return "User updated";
		}
		
		return "User not update";
		
	}

//	Mechanic Log in
	@Override
	public ResponseEntity<?> mechanicLogin(LoginRequestDto loginRequest) {

	    String email = loginRequest.getEmail().trim();
	    String rawPassword = loginRequest.getpassword();

	    Mechanic mechanic = mechanicRepository.findByEmail(email);

	    if (mechanic == null) {
	        return ResponseEntity.status(404).body("User not found");
	    }

	    if (!passwordEncoder.matches(rawPassword, mechanic.getPassword())) {
	        return ResponseEntity.status(401).body("Invalid password");
	    }

	    MechanicLoginResponseDto res = new MechanicLoginResponseDto();
	    res.setMechanicId(mechanic.getMechanicId());
	    res.setFirstName(mechanic.getFirstName());
	    res.setLastName(mechanic.getLastName());
	    res.setEmail(mechanic.getEmail());
	    res.setRole("mechanic");

	    return ResponseEntity.ok(res);
	}

	
	
//	Get Mechanic Profile
	@Override
	public ResponseEntity<?> getMechanicProfile(Long id) {

	    Mechanic mechanic = mechanicRepository.findById(id).orElse(null);

	    if (mechanic == null) {
	        return ResponseEntity.notFound().build();
	    }

	    MechanicProfileResponseDto res = new MechanicProfileResponseDto();
	    res.setMechanicId(mechanic.getMechanicId());
	    res.setFirstName(mechanic.getFirstName());
	    res.setLastName(mechanic.getLastName());
	    res.setEmail(mechanic.getEmail());
	    res.setMobailNumber(mechanic.getMobailNumber());
	    res.setAddress(mechanic.getAddress());
	    res.setSpecialization(mechanic.getSpecialization());
	    res.setServiceLocation(mechanic.getServiceLocation());	
	    res.setYearsOfExperience(mechanic.getYearsOfExperience());

	    res.setCertifications(mechanic.getCertifications());
	    res.setBio(mechanic.getBio());

	    return ResponseEntity.ok(res);
	}


	
//   Update Mechanic Profile
	@Override
	 public Mechanic updateMechanicProfile(MechanicProfileUpdateRequestDto req) {
		 Mechanic mechanic = mechanicRepository.findById(req.getMechanicId())
		            .orElseThrow(() -> new RuntimeException("Mechanic not found"));

		    mechanic.setFirstName(req.getFirstName());
		    mechanic.setLastName(req.getLastName());
		    mechanic.setMobailNumber(req.getMobailNumber());
		   
		    mechanic.setServiceLocation(req.getServiceLocation());
		    mechanic.setAddress(req.getAddress());
		    
		    mechanic.setYearsOfExperience(req.getYearsOfExperience());
		    mechanic.setCertifications(req.getCertifications());
		    mechanic.setBio(req.getBio());

		    // 🔥 String → Enum conversion here
		    mechanic.setSpecialization(mapServiceType(req.getSpecialization()));


		    return mechanicRepository.save(mechanic);
		}

//	Change Mechanic Password (Profile)
	@Override
	public boolean changePassword(Long mechanicId, String oldPassword, String newPassword) {
		Mechanic mechanic = mechanicRepository.findById(mechanicId).orElse(null);

        if (mechanic == null) return false;

        if (!passwordEncoder.matches(oldPassword, mechanic.getPassword())) {
            return false;
        }

        mechanic.setPassword(passwordEncoder.encode(newPassword));
        mechanicRepository.save(mechanic);

        return true;
    }
	
	
	
//	Find Mechanic To Forgot  Password & Send OTP
	@Override
	public ResponseEntity<?> findMechanicForForgotPassword(Map<String, String> request) {

	    String email = request.get("email");

	    if (email == null || email.trim().isEmpty()) {
	        return ResponseEntity.badRequest().body("Email is required");
	    }

	    email = email.trim().toLowerCase();

	    System.out.println("Searching email: [" + email + "]");

	    Mechanic mechanic = mechanicRepository.findByEmail(email);

	    if (mechanic == null) {
	        return ResponseEntity.status(404).body("Email not registered");
	    }

	    String otp = String.valueOf(new Random().nextInt(900000) + 100000);

	    mechanic.setOtp(otp);
	    mechanic.setOtpExpiry(LocalDateTime.now().plusMinutes(5));

	    mechanicRepository.save(mechanic);

	    emailService.sendOtpEmail(mechanic.getEmail(), otp);
	    return ResponseEntity.ok("OTP sent successfully");
	}

	
//	Varify OTP To Forgot Password
	@Override
	public ResponseEntity<?> verifyOtpForForgotPassword(Map<String, String> request) {

	    String email = request.get("email");
	    String otp = request.get("otp");

	    if (email == null || email.trim().isEmpty()
	            || otp == null || otp.trim().isEmpty()) {
	        return ResponseEntity.badRequest().body("Email and OTP are required");
	    }

	    email = email.trim().toLowerCase();
	    otp = otp.trim();

	    Mechanic mechanic = mechanicRepository.findByEmail(email);

	    if (mechanic == null) {
	        return ResponseEntity.status(404).body("Email not registered");
	    }

	    // 🔐 Check OTP
	    if (!otp.equals(mechanic.getOtp())) {
	        return ResponseEntity.status(400).body("Invalid OTP");
	    }

	    // ⏰ Check expiry
	    if (mechanic.getOtpExpiry().isBefore(LocalDateTime.now())) {
	        return ResponseEntity.status(400).body("OTP expired");
	    }

	    return ResponseEntity.ok("OTP verified successfully");
	}

			
// Reset The Mechanic Password	
	@Override
	public ResponseEntity<?> resetForgotPassword(Map<String, String> request) {

	    String email = request.get("email");
	    String newPassword = request.get("newPassword");

	    // ✅ Validation
	    if (email == null || email.trim().isEmpty()
	            || newPassword == null || newPassword.trim().isEmpty()) {
	        return ResponseEntity.badRequest().body("Email and new password are required");
	    }

	    email = email.trim().toLowerCase();
	    newPassword = newPassword.trim();

	    Mechanic mechanic = mechanicRepository.findByEmail(email);

	    if (mechanic == null) {
	        return ResponseEntity.status(404).body("Email not registered");
	    }

	    // 🔐 Ensure OTP exists (means it was generated & verified)
	    if (mechanic.getOtp() == null || mechanic.getOtpExpiry() == null) {
	        return ResponseEntity.status(400).body("OTP verification required");
	    }

	    // ⏰ Check OTP expiry again for safety
	    if (mechanic.getOtpExpiry().isBefore(LocalDateTime.now())) {
	        return ResponseEntity.status(400).body("OTP expired");
	    }

	    // 🔐 Encrypt & update password
	    mechanic.setPassword(passwordEncoder.encode(newPassword));

	    // 🧹 Clear OTP after successful reset
	    mechanic.setOtp(null);
	    mechanic.setOtpExpiry(null);

	    mechanicRepository.save(mechanic);

	    return ResponseEntity.ok("Password reset successful");
	}

	
//	Delete Mechanic Profile (Yet To Used)
	@Override
	public String deleteMechanic(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
// 	Find Nearby Mechanics For Service Request
	@Override
	public List<NearbyMechanicCardResponseDto> findNearbyMechanics(
	        String location,
	        String serviceType
	) {
	    ServiceType type = mapServiceType(serviceType);

	    List<Mechanic> mechanics;

	    if (type == ServiceType.BIKE) {
	        mechanics = mechanicRepository
	            .findByServiceLocationIgnoreCaseAndSpecializationIn(
	                location,
	                List.of(ServiceType.BIKE, ServiceType.BOTH)
	            );
	    } else if (type == ServiceType.CAR) {
	        mechanics = mechanicRepository
	            .findByServiceLocationIgnoreCaseAndSpecializationIn(
	                location,
	                List.of(ServiceType.CAR, ServiceType.BOTH)
	            );
	    } else {
	        mechanics = mechanicRepository
	            .findByServiceLocationIgnoreCaseAndSpecialization(
	                location,
	                ServiceType.BOTH
	            );
	    }

	    return mechanics.stream().map(m -> {
	        NearbyMechanicCardResponseDto dto = new NearbyMechanicCardResponseDto();
	        dto.setMechanicId(m.getMechanicId());
	        dto.setFirstName(m.getFirstName());
	        dto.setLastName(m.getLastName());
	        dto.setSpecialization(m.getSpecialization());
	        dto.setServiceLocation(m.getServiceLocation());
	        dto.setYearsOfExperience(m.getYearsOfExperience());
	        return dto;
	    }).toList();
	}



	
	


	




	


	
	



//	   For Used To Convert ENUM To String To Communicate Between Frontend & Backend.(IMP)
	  private ServiceType mapServiceType(String input) {
		    return switch (input.trim().toLowerCase()) {
		        case "bike" -> ServiceType.BIKE;
		        case "car" -> ServiceType.CAR;
		        case "both" -> ServiceType.BOTH;
		        default -> throw new IllegalArgumentException("Invalid specialization: " + input);
		    };
		}

	  
	  
	  
	  
//	  Reject Service Request And store with status "REJECTED"
	  @Override
	  @Transactional
	  public void rejectRequest(Long requestId) {

	      ServiceRequest request = serviceRequestRepository
	              .findById(requestId)
	              .orElseThrow(() -> new RuntimeException("Service request not found"));

	      Orders order = new Orders();

	      // 🔗 Relations
	      order.setCustomer(request.getCustomer());
	      order.setMechanic(request.getMechanic());

	      // 📦 Copy request data
	      order.setServiceType(request.getServiceType());
	      order.setServiceDate(request.getServiceDate());
	      order.setServiceTime(request.getTime());
	      order.setPackageName(request.getPackageName());

	      order.setVehicleMake(request.getMake());
	      order.setVehicleModel(request.getModel());
	      order.setVehicleYear(request.getVehicleYear());
	      order.setVehicleRegistrationNumber(request.getRegistrationNumber());

	      // 🔥 KEY DIFFERENCE
	      order.setStatus(OrderStatus.REJECTED);

	      order.setOrderNumber("ORD-REJ-" + System.currentTimeMillis());
	      order.setCreatedAt(LocalDateTime.now());

	      // 💾 Save rejected order
	      orderRepository.save(order);

	      // ❌ Remove pending request
	      serviceRequestRepository.delete(request);
	  }
	  
	  
	  
// to fetch orders on mechanic profile
	  @Override
	    public List<MechanicOrderDto> getOrdersForMechanic(Long mechanicId) {

	        List<RequestStatus> allowedStatuses =
	                List.of(RequestStatus.ACCEPTED, RequestStatus.COMPLETED);

	        List<Orders> orders =
	                orderRepository.findByMechanic_MechanicIdAndStatusInOrderByCreatedAtDesc(
	                        mechanicId,
	                        allowedStatuses
	                );

	        List<MechanicOrderDto> result = new ArrayList<>();

	        for (Orders order : orders) {

	            MechanicOrderDto dto = new MechanicOrderDto();

	            dto.setOrderId(order.getOrderId());
	            dto.setOrderNumber(order.getOrderNumber());
	            dto.setStatus(order.getStatus().name());

	            // Customer info
	            Customer customer = order.getCustomer();
	            dto.setCustomerId(customer.getCustomerId());
	            dto.setCustomerName(
	                    customer.getFirstName() + " " + customer.getLastName()
	            );

	            // Order details
	            dto.setServiceType(order.getServiceType());
	            dto.setPackageName(order.getPackageName());
	            dto.setServiceMode(order.getServiceMode());
//	            dto.setVehicle(order.getVehicleMake());
//	            dto.setVehicleModel(order.getVehicleModel());
	            dto.setServiceDate(order.getServiceDate());
	            dto.setServiceTime(order.getServiceTime());

	            dto.setVehicle(
	                    order.getVehicleMake() + " " + order.getVehicleModel()
	            );
	            dto.setRegistrationNumber(order.getVehicleRegistrationNumber());

	            // ✅ BUSINESS RULE
	            if (order.getServiceMode() == ServiceMode.DOORSTEP) {
	                dto.setCustomerAddress(order.getCustomerAddress());
	            }


	            result.add(dto);
	        }

	        return result;
	    }

	    // ==============================
	    // MARK ORDER AS COMPLETED
	    // ==============================
	    @Override
	    public boolean markOrderCompleted(Long orderId, Long mechanicId) {

	        Orders order = orderRepository.findById(orderId)
	                .orElseThrow(() -> new RuntimeException("Order not found"));

	        // ✅ Security check
	        if (!order.getMechanic().getMechanicId().equals(mechanicId)) {
	            throw new RuntimeException("Unauthorized");
	        }

	        // ✅ Only ACCEPTED → COMPLETED
	        if (order.getStatus() != OrderStatus.ACCEPTED) {
	            return false;
	        }

	        order.setStatus(OrderStatus.COMPLETED);
	        orderRepository.save(order);


	        return true;
	    }
	}

	 









