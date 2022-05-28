package com.example.grafikacoffee;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class item_log_tabel implements Initializable {

    @FXML
    private Label idLabel, username, password, level;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setDataBarang(int id, String usernameData, String passwordData, String levelData) {
        idLabel.setText(String.valueOf(id));
        username.setText(usernameData);
        password.setText(passwordData);
        level.setText(levelData);
    }


}
