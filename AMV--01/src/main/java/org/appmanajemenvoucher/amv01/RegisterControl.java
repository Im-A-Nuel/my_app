package org.appmanajemenvoucher.amv01;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

public class RegisterControl {


    @FXML
    private TextField txtnamadepan;
    @FXML
    private TextField txtnamabelakang;
    @FXML
    private TextField txtusername;
    @FXML
    private PasswordField txtpassword;
    @FXML
    private PasswordField txtrepeatpass;
    @FXML
    private CheckBox ketentuan;
    @FXML
    private TextField txtnotelepon;
    @FXML
    private Button buttonback;
    private Connection con = dbconnect.getConnection();

    public RegisterControl() throws SQLException {

    }

    public void btnBackClick(ActionEvent event) throws IOException {

        Alert a;
        a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Konfirmasi");
        a.setContentText("Apakah anda yakin ingin kembali ?");
        ButtonType result = a.showAndWait().orElse(ButtonType.CANCEL);
        if (result == ButtonType.OK){
            try {
                GUI.setRoot("LoginPage", "AMV-01", false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void btnDaftarClick(ActionEvent event) {
        Alert a;
        String inputusername = txtusername.getText();
        String inputpass = txtpassword.getText();
        String inputfirtsname = txtnamadepan.getText();
        String inputlastname = txtnamabelakang.getText();
        String reinputpass = txtrepeatpass.getText();
        String inputnotelepon = txtnotelepon.getText();

        if(Stream.of(inputfirtsname, inputusername, inputpass, inputlastname, reinputpass, inputnotelepon).allMatch(String::isEmpty)){
            String st = "Silahkan lengkapi data anda!!";
            a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText(st);
            a.showAndWait();
        }else{
            if(inputpass.length() >= 8){
                if(!(inputpass.equals(reinputpass))){
                    String st = "Ulangi password anda!!";
                    a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("Error");
                    a.setContentText(st);
                    a.showAndWait();
                }else{
                    String check = "SELECT username from user where username = ?";
                    try{
                        PreparedStatement ps = con.prepareStatement(check);
                        ps.setString(1, inputusername);
                        try(ResultSet resultSet = ps.executeQuery()){
                            if(resultSet.next()){
                                String st = "Username tidak tersedia!!";
                                a = new Alert(Alert.AlertType.ERROR);
                                a.setHeaderText("Error");
                                a.setContentText(st);
                                a.showAndWait();
                            }else{
                                if(ketentuan.isSelected()){
                                    String query = "INSERT INTO user(username, password, firstname, lastname, telepon) VALUES(?,?,?,?,?)";
                                    try(PreparedStatement statement = con.prepareStatement(query)){
                                        statement.setString(1,inputusername);
                                        statement.setString(2,inputpass);
                                        statement.setString(3,inputfirtsname);
                                        statement.setString(4,inputlastname);
                                        statement.setString(5,inputnotelepon);

                                        int rowsAffected = statement.executeUpdate();

                                        if(rowsAffected > 0){
                                            String st = "User registered successfully";
                                            a = new Alert(Alert.AlertType.INFORMATION);
                                            a.setHeaderText("Information");
                                            a.setContentText(st);
                                            a.showAndWait();
                                        }else{
                                            String st = "Failed to registered user";
                                            a = new Alert(Alert.AlertType.INFORMATION);
                                            a.setHeaderText("Information");
                                            a.setContentText(st);
                                            a.showAndWait();
                                        }

                                    }catch (SQLException e){
                                        e.printStackTrace();
                                    }
                                }else {
                                    String st = "Anda belum menyetujui S&K!!";
                                    a = new Alert(Alert.AlertType.ERROR);
                                    a.setHeaderText("Error");
                                    a.setContentText(st);
                                    a.showAndWait();
                                }
                            }
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
            }else{
                String st = "Password minimal 8 karakter!!";
                a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Error");
                a.setContentText(st);
                a.showAndWait();
            }
        }
        }

    }
