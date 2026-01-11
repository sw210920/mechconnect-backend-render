package com.mechconnect.backend.dto;

import com.mechconnect.backend.entity.enums.ServiceMode;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class MechanicOrderDto {
	 private Long orderId;
	    private String orderNumber;
	    private String status;

	    private Long customerId;
	    private String customerName;

	    private String serviceType;
	    private String packageName;
//	    private String serviceMode;
	   
	    private ServiceMode serviceMode;
	    
	    private String serviceDate;
	    private String serviceTime;

	    private String vehicle;
	    private String vehicleModel;

	    private String registrationNumber;

	    private String customerAddress; // ONLY for DOORSTEP

		public Long getOrderId() {
			return orderId;
		}

		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}

		public String getOrderNumber() {
			return orderNumber;
		}

		public void setOrderNumber(String orderNumber) {
			this.orderNumber = orderNumber;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Long getCustomerId() {
			return customerId;
		}

		public void setCustomerId(Long customerId) {
			this.customerId = customerId;
		}

		public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		public String getServiceType() {
			return serviceType;
		}

		public void setServiceType(String serviceType) {
			this.serviceType = serviceType;
		}

		public String getPackageName() {
			return packageName;
		}

		public void setPackageName(String packageName) {
			this.packageName = packageName;
		}
//
//		public String getServiceMode() {
//			return serviceMode;
//		}
//
//		public void setServiceMode(String serviceMode) {
//			this.serviceMode = serviceMode;
//		}
		
		

		public String getServiceDate() {
			return serviceDate;
		}

		public ServiceMode getServiceMode() {
			return serviceMode;
		}

		public void setServiceMode(ServiceMode serviceMode) {
			this.serviceMode = serviceMode;
		}

		public void setServiceDate(String serviceDate) {
			this.serviceDate = serviceDate;
		}

		public String getServiceTime() {
			return serviceTime;
		}

		public void setServiceTime(String serviceTime) {
			this.serviceTime = serviceTime;
		}

		public String getVehicle() {
			return vehicle;
		}

		public void setVehicle(String vehicle) {
			this.vehicle = vehicle;
		}
		
		

		public String getVehicleModel() {
			return vehicleModel;
		}

		public void setVehicleModel(String vehicleModel) {
			this.vehicleModel = vehicleModel;
		}

		public String getRegistrationNumber() {
			return registrationNumber;
		}

		public void setRegistrationNumber(String registrationNumber) {
			this.registrationNumber = registrationNumber;
		}

		public String getCustomerAddress() {
			return customerAddress;
		}

		public void setCustomerAddress(String customerAddress) {
			this.customerAddress = customerAddress;
		}
	    
	    
}
