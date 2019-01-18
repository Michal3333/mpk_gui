package com.snoreware.mpk.request;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.snoreware.mpk.model.StopDTO;
import com.snoreware.mpk.modelIn.InStopDTO;

import java.util.UUID;

public class RouteRequest {
    public static void addRoute(Long number) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/route/add/{id}")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id",number.toString())
                .asJson();
    }
    public static void removeRoute(Long number) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.delete("http://localhost:8080/route/remove/{id}")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id",number.toString())
                .asJson();
    }
    public static Long[] getRoutes() throws UnirestException {
        HttpResponse<Long[]> postResponse = Unirest.get("http://localhost:8080/route/all")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .asObject(Long[].class);
        return  postResponse.getBody();
    }
    public static void addStopsToRoute(UUID[] lista, Long nr) throws UnirestException {
        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/routeStops/modify/{id}")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id",nr.toString())
                .body(lista)
                .asJson();

    }
    public static InStopDTO[] getStopsOnRoute(Long number) throws UnirestException {
        HttpResponse<InStopDTO[]> postResponse = Unirest.get("http://localhost:8080/routeStops/{id}")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .routeParam("id",number.toString())
                .asObject(InStopDTO[].class);
        return  postResponse.getBody();
    }

}
