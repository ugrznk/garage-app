package com.vodafone.garage.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketInfo {
    private Long ticketNo;
    private String plateNo;
    private String color;
    private String type;
    private List<Integer> allocatedParkingSlots;
    private boolean parked;
    private LocalDateTime parkedDate;
    private LocalDateTime leftDate;

    @Override
    public String toString() {
        return plateNo + " " + color + " " + allocatedParkingSlots.toString();
    }
}
