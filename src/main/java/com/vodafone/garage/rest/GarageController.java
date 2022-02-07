package com.vodafone.garage.rest;

import com.vodafone.garage.dto.VehicleModel;
import com.vodafone.garage.service.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/garage")
public class GarageController {

    @Autowired
    GarageService garageService;

    @PostMapping(path = "/park")
    public ResponseEntity<String> park(@RequestBody @Valid VehicleModel vehicle) {
        var response =  garageService.park(vehicle);
        String responseText = "Allocated " + response.getAllocatedParkingSlots().size() +
                (response.getAllocatedParkingSlots().size() == 1 ? " slot." : " slots.");
        return ResponseEntity.ok(responseText);
    }

    @DeleteMapping(path = "/leave/{ticketNo}")
    public void leave(@PathVariable Long ticketNo) {
        garageService.leave(ticketNo);
    }

    @GetMapping(path = "/status")
    public ResponseEntity<String> status() {
        var response = garageService.status();
        return ResponseEntity.ok(response);
    }
}
