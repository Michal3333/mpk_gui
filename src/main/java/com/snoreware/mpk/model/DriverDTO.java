package com.snoreware.mpk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DriverDTO {
    private UUID driverId;
    private String name;
    private String surname;
    private String sex;
    private Float salary;
    private int seniority;
    private List<UUID> tramCourses;
    private List<UUID> busCourses;
}
