package com.snoreware.mpk.guiControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class CourseEditController {
    public Label driverLAbel;
    public Label routeLabel;
    public Label vehicleLAble;
    public ComboBox routeCombo;
    public ComboBox driverCombo;
    public ComboBox vehicleCombo;
    public CheckBox articulated;
    public CheckBox lowFlor;
    public Button edit;
    public Button add;
    public Button delete;
    public ListView BusList;

    public void delete(ActionEvent actionEvent) {
    }

    public void add(ActionEvent actionEvent) {
    }

    public void edit(ActionEvent actionEvent) {
    }

    public void selectBus(MouseEvent mouseEvent) {
    }

    public void goHome(ActionEvent actionEvent) {
        try {
            MenuController.stage.setScene(createMenuScne());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Scene createMenuScne() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        return scene;
    }
}
