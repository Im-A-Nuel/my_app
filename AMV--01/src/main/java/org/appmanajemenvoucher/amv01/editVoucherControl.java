package org.appmanajemenvoucher.amv01;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class editVoucherControl {

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

    private Connection con = dbconnect.getConnection();

    public editVoucherControl() throws SQLException {
    }


    @FXML
    void onBtnBackClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void onBtnBatalClick(ActionEvent event) {

    }

    @FXML
    void onBtnSaveClick(ActionEvent event) throws SQLException {
        Alert a;
        a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Konfirmasi");
        a.setHeaderText("Konfirmasi Edit");
        a.setContentText("Apakah Anda yakin ingin mengedit voucher ini?");
        a.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        a.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.YES){
                int id = Integer.parseInt(fieldid.getText());
                String newname = fieldnama.getText();
                String newjenis = fieldjenis.getText();
                Date newtanggal = Date.valueOf(fieldtanggal.getValue());
                String newkategori = (String) fieldkategori.getValue();
                String newinstruksi = fieldinstruksi.getText();
                String newbatasan = fieldbatasan.getText();

                try{String query = "update voucher set nama = ?, jenis = ?, tanggalKadaluwarsa = ?, kategori = ?, instruksi = ?, batasan = ? where idVoucher = ?";
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1,newname);
                ps.setString(2, newjenis);
                ps.setString(3, String.valueOf(newtanggal));
                ps.setString(4, newkategori);
                ps.setString(5, newinstruksi);
                ps.setString(6, newbatasan);
                ps.setInt(7, id);

                int hasil = ps.executeUpdate();
                if(hasil == 1){
                    Alert b;
                    String st = "Berhasil Mengubah data voucher";
                    b = new Alert(Alert.AlertType.INFORMATION);
                    b.setHeaderText("Information");
                    b.setContentText(st);
                    b.showAndWait();
                }

                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else{

            }
        });

    }

    public void showData(Voucher voucher){

        fieldid.setText(String.valueOf(voucher.getIdVoucher()));
        fieldid.setEditable(false);
        fieldnama.setText(voucher.getNamaVoucher());
        fieldkategori.setValue(voucher.getKategori());
        fieldkategori.setStyle("-fx-text-fill: black;-fx-font-size: 12");
        fieldjenis.setText(voucher.getJenis());

        Instant instant = Instant.ofEpochMilli(voucher.getTanggal().getTime());
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        fieldtanggal.setValue(localDate);
        fieldinstruksi.setText(voucher.getInstruksi());
        fieldbatasan.setText(voucher.getBatasan());

    }

}