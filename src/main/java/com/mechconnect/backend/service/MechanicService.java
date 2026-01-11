package com.mechconnect.backend.service;

/**
 * MechanicService
 *
 * Part of the MechConnect backend application.
 * Responsible for handling service related logic.
 */


import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;


import com.mechconnect.backend.dto.LoginRequestDto;
import com.mechconnect.backend.dto.MechanicOrderDto;
import com.mechconnect.backend.dto.MechanicProfileUpdateRequestDto;
import com.mechconnect.backend.dto.MechanicRegistrationRequest;
import com.mechconnect.backend.dto.NearbyMechanicCardResponseDto;
import com.mechconnect.backend.entity.Mechanic;

public interface MechanicService {

	String saveMechanic(MechanicRegistrationRequest registerMechanic);

	
	
	ResponseEntity<?> mechanicLogin(LoginRequestDto loginRequest);		
	
	

	 ResponseEntity<?> getMechanicProfile(Long id);
	
	
	Mechanic updateMechanicProfile(MechanicProfileUpdateRequestDto request);

	  boolean changePassword(Long mechanicId, String oldPassword, String newPassword);

	  ResponseEntity<?> findMechanicForForgotPassword(Map<String, String> request);
	  
	  ResponseEntity<?> verifyOtpForForgotPassword(Map<String, String> request);
	  
	  ResponseEntity<?> resetForgotPassword(Map<String, String> request);
	  
	  String deleteMechanic(Long id);
	  
	  

	  List<NearbyMechanicCardResponseDto> findNearbyMechanics(String serviceLocation, String serviceType);

		 
	  void rejectRequest(Long requestId);

	

	  List<MechanicOrderDto> getOrdersForMechanic(Long mechanicId);

	    boolean markOrderCompleted(Long orderId, Long mechanicId);
   

	

 

	
	

	

}
