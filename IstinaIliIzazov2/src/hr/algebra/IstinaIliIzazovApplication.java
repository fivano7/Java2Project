/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra;

import hr.algebra.DAL.Repository;
import hr.algebra.controller.MenuBarController;
import static hr.algebra.controller.MenuBarController.FILE_NAME;
import hr.algebra.model.Igra;
import hr.algebra.utilities.SerializationUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author filip
 */
public class IstinaIliIzazovApplication extends Application {

    private static Stage mainStage;
    private Igra trenutnaIgra;

    @Override
    public void start(Stage primaryStage) throws IOException {

        trenutnaIgra = Repository.getTrenutnaIgra();

        mainStage = primaryStage;

        /* mainStage.setOnCloseRequest((WindowEvent event) -> {
        //your code goes here
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Spremanje podataka o igri");
        alert.setHeaderText("Da li želite spremiti podatke o igri prije gašenja?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
        SerializationUtils.spremiIgru(trenutnaIgra, MenuBarController.FILE_NAME);
        }
        }
        );
        
        mainStage.setOnShown((WindowEvent event) -> {
        
        File ucitaniFile = new File(FILE_NAME);
        
        if (ucitaniFile.length() != 0) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Učitavanje podataka");
        alert.setHeaderText("Da li želite učitati zadnje prethodno spremljene podatke o igri?");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
        try {
        Igra ucitanaIgra = (Igra) SerializationUtils.read(FILE_NAME);
        Repository.setTrenutnaIgra(ucitanaIgra);
        trenutnaIgra = ucitanaIgra;
        
        Alert obavijest = new Alert(Alert.AlertType.INFORMATION);
        obavijest.setTitle("Uspjeh");
        obavijest.setHeaderText("Igra je uspješno učitana!");
        obavijest.showAndWait();
        
        String odabraniProzor = "";
        
        odabraniProzor = trenutnaIgra.getImeTrenutnogEkrana();
        
        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/" + odabraniProzor + ".fxml"));
        Stage mainStage = IstinaIliIzazovApplication.getMainStage();
        mainStage.setScene(new Scene(root));
        mainStage.show();
        
        } catch (IOException | ClassNotFoundException e) {
        
        Alert obavijest = new Alert(Alert.AlertType.ERROR);
        obavijest.setTitle("Greška");
        obavijest.setHeaderText("Pogreška kod učitavanja igre");
        
        obavijest.showAndWait();
        }
        }
        }
        
        });*/

        Parent root = FXMLLoader.load(getClass().getResource("view/Pocetna.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setTitle("Početna");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getMainStage() {
        return mainStage;
    }

}
