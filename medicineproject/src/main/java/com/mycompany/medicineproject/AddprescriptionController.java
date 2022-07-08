/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.medicineproject;

import com.mycompany.utils.ConnectDB;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class AddprescriptionController implements Initializable {
    
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnBack;
    @FXML
    private TextField txtId;
    @FXML
    private TextField quantity;
    @FXML
    private ComboBox cbbName;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList l = FXCollections.observableArrayList();
        try {
            Connection con = ConnectDB.getConnect();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM medicine");
            while(rs.next()){
                l.add(rs.getString("name"));
            }
            con.close();
            stm.close();
            cbbName.setItems(l);
        } catch (SQLException ex) {
            Logger.getLogger(AddprescriptionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            
        btnAdd.setOnAction(eh->{
            
            try{
                final int a ;
           
                int b =0;
                
           
                
              
  
                Connection con = ConnectDB.getConnect();
                Statement stmm = con.createStatement();
                ResultSet r = stmm.executeQuery("SELECT * FROM medicine");
                
               while(r.next()){
                   if(r.getString("name").equals(cbbName.getValue().toString())){
                       b = r.getInt("id");
                   }
               }
               a=b;
                
                stmm.close();
                
                PreparedStatement stm = con.prepareStatement("INSERT INTO prescription_detail(prescription_id,medicine_id,sup) VALUES(?,?,?)");
                stm.setInt(1, Integer.parseInt(txtId.getText()));
                stm.setInt(2, a);
                stm.setString(3, quantity.getText());
                stm.executeUpdate();
                con.close();
                stm.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setContentText("Thành công");
                alert.showAndWait();
            } catch (SQLException ex) {
                Logger.getLogger(AddprescriptionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnBack.setOnAction(eh->{
            try {
                App.setRoot("prescription");
            } catch (IOException ex) {
                Logger.getLogger(AddprescriptionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    
    
}
