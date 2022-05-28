package com.example.grafikacoffee;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class menu_tabel implements Initializable {
    @FXML
    public VBox panelBarang = null;

    @FXML
    private TextField nama, jenis, harga;

    @FXML
    private AnchorPane formPane;

    @FXML
    private Button simpanBtn, tutupBtn, tambahBarang;

    @FXML
    private Label judulLabel;


    PreparedStatement preparedStatement;
    ResultSet resultSet;

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
    String tanggal = formatter.format(now);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        showData();

        formPane.setVisible(false);

        simpanBtn.setOnMouseClicked(event -> {

            String nama_menu = nama.getText();
            String jenis_menu = jenis.getText();
            String harga_menu = harga.getText();

            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = DBUtils.getConnect().prepareStatement("INSERT INTO tb_menu(nama_menu, jenis_menu, harga) VALUES(?,?,?)");
                preparedStatement.setString(1, nama_menu);
                preparedStatement.setString(2, jenis_menu);
                preparedStatement.setString(3, harga_menu);
                preparedStatement.execute();

                preparedStatement = DBUtils.getConnect().prepareStatement("INSERT INTO tb_log(username, tanggal, aktivitas) VALUES (?,?,?)");
                preparedStatement.setString(1, "angel");
                preparedStatement.setString(2, tanggal);
                preparedStatement.setString(3, "Menambah data menu");
                preparedStatement.execute();

                panelBarang.getChildren().clear();

                showData();

                formPane.setVisible(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            nama.setText("");
            jenis.setText("");
            harga.setText("");

        });

        tambahBarang.setOnMouseClicked(event -> {
            formPane.setVisible(true);
            nama.setText("");
            jenis.setText("");
            harga.setText("");
            judulLabel.setText("Tambah Data Menu");
        });

        tutupBtn.setOnMouseClicked(event -> {
            formPane.setVisible(false);
        });

    }

    public void showData() {
        Parent root = null;
        try {
            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT * FROM tb_menu");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("item_menu_tabel.fxml"));
                root = (Parent) loader.load();
                item_menu_tabel itemMenuController = loader.getController();
                itemMenuController.setDataBarang(resultSet.getInt("id"), resultSet.getString("nama_menu"), resultSet.getString("jenis_menu"), String.valueOf(resultSet.getInt("harga")));
                panelBarang.getChildren().add(root);

                String idData = String.valueOf(resultSet.getInt("id"));
                String nama_menu = resultSet.getString("nama_menu");
                String jenis_menu = resultSet.getString("jenis_menu");
                String harga_menu = String.valueOf(resultSet.getInt("harga"));

                itemMenuController.deleteBtn.setOnMouseClicked(event -> {
                    try {
                        preparedStatement = DBUtils.getConnect().prepareStatement("DELETE FROM tb_menu WHERE id= ?");
                        preparedStatement.setString(1, idData);
                        preparedStatement.execute();

                        preparedStatement = DBUtils.getConnect().prepareStatement("INSERT INTO tb_log(username, tanggal, aktivitas) VALUES (?,?,?)");
                        preparedStatement.setString(1, "angel");
                        preparedStatement.setString(2, tanggal);
                        preparedStatement.setString(3, "Menghapus data menu");
                        preparedStatement.execute();

                        panelBarang.getChildren().clear();
                        showData();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
//
                itemMenuController.editBtn.setOnMouseClicked(event -> {
                    formPane.setVisible(true);
                    judulLabel.setText("Ubah Data Menu");
                    nama.setText(nama_menu);
                    jenis.setText(jenis_menu);
                    harga.setText(harga_menu);

                    simpanBtn.setOnMouseClicked(event1 -> {
                        String namaUp = nama.getText();
                        String jenisUp = jenis.getText();
                        String hargaUp = harga.getText();

                        try {
                            preparedStatement = DBUtils.getConnect().prepareStatement("UPDATE tb_menu SET nama_menu= ?, jenis_menu= ?, harga= ? WHERE id= ?");
                            preparedStatement.setString(1, namaUp);
                            preparedStatement.setString(2, jenisUp);
                            preparedStatement.setString(3, hargaUp);
                            preparedStatement.setString(4, idData);
                            preparedStatement.executeUpdate();

                            preparedStatement = DBUtils.getConnect().prepareStatement("INSERT INTO tb_log(username, tanggal, aktivitas) VALUES (?,?,?)");
                            preparedStatement.setString(1, "angel");
                            preparedStatement.setString(2, tanggal);
                            preparedStatement.setString(3, "Mengubah data menu");
                            preparedStatement.execute();

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
