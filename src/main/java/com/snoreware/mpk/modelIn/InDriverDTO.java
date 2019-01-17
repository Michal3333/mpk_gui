package com.snoreware.mpk.modelIn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InDriverDTO {
    private UUID driverId;
    private String name;
    private String surname;
    @Override
    public String toString() {
        return
                name + ' ' + surname;
    }
}
