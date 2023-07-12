module com.ia.javafx {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
            requires net.synedra.validatorfx;
                requires org.kordamp.bootstrapfx.core;
	requires org.apache.pdfbox;
	requires org.jfree.chart.fx;
	requires org.jfree.jfreechart;
	requires java.desktop;

	opens com.ia.javafx to javafx.fxml;
    exports com.ia.javafx;
}