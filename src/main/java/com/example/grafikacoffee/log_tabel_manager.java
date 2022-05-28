package com.example.grafikacoffee;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class log_tabel_manager implements Initializable {
    @FXML
    public VBox panelBarang = null;

    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        showData();

    }

    public void showData() {
        Parent root = null;
        try {
            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT * FROM tb_log");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("item_log_tabel.fxml"));
                root = (Parent) loader.load();
                item_log_tabel itemController = loader.getController();
                itemController.setDataBarang(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("tanggal"), resultSet.getString("aktivitas"));
                panelBarang.getChildren().add(root);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
