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

public class transaksi implements Initializable {

    @FXML
    private VBox panelTransaksi, panelDataTransaksi;

    @FXML
    private ImageView searchLogo, refreshImg;

    @FXML
    private Button searchBtn, tambahBtn, bayarBtn, refreshBtn;

    @FXML
    private TextField searchField, jumlahField, bayarField, totalHargaField;

    @FXML
    public Label stokLabel, alertBayarLabel, kembalianLabel, alertStokLabel;

    @FXML
    public Label judul;


    PreparedStatement preparedStatement;
    ResultSet resultSetBarang;
    ResultSet resultSetTransaksi;
    ResultSet resultSetSum;

    public String username;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File searchFile = new File("image/icons8-search-100.png");
        Image searchImage = new Image(searchFile.toURI().toString());
        searchLogo.setImage(searchImage);

        File refreshFile = new File("image/icons8-available-updates-96.png");
        Image refreshImage = new Image(refreshFile.toURI().toString());
        refreshImg.setImage(refreshImage);

        LocalDateTime now1 = LocalDateTime.now();
        DateTimeFormatter bulanformatter = DateTimeFormatter.ofPattern("MMMM");
        String bulan = bulanformatter.format(now1);

        tambahBtn.setDisable(true);

        showDataBarang();

        System.out.println(session.username != null);

        searchBtn.setOnMouseClicked(event -> {
            panelTransaksi.getChildren().clear();
            String searchKey =  searchField.getText();
            jumlahField.setText("");

            try {
                preparedStatement = DBUtils.getConnect().prepareStatement("SELECT * FROM tb_menu WHERE nama_menu LIKE ?");
                preparedStatement.setString(1, "%" + searchKey + "%");
                resultSetBarang = preparedStatement.executeQuery();

                while (resultSetBarang.next()){

                    String nama_menu = resultSetBarang.getString("nama_menu");
                    String jenis_menu = resultSetBarang.getString("jenis_menu");
                    int harga = resultSetBarang.getInt("harga");

                    FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("item_transaksi.fxml"));
                    Parent root = (Parent) loader.load();
                    item_transaksi itemTransaksiController = loader.getController();
                    itemTransaksiController.searchData(resultSetBarang.getString("nama_menu"));
                    panelTransaksi.getChildren().add(root);
                    int id_menu = resultSetBarang.getInt("id");

                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
                    String tanggal = formatter.format(now);


                    itemTransaksiController.dataButton.setOnMouseClicked(event1 -> {
                        panelTransaksi.getChildren().clear();
                        jumlahField.setText("");
                        tambahBtn.setDisable(false);

                        try {
                            FXMLLoader loader1 = new FXMLLoader(DBUtils.class.getResource("item_transaksi.fxml"));
                            Parent root1 = null;
                            root1 = (Parent) loader1.load();
                            item_transaksi itemTransaksiController1 = loader1.getController();
                            itemTransaksiController1.searchData(nama_menu);
                            panelTransaksi.getChildren().add(root1);

                            tambahBtn.setOnMouseClicked(event2 -> {
                                int jumlah = Integer.parseInt(jumlahField.getText());
                                int total = jumlah * harga;

                                totalHargaField.setText("");
                                bayarField.setText("");
                                kembalianLabel.setText("");

                                tambahBtn.setDisable(true);

                                try {

                                    preparedStatement = DBUtils.getConnect().prepareStatement("INSERT INTO prepare_laporan(nama_menu, jenis_menu, jumlah, harga, total, nama_kasir, tanggal) VALUES (?,?,?,?,?,?,?)");
                                    preparedStatement.setString(1, nama_menu);
                                    preparedStatement.setString(2, jenis_menu);
                                    preparedStatement.setInt(3, jumlah);
                                    preparedStatement.setInt(4, harga);
                                    preparedStatement.setInt(5, total);
                                    preparedStatement.setString(6,"davis");
                                    preparedStatement.setString(7, tanggal);
                                    preparedStatement.execute();

                                    preparedStatement = DBUtils.getConnect().prepareStatement("SELECT SUM(total) AS totalharga FROM prepare_laporan");
                                    resultSetSum = preparedStatement.executeQuery();

                                    resultSetSum.next();
                                    totalHargaField.setText(String.valueOf(resultSetSum.getInt("totalharga")));

                                    panelDataTransaksi.getChildren().clear();
                                    showDataTransaksi();


                                }catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    });
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void showDataBarang(){

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        String tanggal = formatter.format(now);

        try {
            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT * FROM tb_menu");
            resultSetBarang = preparedStatement.executeQuery();

            while (resultSetBarang.next()){
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("item_transaksi.fxml"));
                Parent root = (Parent) loader.load();
                item_transaksi itemTransaksiController = loader.getController();
                itemTransaksiController.searchData(resultSetBarang.getString("nama_menu"));
                panelTransaksi.getChildren().add(root);

                String nama_menu = resultSetBarang.getString("nama_menu");
                int harga = resultSetBarang.getInt("harga");
                String jenis_menu = resultSetBarang.getString("jenis_menu");

                itemTransaksiController.dataButton.setOnMouseClicked(event1 -> {
                    panelTransaksi.getChildren().clear();
                    jumlahField.setText("");
                    tambahBtn.setDisable(false);

                    try {
                        FXMLLoader loader1 = new FXMLLoader(DBUtils.class.getResource("item_transaksi.fxml"));
                        Parent root1 = null;
                        root1 = (Parent) loader1.load();
                        item_transaksi itemTransaksiController1 = loader1.getController();
                        itemTransaksiController1.searchData(nama_menu);
                        panelTransaksi.getChildren().add(root1);

                        tambahBtn.setOnMouseClicked(event2 -> {
                            int jumlah = Integer.parseInt(jumlahField.getText());
                            int total = jumlah * harga;

                            totalHargaField.setText("");
                            bayarField.setText("");
                            kembalianLabel.setText("");

                            tambahBtn.setDisable(true);

                            try {

                                preparedStatement = DBUtils.getConnect().prepareStatement("INSERT INTO prepare_laporan(nama_menu, jenis_menu, jumlah, harga, total, nama_kasir, tanggal) VALUES (?,?,?,?,?,?,?)");
                                preparedStatement.setString(1, nama_menu);
                                preparedStatement.setString(2, jenis_menu);
                                preparedStatement.setInt(3, jumlah);
                                preparedStatement.setInt(4, harga);
                                preparedStatement.setInt(5, total);
                                preparedStatement.setString(6,"davis");
                                preparedStatement.setString(7, tanggal);
                                preparedStatement.execute();

                                preparedStatement = DBUtils.getConnect().prepareStatement("SELECT SUM(total) AS totalharga FROM prepare_laporan");
                                resultSetSum = preparedStatement.executeQuery();

                                resultSetSum.next();
                                totalHargaField.setText(String.valueOf(resultSetSum.getInt("totalharga")));

                                panelDataTransaksi.getChildren().clear();
                                showDataTransaksi();


                            }catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showDataTransaksi(){
        try {
            preparedStatement = DBUtils.getConnect().prepareStatement("SELECT * FROM prepare_laporan");
            resultSetTransaksi = preparedStatement.executeQuery();

            while (resultSetTransaksi.next()){
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("item_data_transaksi.fxml"));
                Parent root = (Parent) loader.load();
                item_data_transaksi itemDataTransaksiController = loader.getController();
                itemDataTransaksiController.setDataTransaksi(resultSetTransaksi.getString("nama_menu"), resultSetTransaksi.getInt("jumlah"), resultSetTransaksi.getInt("harga"), resultSetTransaksi.getInt("total"));
                panelDataTransaksi.getChildren().add(root);

                int idBarang = resultSetTransaksi.getInt("id");
                int jumlah = resultSetTransaksi.getInt("jumlah");
                String jenis_menu = resultSetTransaksi.getString("jenis_menu");
                String nama_menu = resultSetTransaksi.getString("nama_menu");
                int harga = resultSetTransaksi.getInt("harga");

                itemDataTransaksiController.hapusBtn.setOnMouseClicked(event -> {
                    try {
                        preparedStatement = DBUtils.getConnect().prepareStatement("DELETE FROM prepare_laporan WHERE id=?");
                        preparedStatement.setInt(1, idBarang);
                        preparedStatement.execute();

                        preparedStatement = DBUtils.getConnect().prepareStatement("SELECT SUM(total) AS totalharga FROM prepare_laporan");
                        resultSetSum = preparedStatement.executeQuery();
                        resultSetSum.next();

                        totalHargaField.setText(String.valueOf(resultSetSum.getInt("totalharga")));


                        panelDataTransaksi.getChildren().clear();
                        showDataTransaksi();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                });

                bayarBtn.setOnMouseClicked(event2 -> {
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
                    String tanggal = formatter.format(now);

                    try {
                        preparedStatement = DBUtils.getConnect().prepareStatement("SELECT SUM(total) AS totalharga FROM prepare_laporan");
                        resultSetSum = preparedStatement.executeQuery();

                        resultSetSum.next();

                        int totalHarga = resultSetSum.getInt("totalharga");
                        int bayar = Integer.parseInt(bayarField.getText());

                        if (totalHarga > bayar){
                            alertBayarLabel.setText("");
                            alertBayarLabel.setText("Pembayaran tidak cukup");
                        }else{
                            alertBayarLabel.setText("");
                            int kembalian = bayar - totalHarga;

                            kembalianLabel.setText(String.valueOf(kembalian));

                            preparedStatement = DBUtils.getConnect().prepareStatement("INSERT INTO tb_laporan (nama_menu, jenis_menu, total_terjual, pemasukan, nama_kasir, tanggal) SELECT nama_menu, jenis_menu, jumlah, total, nama_kasir, tanggal FROM prepare_laporan");
                            preparedStatement.execute();

                            preparedStatement = DBUtils.getConnect().prepareStatement("DELETE FROM prepare_laporan");
                            preparedStatement.execute();

                            preparedStatement = DBUtils.getConnect().prepareStatement("INSERT INTO tb_log(username, tanggal, aktivitas) VALUES (?,?,?)");
                            preparedStatement.setString(1, "davis");
                            preparedStatement.setString(2, tanggal);
                            preparedStatement.setString(3, "Melakukan Transaksi");
                            preparedStatement.execute();

                        }


                    }catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

                refreshBtn.setOnMouseClicked(event -> {
                    totalHargaField.setText("");
                    kembalianLabel.setText("");
                    bayarField.setText("");
                    jumlahField.setText("");
                    panelDataTransaksi.getChildren().clear();
                    panelTransaksi.getChildren().clear();
                });

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
