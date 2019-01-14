package com.snoreware.mpk.guiControllers;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.snoreware.mpk.entities.BusEntity;
import com.snoreware.mpk.entities.StopEntity;
import com.snoreware.mpk.entities.TramEntity;
import com.snoreware.mpk.model.StopDTO;
import com.snoreware.mpk.model.VehicleDTO;
import com.snoreware.mpk.request.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;

import java.util.Arrays;

public class FailuresController {
    public TabPane tabs;
    public ListView<StopEntity> stopsL;
    public ListView<BusEntity> busesL;
    public ListView<TramEntity> tramsL;
    public Label label;
    public Button button;
    private ObservableList<BusEntity> wypelnienieBus = FXCollections.observableArrayList();
    private ObservableList<StopEntity> wypelnienieStop = FXCollections.observableArrayList();
    private ObservableList<TramEntity> wypelnienieTram = FXCollections.observableArrayList();

    public void zglos(ActionEvent actionEvent) throws UnirestException {
        int tab = tabs.getSelectionModel().getSelectedIndex();
        if(tab == 0){
            StopEntity stop = stopsL.getSelectionModel().getSelectedItem();
            StopDTO stopDTO = new StopDTO();
            stopDTO.setStopId(stop.getStopId());
            Request.stopFailure(stopDTO);
            updateStopList();
        }
        else if(tab == 1){
            BusEntity bus = busesL.getSelectionModel().getSelectedItem();
            VehicleDTO vehicleDTO = new VehicleDTO();
            vehicleDTO.setVehicleNumber(bus.getVehicleNumber());
            Request.busFailure(vehicleDTO);
            updateBusList();
        }
        else if(tab == 2){
            TramEntity tram = tramsL.getSelectionModel().getSelectedItem();
            VehicleDTO vehicleDTO = new VehicleDTO();
            vehicleDTO.setVehicleNumber(tram.getVehicleNumber());
            Request.tramFailure(vehicleDTO);
            updateTramList();
        }
    }

    public void tramsClicked(MouseEvent mouseEvent) {
        button.setVisible(true);
        label.setVisible(true);
        TramEntity tram = tramsL.getSelectionModel().getSelectedItem();
        if(tram.getVehicleBreakdown()){
            label.setText("awaria");
            button.setText("napraw");
        }
        else{
            label.setText("dziala");
            button.setText("zglos awarie");
        }
    }

    public void busesClicked(MouseEvent mouseEvent) {
        button.setVisible(true);
        label.setVisible(true);
        BusEntity bus = busesL.getSelectionModel().getSelectedItem();
        if(bus.getVehicleBreakdown()){
            label.setText("awaria bus");
            button.setText("napraw");
        }
        else{
            label.setText("dziala");
            button.setText("zglos awarie");
        }
    }

    public void stopsClicked(MouseEvent mouseEvent) {
        button.setVisible(true);
        label.setVisible(true);
        StopEntity stop = stopsL.getSelectionModel().getSelectedItem();
        if(stop.isStopBreakdown()){
            label.setText("awaria");
            button.setText("napraw");
        }
        else{
            label.setText("dziala");
            button.setText("zglos awarie");
        }
    }

    public void selectStop(Event event) {
        updateStopList();
        button.setVisible(false);
        label.setVisible(false);
    }

    public void selectBus(Event event) {
        updateBusList();
        button.setVisible(false);
        label.setVisible(false);
    }

    public void selectTram(Event event) {
        updateTramList();
        button.setVisible(false);
        label.setVisible(false);
    }

    public void updateBusList(){
        wypelnienieBus.clear();
        updateBus(wypelnienieBus, busesL);
    }

    public void updateTramList(){
        wypelnienieTram.clear();
        updateTrams(wypelnienieTram, tramsL);
    }

    public void updateStopList(){
        wypelnienieStop.clear();
        try {
            StopEntity[] stops = Request.getStops();
            wypelnienieStop.addAll(Arrays.asList(stops));
            stopsL.setItems(wypelnienieStop);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    static void updateTrams(ObservableList<TramEntity> wypelnienieTram, ListView<TramEntity> tramsL) {
        try {
            TramEntity[] trams = Request.getTrams();
            wypelnienieTram.addAll(Arrays.asList(trams));
            tramsL.setItems(wypelnienieTram);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    static void updateBus(ObservableList<BusEntity> wypelnienieBus, ListView<BusEntity> busesL) {
        try {
            BusEntity[] buses = Request.getBuses();
            wypelnienieBus.addAll(Arrays.asList(buses));
            busesL.setItems(wypelnienieBus);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
