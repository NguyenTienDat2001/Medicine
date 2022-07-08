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
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class AddmedicineController implements Initializable {
    @FXML
    private Button add;
    @FXML
    private DatePicker date;
    @FXML
    private Button back;
    @FXML
    private TextField name;
    @FXML
    private TextField des;
    @FXML
    private TextField quantity;
    @FXML
    private ComboBox cbb;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            Connection con = ConnectDB.getConnect();
            Statement stmm = con.createStatement();
            ResultSet rs = stmm.executeQuery("SELECT * FROM category");
            while(rs.next()){
                observableList.add(rs.getString("name_cat"));
            }
           
            
            
            cbb.setItems(observableList);
            add.setOnAction(eh->{
                try {
                    final int cat;
                    Connection connect = ConnectDB.getConnect();
                    Statement stmmm = connect.createStatement();
                    ResultSet re = stmm.executeQuery("SELECT * FROM category WHERE name_cat = '" + String.valueOf(cbb.getValue()) + "' ");
                    re.next();
                    cat = re.getInt("id");
                   
                    System.out.println("Gia tri cbb" + cbb.getValue());
                    Connection conn = ConnectDB.getConnect();
                    PreparedStatement stm = conn.prepareStatement("INSERT INTO medicine(name,category_id,expired_date,description,quantity) VALUES(?,?,?,?,?)");
                    stm.setString(1, name.getText());
                    stm.setInt(2,cat);
                    stm.setString(3, date.getValue().format(DateTimeFormatter.ISO_DATE).toString());
                    stm.setString(4, des.getText());
                    stm.setInt(5, Integer.parseInt(quantity.getText()));
                    stm.executeUpdate();
                    stm.close();
                    
                    stmm.close();
                    stmmm.close();
                    connect.close();
                    con.close();
                    conn.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thành công");
                    alert.setContentText("Bạn đã thêm thành công");
                    alert.showAndWait();
                    // TODO
                } catch (SQLException ex) {
                    Logger.getLogger(AddmedicineController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            back.setOnAction(eh->{
                try {
                    App.setRoot("medicine");
                } catch (IOException ex) {
                    Logger.getLogger(AddmedicineController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(AddmedicineController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
}
