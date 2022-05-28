module com.example.grafikacoffee {
    requires javafx.controls;
    requires java.prefs;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.grafikacoffee to javafx.fxml;
    exports com.example.grafikacoffee;
}