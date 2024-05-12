package org.appmanajemenvoucher.amv01;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class addVoucherControl {


    @FXML
    private TextArea fieldbatasan;

    @FXML
    private TextField fieldid;

    @FXML
    private TextArea fieldinstruksi;

    @FXML
    private ChoiceBox<String> fieldkategori;

    @FXML
    private TextField fieldnama;

    @FXML
    private TextField fieldjenis;

    @FXML
    private DatePicker fieldtanggal;


    @FXML
    void onBtnaddClick(ActionEvent event) {

    }

    @FXML
    void onBtnbackClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void onBtnresetClick(ActionEvent event) {

    }

}
