module hi.vidmot.verkefni {
    requires java.sql;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.controls;

    opens hi.vidmot.verkefni to javafx.fxml;
    exports hi.vidmot.verkefni;
    exports hi.vidmot.verkefni.controller;
    opens hi.vidmot.verkefni.controller to javafx.fxml;
}
