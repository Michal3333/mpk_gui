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
@Table(name = "trams")
public class TramEntity extends VehicleEntity {
    public TramEntity(boolean lowFloor, int numberOfWagons) {
        super(lowFloor);
        this.numberOfWagons = numberOfWagons;
    }

    @Column(name = "number_of_wagons")
    private int numberOfWagons;

    @OneToMany(mappedBy = "tram")
    private List<TramCourseEntity> tramCourses;

    @Override
    public String toString() {
        return super.getVehicleNumber().toString();
    }
}
