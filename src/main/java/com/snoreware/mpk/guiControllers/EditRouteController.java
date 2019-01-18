package com.snoreware.mpk.guiControllers;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.snoreware.mpk.modelIn.InStopDTO;
import com.snoreware.mpk.request.RouteRequest;
import com.snoreware.mpk.request.StopRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class EditRouteController implements Initializable {
    public ListView<Long> ListaTras;
    public ListView<InStopDTO> ListaSops;
    public Button addRoute;
    public Button clear;
    public Button editmode;
    public Button delete;
    private ObservableList<InStopDTO> wypelnienieStop = FXCollections.observableArrayList();
    private ObservableList<InStopDTO> wypelnienieStopAll = FXCollections.observableArrayList();
    private ObservableList<Long> wypelnienieRoute = FXCollections.observableArrayList();
    public Button up;
    public Button down;
    public ComboBox <InStopDTO> combo;
    public TextField textField;

    public void clear(ActionEvent actionEvent) {
        textField.clear();
        wypelnienieStop.clear();
        ListaSops.setItems(wypelnienieStop);
    }

    public void addRoute(ActionEvent actionEvent) throws UnirestException {
        boolean test = true;
//        Long[] istniejace = RouteRequest.getRoutes();
        Long dodawany = Long.parseLong(textField.getText());
//        for(int i = 0; i < istniejace.length; i++){
//            if(istniejace[i] == dodawany) test = false;
//        }
        if(test){
            UUID[] list = new UUID[wypelnienieStop.size()];
            for(int i = 0; i < wypelnienieStop.size(); i++){
                list[i] = wypelnienieStop.get(i).getStopId();
            }
            RouteRequest.addRoute(dodawany);
            RouteRequest.addStopsToRoute(list,dodawany);
            wypelnienieRoute.setAll(RouteRequest.getRoutes());
            ListaTras.setItems(wypelnienieRoute);
        }

    }

    public void editMode(ActionEvent actionEvent) throws UnirestException {
        if(editmode.getText().equals("Edit/Add Mode")){

            up.setVisible(true);
            down.setVisible(true);
            addRoute.setVisible(true);
            clear.setVisible(true);
            combo.setVisible(true);
            delete.setVisible(true);
            wypelnienieStop.clear();
            ListaSops.setItems(wypelnienieStop);
            wypelnienieStopAll.setAll(StopRequest.getStops());
            combo.setItems(wypelnienieStopAll);
            editmode.setText("Quit Edit mode");
        }
        else {

            up.setVisible(false);
            down.setVisible(false);
            addRoute.setVisible(false);
            clear.setVisible(false);
            combo.setVisible(false);
            delete.setVisible(false);
            wypelnienieStop.clear();
            editmode.setText("Edit/Add Mode");
        }

    }

    public void up(ActionEvent actionEvent) {
        int indeks = ListaSops.getSelectionModel().getSelectedIndex();
        if(indeks > 0){
            InStopDTO stopToCHange = wypelnienieStop.get(indeks);
            wypelnienieStop.set(indeks, wypelnienieStop.get(indeks-1));
            wypelnienieStop.set(indeks-1, stopToCHange);
            ListaSops.setItems(wypelnienieStop);
            ListaSops.getSelectionModel().select(indeks-1);
        }
    }

    public void down(ActionEvent actionEvent) {
        int indeks = ListaSops.getSelectionModel().getSelectedIndex();
        if(indeks < wypelnienieStop.size() - 1){
            InStopDTO stopToCHange = wypelnienieStop.get(indeks);
            wypelnienieStop.set(indeks, wypelnienieStop.get(indeks + 1));
            wypelnienieStop.set(indeks + 1, stopToCHange);
            ListaSops.setItems(wypelnienieStop);
            ListaSops.getSelectionModel().select(indeks+1);
        }
    }

    public void selectTrasa(MouseEvent mouseEvent) throws UnirestException {
        Long id = ListaTras.getSelectionModel().getSelectedItem();
        textField.setText(id.toString());
        wypelnienieStop.setAll(RouteRequest.getStopsOnRoute(id));
        ListaSops.setItems(wypelnienieStop);
    }

    public void selectStop(MouseEvent mouseEvent) {



    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        up.setVisible(false);
        down.setVisible(false);
        addRoute.setVisible(false);
        clear.setVisible(false);
        combo.setVisible(false);
        delete.setVisible(false);
        try {
            wypelnienieRoute.setAll(RouteRequest.getRoutes());
            ListaTras.setItems(wypelnienieRoute);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    public void wybierzstopcombo(MouseEvent mouseEvent) {
        if(combo.getSelectionModel().getSelectedItem() != null){
            wypelnienieStop.add(combo.getSelectionModel().getSelectedItem());
            ListaSops.setItems(wypelnienieStop);
        }

    }

    public void delete(ActionEvent actionEvent) throws UnirestException {
        RouteRequest.removeRoute(Long.parseLong(textField.getText()));
        textField.clear();
        wypelnienieStop.clear();
        ListaSops.setItems(wypelnienieStop);
        try {
            wypelnienieRoute.setAll(RouteRequest.getRoutes());
            ListaTras.setItems(wypelnienieRoute);
        } catch (UnirestException e) {
            e.printStackTrace();
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
}
