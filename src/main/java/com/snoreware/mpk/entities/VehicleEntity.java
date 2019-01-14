package com.snoreware.mpk.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Getter
@SequenceGenerator(name = "seq", initialValue = 100, allocationSize = 1000)
public abstract class VehicleEntity {
    public VehicleEntity(boolean lowFloor) {
        this.lowFloor = lowFloor;
        this.vehicleBreakdown = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long vehicleNumber;

    private Boolean vehicleBreakdown;

    private Boolean lowFloor;
}
