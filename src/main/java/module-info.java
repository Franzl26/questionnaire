module frank.lucassen.java {
    requires javafx.controls;

    opens frank.lucassen.java to javafx.fxml;
    exports frank.lucassen.java;
}