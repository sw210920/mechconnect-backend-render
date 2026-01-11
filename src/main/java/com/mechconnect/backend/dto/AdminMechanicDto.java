package com.mechconnect.backend.dto;

public class AdminMechanicDto {

	  private Long mechanicId;
	    private String name;
	    private String specialization;
	    private String yearsOfExperience;
	    private String email;
	    private String address;
		public Long getMechanicId() {
			return mechanicId;
		}
		public void setMechanicId(Long mechanicId) {
			this.mechanicId = mechanicId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSpecialization() {
			return specialization;
		}
		public void setSpecialization(String specialization) {
			this.specialization = specialization;
		}
		
		public String getYearsOfExperience() {
			return yearsOfExperience;
		}
		public void setYearsOfExperience(String yearsOfExperience) {
			this.yearsOfExperience = yearsOfExperience;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
}
