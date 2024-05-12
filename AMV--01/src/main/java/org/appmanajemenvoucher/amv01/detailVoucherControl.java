package org.appmanajemenvoucher.amv01;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class detailVoucherControl {

    @FXML
    private TextArea fieldbatasan;

    @FXML
    private TextField fieldid;

    @FXML
    private TextArea fieldinstruksi;

    @FXML
    private TextField fieldkategori;

    @FXML
    private TextField fieldnama;

    @FXML
    private TextField fieldjenis;

    @FXML
    private DatePicker fieldtanggal;

    private Connection con = dbconnect.getConnection();


    public detailVoucherControl() throws SQLException {

    }


    @FXML
    void onBtnBackClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void onBtnDoneClick(ActionEvent event) {

    }

    @FXML
    void onBtnEditClick(ActionEvent event) {


    }

    public void showData(Voucher voucher){

        fieldid.setText(String.valueOf(voucher.getIdVoucher()));
        fieldid.setEditable(false);
        fieldnama.setText(voucher.getNamaVoucher());
        fieldnama.setEditable(false);
        fieldkategori.setText(voucher.getKategori());
        fieldkategori.setEditable(false);
        fieldjenis.setText(voucher.getJenis());
        fieldjenis.setEditable(false);

        Instant instant = Instant.ofEpochMilli(voucher.getTanggal().getTime());
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        fieldtanggal.setValue(localDate);
        fieldtanggal.setEditable(false);

        fieldinstruksi.setText(voucher.getInstruksi());
        fieldinstruksi.setEditable(false);
        fieldbatasan.setText(voucher.getBatasan());
        fieldbatasan.setEditable(false);

    }
}
