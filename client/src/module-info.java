module client {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.base;

    opens com.eror.fxclient to javafx.fxml;
    opens com.eror.fxclient to javafx.graphics;
    exports com.eror.fxclient
}