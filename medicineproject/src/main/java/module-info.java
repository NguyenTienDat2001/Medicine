module com.mycompany.medicineproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    
    requires java.sql;
    requires java.base;
    
    opens com.mycompany.medicineproject to javafx.fxml;
    exports com.mycompany.medicineproject;
}
