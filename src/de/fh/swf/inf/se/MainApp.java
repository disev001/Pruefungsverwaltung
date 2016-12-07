package de.fh.swf.inf.se;

import de.fh.swf.inf.se.controller.AbschlussnotenController;
import de.fh.swf.inf.se.controller.NotenListeController;
import de.fh.swf.inf.se.model.Fach;
import de.fh.swf.inf.se.model.NotenListeWrapper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.prefs.Preferences;

/**
 * Created by dsee on 28.11.2016.
 */
//https://github.com/disev001/Pruefungsverwaltung.git

public class MainApp extends Application {

    public ObservableList<Fach> notenListe = FXCollections.observableArrayList();
    private Stage primaryStage;

    private AnchorPane rootLayout;

    public MainApp() {

    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Laden der Oberfläche, der Controller und weiteren Initialisierungsdaten
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Pruefungsverwaltung");

        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Notenuebersicht.fxml"));
            rootLayout = loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            NotenListeController controller = loader.getController();
            controller.setMainApp(this);
            //Versuche datei zu laden
            handleOpen();
            controller.setLabels();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Fenster Schließen per Plattformabhängigem Closebutton
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        System.exit(0);
                    }
                });
            }
        });
    }

    //Übergabe der ObservableList an fremde Klassen um damit zu interagieren
    public ObservableList<Fach> getNotenListe() {
        return notenListe;
    }

    //Übergabe der Stage an fremde Klassen um damit zu interagieren
    public Stage getPrimaryStage() {
        return primaryStage;
    }


    /**
     * Besorgt den Ausführungspfad des Programms
     *
     * @return null für ungültigen Pfad
     */
    public File getFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Anzeigen der Benutzen Datei im Programmtitel
     *
     * @param file geladene Datei
     */
    public void setFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("Notenlist - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("Notenliste");
        }
    }

    /**
     * Lade Noten, wird beim start ausgeführt
     *
     * @param file vom Programm bestimmter Dateipfad
     */
    public void loadDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(NotenListeWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            notenListe.clear();
            NotenListeWrapper wrapper = (NotenListeWrapper) um.unmarshal(new FileReader(file));
            notenListe.addAll(wrapper.getNoteliste());
            setFilePath(file);

        } catch (Exception e) { // catches ANY exception
            //Falls keine oder ungültige Datei, lege neuen Eintrag an
            notenListe.add(new Fach("Neue Prüfung", notenListe));
            new InfoWindows("Error", "Datei nicht geladen", "Datei" + file.getAbsolutePath() + " konnte nicht geladen werden");
        }
    }

    /**
     * Speichern der Noten
     *
     * @param file pfad und name nicht frei wählbar
     */
    public void saveDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(NotenListeWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            NotenListeWrapper wrapper = new NotenListeWrapper();
            wrapper.setNoteliste(notenListe);
            m.marshal(wrapper, file);
            setFilePath(file);
        } catch (Exception e) { // catches ANY exception
            new InfoWindows("Error", "Datei nicht gespeichert", "Datei konnte nicht gespeichert werden");
        }
    }

    /**
     * festlegung des zu ladendes Pfades
     */
    private void handleOpen() {
        File f = null;
        try {
            String path = new File(".").getCanonicalPath();
            f = new File(path + "/notenliste.xml");

        } catch (Exception e) {
            new InfoWindows("ERROR", "Datei nicht vorhanden", "notenliste.xml im Ausführungsverzeichniss nicht vorhanden");
        }
        if (f != null) {
            loadDataFromFile(f);
        }
    }

    /**
     * Laden der Stage für Abschlussnoten Dialogfenster
     * @param list aktuelle notenliste
     *
     * @return reaktion auf die art des fenster schließens
     */
    public boolean showAbschluss(ObservableList<Fach> list) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Abschlussnoten.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Abschlussnoten");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            AbschlussnotenController controller = loader.getController();
            controller.setList(notenListe);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}