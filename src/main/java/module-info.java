module com.ia.ia {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
            requires net.synedra.validatorfx;
                requires org.kordamp.bootstrapfx.core;
            
    opens com.IA.JavaFX to javafx.fxml;
    exports com.IA.JavaFX;
}