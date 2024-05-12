package org.appmanajemenvoucher.amv01;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class NotifikasiControl {

    private Button show;
    @FXML
    private TableView<Voucher> table;
    @FXML
    private TableColumn<Voucher, Date> tanggalKadaluwarsa;
    @FXML
    private TableColumn<Voucher,Integer> idVoucher;
    @FXML
    private Button btnback;
    private Connection con = dbconnect.getConnection();
    private String user;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NotifikasiControl() throws SQLException {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @FXML
    private void onClikcBack(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private ObservableList<Voucher> getData(String user) throws SQLException {
        ObservableList<Voucher> data = FXCollections.observableArrayList();
        String query = "SELECT voucher.tanggalKadaluwarsa from voucher where username=?";
        try (PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1,user);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    int idVoucher = rs.getInt("idVoucher");
                    java.sql.Date tanggal = rs.getDate("tanggalKadaluwarsa");

                    data.add(new Voucher(idVoucher,tanggal));
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  data;
    }

    private ObservableList<Voucher> loadData (String user) throws SQLException {
        idVoucher.setCellValueFactory(new PropertyValueFactory<>("IdVoucher"));
        tanggalKadaluwarsa.setCellValueFactory(new PropertyValueFactory<>("tanggalKadaluwarsa"));
        ObservableList<Voucher> vouchers = getData(getUser());
    return vouchers;
    }



    public void onClikshow(ActionEvent event) {


    }
}