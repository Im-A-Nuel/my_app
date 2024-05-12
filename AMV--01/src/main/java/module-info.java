module org.appmanajemenvoucher.amv01 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens org.appmanajemenvoucher.amv01 to javafx.fxml;
    exports org.appmanajemenvoucher.amv01;
}