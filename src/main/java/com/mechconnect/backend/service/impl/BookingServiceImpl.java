package com.mechconnect.backend.service.impl;

/**
 * BookingServiceImpl
 *
 * Part of the MechConnect backend application.
 * Responsible for handling impl related logic.
 */


import com.mechconnect.backend.dto.BookingCreateRequest;
import com.mechconnect.backend.entity.Booking;
import com.mechconnect.backend.repository.BookingRepository;
import com.mechconnect.backend.repository.MechanicRepository;
import com.mechconnect.backend.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository repo;

    @Autowired
    private MechanicRepository mechanicRepository;

    
    @Override
    public Booking saveBooking(BookingCreateRequest req) {
        Booking b = new Booking();
        b.setCustomerId(req.getCustomerId());
        b.setPackageName(req.getPackageName());
        b.setDate(req.getDate());
        b.setTime(req.getTime());

        if (req.getVehicle() != null) {
            b.setMake(req.getVehicle().getMake());
            b.setModel(req.getVehicle().getModel());
            b.setYear(req.getVehicle().getYear());
            b.setReg(req.getVehicle().getReg());
        }

        return repo.save(b);
    }

    @Override
    public List<Booking> findByCustomerId(Long customerId) {
        return repo.findByCustomerId(customerId);
    }
    
    
}
