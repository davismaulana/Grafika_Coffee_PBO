package com.example.grafikacoffee;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class laporan_tabel_kasir implements Initializable {
    @FXML
    private VBox panelLaporan = null;

    @FXML
    private Label keuntunganLabel;

    @FXML
    private Label pendapatanLabel;

    PreparedStatement preparedStatement;
    ResultSet resultSetData;
    ResultSet resultSetPendapatan;
    ResultSet resultSetKeuntungan;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT * FROM tb_laporan WHERE nama_kasir = ?");
            preparedStatement.setString(1, "davis");
            resultSetData = preparedStatement.executeQuery();


            while (resultSetData.next()) {

                int id = resultSetData.getInt("id");
                String nama_menu = resultSetData.getString("nama_menu");
                String jenis_menu = resultSetData.getString("jenis_menu");
                int terjual = resultSetData.getInt("total_terjual");
                int pendapatan = resultSetData.getInt("pemasukan");
                String nama_kasir = resultSetData.getString("nama_kasir");
                String tanggal = resultSetData.getString("tanggal");

                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("item_laporan_tabel.fxml"));
                Parent root = (Parent) loader.load();
                item_laporan_tabel itemLaporanController = loader.getController();
                itemLaporanController.dataLaporan(id, nama_menu, jenis_menu, terjual, pendapatan, nama_kasir,tanggal);
                panelLaporan.getChildren().add(root);
            }

            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT SUM(total_terjual) AS totalTerjual FROM tb_laporan WHERE nama_kasir = ?");
            preparedStatement.setString(1, "davis");
            resultSetPendapatan = preparedStatement.executeQuery();
            resultSetPendapatan.next();
            keuntunganLabel.setText(String.valueOf(resultSetPendapatan.getInt("totalTerjual")));

            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT SUM(pemasukan) AS pendapatan FROM tb_laporan WHERE nama_kasir = ?");
            preparedStatement.setString(1, "davis");
            resultSetKeuntungan = preparedStatement.executeQuery();
            resultSetKeuntungan.next();
            pendapatanLabel.setText("Rp. " + String.valueOf(resultSetKeuntungan.getInt("pendapatan")));


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
