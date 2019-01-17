package com.snoreware.mpk.modelIn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InStopDTO {
    private int stopNumber;
    private UUID stopId;
    private String stopName;
    public String toString() {
        return stopName;
    }
}
