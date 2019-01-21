package com.snoreware.mpk.guiControllers;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.snoreware.mpk.model.CourseDTO;
import com.snoreware.mpk.model.DriverDTO;
import com.snoreware.mpk.modelIn.InCourseDTO;
import com.snoreware.mpk.modelIn.InDriverDTO;
import com.snoreware.mpk.request.BusRequest;
import com.snoreware.mpk.request.DriverRequest;
import com.snoreware.mpk.request.RouteRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import com.snoreware.mpk.request.CourseBusRequest;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseEditController implements Initializable {
    public Label driverLAbel;
    public Label routeLabel;
    public Label vehicleLAble;
    public ComboBox <Long> routeCombo;
    public ComboBox <InDriverDTO>driverCombo;
    public ComboBox <Long>vehicleCombo;
    public CheckBox articulated;
    public CheckBox lowFlor;
    public Button edit;
    public Button add;
    public Button delete;
    public ListView <InCourseDTO> BusList;
    public Button editCourse;
    public TextField stazUstawianie;
    private ObservableList<InCourseDTO> wypelnieniebus = FXCollections.observableArrayList();
    private ObservableList<InDriverDTO> wypelnienieDriver = FXCollections.observableArrayList();
    private ObservableList<Long> wypelnienieRoute = FXCollections.observableArrayList();
    private ObservableList<Long> wypelnienievehicle = FXCollections.observableArrayList();
    private int editMode;
    public boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
    public void showAlert(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Blad wprowadzonych danych");
        alert.setContentText(info);
        alert.showAndWait();
    }



    public void updateCourseTab() throws UnirestException {
        wypelnieniebus.setAll(CourseBusRequest.getBusCourses());
        BusList.setItems(wypelnieniebus);
    }
    public  void updateDriverCombo() throws UnirestException {
        wypelnienieDriver.setAll(DriverRequest.getAviableDrivers());
        driverCombo.setItems(wypelnienieDriver);
    }
    public void updateRouteCombo() throws UnirestException {
        wypelnienieRoute.setAll(RouteRequest.getRoutes());
        routeCombo.setItems(wypelnienieRoute);
    }
    public void updateVehicleCombo() throws UnirestException {
        wypelnienievehicle.setAll(BusRequest.getWorkingBuses());
        vehicleCombo.setItems(wypelnienievehicle);
    }
    public void updateVehicleComboLowFloor() throws UnirestException {
        wypelnienievehicle.setAll(BusRequest.getLowFlorBuses());
        vehicleCombo.setItems(wypelnienievehicle);
    }
    public void updateVehicleComboArticulated() throws UnirestException {
        wypelnienievehicle.setAll(BusRequest.getArticulatedBuses());
        vehicleCombo.setItems(wypelnienievehicle);
    }
    public void updateVehicleComboArticulatedAndLow() throws UnirestException {
        wypelnienievehicle.setAll(BusRequest.getLowFlorandArticulatedBuses());
        vehicleCombo.setItems(wypelnienievehicle);
    }


    public void delete(ActionEvent actionEvent) throws UnirestException {
        if(BusList.getSelectionModel().getSelectedItem() != null){
            CourseBusRequest.deleteBusCourse(BusList.getSelectionModel().getSelectedItem());
        }
        else showAlert("nie zaznaczono kursu do zmiany");
    }

    public boolean checkContainers(){
        String blad ="";
        if(routeCombo.getSelectionModel().getSelectedItem() == null) blad+="nie wybrano trasy\n";
        if(driverCombo.getSelectionModel().getSelectedItem() == null)blad+="nie wybrano kierowcy\n";
        if(vehicleCombo.getSelectionModel().getSelectedItem() == null)blad+="nie wybrano pojazdu\n";
        if(!blad.isEmpty())showAlert(blad);
        return blad.isEmpty();
    }

    public void add(ActionEvent actionEvent) throws UnirestException {
        if(checkContainers()){
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setArticulatedNeeded(articulated.isSelected());
            courseDTO.setLowFloorNeeded(lowFlor.isSelected());
            courseDTO.setRouteNumber(routeCombo.getSelectionModel().getSelectedItem());
            courseDTO.setDriverId(driverCombo.getSelectionModel().getSelectedItem().getDriverId());
            courseDTO.setVehicleNumber(vehicleCombo.getSelectionModel().getSelectedItem());
            System.out.println(courseDTO);
//        courseDTO.setManyWagonsNeeded(false);
            CourseBusRequest.addBusCourse(courseDTO);
        }
    }
    public void editCourse(ActionEvent actionEvent) throws UnirestException {
        if(BusList.getSelectionModel().getSelectedItem() != null){
            if(checkContainers()){
                InCourseDTO course = BusList.getSelectionModel().getSelectedItem();
                if(driverCombo.getSelectionModel().getSelectedItem().getDriverId() != course.getDriverId()){
                    DriverDTO driver = DriverRequest.getDriver(driverCombo.getSelectionModel().getSelectedItem().getDriverId());
                    CourseBusRequest.updateDriver(driver, course.getCourseId());
                }
                if(routeCombo.getSelectionModel().getSelectedItem() != course.getRouteNumber()){
                    CourseBusRequest.updateRoute(routeCombo.getSelectionModel().getSelectedItem(),course.getCourseId());
                }
                if(vehicleCombo.getSelectionModel().getSelectedItem() != course.getVehicleNumber()){
                    CourseBusRequest.updateBus(vehicleCombo.getSelectionModel().getSelectedItem(),course.getCourseId());
                }
            }
        }
        else showAlert("nie wybrano kursu do edytcji");

    }

    public void edit(ActionEvent actionEvent) throws UnirestException {
        if(editMode == 0){
            updateDriverCombo();
            updateRouteCombo();
            updateVehicleCombo();
            driverCombo.setVisible(true);
            vehicleCombo.setVisible(true);
            routeCombo.setVisible(true);
            editCourse.setVisible(true);
            stazUstawianie.setVisible(true);
            add.setVisible(true);
            delete.setVisible(true);
            editMode = 1;
            driverLAbel.setText("Kierowca : ");
            routeLabel.setText("Trasa : ");
            vehicleLAble.setText("pojazd : ");
        }
        else{
            editCourse.setVisible(false);
            driverLAbel.setText("");
            routeLabel.setText("");
            vehicleLAble.setText("");
            driverCombo.setVisible(false);
            vehicleCombo.setVisible(false);
            routeCombo.setVisible(false);
            stazUstawianie.setVisible(false);

            add.setVisible(false);
            delete.setVisible(false);
            editMode = 0;
        }

    }

    public void selectBus(MouseEvent mouseEvent) throws UnirestException {
        if(BusList.getSelectionModel().getSelectedItem() != null){
            if(editMode == 1){
                InCourseDTO selected = BusList.getSelectionModel().getSelectedItem();
                vehicleCombo.getSelectionModel().select(selected.getVehicleNumber());
                DriverDTO driver = DriverRequest.getDriver(selected.getDriverId());
                InDriverDTO in = new InDriverDTO();
                in.setDriverId(driver.getDriverId());
                in.setName(driver.getName());
                in.setSurname(driver.getSurname());
                driverCombo.getSelectionModel().select(in);
                routeCombo.getSelectionModel().select(selected.getRouteNumber());
                lowFlor.setSelected(selected.getLowFloorNeeded());
                articulated.setSelected(selected.getArticulated());
            }
            else{
                InCourseDTO selected = BusList.getSelectionModel().getSelectedItem();
                DriverDTO driver = DriverRequest.getDriver(selected.getDriverId());
                InDriverDTO in = new InDriverDTO();
                in.setDriverId(driver.getDriverId());
                in.setName(driver.getName());
                in.setSurname(driver.getSurname());
                driverLAbel.setText("Kierowca : " +in.toString());
                vehicleLAble.setText("Pojazd : " + selected.getVehicleNumber().toString());
                routeLabel.setText("Trasa : " + selected.getRouteNumber().toString());
            }
        }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            driverLAbel.setText("");
            routeLabel.setText("");
            vehicleLAble.setText("");
            stazUstawianie.setVisible(false);
            driverCombo.setVisible(false);
            vehicleCombo.setVisible(false);
            routeCombo.setVisible(false);
            editCourse.setVisible(false);
            add.setVisible(false);
            delete.setVisible(false);
            editMode = 0;
            updateCourseTab();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

    }



    public void zmienArticulated(ActionEvent actionEvent) throws UnirestException {
        vehicleCombo.getSelectionModel().clearSelection();
        if(articulated.isSelected()){
            if(lowFlor.isSelected()){
                updateVehicleComboArticulatedAndLow();
            }
            else {
                updateVehicleComboArticulated();
            }
        }
        else{
            if(lowFlor.isSelected()){
                updateVehicleComboLowFloor();
            }
            else {
                updateVehicleCombo();
            }
        }
    }

    public void zmienLowFloor(ActionEvent actionEvent) throws UnirestException {
        vehicleCombo.getSelectionModel().clearSelection();
        if(lowFlor.isSelected()){
            if(articulated.isSelected()){
                updateVehicleComboArticulatedAndLow();
            }
            else {
                updateVehicleComboLowFloor();
            }
        }
        else{
            if(articulated.isSelected()){
                updateVehicleComboLowFloor();
            }
            else {
                updateVehicleCombo();
            }
        }
    }

    public void ustawMinstaz(MouseEvent mouseEvent) throws UnirestException {
        if(!stazUstawianie.getText().isEmpty() && isNumeric(stazUstawianie.getText().toString())){
            wypelnienieDriver.setAll(DriverRequest.getDriverswithSeniority(Integer.parseInt(stazUstawianie.getText())));
        }

    }
}
