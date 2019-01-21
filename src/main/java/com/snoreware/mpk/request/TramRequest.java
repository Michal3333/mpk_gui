package com.snoreware.mpk.request;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.snoreware.mpk.entities.BusEntity;
import com.snoreware.mpk.entities.TramEntity;
import com.snoreware.mpk.model.TramDTO;
import com.snoreware.mpk.model.VehicleDTO;

public class TramRequest {
    public static Long[] getTrams() throws UnirestException {
        Long[] trams;
        HttpResponse<Long[]> postResponse = Unirest.get("http://localhost:8080/tram/all").asObject(Long[].class);
        trams = postResponse.getBody();
        return trams;
    }
    public static Long[] getWorkingTrams() throws UnirestException {
        Long[] trams;
        HttpResponse<Long[]> postResponse = Unirest.get("http://localhost:8080/tram/byStatus")
                .queryString("notBroken",true)
                .asObject(Long[].class);
        trams = postResponse.getBody();
        return trams;
    }
    public static void addTram(VehicleDTO vehicleDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/tram/add")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(vehicleDTO)
                .asJson();
    }
    public static void updateTram(VehicleDTO vehicleDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.patch("http://localhost:8080/tram/update/{id}")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id",vehicleDTO.getVehicleNumber().toString())
                .body(vehicleDTO)
                .asJson();
    }
    public static void deleteTram(VehicleDTO vehicleDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.delete("http://localhost:8080/tram/remove/{id}")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id",vehicleDTO.getVehicleNumber().toString())
                .asJson();
    }
    public static void tramFailure(Long id) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/tram/failure/{id}")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id",id.toString())
                .asJson();
    }
    public static TramDTO getTram(Long tram) throws UnirestException {
        HttpResponse<TramDTO> postResponse = Unirest.get("http://localhost:8080/tram/{id}")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id",tram.toString())
                .asObject(TramDTO.class);
        return postResponse.getBody();
    }

}
