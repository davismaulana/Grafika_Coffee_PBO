package com.example.grafikacoffee;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class user_tabel implements Initializable {
    @FXML
    public VBox panelBarang = null;

    @FXML
    private TextField username, password, level;

    @FXML
    private AnchorPane formPane;

    @FXML
    private Button simpanBtn, tutupBtn, tambahBarang;

    @FXML
    private Label judulLabel;


    PreparedStatement preparedStatement;
    ResultSet resultSet;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        showData();

        formPane.setVisible(false);

        simpanBtn.setOnMouseClicked(event -> {

            String usernameField = username.getText();
            String passwordField = password.getText();
            String levelField = level.getText();

            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = DBUtils.getConnect().prepareStatement("INSERT INTO tb_user(username, password, level) VALUES(?,?,?)");
                preparedStatement.setString(1,usernameField);
                preparedStatement.setString(2,passwordField);
                preparedStatement.setString(3,levelField);
                preparedStatement.execute();

                panelBarang.getChildren().clear();

                showData();

                formPane.setVisible(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            username.setText("");
            password.setText("");
            level.setText("");

        });

        tambahBarang.setOnMouseClicked(event -> {
            formPane.setVisible(true);
            username.setText("");
            password.setText("");
            level.setText("");
            judulLabel.setText("Tambah Data User");
        });

        tutupBtn.setOnMouseClicked(event -> {
            formPane.setVisible(false);
        });

    }

    public void showData() {
        Parent root = null;
        try {
            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT * FROM tb_user WHERE level != ?");
            preparedStatement.setString(1, "admin");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("item_userTabel.fxml"));
                root = (Parent) loader.load();
                item_userTabelController itemController = loader.getController();
                itemController.setDataBarang(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("level"));
                panelBarang.getChildren().add(root);

                String idData = String.valueOf(resultSet.getInt("id"));
                String usernameData = resultSet.getString("username");
                String passwordData = resultSet.getString("password");
                String levelData = resultSet.getString("level");

                itemController.deleteBtn.setOnMouseClicked(event -> {
                    try {
                        preparedStatement = DBUtils.getConnect().prepareStatement("DELETE FROM tb_user WHERE id= ?");
                        preparedStatement.setString(1, idData);
                        preparedStatement.execute();


                        panelBarang.getChildren().clear();
                        showData();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
//
                itemController.editBtn.setOnMouseClicked(event -> {
                    formPane.setVisible(true);
                    judulLabel.setText("Ubah Data User");
                    username.setText(usernameData);
                    password.setText(passwordData);
                    level.setText(levelData);

                    simpanBtn.setOnMouseClicked(event1 -> {
                        String usernameUp = username.getText();
                        String passwordUp = password.getText();
                        String levelUp = level.getText();

                        try {
                            preparedStatement = DBUtils.getConnect().prepareStatement("UPDATE tb_user SET username= ?, password= ?, level= ? WHERE id= ?");
                            preparedStatement.setString(1, usernameUp);
                            preparedStatement.setString(2, passwordUp);
                            preparedStatement.setString(3, levelUp);
                            preparedStatement.setString(4, idData);
                            preparedStatement.executeUpdate();


                            panelBarang.getChildren().clear();
                            showData();

                            formPane.setVisible(false);

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });
                });
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
