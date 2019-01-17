package com.snoreware.mpk.guiControllers;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.snoreware.mpk.entities.DriverEntity;
import com.snoreware.mpk.model.DriverDTO;
import com.snoreware.mpk.modelIn.InDriverDTO;
import com.snoreware.mpk.request.StopRequest;
import com.snoreware.mpk.request.DriverRequest;

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
    public ListView<InDriverDTO> driverList;
    public Label name;
    public Label surname;
    public Label sex;
    public Label salary;
    public Label seniority;
    public TextField upgrade;
    private ObservableList<InDriverDTO> wypelnienieDriver = FXCollections.observableArrayList();

    public void updateDriverList(){
        wypelnienieDriver.clear();
        updateDr(wypelnienieDriver, driverList);
    }

    static void updateDr(ObservableList<InDriverDTO> wypelnienieDriver, ListView<InDriverDTO> driverList) {
        try {
            InDriverDTO[] drvers = DriverRequest.getDrivers();
            wypelnienieDriver.addAll(Arrays.asList(drvers));
            driverList.setItems(wypelnienieDriver);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    public void zatwierdz(ActionEvent actionEvent) throws UnirestException {
        InDriverDTO driverid = driverList.getSelectionModel().getSelectedItem();
        DriverDTO driver = DriverRequest.getDriver(driverid);
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setDriverId(driver.getDriverId());
        driverDTO.setSalary(driver.getSalary() + Float.parseFloat(upgrade.getText()));
        DriverRequest.updateDriver(driverDTO);
        updateDriverList();
        name.setVisible(false);
        surname.setVisible(false);
        sex.setVisible(false);
        salary.setVisible(false);
        seniority.setVisible(false);
    }

    public void selectDriver(MouseEvent mouseEvent) throws UnirestException {
        name.setVisible(true);
        surname.setVisible(true);
        sex.setVisible(true);
        salary.setVisible(true);
        seniority.setVisible(true);
        InDriverDTO driverid = driverList.getSelectionModel().getSelectedItem();
        DriverDTO driver = DriverRequest.getDriver(driverid);
        name.setText("Imie: "+ driver.getName());
        surname.setText("Nazwisko :"+ driver.getSurname());
        sex.setText("Plec: "+ driver.getSex());
        salary.setText("Placa: "+ driver.getSalary().toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateDriverList();
    }
}
