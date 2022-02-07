package com.vodafone.garage;

import com.vodafone.garage.dto.VehicleModel;
import com.vodafone.garage.dto.VehicleTypesEnum;
import com.vodafone.garage.exception.GarageFullException;
import com.vodafone.garage.exception.NoTicketFoundException;
import com.vodafone.garage.exception.TicketNotActiveException;
import com.vodafone.garage.service.GarageServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class GarageApplicationTests {

    @InjectMocks
    GarageServiceImpl garageService;

    @Test
    void testPark() {
        var parkResponse = garageService.park(getCarModel());
        assertEquals(1, parkResponse.getAllocatedParkingSlots().size());
    }

    @Test
    void testParkGarageFull() {
        var vehicleModel = VehicleModel.builder().
                plateNo("34-dnu-34").
                color("red").
                vehicleType(VehicleTypesEnum.TRUCK).build();
        garageService.park(vehicleModel);
        vehicleModel.setPlateNo("34-dnu-35");
        garageService.park(vehicleModel);
        String expectedMessage = "Garage is full!";
        try {
            vehicleModel.setPlateNo("34-dnu-36");
            garageService.park(vehicleModel);
        } catch (GarageFullException ex) {
            assertEquals(expectedMessage, ex.getMessage());
        }
    }

    @Test
    void testLeaveNoActiveTicket() {
        var parkResponse = garageService.park(getCarModel());
        garageService.leave(parkResponse.getTicketNo());
        String expectedMessage = "Ticket is not active!";
        try {
            garageService.leave(parkResponse.getTicketNo());
        } catch (TicketNotActiveException ex) {
            assertEquals(expectedMessage, ex.getMessage());
        }
    }

    @Test
    void testLeaveTicketNotFound() {
        var parkResponse = garageService.park(getCarModel());
        String expectedMessage = "No ticket found!";
        try {
            garageService.leave(parkResponse.getTicketNo()+1);
        } catch (NoTicketFoundException ex) {
            assertEquals(expectedMessage, ex.getMessage());
        }
    }

    @Test
    void testLeave() {
        var parkResponse = garageService.park(getCarModel());
        var leftResponse = garageService.leave(parkResponse.getTicketNo());
        assertEquals(false, leftResponse.isParked());
    }

    @Test
    void testStatus() {
        garageService.park(getCarModel());
        assertNotNull(garageService.status());
    }

    private VehicleModel getCarModel() {
        return VehicleModel.builder().
                plateNo("34-dnu-34").
                color("red").
                vehicleType(VehicleTypesEnum.CAR).build();
    }

}
