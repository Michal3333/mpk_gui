package com.snoreware.mpk.guiControllers;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.snoreware.mpk.entities.BusEntity;
import com.snoreware.mpk.entities.StopEntity;
import com.snoreware.mpk.entities.TramEntity;
import com.snoreware.mpk.model.BusDTO;
import com.snoreware.mpk.model.StopDTO;
import com.snoreware.mpk.model.TramDTO;
import com.snoreware.mpk.model.VehicleDTO;
import com.snoreware.mpk.modelIn.InStopDTO;
import com.snoreware.mpk.request.StopRequest;
import com.snoreware.mpk.request.BusRequest;
import com.snoreware.mpk.request.TramRequest;
import com.snoreware.mpk.request.DriverRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class FailuresController implements Initializable {
    public TabPane tabs;
    public ListView<InStopDTO> stopsL;
    public ListView<Long> busesL;
    public ListView<Long> tramsL;
    public Label label;
    public Button button;
    private ObservableList<Long> wypelnienieBus = FXCollections.observableArrayList();
    private ObservableList<InStopDTO> wypelnienieStop = FXCollections.observableArrayList();
    private ObservableList<Long> wypelnienieTram = FXCollections.observableArrayList();

    public void zglos(ActionEvent actionEvent) throws UnirestException {
        int tab = tabs.getSelectionModel().getSelectedIndex();
        if(tab == 0){
            InStopDTO stop = stopsL.getSelectionModel().getSelectedItem();
            StopRequest.stopFailure(stop);
            updateStopList();
        }
        else if(tab == 1){
            Long id = busesL.getSelectionModel().getSelectedItem();
            BusRequest.busFailure(id);
            updateBusList();
        }
        else if(tab == 2){
            Long id = tramsL.getSelectionModel().getSelectedItem();
            TramRequest.tramFailure(id);
            updateTramList();
        }
        button.setVisible(false);
        label.setVisible(false);
    }

    public void tramsClicked(MouseEvent mouseEvent) throws UnirestException {
        button.setVisible(true);
        label.setVisible(true);
        Long id = tramsL.getSelectionModel().getSelectedItem();
        TramDTO tram = TramRequest.getTram(id);
        if(tram.getBreakdown()){
            label.setText("awaria");
            button.setText("napraw");
        }
        else{
            label.setText("dziala");
            button.setText("zglos awarie");
        }
    }

    public void busesClicked(MouseEvent mouseEvent) throws UnirestException {
        button.setVisible(true);
        label.setVisible(true);
        Long id = busesL.getSelectionModel().getSelectedItem();
        BusDTO bus = BusRequest.getBus(id);
        if(bus.getBreakdown()){
            label.setText("awaria bus");
            button.setText("napraw");
        }
        else{
            label.setText("dziala");
            button.setText("zglos awarie");
        }
    }

    public void stopsClicked(MouseEvent mouseEvent) throws UnirestException {
        button.setVisible(true);
        label.setVisible(true);
        InStopDTO stopid = stopsL.getSelectionModel().getSelectedItem();
        StopDTO stop = StopRequest.getStop(stopid);
        if(stop.getStopBreakdown()){
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
            InStopDTO[] stops = StopRequest.getStops();
            wypelnienieStop.addAll(Arrays.asList(stops));
            stopsL.setItems(wypelnienieStop);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    static void updateTrams(ObservableList<Long> wypelnienieTram, ListView<Long> tramsL) {
        try {
            Long[] trams = TramRequest.getTrams();
            wypelnienieTram.addAll(Arrays.asList(trams));
            tramsL.setItems(wypelnienieTram);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    static void updateBus(ObservableList<Long> wypelnienieBus, ListView<Long> busesL) {
        try {
            Long[] buses =BusRequest.getBuses();
            wypelnienieBus.addAll(Arrays.asList(buses));
            busesL.setItems(wypelnienieBus);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateStopList();
    }
}
