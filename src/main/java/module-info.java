module com.pooa {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.pooa to javafx.fxml;
    opens com.pooa.controller to javafx.fxml;

    exports com.pooa;
    exports com.pooa.controller;
}
