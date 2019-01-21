package com.snoreware.mpk.guiControllers;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.snoreware.mpk.model.CourseDTO;
import com.snoreware.mpk.model.DriverDTO;
import com.snoreware.mpk.model.RouteDTO;
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
import jdk.nashorn.internal.ir.RuntimeNode;
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
    private ObservableList<InCourseDTO> wypelnieniebus = FXCollections.observableArrayList();
    private ObservableList<InDriverDTO> wypelnienieDriver = FXCollections.observableArrayList();
    private ObservableList<Long> wypelnienieRoute = FXCollections.observableArrayList();
    private ObservableList<Long> wypelnienievehicle = FXCollections.observableArrayList();
    int editMode;



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
        CourseBusRequest.deleteBusCourse(BusList.getSelectionModel().getSelectedItem());
    }

    public void add(ActionEvent actionEvent) throws UnirestException {
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
    public void editCourse(ActionEvent actionEvent) throws UnirestException {
        InCourseDTO course = BusList.getSelectionModel().getSelectedItem();

        if(driverCombo.getSelectionModel().getSelectedItem().getDriverId() == course.getDriverId()){
            DriverDTO driver = DriverRequest.getDriver(driverCombo.getSelectionModel().getSelectedItem().getDriverId());
            CourseBusRequest.updateDriver(driver, course.getCourseId());
        }
        if(routeCombo.getSelectionModel().getSelectedItem() == course.getRouteNumber()){
//            RouteDTO routeDTO = RouteRequest.get
        }

    }

    public void edit(ActionEvent actionEvent) throws UnirestException {
        if(editMode == 0){
            updateDriverCombo();
            updateRouteCombo();
            updateVehicleCombo();
            driverCombo.setVisible(true);
            vehicleCombo.setVisible(true);
            routeCombo.setVisible(true);
            add.setVisible(true);
            delete.setVisible(true);
            editMode = 1;
            driverLAbel.setText("Kierowca : ");
            routeLabel.setText("Trasa : ");
            vehicleLAble.setText("pojazd : ");
        }
        else{
            driverLAbel.setText("");
            routeLabel.setText("");
            vehicleLAble.setText("");
            driverCombo.setVisible(false);
            vehicleCombo.setVisible(false);
            routeCombo.setVisible(false);
            add.setVisible(false);
            delete.setVisible(false);
            editMode = 0;
        }

    }

    public void selectBus(MouseEvent mouseEvent) throws UnirestException {
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
            driverCombo.setVisible(false);
            vehicleCombo.setVisible(false);
            routeCombo.setVisible(false);
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
}
