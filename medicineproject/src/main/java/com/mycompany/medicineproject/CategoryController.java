/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.medicineproject;

import com.mycompany.utils.ConnectDB;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class CategoryController implements Initializable {
    @FXML
    private ListView list;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnBack;
    @FXML
    private TextField txtName;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectDB.getConnect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM category");
            while(rs.next()){
                observableList.add("Tên danh mục: " + rs.getString("name_cat"));
            }
            stm.close();
            conn.close();
            list.setItems(observableList);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        btnAdd.setOnAction(eh->{
            try {
                String name = txtName.getText();
                Connection conn = ConnectDB.getConnect();
                PreparedStatement stm = conn.prepareStatement("INSERT INTO category(name_cat) VALUES(?)");
                stm.setString(1, name);
                stm.executeUpdate();
                stm.close();
                conn.close();
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setContentText("Thêm danh mục thành công");
                alert.showAndWait();
                observableList.add(name);
                list.refresh();
                list.setItems(observableList);
            } catch (SQLException ex) {
                Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnBack.setOnAction(eh->{
            try {
                App.setRoot("manager");
            } catch (IOException ex) {
                Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
    }    
    
}
