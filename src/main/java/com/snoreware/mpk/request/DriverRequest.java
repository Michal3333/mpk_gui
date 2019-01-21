package com.snoreware.mpk.request;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.snoreware.mpk.entities.DriverEntity;
import com.snoreware.mpk.model.DriverDTO;
import com.snoreware.mpk.modelIn.InDriverDTO;

import java.util.UUID;

public class DriverRequest {
    public static InDriverDTO[] getDrivers() throws UnirestException {
        InDriverDTO[] drivers;
        HttpResponse<InDriverDTO[]> postResponse = Unirest.get("http://localhost:8080/driver/all")
                .asObject(InDriverDTO[].class);
        drivers = postResponse.getBody();
        return drivers;
    }
    public static InDriverDTO[] getAviableDrivers() throws UnirestException {
        InDriverDTO[] drivers;
        HttpResponse<InDriverDTO[]> postResponse = Unirest.get("http://localhost:8080/driver/byStatus")
                .queryString("onlyAvailable",true)
                .asObject(InDriverDTO[].class);
        drivers = postResponse.getBody();
        return drivers;
    }
    public static void addDriver(DriverDTO driverDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/driver/add")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(driverDTO)
                .asJson();
    }
    public static void updateDriver(DriverDTO driverDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.patch("http://localhost:8080/driver/update/{id}")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id", driverDTO.getDriverId().toString())
                .body(driverDTO)
                .asJson();
    }
    public static void deleteDriver(DriverDTO driverDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.delete("http://localhost:8080/driver/delete/{id}")
                .routeParam("id", driverDTO.getDriverId().toString())
                .asJson();
    }
    public static DriverDTO getDriver(UUID driver) throws UnirestException {
        HttpResponse<DriverDTO> postResponse = Unirest.get("http://localhost:8080/driver/{id}")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id",driver.toString())
                .asObject(DriverDTO.class);
        return postResponse.getBody();
    }




}
