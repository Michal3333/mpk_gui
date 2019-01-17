package com.snoreware.mpk.guiControllers;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.snoreware.mpk.entities.*;
import com.snoreware.mpk.model.*;
import com.snoreware.mpk.modelIn.InDriverDTO;
import com.snoreware.mpk.modelIn.InStopDTO;
import com.snoreware.mpk.request.StopRequest;
import com.snoreware.mpk.request.BusRequest;
import com.snoreware.mpk.request.DriverRequest;
import com.snoreware.mpk.request.TramRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class EditMenuController implements Initializable {
    public Label label1;
    public Label label2;
    public Label label3;
    public Label label4;
    public Label label5;
    public TextField textField1;
    public TextField textField2;
    public TextField textField3;
    public TextField textField4;
    public TextField textField5;
    public TabPane tabs;
    public ListView <Long>busList;
    public ListView <Long>tramList;
    public ListView <InDriverDTO>driverList;
    public ListView <InStopDTO>StopList;
    public CheckBox checkBox1;
    public CheckBox checkBox2;
    private ObservableList<InDriverDTO> wypelnienieDriver = FXCollections.observableArrayList();
    private ObservableList<Long> wypelnienieBus = FXCollections.observableArrayList();
    private ObservableList<InStopDTO> wypelnienieStop = FXCollections.observableArrayList();
    private ObservableList<Long> wypelnienieTram = FXCollections.observableArrayList();


    public void clear(){
        textField1.clear();
        textField2.clear();
        textField3.clear();
        textField4.clear();
        textField5.clear();
        checkBox1.setSelected(false);
        checkBox2.setSelected(false);
    }

    public void setLabelsForBus(){
        label1.setText("lowflor");
        label2.setText("przegubowy");
        checkBox1.setVisible(true);
        checkBox2.setVisible(true);
        label2.setVisible(true);
        label3.setVisible(false);
        label4.setVisible(false);
        label5.setVisible(false);
        textField1.setVisible(false);
        textField2.setVisible(false);
        textField4.setVisible(false);
        textField5.setVisible(false);
        textField3.setVisible(false);
        clear();
        updateBusList();


    }
    public void setLabelsForTram(){
        label2.setVisible(true);
        label1.setText("lowflor");
        label2.setText("ilosc wagonow");
        checkBox1.setVisible(true);
        checkBox2.setVisible(false);
        label3.setVisible(false);
        label4.setVisible(false);
        label5.setVisible(false);
        textField1.setVisible(false);
        textField2.setVisible(true);
        textField3.setVisible(false);
        textField4.setVisible(false);
        textField5.setVisible(false);
        clear();
        updateTramList();

    }
    public void setLabelsForDriver(){
        checkBox1.setVisible(false);
        checkBox2.setVisible(false);
        label2.setVisible(true);
        label3.setVisible(true);
        label4.setVisible(true);
        label5.setVisible(false);
        textField1.setVisible(true);
        textField2.setVisible(true);
        textField3.setVisible(true);
        textField4.setVisible(true);
        textField5.setVisible(false);
        label1.setText("name");
        label2.setText("surname");
        label3.setText("sex");
        label4.setText("salery");
        clear();
        updateDriverList();

    }
    public void setLabelsForStop(){
        checkBox1.setVisible(false);
        checkBox2.setVisible(false);
        label1.setText("nazwa");
        label2.setVisible(false);
        label3.setVisible(false);
        label4.setVisible(false);
        label5.setVisible(false);
        textField1.setVisible(true);
        textField2.setVisible(false);
        textField3.setVisible(false);
        textField4.setVisible(false);
        textField5.setVisible(false);
        clear();
        updateStopList();

    }

    public void updateBusList(){
        wypelnienieBus.clear();
        FailuresController.updateBus(wypelnienieBus, busList);
    }
    public void updateTramList(){
        wypelnienieTram.clear();
        FailuresController.updateTrams(wypelnienieTram, tramList);
    }
    public void updateDriverList(){
        wypelnienieDriver.clear();
        SalaryUpgradeController.updateDr(wypelnienieDriver, driverList);
    }
    public void updateStopList(){
        wypelnienieStop.clear();
        try {
            InStopDTO[] stops = StopRequest.getStops();
            wypelnienieStop.addAll(Arrays.asList(stops));
            StopList.setItems(wypelnienieStop);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }



    public void add(ActionEvent actionEvent) throws UnirestException {
       int tab = tabs.getSelectionModel().getSelectedIndex();
       String [] labels = new String[5];
       labels[0] = textField1.getText();
       labels[1] = textField2.getText();
       labels[2] = textField3.getText();
       labels[3] = textField4.getText();
       labels[4] = textField5.getText();
       boolean check1 = checkBox1.isSelected();
       boolean check2 = checkBox2.isSelected();
       if(tab == 0){
           VehicleDTO vehicleDTO = new VehicleDTO();
           vehicleDTO.setArticulated(check2);
           vehicleDTO.setLowFloor(check1);
           BusRequest.addBus(vehicleDTO);
           updateBusList();
       }
       else if(tab == 1){
           VehicleDTO vehicleDTO = new VehicleDTO();
           vehicleDTO.setLowFloor(check1);
           vehicleDTO.setNumberOfWagons(Integer.parseInt(labels[1]));
           TramRequest.addTram(vehicleDTO);
           updateTramList();
       }
       else if(tab == 2){
           DriverDTO driverDTO = new DriverDTO();
           driverDTO.setName(labels[0]);
           driverDTO.setSurname(labels[1]);
           driverDTO.setSex(labels[2]);
           driverDTO.setSalary(Float.parseFloat(labels[3]));
           DriverRequest.addDriver(driverDTO);
           updateDriverList();
       }
       else if(tab == 3){
           StopDTO stopDTO = new StopDTO();
           stopDTO.setStopName(labels[0]);
           StopRequest.addStop(stopDTO);
            updateStopList();
       }
       clear();
    }

    public void update(ActionEvent actionEvent) throws UnirestException {
        int tab = tabs.getSelectionModel().getSelectedIndex();
        String [] labels = new String[5];
        labels[0] = textField1.getText();
        labels[1] = textField2.getText();
        labels[2] = textField3.getText();
        labels[3] = textField4.getText();
        labels[4] = textField5.getText();
        boolean check1 = checkBox1.isSelected();
        boolean check2 = checkBox2.isSelected();
        if(tab == 0){
            Long id = busList.getSelectionModel().getSelectedItem();
            VehicleDTO vehicleDTO = new VehicleDTO();
            vehicleDTO.setVehicleNumber(id);
            vehicleDTO.setArticulated(check2);
            vehicleDTO.setLowFloor(check1);
            BusRequest.updateBus(vehicleDTO);
            updateBusList();
        }
        else if(tab == 1){
            Long id  = tramList.getSelectionModel().getSelectedItem();
            VehicleDTO vehicleDTO = new VehicleDTO();
            vehicleDTO.setVehicleNumber(id);
            vehicleDTO.setLowFloor(check1);
            vehicleDTO.setNumberOfWagons(Integer.parseInt(labels[1]));
            TramRequest.updateTram(vehicleDTO);
            updateTramList();
        }
        else if(tab == 2){
            DriverDTO driver = DriverRequest.getDriver(driverList.getSelectionModel().getSelectedItem());
            DriverDTO driverDTO = new DriverDTO();
            driverDTO.setDriverId(driver.getDriverId());
            driverDTO.setName(labels[0]);
            driverDTO.setSurname(labels[1]);
            driverDTO.setSex(labels[2]);
            driverDTO.setSalary(Float.parseFloat(labels[3]));
            DriverRequest.updateDriver(driverDTO);
            updateDriverList();
        }
        else if(tab == 3){
            InStopDTO stop =StopList.getSelectionModel().getSelectedItem();
            stop.setStopId(stop.getStopId());
            stop.setStopName(labels[0]);
            StopRequest.updateStop(stop);
            updateStopList();
        }

        clear();
    }

    public void delete(ActionEvent actionEvent) throws UnirestException {
        int tab = tabs.getSelectionModel().getSelectedIndex();


        if(tab == 0){
            Long id = busList.getSelectionModel().getSelectedItem();
            VehicleDTO vehicleDTO = new VehicleDTO();
            vehicleDTO.setVehicleNumber(id);
            BusRequest.deleteBus(vehicleDTO);
            updateBusList();
        }
        else if(tab == 1){
            Long id = tramList.getSelectionModel().getSelectedItem();
            VehicleDTO vehicleDTO = new VehicleDTO();
            vehicleDTO.setVehicleNumber(id);
            TramRequest.deleteTram(vehicleDTO);
            updateTramList();
        }
        else if(tab == 2){
            InDriverDTO driver = driverList.getSelectionModel().getSelectedItem();
            DriverDTO driverDTO = new DriverDTO();
            driverDTO.setDriverId(driver.getDriverId());
            DriverRequest.deleteDriver(driverDTO);
            updateDriverList();
        }
        else if(tab == 3){
            InStopDTO stop = StopList.getSelectionModel().getSelectedItem();
            StopDTO stopDTO = new StopDTO();
            stopDTO.setStopId(stop.getStopId());
            StopRequest.deleteStop(stopDTO);
            updateStopList();
        }

        clear();
    }

    public void selectTram(Event event) {
       setLabelsForTram();
    }

    public void selectBus(Event event) {
       setLabelsForBus();
    }

    public void selectDriver(Event event) {
       setLabelsForDriver();
    }

    public void selectStop(Event event) {
        setLabelsForStop();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      setLabelsForBus();
    }

    public void wybierzBus(MouseEvent mouseEvent) throws UnirestException {
        Long id = busList.getSelectionModel().getSelectedItem();
        BusDTO bus = BusRequest.getBus(id);
        checkBox1.setSelected(bus.getLowFloor());
        checkBox2.setSelected(bus.getArticulated());
    }

    public void wybierzTram(MouseEvent mouseEvent) throws UnirestException {
        Long id = tramList.getSelectionModel().getSelectedItem();
        TramDTO tram = TramRequest.getTram(id);
        checkBox1.setSelected(tram.getLowFloor());
        textField2.setText(Integer.toString(tram.getNumberOfWagons()));
    }

    public void wybierzDriver(MouseEvent mouseEvent) throws UnirestException {
        InDriverDTO driverid = driverList.getSelectionModel().getSelectedItem();
        DriverDTO driver = DriverRequest.getDriver(driverid);
        textField1.setText(driver.getName());
        textField2.setText(driver.getSurname());
        textField3.setText(driver.getSex());
        textField4.setText(driver.getSalary().toString());

    }

    public void wybierzStop(MouseEvent mouseEvent) throws UnirestException {
        InStopDTO stop= StopList.getSelectionModel().getSelectedItem();
        textField1.setText(stop.getStopName());
    }


}
