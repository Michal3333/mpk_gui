package com.snoreware.mpk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    private UUID courseId;
    private Long routeNumber;
    private UUID driverId;
    private Long vehicleNumber;
    private Boolean manyWagonsNeeded;
    private Boolean articulatedNeeded;
    private Boolean lowFloorNeeded;

    @Override
    public String toString() {
        return "CourseDTO{" +
                "courseId=" + courseId +
                ", routeNumber=" + routeNumber +
                ", driverId=" + driverId +
                ", vehicleNumber=" + vehicleNumber +
                ", manyWagonsNeeded=" + manyWagonsNeeded +
                ", articulatedNeeded=" + articulatedNeeded +
                ", lowFloorNeeded=" + lowFloorNeeded +
                '}';
    }
}
