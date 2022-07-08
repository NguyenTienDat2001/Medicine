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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class PrimaryController {
   
    
    @FXML
    private Button login;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    
    @FXML
    private void switchToSecondary() throws IOException {
       
        App.setRoot("manager");
    }
    
    
    @FXML
    private void loginEvt(ActionEvent e){
        try {
            Connection conn = ConnectDB.getConnect();
           
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user");
            ResultSet result = stm.executeQuery();
            while(result.next()){
                if(result.getString("username").equals(username.getText()) && result.getString("password").equals(password.getText())) 
                try 
                {
                    System.err.println("Thanh Cong");
                    App.setRoot("manager");
                } catch (IOException ex) {
                   Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                }
                else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Lỗi");
                        alert.setContentText("Sai tài khoản hoặc mật khẩu.");
                        alert.showAndWait();
                        }
            }
            
            stm.close();
            conn.close();
        } catch (SQLException ex) {
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
