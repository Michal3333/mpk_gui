package com.snoreware.mpk.guiControllers;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.snoreware.mpk.model.*;
import com.snoreware.mpk.modelIn.InDriverDTO;
import com.snoreware.mpk.modelIn.InStopDTO;
import com.snoreware.mpk.request.BusRequest;
import com.snoreware.mpk.request.DriverRequest;
import com.snoreware.mpk.request.StopRequest;
import com.snoreware.mpk.request.TramRequest;
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
    public ListView<Long> busList;
    public ListView<Long> tramList;
    public ListView<InDriverDTO> driverList;
    public ListView<InStopDTO> StopList;
    public CheckBox checkBox1;
    public CheckBox checkBox2;
    public TextField searchbar;
    public Button szukajButton;
    private ObservableList<InDriverDTO> wypelnienieDriver = FXCollections.observableArrayList();
    private ObservableList<Long> wypelnienieBus = FXCollections.observableArrayList();
    private ObservableList<InStopDTO> wypelnienieStop = FXCollections.observableArrayList();
    private ObservableList<Long> wypelnienieTram = FXCollections.observableArrayList();
    private ObservableList<InDriverDTO> filtrwypelnienieDriver = FXCollections.observableArrayList();
    private ObservableList<Long> filtrwypelnienieBus = FXCollections.observableArrayList();
    private ObservableList<InStopDTO> filtrwypelnienieStop = FXCollections.observableArrayList();
    private ObservableList<Long> filtrwypelnienieTram = FXCollections.observableArrayList();
    private Boolean isFiltr;

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

    private void clear() {
        textField1.clear();
        textField2.clear();
        textField3.clear();
        textField4.clear();
        textField5.clear();
        checkBox1.setSelected(false);
        checkBox2.setSelected(false);
    }

    private void setLabelsForBus() {
        label1.setText("Niskopodłogowy");
        label2.setText("Przegubowy");
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

    private void setLabelsForTram() {
        label2.setVisible(true);
        label1.setText("Niskopodłogowy");
        label2.setText("Liczba wagonów");
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

    private void setLabelsForDriver() {
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
        label1.setText("Imię");
        label2.setText("Nazwisko");
        label3.setText("Płeć");
        label4.setText("Płaca");
        clear();
        updateDriverList();

    }

    private void setLabelsForStop() {
        checkBox1.setVisible(false);
        checkBox2.setVisible(false);
        label1.setText("Nazwa");
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

    private void updateBusList() {
        wypelnienieBus.clear();
        FailuresController.updateBus(wypelnienieBus, busList);
        if (isFiltr) {
            searchFunction();
        }
    }

    private void updateTramList() {
        wypelnienieTram.clear();
        FailuresController.updateTrams(wypelnienieTram, tramList);
        if (isFiltr) {
            searchFunction();
        }
    }

    private void updateDriverList() {
        wypelnienieDriver.clear();
        SalaryUpgradeController.updateDr(wypelnienieDriver, driverList);
        if (isFiltr) {
            searchFunction();
        }
    }

    private void updateStopList() {
        wypelnienieStop.clear();
        try {
            InStopDTO[] stops = StopRequest.getStops();
            wypelnienieStop.addAll(Arrays.asList(stops));
            StopList.setItems(wypelnienieStop);
            if (isFiltr) {
                searchFunction();
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    private Boolean checkContainers(int tab) {
        String blad = "";
        if (tab == 1) {
            if (isNumeric(textField2.getText()) || textField2.getText().isEmpty())
                blad += "nie podano ilosci wagonow lub podana wartość nie jest prawidlowa";
            if (!blad.isEmpty()) showAlert(blad);
            return blad.isEmpty();
        } else if (tab == 2) {
            if (textField1.getText().isEmpty())
                blad += "Nie podano imienia kierowcy lub podana wartość jest niepoprawna\n";
            if (textField2.getText().isEmpty())
                blad += "Nie podano nazwiska kierowcy lub podana wartość jest niepoprawna\n";
            if (textField3.getText().isEmpty())
                blad += "Nie podano plci kierowcy lub podana wartość jest niepoprawna\n";
            if (!isNumeric(textField4.getText()) || textField4.getText().isEmpty())
                blad += "Nie podano placy kierowcy lub podana wartość jest niepoprawna\n";
            if (!blad.isEmpty()) showAlert(blad);
            return blad.isEmpty();
        } else {
            if (textField1.getText().isEmpty())
                blad += "Nie podano nazwy przystanku lub podana wartość jest niepoprawna\n";
            if (!blad.isEmpty()) showAlert(blad);
            return blad.isEmpty();
        }

    }


    public void add(ActionEvent actionEvent) throws UnirestException {
        int tab = tabs.getSelectionModel().getSelectedIndex();
        String[] labels = new String[5];
        labels[0] = textField1.getText();
        labels[1] = textField2.getText();
        labels[2] = textField3.getText();
        labels[3] = textField4.getText();
        labels[4] = textField5.getText();
        boolean check1 = checkBox1.isSelected();
        boolean check2 = checkBox2.isSelected();
        if (tab == 0) {
            VehicleDTO vehicleDTO = new VehicleDTO();
            vehicleDTO.setArticulated(check2);
            vehicleDTO.setLowFloor(check1);
            BusRequest.addBus(vehicleDTO);
            updateBusList();
        } else if (tab == 1) {
            if (checkContainers(tab)) {
                VehicleDTO vehicleDTO = new VehicleDTO();
                vehicleDTO.setLowFloor(check1);
                vehicleDTO.setNumberOfWagons(Integer.parseInt(labels[1]));
                TramRequest.addTram(vehicleDTO);
                updateTramList();
            }
        } else if (tab == 2) {
            if (checkContainers(tab)) {
                DriverDTO driverDTO = new DriverDTO();
                driverDTO.setName(labels[0]);
                driverDTO.setSurname(labels[1]);
                driverDTO.setSex(labels[2]);
                driverDTO.setSalary(Float.parseFloat(labels[3]));
                DriverRequest.addDriver(driverDTO);
                updateDriverList();
            }
        } else if (tab == 3) {
            if (checkContainers(tab)) {
                StopDTO stopDTO = new StopDTO();
                stopDTO.setStopName(labels[0]);
                StopRequest.addStop(stopDTO);
                updateStopList();
            }

        }
        clear();
    }

    public void update(ActionEvent actionEvent) throws UnirestException {

        int tab = tabs.getSelectionModel().getSelectedIndex();
        String[] labels = new String[5];
        labels[0] = textField1.getText();
        labels[1] = textField2.getText();
        labels[2] = textField3.getText();
        labels[3] = textField4.getText();
        labels[4] = textField5.getText();
        boolean check1 = checkBox1.isSelected();
        boolean check2 = checkBox2.isSelected();
        if (tab == 0) {
            if (busList.getSelectionModel().getSelectedItem() != null) {
                Long id = busList.getSelectionModel().getSelectedItem();
                VehicleDTO vehicleDTO = new VehicleDTO();
                vehicleDTO.setVehicleNumber(id);
                vehicleDTO.setArticulated(check2);
                vehicleDTO.setLowFloor(check1);
                BusRequest.updateBus(vehicleDTO);
                updateBusList();
            } else showAlert("Nie zaznaczono autobusu do zmiany");

        } else if (tab == 1) {
            if (tramList.getSelectionModel().getSelectedItem() != null) {
                if (checkContainers(tab)) {
                    Long id = tramList.getSelectionModel().getSelectedItem();
                    VehicleDTO vehicleDTO = new VehicleDTO();
                    vehicleDTO.setVehicleNumber(id);
                    vehicleDTO.setLowFloor(check1);
                    vehicleDTO.setNumberOfWagons(Integer.parseInt(labels[1]));
                    TramRequest.updateTram(vehicleDTO);
                    updateTramList();
                }
            } else showAlert("Nie zaznaczono tramwaju do zmiany");
        } else if (tab == 2) {
            if (driverList.getSelectionModel().getSelectedItem() != null) {
                if (checkContainers(tab)) {
                    DriverDTO driver = DriverRequest.getDriver(driverList.getSelectionModel().getSelectedItem().getDriverId());
                    DriverDTO driverDTO = new DriverDTO();
                    driverDTO.setDriverId(driver.getDriverId());
                    driverDTO.setName(labels[0]);
                    driverDTO.setSurname(labels[1]);
                    driverDTO.setSex(labels[2]);
                    driverDTO.setSalary(Float.parseFloat(labels[3]));
                    DriverRequest.updateDriver(driverDTO);
                    updateDriverList();
                }
            } else showAlert("Nie zaznaczono kierowcy do zmiany");

        } else if (tab == 3) {
            if (StopList.getSelectionModel().getSelectedItem() != null) {
                if (checkContainers(tab)) {
                    InStopDTO stop = StopList.getSelectionModel().getSelectedItem();
                    stop.setStopId(stop.getStopId());
                    stop.setStopName(labels[0]);
                    StopRequest.updateStop(stop);
                    updateStopList();
                }
            } else showAlert("Nie zaznaczono przystanku do zmiany");

        }
        clear();
    }

    public void delete(ActionEvent actionEvent) throws UnirestException {
        int tab = tabs.getSelectionModel().getSelectedIndex();


        if (tab == 0) {
            if (busList.getSelectionModel().getSelectedItem() != null) {
                Long id = busList.getSelectionModel().getSelectedItem();
                VehicleDTO vehicleDTO = new VehicleDTO();
                vehicleDTO.setVehicleNumber(id);
                BusRequest.deleteBus(vehicleDTO);
                updateBusList();
            } else showAlert("Nie zaznaczono tramwaju do usunięcia");

        } else if (tab == 1) {
            if (tramList.getSelectionModel().getSelectedItem() != null) {
                Long id = tramList.getSelectionModel().getSelectedItem();
                VehicleDTO vehicleDTO = new VehicleDTO();
                vehicleDTO.setVehicleNumber(id);
                TramRequest.deleteTram(vehicleDTO);
                updateTramList();
            } else showAlert("Nie zaznaczono autobusu do usunięcia");

        } else if (tab == 2) {
            if (driverList.getSelectionModel().getSelectedItem() != null) {
                InDriverDTO driver = driverList.getSelectionModel().getSelectedItem();
                DriverDTO driverDTO = new DriverDTO();
                driverDTO.setDriverId(driver.getDriverId());
                DriverRequest.deleteDriver(driverDTO);
                updateDriverList();
            } else showAlert("Nie zaznaczono kieorwcy do usunięcia");

        } else if (tab == 3) {
            if (StopList.getSelectionModel().getSelectedItem() != null) {
                InStopDTO stop = StopList.getSelectionModel().getSelectedItem();
                StopDTO stopDTO = new StopDTO();
                stopDTO.setStopId(stop.getStopId());
                StopRequest.deleteStop(stopDTO);
                updateStopList();
            } else showAlert("Nie zaznaczono przystanku do usunięcia");

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
        isFiltr = false;
        setLabelsForBus();

    }

    public void wybierzBus(MouseEvent mouseEvent) throws UnirestException {
        if (busList.getSelectionModel().getSelectedItem() != null) {
            Long id = busList.getSelectionModel().getSelectedItem();
            BusDTO bus = BusRequest.getBus(id);
            checkBox1.setSelected(bus.getLowFloor());
            checkBox2.setSelected(bus.getArticulated());
        }
    }

    public void wybierzTram(MouseEvent mouseEvent) throws UnirestException {
        if (tramList.getSelectionModel().getSelectedItem() != null) {
            Long id = tramList.getSelectionModel().getSelectedItem();
            TramDTO tram = TramRequest.getTram(id);
            checkBox1.setSelected(tram.getLowFloor());
            textField2.setText(Integer.toString(tram.getNumberOfWagons()));
        }
    }

    public void wybierzDriver(MouseEvent mouseEvent) throws UnirestException {
        if (driverList.getSelectionModel().getSelectedItem() != null) {
            InDriverDTO driverid = driverList.getSelectionModel().getSelectedItem();
            DriverDTO driver = DriverRequest.getDriver(driverid.getDriverId());
            textField1.setText(driver.getName());
            textField2.setText(driver.getSurname());
            textField3.setText(driver.getSex());
            textField4.setText(driver.getSalary().toString());
        }
    }

    public void wybierzStop(MouseEvent mouseEvent) throws UnirestException {
        if (StopList.getSelectionModel().getSelectedItem() != null) {
            InStopDTO stop = StopList.getSelectionModel().getSelectedItem();
            textField1.setText(stop.getStopName());
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

    public void wyczyscFiltr(ActionEvent actionEvent) {
        isFiltr = false;
        searchbar.setText("");
        int tab = tabs.getSelectionModel().getSelectedIndex();
        if (tab == 0) {
            busList.setItems(wypelnienieBus);
        } else if (tab == 1) {
            tramList.setItems(wypelnienieTram);
        } else if (tab == 2) {
            driverList.setItems(wypelnienieDriver);
        } else if (tab == 3) {
            StopList.setItems(wypelnienieStop);
        }
    }

    private void searchFunction() {
        int tab = tabs.getSelectionModel().getSelectedIndex();
        if (!searchbar.getText().isEmpty()) {
            if (tab == 0) {
                if (isNumeric(searchbar.getText())) {
                    filtrwypelnienieBus.clear();
                    for (Long i : wypelnienieBus) {
                        if (i.toString().contains(searchbar.getText())) filtrwypelnienieBus.add(i);
                    }
                    busList.setItems(filtrwypelnienieBus);
                    isFiltr = true;
                }
            } else if (tab == 2) {
                filtrwypelnienieDriver.clear();
                for (InDriverDTO driver : wypelnienieDriver) {
                    if (driver.toString().contains(searchbar.getText())) filtrwypelnienieDriver.add(driver);
                }
                driverList.setItems(filtrwypelnienieDriver);
                isFiltr = true;

            } else if (tab == 1) {
                if (isNumeric(searchbar.getText())) {
                    System.out.println("");//xd
                    filtrwypelnienieTram.clear();
                    for (Long i : wypelnienieTram) {
                        if (i.toString().contains(searchbar.getText())) filtrwypelnienieTram.add(i);
                    }
                    tramList.setItems(filtrwypelnienieTram);
                    isFiltr = true;

                }
            } else if (tab == 2) {
                filtrwypelnienieStop.clear();
                for (InStopDTO stop : wypelnienieStop) {
                    if (stop.toString().contains(searchbar.getText())) filtrwypelnienieStop.add(stop);
                }
                StopList.setItems(filtrwypelnienieStop);
                isFiltr = true;

            }
        }

    }

    public void szukaj(ActionEvent actionEvent) {
        searchFunction();
    }
}
