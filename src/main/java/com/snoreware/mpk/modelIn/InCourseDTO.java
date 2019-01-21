package com.snoreware.mpk.modelIn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InCourseDTO {
    private UUID courseId;
    private Boolean lowFloorNeeded;
    private Boolean articulated;
    private int numberOfWagons;
    private Long vehicleNumber;
    private UUID driverId;
    private Long routeNumber;

    @Override
    public String toString() {
        return  vehicleNumber+ " " + routeNumber;
    }
}
