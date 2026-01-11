package com.mechconnect.backend.repository;

/**
 * MechanicRequestRepository
 *
 * Part of the MechConnect backend application.
 * Responsible for handling repository related logic.
 */


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.mechconnect.backend.entity.ServiceRequest;
import com.mechconnect.backend.entity.Orders;
import com.mechconnect.backend.entity.enums.RequestStatus;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
	
    List<ServiceRequest> findByCustomer_CustomerId(Long customerId);

   

    
    List<ServiceRequest> findByMechanic_MechanicIdAndStatus(Long mechanicId, RequestStatus status);

	
	List<ServiceRequest> 
	findByCustomer_CustomerIdAndStatusOrderByCreatedAtDesc(
            Long customerId,
            RequestStatus status
    );
    
}
