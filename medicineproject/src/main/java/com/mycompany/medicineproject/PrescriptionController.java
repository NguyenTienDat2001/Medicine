/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.medicineproject;

import com.mycompany.utils.ConnectDB;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class PrescriptionController implements Initializable {
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnBack;
    @FXML
    private TextField txtName;
    @FXML
    private ListView list;
    @FXML
    private Button btnAddPrescription;
    @FXML
    private DatePicker date;
    @FXML
    private TextField txtNamedelete;
    @FXML
    private Button btnDeletePrescription;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int c =0;
        ObservableList<String> l = FXCollections.observableArrayList();
        try {
            btnBack.setOnAction(eh->{
                try {
                    App.setRoot("manager");
                } catch (IOException ex) {
                    Logger.getLogger(PrescriptionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            Connection conn = ConnectDB.getConnect();
            PreparedStatement stmm  = conn.prepareStatement("SELECT * FROM prescription");
            ResultSet r = stmm.executeQuery();
            while(r.next()){
                l.add("M?? toa thu???c: "+r.getInt("id") + " Th??ng tin toa thu???c: "+ r.getString("name") + " Ng??y l???p: " + r.getDate("date").toString());
                c = r.getInt("id");
            }
            stmm.close();
            conn.close();
            list.setItems(l);
            final int d = c;
            btnAdd.setOnAction(eh->{
                try {
                    
                    Connection con = ConnectDB.getConnect();
                    PreparedStatement stm  = con.prepareStatement("INSERT INTO prescription(name,date) VALUES(?,?)");
                    stm.setString(1, txtName.getText());
                    stm.setString(2, date.getValue().format(DateTimeFormatter.ISO_DATE).toString() );
                    stm.executeUpdate();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Th??nh c??ng");
                    alert.setContentText("Th??nh c??ng");
                    alert.showAndWait();
                    l.add("M?? toa thu???c: " + String.valueOf(d +1) + " Th??ng tin toa thu???c: " + txtName.getText() + " Ng??y l???p: " + date.getValue().format(DateTimeFormatter.ISO_DATE).toString());
                    list.refresh();
                    list.setItems(l);
                    stm.close();
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PrescriptionController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            
            });
        btnDeletePrescription.setOnAction(eh->{
                try {
                    
                    Connection con = ConnectDB.getConnect();
                    PreparedStatement stm  = con.prepareStatement("Delete from prescription where name = ?");
                    stm.setString(1, txtNamedelete.getText());
                    stm.executeUpdate();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Th??nh c??ng");
                    alert.setContentText("X??a toa thu???c th??nh c??ng");
                    alert.showAndWait();
                    App.setRoot("manager");
                    stm.close();
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PrescriptionController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PrescriptionController.class.getName()).log(Level.SEVERE, null, ex);
                }
        });
        } catch (SQLException ex) {
            Logger.getLogger(PrescriptionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        btnAddPrescription.setOnAction(eh->{
            try {
                App.setRoot("addprescription");
            } catch (IOException ex) {
                Logger.getLogger(PrescriptionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                try {
                    int a = Integer.parseInt( t1.replace("M?? toa thu???c:", "").substring(1, 2));
                    String content = "";
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    Connection connn = ConnectDB.getConnect();
                    PreparedStatement stmmm = connn.prepareStatement("SELECT * FROM medicine M inner join prescription_detail D on M.id = D.medicine_id inner join prescription P on D.prescription_id = P.id WHERE P.id = ?");
                    stmmm.setInt(1, a);
                    
                    ResultSet rs = stmmm.executeQuery();
                    while(rs.next()){
                        content+= " Thu???c: " +rs.getString("name");
                        content+=" H?????ng d???n u???ng: " +rs.getString("sup") + "\n";
                    }
                    
                    stmmm.close();
                    connn.close();
                    alert1.setTitle("Th??ng tin toa thu???c");
                    alert1.setContentText(content);
                    alert1.setHeight(500);
                    alert1.showAndWait();
                } catch (SQLException ex) {
                    Logger.getLogger(PrescriptionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
    }    
    
}
