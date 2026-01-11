package com.mechconnect.backend.entity;

import java.time.LocalDateTime;

/**
 * MechanicRequest
 *
 * Part of the MechConnect backend application.
 * Responsible for handling entity related logic.
 */



import com.mechconnect.backend.entity.enums.RequestStatus;
import com.mechconnect.backend.entity.enums.ServiceMode;

import jakarta.persistence.*;

@Entity
@Table(name = "mechanic_request")
public class ServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

   

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "mechanic_id", nullable = false)
    private Mechanic mechanic;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_mechanic_id")
    private Mechanic requestedMechanic;

  
	@Column(name = "vehicle_year", nullable = false)
    private String vehicleYear;
   
    @Enumerated(EnumType.STRING)
    private ServiceMode serviceMode;    

    @Column(length = 500)
    private String serviceAddress;        
    
    
  
	private String customerName;
    private String serviceType;
    private String serviceLocation;
    private String packageName;
   
    private String time;
    
   
    private String ServiceDate;

   
    private String make;
    private String model;
    private String registrationNumber;

   

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;
       
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    
//    GETTER SETTER
    
    
    
    public Mechanic getRequestedMechanic() {
		return requestedMechanic;
	}

	public void setRequestedMechanic(Mechanic requestedMechanic) {
		this.requestedMechanic = requestedMechanic;
	}

    
    
    public ServiceMode getServiceMode() {
  		return serviceMode;
  	}

  	public void setServiceMode(ServiceMode serviceMode) {
  		this.serviceMode = serviceMode;
  	}

  	public String getServiceAddress() {
  		return serviceAddress;
  	}

  	public void setServiceAddress(String serviceAddress) {
  		this.serviceAddress = serviceAddress;
  	}


    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
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

    public String getServiceLocation() {
        return serviceLocation;
    }

    public void setServiceLocation(String serviceLocation) {
        this.serviceLocation = serviceLocation;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

   

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

   

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

   

	public RequestStatus getstatus() {
		return status;
	}

	public void setstatus(RequestStatus status) {
		this.status = status;
	}

	public String getVehicleYear() {
		return vehicleYear;
	}

	public void setVehicleYear(String vehicleYear) {
		this.vehicleYear = vehicleYear;
	}

	public String getServiceDate() {
		return ServiceDate;
	}

	public void setServiceDate(String serviceDate) {
		this.ServiceDate = serviceDate;
	}

	public Object getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	
    
	}
