package com.example.grafikacoffee;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class item_laporan_tabel {
    @FXML
    private Label idLabel;

    @FXML
    private Label jenisMenu;

    @FXML
    private Label namaKasir;

    @FXML
    private Label namaMenu;

    @FXML
    private Label pendapatan;

    @FXML
    private Label tanggal;

    @FXML
    private Label totalTerjual;

    public void dataLaporan(int id, String nama, String jenis,int terjual, int pendapat, String kasir, String tgl){
        idLabel.setText(String.valueOf(id));
        namaMenu.setText(nama);
        jenisMenu.setText(jenis);
        totalTerjual.setText(String.valueOf(terjual));
        pendapatan.setText(String.valueOf(pendapat));
        namaKasir.setText(kasir);
        tanggal.setText(tgl);
    }
}
