package com.vodafone.garage.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VehicleModel {
    @NotNull(message = "vehicleType can not be null.")
    private VehicleTypesEnum vehicleType;
    @NotNull(message = "plateNo can not be null.")
    private String plateNo;
    @NotNull(message = "color can not be null.")
    private String color;

    @Override
    public String toString() {
        return plateNo+" "+color;
    }
}
