package com.mechconnect.backend.entity;

/**
 * Mechanic
 *
 * Part of the MechConnect backend application.
 * Responsible for handling entity related logic.
 */


import java.time.LocalDateTime;
import java.util.Set;

import com.mechconnect.backend.entity.enums.ServiceType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="mechanic")
public class Mechanic {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long mechanicId;
	
	@Column(name="firstName")
	private String firstName;
	
	@Column(name="lastName")
	private String lastName;

	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="MobailNumber")
	private String MobailNumber;
	
	@Column(name="YearsOfExperience")
	private String YearsOfExperience;
	
	@Column(name="ServiceLocation")
	private String serviceLocation;
	
	@Column(nullable = false)
	private String address;

	
	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	@Column(name="Specialization")
	@Enumerated(EnumType.STRING)
    private ServiceType specialization;
	
	@Column (name="otp")
    private String otp;

    @Column (name="otpExpiry")
    private LocalDateTime otpExpiry;
	
	
    

	
	
	@Column(name="Certifications")
	private String Certifications;
	
	@Column(name="Bio")
	private String Bio;
	
	
	@OneToMany(mappedBy="mechanic",cascade=CascadeType.ALL)
	private Set<Orders>orderList;


	
	
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


	public ServiceType getSpecialization() {
		return specialization;
	}


	public void setSpecialization(ServiceType specialization) {
		this.specialization = specialization;
	}


	public String getPassword() {    
	    return password;
	}

	public void setPassword(String password) {   	    this.password = password;
	}


	public String getMobailNumber() {
		return MobailNumber;
	}


	public void setMobailNumber(String mobailNumber) {
		MobailNumber = mobailNumber;
	}


	public String getYearsOfExperience() {
		return YearsOfExperience;
	}


	public void setYearsOfExperience(String yearsOfExperience) {
		YearsOfExperience = yearsOfExperience;
	}


	


	public String getServiceLocation() {
		return serviceLocation;
	}


	public void setServiceLocation(String serviceLocation) {
	    this.serviceLocation = serviceLocation;
	}



	public String getCertifications() {
		return Certifications;
	}


	public void setCertifications(String certifications) {
		Certifications = certifications;
	}


	public String getBio() {
		return Bio;
	}


	public void setBio(String bio) {
		Bio = bio;
	}


	public Set<Orders> getOrderList() {
		return orderList;
	}


	public void setOrderList(Set<Orders> orderList) {
		this.orderList = orderList;
	}


	public String getOtp() {
		return otp;
	}


	public void setOtp(String otp) {
		this.otp = otp;
	}


	public LocalDateTime getOtpExpiry() {
		return otpExpiry;
	}


	public void setOtpExpiry(LocalDateTime otpExpiry) {
		this.otpExpiry = otpExpiry;
	}

    
	

	
}