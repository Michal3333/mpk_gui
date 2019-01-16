package com.snoreware.mpk.request;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.snoreware.mpk.entities.BusEntity;
import com.snoreware.mpk.entities.DriverEntity;
import com.snoreware.mpk.entities.StopEntity;
import com.snoreware.mpk.entities.TramEntity;
import com.snoreware.mpk.model.DriverDTO;
import com.snoreware.mpk.model.StopDTO;
import com.snoreware.mpk.model.VehicleDTO;

import java.util.UUID;

public class Request {
    public static DriverEntity[] getDrivers() throws UnirestException {
        DriverEntity[] drivers;
        HttpResponse<DriverEntity[]> postResponse = Unirest.get("http://localhost:8080/driver/all").asObject(DriverEntity[].class);
        drivers = postResponse.getBody();
        return drivers;
    }
    public static BusEntity[] getBuses() throws UnirestException {
        BusEntity[] buses;
        HttpResponse<BusEntity[]> postResponse = Unirest.get("http://localhost:8080/bus/all").asObject(BusEntity[].class);
        buses = postResponse.getBody();
        return buses;
    }
    public static TramEntity[] getTrams() throws UnirestException {
        TramEntity[] trams;
        HttpResponse<TramEntity[]> postResponse = Unirest.get("http://localhost:8080/tram/all").asObject(TramEntity[].class);
        trams = postResponse.getBody();
        return trams;
    }
    public static StopEntity[] getStops() throws UnirestException {
        StopEntity[] stops;
        HttpResponse<StopEntity[]> postResponse = Unirest.get("http://localhost:8080/stop/all").asObject(StopEntity[].class);
        stops = postResponse.getBody();
        return stops;
    }

    public static void addDriver(DriverDTO driverDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/driver/add")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(driverDTO)
                .asJson();
    }
    public static void addTram(VehicleDTO vehicleDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/tram/add")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(vehicleDTO)
                .asJson();
    }
    public static void addBus(VehicleDTO vehicleDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/bus/add")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(vehicleDTO)
                .asJson();
    }
    public static void addStop(StopDTO stopDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/stop/add")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(stopDTO)
                .asJson();
    }
    public static void updateDriver(DriverDTO driverDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.patch("http://localhost:8080/driver/update/{id}")
                .routeParam("id", driverDTO.getDriverId().toString())
                .body(driverDTO)
                .asJson();
    }
    public static void updateBus(VehicleDTO vehicleDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.patch("http://localhost:8080/bus/update")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(vehicleDTO)
                .asJson();
    }
    public static void updateTram(VehicleDTO vehicleDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.patch("http://localhost:8080/tram/update")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(vehicleDTO)
                .asJson();
    }
    public static void updateStop(StopDTO stopDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.patch("http://localhost:8080/stop/update")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(stopDTO)
                .asJson();
    }
    public static void deleteDriver(DriverDTO driverDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.delete("http://localhost:8080/driver/delete/{id}")
                .routeParam("id", driverDTO.getDriverId().toString())
                .asJson();
    }
    public static void deleteBus(VehicleDTO vehicleDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.delete("http://localhost:8080/bus/remove")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(vehicleDTO)
                .asJson();
    }
    public static void deleteTram(VehicleDTO vehicleDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.delete("http://localhost:8080/tram/remove")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(vehicleDTO)
                .asJson();
    }
    public static void deleteStop(StopDTO stopDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.delete("http://localhost:8080/stop/remove")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(stopDTO)
                .asJson();
    }
    public static void stopFailure(StopDTO stopDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.patch("http://localhost:8080/stop/failure")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(stopDTO)
                .asJson();

    }
    public static void busFailure(VehicleDTO vehicleDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.patch("http://localhost:8080/bus/failure")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(vehicleDTO)
                .asJson();

    }
    public static void tramFailure(VehicleDTO vehicleDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.patch("http://localhost:8080/tram/failure")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(vehicleDTO)
                .asJson();
    }
    public static DriverDTO getDriver(DriverEntity driver) throws UnirestException {
        HttpResponse<DriverDTO> postResponse = Unirest.get("http://localhost:8080/driver/{id}")
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .routeParam("id",driver.getDriverId().toString())
                .asObject(DriverDTO.class);
        return postResponse.getBody();
    }

}
