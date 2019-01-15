package com.snoreware.mpk.guiControllers;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.snoreware.mpk.entities.DriverEntity;
import com.snoreware.mpk.model.DriverDTO;
import com.snoreware.mpk.request.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class SalaryUpgradeController implements Initializable {
    public ListView<DriverEntity> driverList;
    public Label name;
    public Label surname;
    public Label sex;
    public Label salary;
    public Label seniority;
    public TextField upgrade;
    private ObservableList<DriverEntity> wypelnienieDriver = FXCollections.observableArrayList();

    public void updateDriverList(){
        wypelnienieDriver.clear();
        updateDr(wypelnienieDriver, driverList);
    }

    static void updateDr(ObservableList<DriverEntity> wypelnienieDriver, ListView<DriverEntity> driverList) {
        try {
            DriverEntity[] drvers = Request.getDrivers();
            wypelnienieDriver.addAll(Arrays.asList(drvers));
            driverList.setItems(wypelnienieDriver);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    public void zatwierdz(ActionEvent actionEvent) throws UnirestException {
        DriverEntity driver = driverList.getSelectionModel().getSelectedItem();
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setDriverId(driver.getDriverId());
        driverDTO.setSalary(driver.getSalary() + Float.parseFloat(upgrade.getText()));
        Request.updateDriver(driverDTO);
        updateDriverList();
        name.setVisible(false);
        surname.setVisible(false);
        sex.setVisible(false);
        salary.setVisible(false);
        seniority.setVisible(false);
    }

    public void selectDriver(MouseEvent mouseEvent) {
        name.setVisible(true);
        surname.setVisible(true);
        sex.setVisible(true);
        salary.setVisible(true);
        seniority.setVisible(true);
        DriverEntity driver = driverList.getSelectionModel().getSelectedItem();
        name.setText("Imie: "+ driver.getName());
        surname.setText("Nazwisko :"+ driver.getSurname());
        sex.setText("Plec: "+ driver.getSex());
        salary.setText("Placa: "+ driver.getSalary().toString());
        seniority.setText("Staz: "+ driver.getSeniority());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateDriverList();
    }
}
