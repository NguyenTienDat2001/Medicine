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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class MedicineController implements Initializable {
    
    @FXML
    private ListView list;
    @FXML
    private Button back;
    @FXML
    private Button addMedicine;
    @FXML
    private Button deleteMedicine;
    @FXML
    private TextField txtdelete;
    @FXML 
    private Button find;
    @FXML
    private TextField txtFind;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        try {
            Connection conn = ConnectDB.getConnect();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM medicine M inner join category C on M.category_id = C.id;");
           
            while(rs.next()){
                observableList.add(String.format("Tên thuốc: %s Loại thuốc %s Số lượng: %d", rs.getString("name"),rs.getString("name_cat"),rs.getInt("quantity")));
                
            }
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(MedicineController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        list.setItems(observableList);
        list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                System.out.print("hello");
                int index = t1.replaceAll("Tên thuốc:", "").trim().indexOf("Loại thuốc");
                try {
                    Connection conn = ConnectDB.getConnect();
                    
                    PreparedStatement stm = conn.prepareStatement("SELECT * FROM medicine WHERE name = ?");
                    stm.setString(1, t1.replaceAll("Tên thuốc:", "").trim().substring(0, index));
                    System.out.println(t1.replaceAll("Tên thuốc:", "").trim().substring(0, index));
                    ResultSet result = stm.executeQuery();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    while(result.next()){
                        
                        alert.setTitle("Thông tin thuốc");
                        alert.setHeaderText(result.getString("name"));
                        alert.setContentText(result.getString("description"));
                        alert.setResizable(true);
                        alert.setHeight(500);
                        System.out.println(result.getString("description"));
                    }
                    alert.showAndWait();
                    
                    stm.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MedicineController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        back.setOnAction(eh->{
            try {
                App.setRoot("manager");
            } catch (IOException ex) {
                Logger.getLogger(MedicineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        addMedicine.setOnAction(eh->{
             try {
                App.setRoot("addmedicine");
            } catch (IOException ex) {
                Logger.getLogger(MedicineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        deleteMedicine.setOnAction(eh->{
            try {
                String name = txtdelete.getText();
                Connection conn = ConnectDB.getConnect();
                PreparedStatement stm = conn.prepareStatement("Delete from medicine where name = ?");
                stm.setString(1, name);
                stm.executeUpdate();
                stm.close();
                conn.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thành công");
                alert.setContentText("Xóa thuốc thành công");
                alert.showAndWait();
            } catch (SQLException ex) {
                Logger.getLogger(CategoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        find.setOnAction(eh->{
            try {
                ObservableList<String> l = FXCollections.observableArrayList();
                String q = txtFind.getText();
                Connection con = ConnectDB.getConnect();
                PreparedStatement stm = con.prepareStatement("SELECT * FROM medicine M inner join category C on M.category_id = C.id WHERE M.name like ? OR C.name_cat like ? ");
                stm.setString(1,"%" + q  +"%");
                stm.setString(2,"%" + q  +"%");
                ResultSet rs = stm.executeQuery();
                while(rs.next()){
                   l.add(String.format("Tên thuốc: %s Loại thuốc %s Số lượng: %d", rs.getString("name"),rs.getString("name_cat"),rs.getInt("quantity")));
                }
                list.refresh();
                list.setItems(l);
                stm.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(MedicineController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
    }    
    
}
