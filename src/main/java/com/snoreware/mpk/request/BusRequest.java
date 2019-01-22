package com.snoreware.mpk.request;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.snoreware.mpk.entities.BusEntity;
import com.snoreware.mpk.entities.DriverEntity;
import com.snoreware.mpk.model.BusDTO;
import com.snoreware.mpk.model.DriverDTO;
import com.snoreware.mpk.model.VehicleDTO;

import java.util.List;

public class BusRequest {
    public static Long[] getBuses() throws UnirestException {
        Long[] buses;
        HttpResponse<Long[]> postResponse = Unirest.get("http://localhost:8080/bus/all")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .asObject(Long[].class);
        buses = postResponse.getBody();
        return buses;
    }
    public static Long[] getWorkingBuses() throws UnirestException {
        Long[] buses;
        HttpResponse<Long[]> postResponse = Unirest.get("http://localhost:8080/bus/byStatus")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .queryString("notBroken",true)
                .asObject(Long[].class);
        buses = postResponse.getBody();
        return buses;
    }
    public static void addBus(VehicleDTO vehicleDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/bus/add")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(vehicleDTO)
                .asJson();
    }
    public static void updateBus(VehicleDTO vehicleDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.patch("http://localhost:8080/bus/update/{id}")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id", vehicleDTO.getVehicleNumber().toString())
                .body(vehicleDTO)
                .asJson();
    }
    public static void deleteBus(VehicleDTO vehicleDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.delete("http://localhost:8080/bus/remove/{id}")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id",vehicleDTO.getVehicleNumber().toString())
                .asJson();
    }
    public static void busFailure(Long id) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/bus/failure/{id}")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id",id.toString())
                .asJson();

    }
    public static BusDTO getBus(Long bus) throws UnirestException {
        HttpResponse<BusDTO> postResponse = Unirest.get("http://localhost:8080/bus/{id}")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id",bus.toString())
                .asObject(BusDTO.class);
        return postResponse.getBody();
    }
    public static Long[] getLowFlorBuses() throws UnirestException {
        Long[] buses;
        HttpResponse<Long[]> postResponse = Unirest.get("http://localhost:8080/bus/lowFloor")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .asObject(Long[].class);
        buses = postResponse.getBody();
        return buses;
    }
    public static Long[] getArticulatedBuses() throws UnirestException {
        Long[] buses;
        HttpResponse<Long[]> postResponse = Unirest.get("http://localhost:8080/bus/articulated")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .asObject(Long[].class);
        buses = postResponse.getBody();
        return buses;
    }
    public static Long[] getLowFlorandArticulatedBuses() throws UnirestException {
        Long[] buses;
        HttpResponse<Long[]> postResponse = Unirest.get("http://localhost:8080/bus/lowFloorAndArt")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .asObject(Long[].class);
        buses = postResponse.getBody();
        return buses;

    }



}
