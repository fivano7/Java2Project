/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utilities;

import hr.algebra.IstinaIliIzazovApplication;
import hr.algebra.model.Igra;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author filip
 */
public class SerializationUtils {



    private SerializationUtils() {
    }

    public static void write(Object object, String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(object);
        }
    }

    public static Object read(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return ois.readObject();
        }
    }

    public static void spremiIgru(Igra trenutnaIgra, String FILE_NAME) {
        try {
            SerializationUtils.write(trenutnaIgra, FILE_NAME);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspjeh");
            alert.setHeaderText("Igra je uspješno spremljena!");
            alert.showAndWait();
        } catch (IOException iOException) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Greška");
            alert.setHeaderText("Pogreška kod spremanja igre");

            alert.showAndWait();
        }
    }


}
