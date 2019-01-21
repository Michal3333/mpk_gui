package com.snoreware.mpk.request;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.snoreware.mpk.model.BusDTO;
import com.snoreware.mpk.model.CourseDTO;
import com.snoreware.mpk.model.DriverDTO;
import com.snoreware.mpk.model.StopDTO;
import com.snoreware.mpk.modelIn.InCourseDTO;

import java.util.UUID;

public class CourseBusRequest {
    public static InCourseDTO[] getBusCourses() throws UnirestException {
        HttpResponse<InCourseDTO[]> postResponse = Unirest.get("http://localhost:8080/busCourse/all")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .asObject(InCourseDTO[].class);
        return postResponse.getBody();
    }
    public static void addBusCourse(CourseDTO courseDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/busCourse/add")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(courseDTO)
                .asJson();
    }
    public static void deleteBusCourse(InCourseDTO courseDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.delete("http://localhost:8080/busCourse/delete/{id}")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id",courseDTO.getCourseId().toString())
                .asJson();
    }
    public static void updateDriver(DriverDTO driverDTO, UUID id) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/busCourse/{id}/driver")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id", id.toString())
                .body(driverDTO)
                .asJson();
    }
    public static void updateRoute(StopDTO stopDTO, UUID id) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/busCourse/{id}/route")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id", id.toString())
                .body(stopDTO)
                .asJson();
    }
    public static void updateBus(BusDTO busDTO, UUID id) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/busCourse/{id}/bus")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id", id.toString())
                .body(busDTO)
                .asJson();
    }

}
