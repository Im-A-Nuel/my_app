package org.appmanajemenvoucher.amv01;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class GUI extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.setTitle("AMV-01");
        if(SessionManager.getInstance().isLoggedIn()){
            primaryStage.setScene(new Scene(loadFXML("MainPage")));
        }else{
            primaryStage.setScene(new Scene(loadFXML("LoginPage")));
        }
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(appMain.class
                .getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void setRoot(String fxml, String title, boolean isResizeable)
            throws IOException {
        primaryStage.getScene().setRoot(loadFXML(fxml));
        primaryStage.sizeToScene();
        primaryStage.setResizable(isResizeable);
        if (title != null) {
            primaryStage.setTitle(title);
        }
        primaryStage.show();
    }

    public static void openViewWithModal(String fxml, String title, boolean isResizeable)
            throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(loadFXML(fxml)));
        stage.sizeToScene();
        stage.setTitle(title);
        stage.setResizable(isResizeable);
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
    }

    public static void openViewWithModal(String fxml, String title, boolean isResizeable, Voucher voucher)
            throws IOException{
        FXMLLoader loader = new FXMLLoader(GUI.class.getResource(fxml + ".fxml"));
        Parent root = loader.load();

        if(fxml.equals("editVoucherPage")){
            editVoucherControl controller = loader.getController();
            controller.showData(voucher);
        } else if (fxml.equals("detailVoucherPage")) {
            detailVoucherControl controller = loader.getController();
            controller.showData(voucher);
        }

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.setResizable(isResizeable);
        stage.initOwner(primaryStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
    }


    public static void main(String[] args) {
        launch();
    }
}