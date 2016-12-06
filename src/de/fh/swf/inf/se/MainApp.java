package de.fh.swf.inf.se;

import de.fh.swf.inf.se.controller.AbschlussnotenController;
import de.fh.swf.inf.se.controller.NotenListeController;
import de.fh.swf.inf.se.model.Abschlussnoten;
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
import javafx.stage.FileChooser;
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
            //TODO: Wenn datei nicht existiert?
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

    public ObservableList<Fach> getNotenListe() {
        return notenListe;
    }

    /**
     * Returns the main stage.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     *
     * @param file
     */
    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return
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
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param file the file or null to remove the path
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

    public void loadDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(NotenListeWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            notenListe.clear();
            NotenListeWrapper wrapper = (NotenListeWrapper) um.unmarshal(new FileReader(file));


            notenListe.addAll(wrapper.getNoteliste());

            // Save the file path to the registry.
            setFilePath(file);


        } catch (Exception e) { // catches ANY exception
            System.out.println(e);
            notenListe.add(new Fach("Neue Prüfung", notenListe));

            new InfoWindows("Error", "Datei nicht geladen", "Datei" + file.getAbsolutePath() + " konnte nicht geladen werden");
        }
    }

    /**
     * Saves the current person data to the specified file.
     *
     * @param file
     */
    public void saveDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(NotenListeWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            NotenListeWrapper wrapper = new NotenListeWrapper();
            wrapper.setNoteliste(notenListe);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setFilePath(file);
        } catch (Exception e) { // catches ANY exception
            new InfoWindows("Error", "Datei nicht gespeichert", "Datei konnte nicht gespeichert werden");
        }
    }

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

    public boolean showAbschluss(ObservableList<Fach> list) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Abschlussnoten.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Abschlussnoten");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.

            AbschlussnotenController controller = loader.getController();
            controller.setList(notenListe);
            controller.setDialogStage(dialogStage);


            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}