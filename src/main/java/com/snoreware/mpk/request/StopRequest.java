package com.snoreware.mpk.request;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import com.snoreware.mpk.entities.DriverEntity;
import com.snoreware.mpk.entities.StopEntity;

import com.snoreware.mpk.model.DriverDTO;
import com.snoreware.mpk.model.StopDTO;
import com.snoreware.mpk.modelIn.InStopDTO;


public class StopRequest {

    public static InStopDTO[] getStops() throws UnirestException {
        InStopDTO[] stops;
        HttpResponse<InStopDTO[]> postResponse = Unirest.get("http://localhost:8080/stop/all").asObject(InStopDTO[].class);
        stops = postResponse.getBody();
        return stops;
    }
    public static InStopDTO[] getWorkingStops() throws UnirestException {
        InStopDTO[] stops;
        HttpResponse<InStopDTO[]> postResponse = Unirest.get("http://localhost:8080/stop/byStatus")
                .queryString("notBroken", true)
                .asObject(InStopDTO[].class);
        stops = postResponse.getBody();
        return stops;
    }
    public static void addStop(StopDTO stopDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/stop/add")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(stopDTO)
                .asJson();
    }
    public static void updateStop(InStopDTO stopDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.patch("http://localhost:8080/stop/update/{id}")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id",stopDTO.getStopId().toString())
                .body(stopDTO)
                .asJson();
    }
    public static void deleteStop(StopDTO stopDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.delete("http://localhost:8080/stop/remove/{id}")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id",stopDTO.getStopId().toString())
                .asJson();
    }
    public static void stopFailure(InStopDTO stopDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.patch("http://localhost:8080/stop/failure/{id}")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id", stopDTO.getStopId().toString())
                .asJson();

    }
    public static StopDTO getStop(InStopDTO stop) throws UnirestException {
        HttpResponse<StopDTO> postResponse = Unirest.get("http://localhost:8080/stop/{id}")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id",stop.getStopId().toString())
                .asObject(StopDTO.class);
        return postResponse.getBody();
    }
}
