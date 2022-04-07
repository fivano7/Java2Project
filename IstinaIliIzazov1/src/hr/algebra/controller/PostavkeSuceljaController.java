/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class PostavkeSuceljaController implements Initializable {
    
    @FXML
    private ComboBox<String> cbTeme;
    
    private static final String TAMNA_TEMA = "Tamna tema";
    
    private static final String PATH_TO_CSS = ".\\src\\hr\\algebra\\style\\main.css";


    private static final ObservableList<String> OPCIJE
            = FXCollections.observableArrayList(TAMNA_TEMA, "JavaFX tema");

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cbTeme.setItems(OPCIJE);
        cbTeme.getSelectionModel().select(0);
    }

    @FXML
    private void ucitajTemu(ActionEvent event) throws IOException {

        String odabranaTema = cbTeme.getValue();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Obavijest o promjeni teme");
        alert.setHeaderText("Za promjenu teme potrebno je ponovno upaliti aplikaciju");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            if (odabranaTema.equals(TAMNA_TEMA)) {

                try {
                    String styleCss = dohvatiCss(odabranaTema, "/hr/algebra/style/style.css");
                    zapisiUCss(styleCss, PATH_TO_CSS);

                } catch (IOException iOException) {
                    Alert alert2 = new Alert(AlertType.ERROR);
                    alert2.setTitle("Obavijest o učitavanju teme");
                    alert2.setHeaderText("Greška pri učitavanju teme!");
                }

            } else {
                zapisiUCss(" ", PATH_TO_CSS);
            }
            Platform.exit();
        }

    }


    private String dohvatiCss(String odabranaTema, String path) throws IOException {

        StringBuilder sb = new StringBuilder();

        InputStream in = getClass().getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }

        return sb.toString();
    }

    private void zapisiUCss(String text, String path) throws IOException {
        FileWriter fstream = new FileWriter(path);
        try (BufferedWriter out = new BufferedWriter(fstream)) {
            out.write(text);
        }
    }

    @FXML
    private void zatvoriProzor(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}
