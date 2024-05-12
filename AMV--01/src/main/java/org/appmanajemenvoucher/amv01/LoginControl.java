package org.appmanajemenvoucher.amv01;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginControl {

    @FXML
    private TextField usernamefield;
    @FXML
    private PasswordField passwordfield;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Hyperlink linkforgetpass;

    private Connection con = dbconnect.getConnection();

    public LoginControl() throws SQLException {
    }

    @FXML
    private void loginButtonClick() {
        Alert a;
        String inputusername = usernamefield.getText();
        String inputpass = passwordfield.getText();

        //query
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";

        try(PreparedStatement statement = con.prepareStatement(query)){
            statement.setString(1, inputusername);
            statement.setString(2, inputpass);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("firstname");
                    a = new Alert(Alert.AlertType.INFORMATION);
                    a.setHeaderText("Information");
                    a.setContentText("Selamat Datang " + name);
                    a.showAndWait();
                    String user = usernamefield.getText();
                    SessionManager.getInstance().login(user,name);
                    try {
                        GUI.setRoot("MainPage", "AMV-01", false);
                        System.out.println(user + " Login Successful");
                    }catch(IOException e){
                        e.printStackTrace();
                    }

                } else {
                    a = new Alert(Alert.AlertType.ERROR);
                    a.setHeaderText("Error");
                    a.setContentText("Login Gagal!!\nPeriksa Username dan Password anda!.");
                    a.showAndWait();
                    usernamefield.requestFocus();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    @FXML
    private void registerButtonClick(ActionEvent event) throws IOException {
        GUI.setRoot("registrationPage", "AMV-01", false);
    }

    @FXML
    private void linkfotgetpassClick(ActionEvent event) {

    }

    @FXML
    protected void onKeyPressEvent(KeyEvent event) throws IOException {
        if( event.getCode() == KeyCode.ENTER ) {
            loginButtonClick();
        }
    }
}
