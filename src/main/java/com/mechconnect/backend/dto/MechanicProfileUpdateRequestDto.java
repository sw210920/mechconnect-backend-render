package com.mechconnect.backend.dto;



/**
 * MechanicProfileUpdateRequestDto
 *
 * Part of the MechConnect backend application.
 * Responsible for handling dto related logic.
 */


public class MechanicProfileUpdateRequestDto {
	 private Long mechanicId;
	    private String firstName;
	    private String lastName;
	    private String mobailNumber;
	    
	    private String address;
	  		private String specialization;
	    
	    private String serviceLocation;
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
		
		public String getMobailNumber() {
			return mobailNumber;
		}
		public void setMobailNumber(String mobailNumber) {
			this.mobailNumber = mobailNumber;
		}
	
		
		
		
		public String getSpecialization() {
			return specialization;
		}
		public void setSpecialization(String specialization) {
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
