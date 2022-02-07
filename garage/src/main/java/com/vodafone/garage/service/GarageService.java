package com.vodafone.garage.service;

import com.vodafone.garage.dto.VehicleModel;
import com.vodafone.garage.entity.TicketInfo;

public interface GarageService {
    TicketInfo park(VehicleModel vehicle);

    TicketInfo leave(Long ticketNo);

    String status();
}
