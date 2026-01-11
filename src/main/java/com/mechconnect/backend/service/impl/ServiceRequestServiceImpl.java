package com.mechconnect.backend.service.impl;

/**
 * MechanicRequestServiceImpl
 *
 * Part of the MechConnect backend application.
 * Responsible for handling impl related logic.
 */


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mechconnect.backend.dto.MechanicFetchPendingRequestsDto;
import com.mechconnect.backend.dto.ServiceRequestCreateDto;
import com.mechconnect.backend.entity.Customer;
import com.mechconnect.backend.entity.Mechanic;
import com.mechconnect.backend.entity.ServiceRequest;
import com.mechconnect.backend.entity.Orders;
import com.mechconnect.backend.entity.enums.OrderStatus;
import com.mechconnect.backend.entity.enums.RequestStatus;
import com.mechconnect.backend.repository.CustomerRepository;
import com.mechconnect.backend.repository.MechanicRepository;
import com.mechconnect.backend.repository.ServiceRequestRepository;
import com.mechconnect.backend.repository.OrderRepository;
import com.mechconnect.backend.service.ServiceRequestService;



@Service
public class ServiceRequestServiceImpl implements ServiceRequestService {
	

	    @Autowired
	    private CustomerRepository customerRepo;

	    @Autowired
	    private MechanicRepository mechanicRepo;
	    

	    
	    @Autowired
	    private ServiceRequestRepository serviceRequestRepository;

	    @Autowired
	    private OrderRepository orderRepository;




	    // CUSTOMER SEND Service Request
	    @Override
	    public String sendRequest(ServiceRequestCreateDto dto) {

	        if (dto.getCustomerId() == null) {
	            throw new RuntimeException("Customer ID is missing");
	        }

	        if (dto.getMechanicId() == null) {
	            throw new RuntimeException("Mechanic ID is missing");
	        }

	        Customer customer = customerRepo.findById(dto.getCustomerId())
	                .orElseThrow(() -> new RuntimeException("Customer not found"));

	        Mechanic mechanic = mechanicRepo.findById(dto.getMechanicId())
	                .orElseThrow(() -> new RuntimeException("Mechanic not found"));

	        ServiceRequest request = new ServiceRequest();

	       
	        request.setCustomer(customer);
	        request.setMechanic(mechanic);

	    
	        request.setCustomerName(dto.getCustomerName());
	        request.setServiceType(dto.getServiceType());
	        request.setServiceLocation(dto.getServiceLocation());
	        //System.out.println("Received servicelocation from frontend: " + dto.getServiceLocation());

	        request.setPackageName(dto.getPackageName());
	        request.setServiceDate(dto.getServiceDate());
	        //System.out.println("Received serviceDate from frontend: " + dto.getServiceDate());

	        request.setTime(dto.getTime());
	        request.setServiceMode(dto.getServiceMode());
	        request.setServiceAddress(dto.getServiceAddress());

	        request.setMake(dto.getMake());
	        request.setModel(dto.getModel());
	        request.setVehicleYear(dto.getVehicleYear());
	        request.setRegistrationNumber(dto.getRegistrationNumber());

	        request.setstatus(RequestStatus.PENDING);

	        
	        if (dto.getMechanicId() != null) {
	            Mechanic mechanic1 = mechanicRepo
	                .findById(dto.getMechanicId())
	                .orElseThrow(() -> new RuntimeException("Mechanic not found"));

	            request.setRequestedMechanic(mechanic1);
	        }

	        
	        serviceRequestRepository.save(request);

	        return "REQUEST_SENT";
	    }



	  
	    

//		fFETCH PENDING SERVICE REQUESTS FOR MECHANICS
		 @Override
		 public List<MechanicFetchPendingRequestsDto> getRequestsForMechanic(Long mechanicId) {
			 List<ServiceRequest> requests =
					 serviceRequestRepository.findByMechanic_MechanicIdAndStatus(
		                        mechanicId, RequestStatus.PENDING
		                );

		        return requests.stream()
		                .map(this::mapToDTO)
		                .toList();
		    }

		    private MechanicFetchPendingRequestsDto mapToDTO(ServiceRequest request) {

		    	MechanicFetchPendingRequestsDto dto = new MechanicFetchPendingRequestsDto();
		    	  dto.setRequestId(request.getRequestId());
		  	    dto.setCustomerName(request.getCustomerName());
		  	    dto.setServiceAddress(request.getServiceAddress());
		  	    dto.setServiceLocation(request.getServiceLocation());
		  	    
		  	    
		  	    
		  	    
		  	    dto.setServiceType(request.getServiceType());
		  	    dto.setServiceDate(request.getServiceDate());
		  	    dto.setTime(request.getTime());
		  	    dto.setStatus(request.getstatus().name());
		  	    dto.setPackageName(request.getPackageName());
		  	    dto.setMake(request.getMake());
		  	    dto.setModel(request.getModel());
		  	    dto.setVehicleYear(request.getVehicleYear());
		  	    dto.setRegistrationNumber(request.getRegistrationNumber());
		  	   
		  	    
		  	  dto.setServiceMode(
		  	        request.getServiceMode() != null
		  	            ? request.getServiceMode().name()
		  	            : null
		  	    );
		        return dto;
		    }
		


		

 

		
//		ACCEPT REQUEST AND UPDATE STATUS "ACCEPTED" & MOVE REQUEST TO ORDER TABLE
		 @Override
		    public void acceptOrder(Long requestId, String status) {

		        ServiceRequest request = serviceRequestRepository.findById(requestId)
		                .orElseThrow(() -> new RuntimeException("Request not found"));

		        if ("ACCEPTED".equals(status)) {
		            moveRequestToOrders(request);
		        }

		        serviceRequestRepository.delete(request);
		    }
		    
//		 For move request to orders after accepting request
		 private void moveRequestToOrders(ServiceRequest req) {

			    Orders order = new Orders();

			    order.setOrderNumber("ORD-" + System.currentTimeMillis());

			    order.setVehicleMake(req.getMake());
			    order.setVehicleModel(req.getModel());
			    order.setVehicleYear(req.getVehicleYear()); 
			    order.setVehicleRegistrationNumber(req.getRegistrationNumber());

			    order.setServiceType(req.getServiceType());
			    order.setServiceDate(req.getServiceDate());
			    order.setServiceTime(req.getTime());
			    order.setPackageName(req.getPackageName());
			    order.setServiceMode(req.getServiceMode());
			    order.setCustomerAddress(req.getServiceAddress());
			    order.setCustomer(req.getCustomer());
			    order.setMechanic(req.getMechanic());

			    order.setStatus(OrderStatus.ACCEPTED);
			    
			    System.out.println("DEBUG vehicleYear = " + req.getVehicleYear());

			    
			    orderRepository.save(order);

			    serviceRequestRepository.delete(req);
			}

		 
		 
		 @Override
		 public String rejectRequest(Long requestId) {

		     ServiceRequest req = serviceRequestRepository.findById(requestId)
		             .orElseThrow(() -> new RuntimeException("Request not found"));

		     req.setstatus(RequestStatus.REJECTED);
		     serviceRequestRepository.save(req);

		     Orders order = new Orders();
		     order.setOrderNumber("ORD-" + System.currentTimeMillis());

		     order.setVehicleMake(req.getMake());
		     order.setVehicleModel(req.getModel());
		     order.setVehicleYear(req.getVehicleYear());
		     order.setVehicleRegistrationNumber(req.getRegistrationNumber());

		     order.setServiceType(req.getServiceType());
		     order.setServiceDate(req.getServiceDate());
		     order.setServiceTime(req.getTime());
		     order.setPackageName(req.getPackageName());
		     order.setServiceMode(req.getServiceMode());
		     
		     order.setCustomer(req.getCustomer());
		     order.setMechanic(req.getMechanic());

		     order.setStatus(OrderStatus.REJECTED);

		     orderRepository.save(order);

		     return "Request rejected and archived in orders";
		 }




//		 
//		 @Override
//		 public void updateOrderStatus(Long orderId, OrderStatus status) {
//
//		     Orders order = orderRepository.findById(orderId)
//		             .orElseThrow(() -> new RuntimeException("Order not found"));
//
//		     order.setStatus(status);
//		     orderRepository.save(order);
//		 }
		}
		
		
	


