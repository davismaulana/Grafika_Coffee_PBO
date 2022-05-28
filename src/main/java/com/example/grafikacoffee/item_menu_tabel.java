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

public class item_menu_tabel implements Initializable {
    @FXML
    private ImageView delete;

    @FXML
    private Label idLabel, username, password, level;

    @FXML
    private ImageView edit;

    @FXML
    public Button editBtn, deleteBtn;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File editFile = new File("image/icons8-edit-property-48.png");
        Image editImage = new Image(editFile.toURI().toString());
        edit.setImage(editImage);

        File deleteFile = new File("image/icons8-delete-48.png");
        Image deleteImage = new Image(deleteFile.toURI().toString());
        delete.setImage(deleteImage);
    }

    public void setDataBarang(int id, String nama_menuData, String jenis_menuData, String hargaData) {
        idLabel.setText(String.valueOf(id));
        username.setText(nama_menuData);
        password.setText(jenis_menuData);
        level.setText(hargaData);
    }
}
