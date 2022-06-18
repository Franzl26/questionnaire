module frank.lucassen.java {
    requires javafx.controls;
    requires javafx.fxml;

    opens frank.lucassen.java to javafx.fxml;
    exports frank.lucassen.java;
}