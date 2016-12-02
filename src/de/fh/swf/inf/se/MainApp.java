package de.fh.swf.inf.se;

import de.fh.swf.inf.se.model.Fach;
import de.fh.swf.inf.se.view.NotenListeController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by dsee on 28.11.2016.
 */
//https://github.com/disev001/Pruefungsverwaltung.git

public class MainApp extends Application {

    private ObservableList<Fach> notenListe = FXCollections.observableArrayList();
    private Stage primaryStage;

    private AnchorPane rootLayout;

    public MainApp() {
        // 1.Eintrag
        notenListe.add(new Fach("Neue Prüfung"));
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
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}