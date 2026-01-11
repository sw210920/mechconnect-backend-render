package com.mechconnect.backend.controller;

import java.util.List;

/**
 * MechanicController
 *
 * Part of the MechConnect backend application.
 * Responsible for handling controller related logic.
 */



																																																																																																														

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
import com.mechconnect.backend.dto.MechanicOrderDto;
import com.mechconnect.backend.dto.MechanicProfileUpdateRequestDto;
import com.mechconnect.backend.dto.PasswordResetRequestDto;
import com.mechconnect.backend.dto.MechanicRegistrationRequest;
import com.mechconnect.backend.service.MechanicService;


import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping(value="/api")
public class MechanicController {
	
	
	
	
	  
	@Autowired
	MechanicService mechanicService;
	
	public String MechanicNameFromProp;
	


	
	@Value
	("${test.Mechanic.name}")
	
	@PostConstruct
	public void postConstruct() {
		System.out.println("In Post Constructor");
		
	}
	
	//localhost:6060/api/saveUser
		@CrossOrigin
	//localhost:6060/api/saveUser
	@PostMapping(value="/saveMechanic",consumes=MediaType.APPLICATION_JSON_VALUE)
	 public String saveCustomer(@RequestBody MechanicRegistrationRequest registerMechanic) {
//		System.out.println("Controller Started");
//		 System.out.println("In Save Mechanic");
//		 System.out.println("ServiceLocation = " + registerMechanic.getServiceLocation());
//		 System.out.println("mechanic specialization =" + registerMechanic.getSpecialization());
		 
		return mechanicService.saveMechanic(registerMechanic);
		 
	 }
		
		
		
		
		@CrossOrigin(origins = "http://127.0.0.1:5500")
		@PostMapping("/Mechaniclogin")
		public ResponseEntity<?> mechanicLogin(@RequestBody LoginRequestDto loginRequest) {
		    return mechanicService.mechanicLogin(loginRequest);
//		    System.out.println("==== LOGIN DEBUG ====");
//		    System.out.println("Email entered     : " + email);
//		    System.out.println("Password entered  : " + rawPassword);
//		    System.out.println("Password in DB    : " + mechanic.getPassword());
//		    System.out.println("Is password match : " +
//		    passwordEncoder.matches(rawPassword, mechanic.getPassword())	    );
		}

		
		
	
		
//		Get Mechanic Profile
		@GetMapping("/mechanic/{id}/profile")
		public ResponseEntity<?> getMechanicProfile(@PathVariable Long id) {
		    return mechanicService.getMechanicProfile(id);
		}

			

//		 Update Mechanic Profile
	       @CrossOrigin
	       @PutMapping("/mechanic/update")
	       public ResponseEntity<?> updateMechanic(
	               @RequestBody MechanicProfileUpdateRequestDto req
	       ) {
	           mechanicService.updateMechanicProfile(req);
	           return ResponseEntity.ok("Profile updated");
	       }

	     

		// 3️⃣ CHANGE MECHANIC PASSWORD(From Profile)

		@CrossOrigin(origins = "http://127.0.0.1:5500") // allow your frontend
		 	    @PutMapping("/mechanic/change-password")
	    public ResponseEntity<?> changePassword(@RequestBody PasswordResetRequestDto req) {

	        boolean success = mechanicService.changePassword(
	                req.getMechanicId(),
	                req.getOldPassword(),
	                req.getNewPassword()
	        );

	        if (!success) {
	            return ResponseEntity.status(400)
	                    .body("Current password incorrect or user not found");
	        }

	        return ResponseEntity.ok("Password updated successfully");
	    }
	

//      Find user to forgot password 
       @CrossOrigin
       @PostMapping("/mechanic/forgot-password/find-user")
       public ResponseEntity<?> forgotPassword1(@RequestBody Map<String, String> request) {
           return mechanicService.findMechanicForForgotPassword(request);
       }

       


//		Varify OTP	
       @CrossOrigin
       @PostMapping("/mechanic/forgot-password/verify-otp")
       public ResponseEntity<?> verifyOtp1(@RequestBody Map<String, String> request) {
           return mechanicService.verifyOtpForForgotPassword(request);
       }



//		RESET PASSWORD

       @CrossOrigin
       @PostMapping("/mechanic/forgot-password/reset-password")
       public ResponseEntity<?> resetPassword1(@RequestBody Map<String, String> request) {
           return mechanicService.resetForgotPassword(request);
       }

       
		
		
       
// 		For Deleting Mechanic PRofile  (Not Yet Used)
       @DeleteMapping("/deleteMechanic/{mechanicId}/{name}")
    	   public String deleteMechanic(@PathVariable Long mechanicId,@PathVariable String name,@RequestParam String email,@RequestParam String Password ,@RequestParam String MobailNumber ,@RequestParam String Bio ,@RequestParam String Certifications ,@RequestParam String YearsOfExperience ,@RequestParam String Specializations ,@RequestParam String ServiceLocation ) {
    	   System.out.println("In Delet API "+mechanicId+" "+name+" "+email+" "+Password+" "+Bio+" "+Certifications+""+MobailNumber+""+YearsOfExperience+""+Specializations+""+ServiceLocation+"");
    	   return mechanicService.deleteMechanic(mechanicId);
       }
       
       
          
// to fetch orders on mechanic log in
       @GetMapping("/mechanic/orders")
       public ResponseEntity<List<MechanicOrderDto>> getOrders(
               @RequestParam Long mechanicId
       ) {
           return ResponseEntity.ok(
                   mechanicService.getOrdersForMechanic(mechanicId)
           );
       }

       // ✅ Mark order completed
       @PostMapping("/orders/{orderId}/complete")
       public ResponseEntity<?> completeOrder(
               @PathVariable Long orderId,
               @RequestParam Long mechanicId
       ) {
           boolean success =
                   mechanicService.markOrderCompleted(orderId, mechanicId);

           if (!success) {
               return ResponseEntity.badRequest()
                       .body("Order cannot be completed");
           }

           return ResponseEntity.ok("Order marked as completed");
       }
       
       
       
       
   }
     


       

