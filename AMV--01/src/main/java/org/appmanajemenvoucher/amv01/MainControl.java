package org.appmanajemenvoucher.amv01;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MainControl implements Initializable {
    @FXML
    private Button btnNotif;
    @FXML
    private Button logout;
    @FXML
    private Button editbutton;
    @FXML
    private Button deletebutton;
    @FXML
    private Button viewbutton;
    @FXML
    private Label namelabel;
    @FXML
    private Button search;
    @FXML
    private ChoiceBox filter;
    @FXML
    private TableView<Voucher> tableView;
    @FXML
    private TableColumn<Voucher, Integer> idVoucherColumn;
    @FXML
    private TableColumn<Voucher, String> namaColumn;
    @FXML

    private TableColumn<Voucher, String> jenisColumn;
    @FXML
    private TableColumn<Voucher, java.sql.Date> tanggalColumn;
    @FXML
    private TableColumn<Voucher, String> kategoriColumn;
    @FXML
    private TableColumn actionColumn;
    @FXML
    private Button refresh;
    @FXML
    private Button add;
    @FXML
    private Label notification;

    private String user;
    private String name;

    private String getUser() {
        return user;
    }

    private void setUser(String user) {
        this.user = user;
    }
    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    private final Connection con = dbconnect.getConnection();

    public MainControl() throws SQLException {

    }


    public void onLogoutButtonClick(ActionEvent event) throws IOException {
        Alert a;
        a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Konfirmasi Logout");
        a.setHeaderText("Apakah anda yakin ingin Logout?");
        a.setContentText("Klik Ok untuk Logout");
        ButtonType result = a.showAndWait().orElse(ButtonType.CANCEL);
        if (result == ButtonType.OK){
            SessionManager.getInstance().logout();
            try {
                GUI.setRoot("LoginPage", "AMV-01", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void onAddButtonClick(ActionEvent event) throws IOException {
        GUI.openViewWithModal("addVoucherPage", "AMV-01", false);
    }
    public void onRefreshButtonClick(ActionEvent event) {
    }

    @FXML
    private void initializeTable() {
        // Initialize TableView columns
        idVoucherColumn.setCellValueFactory(new PropertyValueFactory<>("IdVoucher"));
        namaColumn.setCellValueFactory(new PropertyValueFactory<>("namaVoucher"));
        jenisColumn.setCellValueFactory(new PropertyValueFactory<>("jenis"));
        tanggalColumn.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        kategoriColumn.setCellValueFactory(new PropertyValueFactory<>("kategori"));

        Callback<TableColumn<Voucher, Void>, TableCell<Voucher, Void>> cellFactory = param -> new TableCell<>() {
            javafx.scene.text.Font font = new Font("Berlin Sans FB", 12);

            private final Button detailButton = new Button("\uD83D\uDC41");
            private final Button editButton = new Button("\uD83D\uDCDD");
            private final Button deleteButton = new Button("\uD83D\uDDD1");


            private final HBox buttonbox = new HBox(detailButton, editButton, deleteButton);

            {
                detailButton.setFont(font);
                detailButton.setStyle("-fx-background-color: #7ABA78;-fx-border-width: 1;-fx-border-radius: 4;-fx-border-color: #337357");
                editButton.setFont(font);
                editButton.setStyle("-fx-background-color: #F3CA52;-fx-border-width: 1;-fx-border-radius: 4;-fx-border-color: #966f00");
                deleteButton.setFont(font);
                deleteButton.setStyle("-fx-background-color: #EF4040;-fx-border-width: 1;-fx-border-radius: 4;-fx-border-color: #7e0622;-fx-text-fill: #ffffff");
                buttonbox.setAlignment(Pos.CENTER);
                buttonbox.setSpacing(2);

                detailButton.setOnAction(event -> {
                    Voucher voucher = getTableView().getItems().get(getIndex());

                    try {
                        showDetailPopup(voucher);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                });


                editButton.setOnAction(event -> {
                    Voucher voucher = getTableView().getItems().get(getIndex());

                    try {
                        showEditPopup(voucher);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                });


                deleteButton.setOnAction(event -> {
                    Voucher voucher = getTableView().getItems().get(getIndex());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Konfirmasi Hapus");
                    alert.setHeaderText("Menghapus voucher " + voucher.getNamaVoucher() + " ?");

                    alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType == ButtonType.YES){
                            delVoucher(voucher);
//                            ChildControl.this.updateTableView();
                        }else{

                        }
                    });
                });
            }


            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Tampilkan tombol sesuai dengan mode
                    setGraphic(buttonbox);
                }
            }



        };
        actionColumn.setCellFactory(cellFactory);
    }

    private ObservableList<Voucher> loadDataIntoTableView(String user) {
        ObservableList<Voucher> data = FXCollections.observableArrayList();
        String query = "SELECT * FROM voucher WHERE username =?";
        // Connect to the database
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, user);
            // Execute query to retrieve data from database table
            try (ResultSet resultSet = statement.executeQuery()) {
                // Process result set and populate data into ObservableList
                while (resultSet.next()) {
                    int idVoucher = resultSet.getInt("idVoucher");
                    String usern = resultSet.getString("username");
                    String nama = resultSet.getString("nama");
                    String jenis = resultSet.getString("jenis");
                    String tgl = resultSet.getString("tanggalKadaluwarsa");
                    java.sql.Date tanggal = Date.valueOf(tgl);
                    String kategori = resultSet.getString("kategori");
                    String instruksi = resultSet.getString("instruksi");
                    String batasan = resultSet.getString("batasan");

                    data.add(new Voucher(idVoucher, usern, nama, jenis, tanggal, kategori, instruksi, batasan));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUser(SessionManager.getInstance().getUsername());
        setName(SessionManager.getInstance().getName());
        ObservableList<Voucher> vouchers = loadDataIntoTableView(getUser());
        tableView.setItems(vouchers);
        tableView.setEditable(false);
        initializeTable();
        namelabel.setText(getName());
    }

    public void onNotifClick() throws IOException {
        GUI.openViewWithModal("notif-view", "Notifikasi", false);
    }

    public void updateTableView() {
        ObservableList<Voucher> updatedVouchers = loadDataIntoTableView(getUser());
        tableView.setItems(updatedVouchers);
    }

    private void showEditPopup(Voucher voucher) throws IOException{

        GUI.openViewWithModal("editVoucherPage", "Edit Voucher", false, voucher);
        updateTableView();
    }

    private void showDetailPopup(Voucher voucher) throws IOException{

        GUI.openViewWithModal("detailVoucherPage", "Detail Voucher", false, voucher);
    }

    private void delVoucher(Voucher voucher){
        Alert a;
        String query = "Delete from voucher where idVoucher = ? and username = ?";

        try(PreparedStatement ps = con.prepareStatement(query))
        {
            ps.setInt(1,voucher.getIdVoucher());
            ps.setString(2,getUser());
            int hasil = ps.executeUpdate();
            if(hasil == 1){
                a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Berhasil!!");
                a.setContentText("Menghapus Voucher!!");
                a.showAndWait();
                updateTableView();
            }else {
                a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Gagal!!");
                a.setContentText("Menghapus Voucher!!");
                a.showAndWait();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
