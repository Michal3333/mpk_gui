package com.snoreware.mpk.guiControllers;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.snoreware.mpk.model.DriverDTO;
import com.snoreware.mpk.modelIn.InDriverDTO;
import com.snoreware.mpk.request.DriverRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
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

    private void showAlert(String info) {
        badDataWarning(info);
    }

    static void badDataWarning(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Błąd wprowadzonych danych");
        alert.setContentText(info);
        alert.showAndWait();
    }

    private boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private void updateDriverList() {
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
        if (driverList.getSelectionModel().getSelectedItem() != null) {
            if (isNumeric(upgrade.getText()) && !upgrade.getText().isEmpty()) {
                InDriverDTO driverid = driverList.getSelectionModel().getSelectedItem();
                DriverDTO driver = DriverRequest.getDriver(driverid.getDriverId());
                DriverDTO driverDTO = new DriverDTO();
                driverDTO.setDriverId(driver.getDriverId());
                if (driver.getSalary() + Float.parseFloat(upgrade.getText()) > 0) {
                    driverDTO.setSalary(driver.getSalary() + Float.parseFloat(upgrade.getText()));
                    DriverRequest.updateDriver(driverDTO);
                    updateDriverList();
                    name.setVisible(false);
                    surname.setVisible(false);
                    sex.setVisible(false);
                    salary.setVisible(false);
                    seniority.setVisible(false);
                } else showAlert("Kierowca nie może miec ujemnego wynagrodzenia");
            } else showAlert("Nie wprowadzono wartości pola zmiana lub wprowadzona wartość jest niepoprawna");
        }

    }

    public void selectDriver(MouseEvent mouseEvent) throws UnirestException {
        if (driverList.getSelectionModel().getSelectedItem() != null) {
            name.setVisible(true);
            surname.setVisible(true);
            sex.setVisible(true);
            salary.setVisible(true);
            seniority.setVisible(true);
            InDriverDTO driverid = driverList.getSelectionModel().getSelectedItem();
            DriverDTO driver = DriverRequest.getDriver(driverid.getDriverId());
            name.setText("Imię: " + driver.getName());
            surname.setText("Nazwisko :" + driver.getSurname());
            sex.setText("Płeć: " + driver.getSex());
            salary.setText("Płaca: " + driver.getSalary().toString());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateDriverList();
    }

    public void goHome(ActionEvent actionEvent) {
        try {
            MenuController.stage.setScene(createMenuScne());
        } catch (IOException e) {
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
