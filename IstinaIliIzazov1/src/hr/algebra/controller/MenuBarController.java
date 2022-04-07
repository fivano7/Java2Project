/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controller;

import hr.algebra.DAL.Repository;
import hr.algebra.IstinaIliIzazovApplication;
import hr.algebra.model.Igra;
import hr.algebra.utilities.ReflectionUtils;
import hr.algebra.utilities.SerializationUtils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class MenuBarController implements Initializable {

    private Igra trenutnaIgra;
    public static final String FILE_NAME = "igra.ser";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        trenutnaIgra = Repository.getTrenutnaIgra();
    }

    @FXML
    private void prikaziInformacije(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informacije");
        alert.setHeaderText("Informacije o aplikaciji:");
        alert.setContentText("Istina ili izazov aplikacija \n"
                + "Verzija: 1.0\n"
                + "Developer: Filip Ivanović");

        alert.showAndWait();

    }

    @FXML
    private void zatvoriAplikaciju(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    private void otvoriPostavkeSucelja(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/PostavkeSucelja.fxml"));

        Stage stage = new Stage();
        stage.setTitle("Postavke sučelja");
        stage.setScene(new Scene(root));

        stage.show();
    }

    @FXML
    private void zavrsiIgru(ActionEvent event) throws IOException {
        Repository.getTrenutnaIgra().setBrojRunde(0);

        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/ZavrsetakIgre.fxml"));

        Stage mainStage = IstinaIliIzazovApplication.getMainStage();
        mainStage.setScene(new Scene(root));

        mainStage.show();
    }

    @FXML
    private void spremiIgru() {

        SerializationUtils.spremiIgru(trenutnaIgra, FILE_NAME);

    }

    @FXML
    private void ucitajIgru() {

        try {
            Igra ucitanaIgra = (Igra) SerializationUtils.read(FILE_NAME);
            Repository.setTrenutnaIgra(ucitanaIgra);
            trenutnaIgra = ucitanaIgra;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspjeh");
            alert.setHeaderText("Igra je uspješno učitana!");
            alert.showAndWait();

            String odabraniProzor = "";

            odabraniProzor = trenutnaIgra.getEkranPrvogIgraca();

            Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/" + odabraniProzor + ".fxml"));
            Stage mainStage = IstinaIliIzazovApplication.getMainStage();
            mainStage.setScene(new Scene(root));
            mainStage.show();

        } catch (IOException | ClassNotFoundException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Pogreška kod učitavanja igre");

            alert.showAndWait();
        }
    }

    @FXML
    private void kreirajDokumentaciju(ActionEvent event) {

        String rootPackage = "hr.algebra";

        StringBuilder sb = new StringBuilder();

        sb.append("<!DOCTYPE html>\n");
        sb.append("<html>\n");
        sb.append("<head>\n");
        sb.append("<title>Dokumentacija projekta Istina ili Izazov" + "</title>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");

        sb.append("<ul>");

        String packageLocation = ".\\src\\hr\\algebra";
        String[] packages = new File(packageLocation).list();

        for (String currentPackage : packages) {

            if (currentPackage.endsWith(".java")) {
                sb.append("<h1>").append(rootPackage).append("</h1>");
                try {
                    Class clazz = Class.forName(rootPackage + "."
                            + currentPackage.substring(0, currentPackage.indexOf(".")));
                    ReflectionUtils.readClassAndMembersInfo(clazz, sb);

                } catch (ClassNotFoundException ex) {
                    System.out.println("GREŠKA");
                }
            } else {
                sb.append("<h1>").append(rootPackage).append(".").append(currentPackage).append("</h1>");
                String[] classes = new File(packageLocation + "\\" + currentPackage).list();

                for (String classe : classes) {

                    if (classe.endsWith(".java")) {
                        try {
                            Class clazz = Class.forName(rootPackage + "." + currentPackage + "." + classe.substring(0, classe.indexOf(".java")));

                            ReflectionUtils.readClassAndMembersInfo(clazz, sb);

                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }

            }

        }

        sb.append("</ul>");
        
        sb.append("</body>\n");
        sb.append("</html>\n");

        
        try (FileWriter zapisivac = new FileWriter("dokumentacija.html")) {
            zapisivac.write(sb.toString());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspješno generiranje dokumentacije");
            alert.setHeaderText(
                    "Dokumentacija s popisom klasa je uspješno generirana!");
            alert.setContentText(
                    "Datoteka \"dokumentacija.html\" je uspješno generirana!");

            alert.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Neuspješno generiranje dokumentacije");
            alert.setHeaderText(
                    "Dogodila se pogreška tijekom generiranja datoteke"
                    + " s dokumentacijom!");
            alert.setContentText(
                    "Datoteka \"dokumentacija.html\" nije uspješno "
                    + "generirana!");

            alert.showAndWait();
        }
        //USPJEŠNO LOADANO!!
        System.out.println(sb);

    }

}
