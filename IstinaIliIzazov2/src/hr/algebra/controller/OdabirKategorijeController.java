/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controller;

import hr.algebra.ClientThread;
import hr.algebra.DAL.Repository;
import hr.algebra.IstinaIliIzazovApplication;
import hr.algebra.model.Igra;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class OdabirKategorijeController implements Initializable {

    @FXML
    private Label lblIgracKojiIgra;
    @FXML
    private Button btnIstina;

    private Igra trenutnaIgra;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Stage mainStage = IstinaIliIzazovApplication.getMainStage();
        mainStage.setTitle("Odabir kategorije");
        
        trenutnaIgra = Repository.getTrenutnaIgra();

        String[] simpleName = this.getClass().getSimpleName().split("Controller");
        trenutnaIgra.setEkranDrugogIgraca(simpleName[0]);

        String prviIgrac = trenutnaIgra.getNadimakPrvogIgraca();
        String drugIgrac = trenutnaIgra.getNadimakDrugogIgraca();

        if (trenutnaIgra.getBrojRunde() % 2 == 1) {

            trenutnaIgra.setNadimakPrvogIgraca(drugIgrac);
            trenutnaIgra.setNadimakDrugogIgraca(prviIgrac);
        }

        lblIgracKojiIgra.setText(trenutnaIgra.getNadimakPrvogIgraca() + " bira kategoriju!");

    }

    @FXML
    private void odabirKategorije(ActionEvent event) throws IOException {

        Button odabraniGumb = (Button) event.getSource();

        //Imamo ovaj if i donji if zbog toga što ako postavimo trenutnu igru
        //poslije getMainStage neće se pravilno inicijalizirati
        if (odabraniGumb == btnIstina) {
            trenutnaIgra.setOdabranaIstina(true);

        } else {

            trenutnaIgra.setOdabranaIstina(false);
        }

        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/UnosTeksta.fxml"));
        Stage mainStage = IstinaIliIzazovApplication.getMainStage();

        mainStage.setScene(new Scene(root));
        mainStage.show();

    }
    
     private void updateTrenutnaIgra() {
        trenutnaIgra = new Igra("", "", "", "", 0, false, "", "", "");
        new ClientThread(trenutnaIgra).start();

        try {
            Thread.sleep(250);
        } catch (InterruptedException ex) {
            Logger.getLogger(PocetnaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        trenutnaIgra = ClientThread.getClientIgra();
        System.out.println("PocetnaController 1 - " + trenutnaIgra);
    }
    
     private void executeChange(String imeControllera) {
        new ClientThread(trenutnaIgra).start();
        
        try {
            Thread.sleep(250);
        } catch (InterruptedException ex) {
            Logger.getLogger(PocetnaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        trenutnaIgra = ClientThread.getClientIgra();
        Repository.setTrenutnaIgra(trenutnaIgra);
        System.out.println(imeControllera + " - " + ClientThread.getClientIgra());
    }

}
