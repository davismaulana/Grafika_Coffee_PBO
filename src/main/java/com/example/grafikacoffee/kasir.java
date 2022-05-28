package com.example.grafikacoffee;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class kasir implements Initializable {
    @FXML
    private ImageView menu, dashboard, logout, close, dashboard1;

    @FXML
    private AnchorPane pane2;

    @FXML
    private StackPane contentArea;

    @FXML
    private Button logout_btn, closeBtn;

    @FXML
    private Label usernameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        File menuFile = new File("image/icons8-menu-96.png");
        Image menuImage = new Image(menuFile.toURI().toString());
        menu.setImage(menuImage);

        File closeFile = new File("image/icons8-close-96.png");
        Image closeImage = new Image(closeFile.toURI().toString());
        close.setImage(closeImage);

        File dashboardFile = new File("image/icons8-calculator-128.png");
        Image dashboardImage = new Image(dashboardFile.toURI().toString());
        dashboard.setImage(dashboardImage);

        File dashboard1File = new File("image/icons8-menu-squared-100.png");
        Image dashboard1Image = new Image(dashboard1File.toURI().toString());
        dashboard1.setImage(dashboard1Image);

        File logFile = new File("image/icons8-logout-90.png");
        Image logImage = new Image(logFile.toURI().toString());
        logout.setImage(logImage);


        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("transaksi.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);

        } catch (IOException e) {
            Logger.getLogger(ModuleLayer.Controller.class.getName()).log(Level.SEVERE, null, e);
        }

        logout_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(GrafikaCoffeeApp.class.getResource("login.fxml"));
                    Scene scene = null;
                    scene = new Scene(fxmlLoader.load(), 520, 400);
                    Stage stage1 = new Stage();
                    stage1.initStyle(StageStyle.UNDECORATED);
                    stage1.setScene(scene);
                    stage1.centerOnScreen();
                    stage1.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = (Stage) logout_btn.getScene().getWindow();
                stage.close();
            }
        });
    }

    public void closeActionBtn(ActionEvent event){
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }


    public void transaksiPane(ActionEvent event) throws Exception{
        changeStackPane("transaksi.fxml");
    }

    public void laporanPane(ActionEvent event) throws Exception{
        changeStackPane("laporan_tabel_kasir.fxml");
    }

    public void changeStackPane(String fxmlFile) throws IOException {
        Parent fxml = FXMLLoader.load(admin.class.getResource(fxmlFile));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void setUserInformation(String username){
        usernameLabel.setText(username);
        Parent root = null;
            System.out.println("execute " + username);
    }
}
