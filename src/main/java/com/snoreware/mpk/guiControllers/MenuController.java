package com.snoreware.mpk.guiControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    public static Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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


    public void przejdzDoEdycji(ActionEvent actionEvent) {
        try {
            MenuController.stage.setScene(createEditScene());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Scene createEditScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        return scene;
    }

    public void przejdzDoAwari(ActionEvent actionEvent) {
        try {
            MenuController.stage.setScene(createFailureScene());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Scene createFailureScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Failures.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        return scene;
    }

    public void przejdzPodwyzki(ActionEvent actionEvent) {
        try {
            MenuController.stage.setScene(createUpgradeScene());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Scene createUpgradeScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SalaryUpgrade.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        return scene;
    }

    public void przejdzDoEdycjitras(ActionEvent actionEvent) {
        try {
            MenuController.stage.setScene(createRouteScene());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Scene createRouteScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditRoute.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        return scene;
    }

    public void przjdzKursy(ActionEvent actionEvent) {
        try {
            MenuController.stage.setScene(createCourse());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Scene createCourse() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CourseEdit.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        return scene;
    }
}
