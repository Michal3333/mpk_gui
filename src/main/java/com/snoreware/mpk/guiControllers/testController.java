package com.snoreware.mpk.guiControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.snoreware.mpk.entities.BusCourseEntity;
import com.snoreware.mpk.entities.BusEntity;
import com.snoreware.mpk.entities.DriverEntity;
import com.snoreware.mpk.model.DriverDTO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class testController implements Initializable {
    public static Stage stage;
    public Button button;
    public Label label;


    public void robcos(ActionEvent actionEvent) throws IOException, UnirestException {

        long xd = 1;
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setName("name");
        driverDTO.setSurname("surname");
        driverDTO.setSalary(100.f);
        driverDTO.setSex("male");

        HttpResponse<JsonNode> postResponse = Unirest.post("http://localhost:8080/driver/add")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(driverDTO)
                .asJson();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Only one time
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void odczytaj(ActionEvent actionEvent) throws UnirestException {
        DriverEntity[] busCourses;


        HttpResponse<DriverEntity[]> postResponse = Unirest.get("http://localhost:8080/driver/all").asObject(DriverEntity[].class);
//        JSONObject jsonObject = new JSONObject(postResponse.getBody());
//        JSONArray array = jsonObject.getJSONArray("array");
//        JSONArray jarray = array.getJSONObject(0).getJSONArray("items");
//        String id = "";
//        int j = jarray.length();
//        if (j>0){
//            id = jarray.getJSONObject(0).getString("id");
//        }
         busCourses= postResponse.getBody();

        label.setText(busCourses[0].getName().toString());


    }



}
