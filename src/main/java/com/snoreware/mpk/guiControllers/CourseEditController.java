package com.snoreware.mpk.guiControllers;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.snoreware.mpk.model.CourseDTO;
import com.snoreware.mpk.model.DriverDTO;
import com.snoreware.mpk.modelIn.InCourseDTO;
import com.snoreware.mpk.modelIn.InDriverDTO;
import com.snoreware.mpk.request.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseEditController implements Initializable {
    public Label driverLAbel;
    public Label routeLabel;
    public Label vehicleLAble;
    public ComboBox<Long> routeCombo;
    public ComboBox<InDriverDTO> driverCombo;
    public ComboBox<Long> vehicleCombo;
    public CheckBox articulated;
    public CheckBox lowFlor;
    public Button edit;
    public Button add;
    public Button delete;
    public ListView<InCourseDTO> BusList;
    public Button editCourse;
    public TextField stazUstawianie;
    public ListView<InCourseDTO> TramList;
    public TabPane tabs;
    public TextField wagonUstawanie;
    private ObservableList<InCourseDTO> wypelnieniebus = FXCollections.observableArrayList();
    private ObservableList<InDriverDTO> wypelnienieDriver = FXCollections.observableArrayList();
    private ObservableList<Long> wypelnienieRoute = FXCollections.observableArrayList();
    private ObservableList<Long> wypelnienievehicle = FXCollections.observableArrayList();
    private int editMode = 0;

    private boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private void showAlert(String info) {
        EditRouteController.badDataWarning(info, "Błąd wprowadzonych danych");
    }


    private void updateCourseTab() throws UnirestException {
        wypelnieniebus.setAll(CourseBusRequest.getBusCourses());
        BusList.setItems(wypelnieniebus);
    }

    public void updateCourseTabTram() throws UnirestException {
        wypelnieniebus.setAll(CourseTramRequest.getTramCourses());
        TramList.setItems(wypelnieniebus);
    }

    private void updateDriverCombo() throws UnirestException {
        wypelnienieDriver.setAll(DriverRequest.getAviableDrivers());
        driverCombo.setItems(wypelnienieDriver);
    }

    private void updateRouteCombo() throws UnirestException {
        wypelnienieRoute.setAll(RouteRequest.getRoutes());
        routeCombo.setItems(wypelnienieRoute);
    }

    private void updateVehicleCombo() throws UnirestException {
        wypelnienievehicle.setAll(BusRequest.getWorkingBuses());
        vehicleCombo.setItems(wypelnienievehicle);
    }

    private void updateVehicleComboLowFloor() throws UnirestException {
        wypelnienievehicle.setAll(BusRequest.getLowFlorBuses());
        vehicleCombo.setItems(wypelnienievehicle);
    }

    private void updateVehicleComboArticulated() throws UnirestException {
        wypelnienievehicle.setAll(BusRequest.getArticulatedBuses());
        vehicleCombo.setItems(wypelnienievehicle);
    }

    private void updateVehicleComboArticulatedAndLow() throws UnirestException {
        wypelnienievehicle.setAll(BusRequest.getLowFlorandArticulatedBuses());
        vehicleCombo.setItems(wypelnienievehicle);
    }

    private void updateVehicleComboTram() throws UnirestException {
        wypelnienievehicle.setAll(TramRequest.getWorkingTrams());
        vehicleCombo.setItems(wypelnienievehicle);
    }

    private void updateVehicleComboTramLow() throws UnirestException {
        wypelnienievehicle.setAll(TramRequest.getLowFloorTrams());
        vehicleCombo.setItems(wypelnienievehicle);
    }


    public void delete(ActionEvent actionEvent) throws UnirestException {
        if (tabs.getSelectionModel().getSelectedIndex() == 0) {
            if (BusList.getSelectionModel().getSelectedItem() != null) {
                CourseBusRequest.deleteBusCourse(BusList.getSelectionModel().getSelectedItem());
                updateCourseTab();
            } else showAlert("Nie zaznaczono kursu do usunięcia");
        } else if (tabs.getSelectionModel().getSelectedIndex() == 1) {
            if (TramList.getSelectionModel().getSelectedItem() != null) {
                CourseTramRequest.deleteTramCourse(TramList.getSelectionModel().getSelectedItem());
                updateCourseTabTram();
            } else showAlert("Nie zaznaczono kursu do usunięcia");
        }
    }

    private boolean checkContainers() {
        String blad = "";
        if (routeCombo.getSelectionModel().getSelectedItem() == null) blad += "Nie wybrano trasy\n";
        if (driverCombo.getSelectionModel().getSelectedItem() == null) blad += "Nie wybrano kierowcy\n";
        if (vehicleCombo.getSelectionModel().getSelectedItem() == null) blad += "Nie wybrano pojazdu\n";
        if (!blad.isEmpty()) showAlert(blad);
        return blad.isEmpty();
    }

    public void add(ActionEvent actionEvent) throws UnirestException {
        if (tabs.getSelectionModel().getSelectedIndex() == 0) {
            if (checkContainers()) {
                CourseDTO courseDTO = new CourseDTO();
                courseDTO.setArticulatedNeeded(articulated.isSelected());
                courseDTO.setLowFloorNeeded(lowFlor.isSelected());
                courseDTO.setRouteNumber(routeCombo.getSelectionModel().getSelectedItem());
                courseDTO.setDriverId(driverCombo.getSelectionModel().getSelectedItem().getDriverId());
                courseDTO.setVehicleNumber(vehicleCombo.getSelectionModel().getSelectedItem());
                System.out.println(courseDTO);
//        courseDTO.setManyWagonsNeeded(false);
                CourseBusRequest.addBusCourse(courseDTO);

                updateCourseTab();
            }
        } else if (tabs.getSelectionModel().getSelectedIndex() == 1) {
            if (checkContainers()) {
                CourseDTO courseDTO = new CourseDTO();
                courseDTO.setManyWagonsNeeded(true);
                courseDTO.setLowFloorNeeded(lowFlor.isSelected());
                courseDTO.setRouteNumber(routeCombo.getSelectionModel().getSelectedItem());
                courseDTO.setDriverId(driverCombo.getSelectionModel().getSelectedItem().getDriverId());
                courseDTO.setVehicleNumber(vehicleCombo.getSelectionModel().getSelectedItem());


//        courseDTO.setManyWagonsNeeded(false);
                CourseTramRequest.addTramCourse(courseDTO);
                updateCourseTabTram();

            }
        }

    }

    public void editCourse(ActionEvent actionEvent) throws UnirestException {
        if (tabs.getSelectionModel().getSelectedIndex() == 0) {
            if (BusList.getSelectionModel().getSelectedItem() != null) {
                if (checkContainers()) {
                    InCourseDTO course = BusList.getSelectionModel().getSelectedItem();
                    if (driverCombo.getSelectionModel().getSelectedItem().getDriverId() != course.getDriverId()) {
                        DriverDTO driver = DriverRequest.getDriver(driverCombo.getSelectionModel().getSelectedItem().getDriverId());
                        CourseBusRequest.updateDriver(driver, course.getCourseId());
                    }
                    if (routeCombo.getSelectionModel().getSelectedItem() != course.getRouteNumber()) {
                        CourseBusRequest.updateRoute(routeCombo.getSelectionModel().getSelectedItem(), course.getCourseId());
                    }
                    if (vehicleCombo.getSelectionModel().getSelectedItem() != course.getVehicleNumber()) {
                        CourseBusRequest.updateBus(vehicleCombo.getSelectionModel().getSelectedItem(), course.getCourseId());
                    }
                    updateCourseTab();
                }
            } else showAlert("nie wybrano kursu do edytcji");
        } else if (tabs.getSelectionModel().getSelectedIndex() == 1) {
            if (TramList.getSelectionModel().getSelectedItem() != null) {
                if (checkContainers()) {
                    InCourseDTO course = TramList.getSelectionModel().getSelectedItem();
                    if (driverCombo.getSelectionModel().getSelectedItem().getDriverId() != course.getDriverId()) {
                        DriverDTO driver = DriverRequest.getDriver(driverCombo.getSelectionModel().getSelectedItem().getDriverId());
                        CourseTramRequest.updateDriver(driver, course.getCourseId());
                    }
                    if (routeCombo.getSelectionModel().getSelectedItem() != course.getRouteNumber()) {
                        CourseTramRequest.updateRoute(routeCombo.getSelectionModel().getSelectedItem(), course.getCourseId());
                    }
                    if (vehicleCombo.getSelectionModel().getSelectedItem() != course.getVehicleNumber()) {
                        CourseTramRequest.updateTram(vehicleCombo.getSelectionModel().getSelectedItem(), course.getCourseId());
                    }
                    updateCourseTabTram();
                }
            } else showAlert("nie wybrano kursu do edytcji");
        }


    }

    public void edit(ActionEvent actionEvent) throws UnirestException {
        if (tabs.getSelectionModel().getSelectedIndex() == 0) {
            if (editMode == 0) {
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
                wagonUstawanie.setVisible(false);
                articulated.setVisible(true);
            } else {
                editCourse.setVisible(false);
                driverLAbel.setText("");
                routeLabel.setText("");
                wagonUstawanie.setVisible(false);
                vehicleLAble.setText("");
                driverCombo.setVisible(false);
                vehicleCombo.setVisible(false);
                routeCombo.setVisible(false);
                stazUstawianie.setVisible(false);
                add.setVisible(false);
                delete.setVisible(false);

                editMode = 0;
            }
        } else if (tabs.getSelectionModel().getSelectedIndex() == 1) {
            if (editMode == 0) {
                editMode = 1;
                updateDriverCombo();
                updateRouteCombo();
                updateVehicleComboTram();
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
                wagonUstawanie.setVisible(true);
                articulated.setVisible(false);
            } else {
                editMode = 0;
                editCourse.setVisible(false);
                driverLAbel.setText("");
                routeLabel.setText("");
                wagonUstawanie.setVisible(false);
                vehicleLAble.setText("");
                driverCombo.setVisible(false);
                vehicleCombo.setVisible(false);
                routeCombo.setVisible(false);
                stazUstawianie.setVisible(false);
                add.setVisible(false);
                delete.setVisible(false);
                wagonUstawanie.setVisible(false);
            }
        }


    }

    public void selectBus(MouseEvent mouseEvent) throws UnirestException {
        if (BusList.getSelectionModel().getSelectedItem() != null) {
            if (editMode == 1) {
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
            } else {
                InCourseDTO selected = BusList.getSelectionModel().getSelectedItem();
                DriverDTO driver = DriverRequest.getDriver(selected.getDriverId());
                InDriverDTO in = new InDriverDTO();
                in.setDriverId(driver.getDriverId());
                in.setName(driver.getName());
                in.setSurname(driver.getSurname());
                driverLAbel.setText("Kierowca : " + in.toString());
                vehicleLAble.setText("Pojazd : " + selected.getVehicleNumber().toString());
                routeLabel.setText("Trasa : " + selected.getRouteNumber().toString());
            }
        }
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

    private void zmienArtTemp() throws UnirestException {
        if (tabs.getSelectionModel().getSelectedIndex() == 0) {
            vehicleCombo.getSelectionModel().clearSelection();
            if (articulated.isSelected()) {
                if (lowFlor.isSelected()) {
                    updateVehicleComboArticulatedAndLow();
                } else {
                    updateVehicleComboArticulated();
                }
            } else {
                if (lowFlor.isSelected()) {
                    updateVehicleComboLowFloor();
                } else {
                    updateVehicleCombo();
                }
            }
        }
    }


    public void zmienArticulated(ActionEvent actionEvent) throws UnirestException {
        zmienArtTemp();
    }

    private void zmienLowFloorTemp() throws UnirestException {
        if (tabs.getSelectionModel().getSelectedIndex() == 0) {
            vehicleCombo.getSelectionModel().clearSelection();
            if (lowFlor.isSelected()) {
                if (articulated.isSelected()) {
                    updateVehicleComboArticulatedAndLow();
                } else {
                    updateVehicleComboLowFloor();
                }
            } else {
                if (articulated.isSelected()) {
                    updateVehicleComboLowFloor();
                } else {
                    updateVehicleCombo();
                }
            }
        } else if (tabs.getSelectionModel().getSelectedIndex() == 1) {
            if (lowFlor.isSelected()) {
                if (!wagonUstawanie.getText().isEmpty() && isNumeric(wagonUstawanie.getText())) {
                    wypelnienievehicle.setAll(TramRequest.getNumberLowFloorTrams(Integer.parseInt(wagonUstawanie.getText())));
                    vehicleCombo.setItems(wypelnienievehicle);
                } else {
                    updateVehicleComboTramLow();
                }
            } else {
                if (!wagonUstawanie.getText().isEmpty() && isNumeric(wagonUstawanie.getText())) {
                    wypelnienievehicle.setAll(TramRequest.getNumberTrams(Integer.parseInt(wagonUstawanie.getText())));
                    vehicleCombo.setItems(wypelnienievehicle);
                } else {
                    updateVehicleComboTram();
                }
            }
        }
    }

    public void zmienLowFloor(ActionEvent actionEvent) throws UnirestException {
        zmienLowFloorTemp();
    }

    private void ustawMinStazTemp() throws UnirestException {
        if (!stazUstawianie.getText().isEmpty() && isNumeric(stazUstawianie.getText().toString())) {
            wypelnienieDriver.setAll(DriverRequest.getDriverswithSeniority(Integer.parseInt(stazUstawianie.getText())));
            driverCombo.setItems(wypelnienieDriver);
        } else updateDriverCombo();
    }

    public void ustawMinstaz(MouseEvent mouseEvent) throws UnirestException {
        ustawMinStazTemp();
    }

    private void ustwaMinWagonTemp() throws UnirestException {
        if (tabs.getSelectionModel().getSelectedIndex() == 1) {
            if (!wagonUstawanie.getText().isEmpty() && isNumeric(wagonUstawanie.getText())) {
                if (lowFlor.isSelected()) {
                    wypelnienievehicle.setAll(TramRequest.getNumberLowFloorTrams(Integer.parseInt(wagonUstawanie.getText())));
                    vehicleCombo.setItems(wypelnienievehicle);
                } else {
                    wypelnienievehicle.setAll(TramRequest.getNumberTrams(Integer.parseInt(wagonUstawanie.getText())));
                    vehicleCombo.setItems(wypelnienievehicle);
                }
            }
        }
    }

    public void ustawMinWagon(MouseEvent mouseEvent) throws UnirestException {
        ustwaMinWagonTemp();
    }


    public void selectTram(MouseEvent mouseEvent) throws UnirestException {
        if (TramList.getSelectionModel().getSelectedItem() != null) {
            if (editMode == 1) {
                InCourseDTO selected = TramList.getSelectionModel().getSelectedItem();
                vehicleCombo.getSelectionModel().select(selected.getVehicleNumber());
                DriverDTO driver = DriverRequest.getDriver(selected.getDriverId());
                InDriverDTO in = new InDriverDTO();
                in.setDriverId(driver.getDriverId());
                in.setName(driver.getName());
                in.setSurname(driver.getSurname());
                driverCombo.getSelectionModel().select(in);
                routeCombo.getSelectionModel().select(selected.getRouteNumber());
                lowFlor.setSelected(selected.getLowFloorNeeded());
            } else {
                InCourseDTO selected = TramList.getSelectionModel().getSelectedItem();
                DriverDTO driver = DriverRequest.getDriver(selected.getDriverId());
                InDriverDTO in = new InDriverDTO();
                in.setDriverId(driver.getDriverId());
                in.setName(driver.getName());
                in.setSurname(driver.getSurname());
                driverLAbel.setText("Kierowca : " + in.toString());
                vehicleLAble.setText("Pojazd : " + selected.getVehicleNumber().toString());
                routeLabel.setText("Trasa : " + selected.getRouteNumber().toString());
            }
        }
    }


    public void wybierzBus(Event event) throws UnirestException {
        updateCourseTab();
        if (editMode == 0) {
            editCourse.setVisible(false);
            driverLAbel.setText("");
            routeLabel.setText("");
            vehicleLAble.setText("");
            driverCombo.setVisible(false);
            vehicleCombo.setVisible(false);
            routeCombo.setVisible(false);
            stazUstawianie.setVisible(false);
            wagonUstawanie.setVisible(false);
            add.setVisible(false);
            delete.setVisible(false);
            editMode = 0;
        } else {
            updateDriverCombo();
            updateRouteCombo();
            updateVehicleCombo();
            driverCombo.setVisible(true);
            vehicleCombo.setVisible(true);
            routeCombo.setVisible(true);
            editCourse.setVisible(true);
            stazUstawianie.setVisible(true);
            wagonUstawanie.setVisible(false);
            add.setVisible(true);
            delete.setVisible(true);
            editMode = 1;
            driverLAbel.setText("Kierowca : ");
            routeLabel.setText("Trasa : ");
            vehicleLAble.setText("pojazd : ");
        }
    }

    public void wybierzTram(Event event) throws UnirestException {
        updateCourseTabTram();
        if (editMode == 0) {
            editCourse.setVisible(false);
            driverLAbel.setText("");
            routeLabel.setText("");
            wagonUstawanie.setVisible(false);
            vehicleLAble.setText("");
            driverCombo.setVisible(false);
            vehicleCombo.setVisible(false);
            routeCombo.setVisible(false);
            stazUstawianie.setVisible(false);
            add.setVisible(false);
            delete.setVisible(false);
            wagonUstawanie.setVisible(false);
            editMode = 0;
        } else {
            updateDriverCombo();
            updateRouteCombo();
            updateVehicleComboTram();
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
            vehicleLAble.setText("Pojazd : ");
            wagonUstawanie.setVisible(true);
            articulated.setVisible(false);
        }
    }
}
