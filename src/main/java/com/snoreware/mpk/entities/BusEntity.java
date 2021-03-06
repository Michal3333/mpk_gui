package com.snoreware.mpk.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "buses")
public class BusEntity extends VehicleEntity {
    public BusEntity(Boolean lowFloor, Boolean articulated) {
        super(lowFloor);
        this.articulated = articulated;
    }

    @Column(name = "articulated", nullable = false)
    private Boolean articulated;

    @OneToMany(mappedBy = "bus")
    private List<BusCourseEntity> busCourses;

    @Override
    public String toString() {
        return super.getVehicleNumber().toString();
    }
}
