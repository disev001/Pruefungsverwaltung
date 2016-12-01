package de.fh.swf.inf.se.view;

import de.fh.swf.inf.se.MainApp;
import de.fh.swf.inf.se.model.Fach;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class NotenListeController {

    @FXML
    private TableView<Fach> notenTable;
    @FXML
    private TableColumn<Fach, String> tc_fach;
    @FXML
    private TableColumn<Fach, Double> tc_note;
    @FXML
    private TableColumn<Fach, Integer> tc_cp;
    @FXML
    private TableColumn<Fach, Integer> tc_versuch;

    @FXML
    private Label lbl_note;
    @FXML
    private Label lbl_cp;
    @FXML
    private Label lbl_static_gesammtcp;
    @FXML
    private Label lbl_static_notendurchschnitt;
    @FXML
    private Button btn_save;
    @FXML
    private Button btn_extra;

    @SuppressWarnings("unused")
    private MainApp mainApp;

    /**
     * The constructor. The constructor is called before the initialize()
     * method.
     */
    public NotenListeController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        tc_fach.setCellValueFactory(cellData -> cellData.getValue().fachnameProperty());
        tc_note.setCellValueFactory(cellData -> cellData.getValue().noteProperty().asObject());
        tc_cp.setCellValueFactory(cellData -> cellData.getValue().cpProperty().asObject());
        tc_versuch.setCellValueFactory(cellData -> cellData.getValue().versuchProperty().asObject());
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        notenTable.setItems(mainApp.getNotenListe());
    }
}
