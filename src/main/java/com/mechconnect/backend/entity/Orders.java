package com.mechconnect.backend.entity;

import java.time.LocalDateTime;

import com.mechconnect.backend.entity.enums.OrderStatus;
import com.mechconnect.backend.entity.enums.ServiceMode;

/**
 * Orders
 *
 * Part of the MechConnect backend application.
 * Responsible for handling entity related logic.
 */
import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false, unique = true)
    private String orderNumber;

   
    @Column(nullable = false)
    private String vehicleMake;

    @Column(nullable = false)
    private String vehicleModel;

    @Column(nullable = false)
    private String vehicleyear;

    @Column(nullable = false)
    private String vehicleRegistrationNumber;

   
    @Column(nullable = false)
    private String serviceType;
    
    @Enumerated(EnumType.STRING)
    private ServiceMode serviceMode;    
    
    private String customerAddress;
   
	private String packageName;

    private  String ServiceDate;

    private String serviceTime;

   
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

   
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customerId", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mechanic_id", referencedColumnName = "mechanicId", nullable = false)
    private Mechanic mechanic;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }


    public Long getOrderId() {
        return orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    
    
    
    public String getCustomerAddress() {
		return customerAddress;
	}


	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}


	public ServiceMode getServiceMode() {
		return serviceMode;
	}


	public void setServiceMode(ServiceMode serviceMode) {
		this.serviceMode = serviceMode;
	}


    
    public String getVehicleYear() {
        return vehicleyear;
    }

    public void setVehicleYear(String vehicleYear) {
        this.vehicleyear = vehicleYear;
    }

    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

   

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

	public void setMechanicRequest(ServiceRequest request) {
		// TODO Auto-generated method stub
		
	}

	public void setServiceLocation(String serviceLocation) {
		// TODO Auto-generated method stub
		
	}

	public void setTime(String time) {
		// TODO Auto-generated method stub
		
	}

	public void setDate(Object date) {
		// TODO Auto-generated method stub
		
	}

	public void setStatus(String string) {
		// TODO Auto-generated method stub
		
	}


	public String getServiceDate() {
		return ServiceDate;
	}


	public void setServiceDate(String serviceDate) {
		this.ServiceDate = serviceDate;
	}

	
}
