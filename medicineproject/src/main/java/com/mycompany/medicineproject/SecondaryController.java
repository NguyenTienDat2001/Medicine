package com.mycompany.medicineproject;

import com.mycompany.utils.ConnectDB;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class SecondaryController implements Initializable{
    
    @FXML
    private Button btnMedicine;
    @FXML
    private Button btnCategory;
    @FXML
    private Button btnPrescription;
    @FXML
    private Button btnAlert;
    @FXML
    private ListView list;
    @FXML
    private Button logout;
    
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
    @FXML
    private void medicineManager() throws IOException{
        App.setRoot("medicine");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            String a = "";
            btnCategory.setOnAction(eh->{
                try {
                    App.setRoot("category");
                } catch (IOException ex) {
                    Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            logout.setOnAction(eh->{
                try {
                    App.setRoot("login");
                } catch (IOException ex) {
                    Logger.getLogger(PrescriptionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            btnPrescription.setOnAction(eh->{
                try {
                    App.setRoot("prescription");
                } catch (IOException ex) {
                    Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                
                Connection con = ConnectDB.getConnect();
                PreparedStatement stm = con.prepareStatement("SELECT * FROM medicine WHERE expired_date < ?");
                stm.setString(1, format.format(new GregorianCalendar().getTime()).toString());
                ResultSet rs = stm.executeQuery();
                while(rs.next()){
                    a+="Thuốc hết hạn : " + rs.getString("name") + " Ngày hết hạn: " + rs.getDate("expired_date").toString() +"\n";
                }
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setContentText(a);
                alert.showAndWait();
                stm.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
            }
            final String b = a;
            btnAlert.setOnAction(eh->{
                
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setContentText(b);
                alert.setHeight(500);
                alert.showAndWait();
                
                
            });
            ObservableList<String> l = FXCollections.observableArrayList();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Connection conn = ConnectDB.getConnect();
            PreparedStatement stmm = conn.prepareStatement("SELECT * FROM prescription WHERE date > ?");
            stmm.setString(1, format.format(new GregorianCalendar().getTime()).toString());
            ResultSet rs = stmm.executeQuery();
            while(rs.next()){
                l.add(rs.getString("name"));
            }
            list.setItems(l);
            stmm.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
}