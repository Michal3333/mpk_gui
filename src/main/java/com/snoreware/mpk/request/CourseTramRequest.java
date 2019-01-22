package com.snoreware.mpk.request;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.snoreware.mpk.model.CourseDTO;
import com.snoreware.mpk.model.DriverDTO;
import com.snoreware.mpk.modelIn.InCourseDTO;

import java.util.UUID;

public class CourseTramRequest {
    public static InCourseDTO[] getTramCourses() throws UnirestException {
        HttpResponse<InCourseDTO[]> postResponse = Unirest.get("http://localhost:8080/tramCourse/all")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .asObject(InCourseDTO[].class);
        return postResponse.getBody();
    }
    public static void addTramCourse(CourseDTO courseDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/tramCourse/add")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(courseDTO)
                .asJson();
    }
    public static void deleteTramCourse(InCourseDTO courseDTO) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.delete("http://localhost:8080/tramCourse/delete/{id}")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id",courseDTO.getCourseId().toString())
                .asJson();
    }
    public static void updateDriver(DriverDTO driverDTO, UUID id) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/tramCourse/{id}/driver")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id", id.toString())
                .body(driverDTO)
                .asJson();
    }
    public static void updateRoute(Long is, UUID id) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/tramCourse/{id}/route")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id", id.toString())
                .queryString("routeNumber",is)
                .asJson();
    }
    public static void updateTram(Long busId, UUID id) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/tranCourse/{id}/tram")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id", id.toString())
                .queryString("vehicleNumber",busId)
                .asJson();
    }
}
