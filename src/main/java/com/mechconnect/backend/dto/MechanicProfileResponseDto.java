package com.mechconnect.backend.dto;

import com.mechconnect.backend.entity.enums.ServiceType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * MechanicProfileResponseDto
 *
 * Part of the MechConnect backend application.
 * Responsible for handling dto related logic.
 */


public class MechanicProfileResponseDto {

	private Long mechanicId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobailNumber;

    @Enumerated(EnumType.STRING)
    private ServiceType specialization;
    
    
    private String serviceLocation;
    private String address;
   
	private String yearsOfExperience;

    private String certifications;
    private String bio;
	public Long getMechanicId() {
		return mechanicId;
	}
	public void setMechanicId(Long mechanicId) {
		this.mechanicId = mechanicId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobailNumber() {
		return mobailNumber;
	}
	public void setMobailNumber(String mobailNumber) {
		this.mobailNumber = mobailNumber;
	}
	
	public ServiceType getSpecialization() {
		return specialization;
	}
	public void setSpecialization(ServiceType specialization) {
		this.specialization = specialization;
	}
	public String getServiceLocation() {
		return serviceLocation;
	}
	public void setServiceLocation(String serviceLocation) {
		this.serviceLocation = serviceLocation;
	}
	
	 public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}	
	
	public String getYearsOfExperience() {
		return yearsOfExperience;
	}
	public void setYearsOfExperience(String yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}
	public String getCertifications() {
		return certifications;
	}
	public void setCertifications(String certifications) {
		this.certifications = certifications;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}

    
    
	
}
