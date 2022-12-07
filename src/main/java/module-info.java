module com.example.bjfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.bjfx to javafx.fxml;
    exports com.example.bjfx;
}