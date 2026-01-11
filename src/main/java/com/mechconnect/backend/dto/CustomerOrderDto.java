package com.mechconnect.backend.dto;

import com.mechconnect.backend.entity.enums.ServiceMode;

public class CustomerOrderDto {

    private String orderNumber;
    private String serviceType;
    private String packageName;
    private String serviceDate;
    private String serviceTime;
    
    private ServiceMode serviceMode;    
//  private OrderStatus status;
    private String mechanicAddress;
    private String vehicleRegistrationNumber;
    

	private Long mechanicId;
    private String mechanicName;
    
    private String vehicleMake;
    private String vehicleModel;
    private String status;

	public Long getMechanicId() {
		return mechanicId;
	}

	public void setMechanicId(Long mechanicId) {
		this.mechanicId = mechanicId;
	}

	public String getMechanicName() {
		return mechanicName;
	}

	public void setMechanicName(String mechanicName) {
		this.mechanicName = mechanicName;
	}

	
	public String getMechanicAddress() {
		return mechanicAddress;
	}

	public void setMechanicAddress(String mechanicAddress) {
		this.mechanicAddress = mechanicAddress;
	}

   

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
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

	public String getServiceDate() {
		return serviceDate;
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

	public String getVehicleMake() {
		return vehicleMake;
	}

	public void setVehicleMake(String vehicleMake) {
		this.vehicleMake = vehicleMake;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ServiceMode getServiceMode() {
		return serviceMode;
	}

	public void setServiceMode(ServiceMode serviceMode) {
		this.serviceMode = serviceMode;
	}
	
	public String getVehicleRegistrationNumber() {
		return vehicleRegistrationNumber;
	}

	public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
		this.vehicleRegistrationNumber = vehicleRegistrationNumber;
	}


//	public OrderStatus getStatus() {
//		return status;
//	}
//
//	public void setStatus(OrderStatus status) {
//		this.status = status;
//	}
//    
    
}
