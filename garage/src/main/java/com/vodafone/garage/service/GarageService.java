package com.vodafone.garage.service;

import com.vodafone.garage.dto.VehicleModel;

public interface GarageService {
    String park(VehicleModel vehicle);

    void leave(Long ticketNo);

    String status();
}
