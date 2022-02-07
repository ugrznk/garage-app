package com.vodafone.garage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VehicleTypesEnum {
    CAR("car",1),
    JEEP("jeep",2),
    TRUCK("truck",4);

    private final String type;
    private final Integer width;

}
