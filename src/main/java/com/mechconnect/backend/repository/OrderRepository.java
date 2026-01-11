package com.mechconnect.backend.repository;

/**
 * OrderRepository
 *
 * Part of the MechConnect backend application.
 * Responsible for handling repository related logic.
 */


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mechconnect.backend.entity.Customer;
import com.mechconnect.backend.entity.Mechanic;
import com.mechconnect.backend.entity.Orders;
import com.mechconnect.backend.entity.enums.RequestStatus;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
  
	
	// Fetch all orders for a customer (latest first)
    List<Orders> findByCustomer_CustomerIdOrderByCreatedAtDesc(Long customerId);

    // Fetch orders with mechanic details (safe for DTO mapping)
    @Query("""
        SELECT o
        FROM Orders o
        JOIN FETCH o.mechanic
        WHERE o.customer.customerId = :customerId
        ORDER BY o.createdAt DESC
    """)
    List<Orders> findOrdersWithMechanic(
        @Param("customerId") Long customerId
    );
    
    
    // ✅ Fetch only ACCEPTED & COMPLETED for mechanic
    List<Orders> findByMechanic_MechanicIdAndStatusInOrderByCreatedAtDesc(
            Long mechanicId,
            List<RequestStatus> statuses
    );
    
}