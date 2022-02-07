package com.vodafone.garage.service;

import com.vodafone.garage.dto.VehicleModel;
import com.vodafone.garage.entity.TicketInfo;
import com.vodafone.garage.exception.GarageFullException;
import com.vodafone.garage.exception.NoTicketFoundException;
import com.vodafone.garage.exception.TicketNotActiveException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GarageServiceImpl implements GarageService {

    private static final Integer GARAGE_CAPACITY = 10;
    private final Map<Long, TicketInfo> ticketMap = new ConcurrentHashMap<>();
    private final boolean[] parkSlotUsed = new boolean[GARAGE_CAPACITY];
    private Long ticketNo = 1L;

    @Override
    public TicketInfo park(VehicleModel vehicle) {
        log.info(vehicle.getVehicleType().getType() + " Entered!");
        var allocatedSpaces = getAvailableSlots(vehicle.getVehicleType().getWidth());
        if(allocatedSpaces.isEmpty()) {
            log.error("Couldn't allocate space, garage is full");
            throw new GarageFullException("Garage is full!");
        }
        setParkingLotsUsage(true, allocatedSpaces);
        var nextTicket = getNextTicketNo();
        var ticketInfo = TicketInfo.builder().ticketNo(nextTicket)
                .type(vehicle.getVehicleType().getType())
                .color(vehicle.getColor())
                .plateNo(vehicle.getPlateNo())
                .parked(true)
                .allocatedParkingSlots(allocatedSpaces)
                .parkedDate(LocalDateTime.now())
                .build();
        ticketMap.put(nextTicket, ticketInfo);
        log.info("Successfully allocated space. Ticket no:" +nextTicket);
        return ticketInfo;
    }

    private synchronized Long getNextTicketNo() {
        return ticketNo++;
    }

    private synchronized List<Integer> getAvailableSlots(Integer vehicleWidth) {
        List<Integer> allocatedSpaces = new ArrayList<>();
        for(int i = 0; i< GARAGE_CAPACITY-vehicleWidth+1; i++) {
            if(i==0 || (i!= 0 && !parkSlotUsed[i-1])) {
                for(int j = i; j < i+vehicleWidth; j++) {
                    if(!parkSlotUsed[j]) {
                        allocatedSpaces.add(j+1);
                    } else {
                        break;
                    }
                }
            }
            if(allocatedSpaces.size() == vehicleWidth) {
                return allocatedSpaces;
            }
        }
        return new ArrayList<>();
    }

    @Override
    public TicketInfo leave(Long ticketNo) {
        var ticketInfo = ticketMap.get(ticketNo);
        if(ticketInfo == null) {
            log.error("ticketNo: "+ticketNo + " not found!");
            throw new NoTicketFoundException("No ticket found!");
        }
        if(!ticketInfo.isParked()) {
            log.error("ticketNo: "+ticketNo + " is not active!");
            throw new TicketNotActiveException("Ticket is not active!");
        }
        setParkingLotsUsage(false, ticketInfo.getAllocatedParkingSlots());
        ticketInfo.setParked(false);
        ticketInfo.setLeftDate(LocalDateTime.now());
        ticketMap.put(ticketNo, ticketInfo);
        log.info(ticketNo+ " successfully left!");
        return ticketInfo;
    }

    private synchronized void setParkingLotsUsage(boolean availability, List<Integer> allocatedSpaces) {
        allocatedSpaces.forEach(index ->
                parkSlotUsed[index-1] = availability
        );
    }

    @Override
    public String status() {
         var status = ticketMap.values().stream()
                        .filter(TicketInfo::isParked)
                        .map(Object::toString)
                        .collect(Collectors.joining(System.lineSeparator()));
        return (status != null && !status.isBlank()) ? "Status:" + System.lineSeparator() + status : "No parking car found!";
    }
}
