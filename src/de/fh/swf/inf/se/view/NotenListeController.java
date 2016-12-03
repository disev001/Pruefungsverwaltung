package de.fh.swf.inf.se.view;

import de.fh.swf.inf.se.MainApp;
import de.fh.swf.inf.se.model.Fach;
import de.fh.swf.inf.se.model.FachRechnungen;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class NotenListeController {

    ObservableList<Fach> notenListe;
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

    @FXML
    private void initialize() {
        // Initialisierung, wird vom fxml loader aufgerufen
        tc_fach.setCellValueFactory(cellData -> cellData.getValue().fachnameProperty());
        tc_note.setCellValueFactory(cellData -> cellData.getValue().noteProperty().asObject());
        tc_cp.setCellValueFactory(cellData -> cellData.getValue().cpProperty().asObject());
        tc_versuch.setCellValueFactory(cellData -> cellData.getValue().versuchProperty().asObject());

        fachnameEvents();
        noteEvents();
        cpEvents();
        versuchEvents(); //überhaupt nötig?
        rowEvents();
    }
    private void fachnameEvents() {
        //Editiertbarkeit Fachname
        tc_fach.setCellFactory(TextFieldTableCell.forTableColumn());
        tc_fach.setOnEditCommit(
                new EventHandler<CellEditEvent<Fach, String>>() {
                    @Override
                    //Ändern von Fachname
                    public void handle(CellEditEvent<Fach, String> t) {
                        if (!t.getNewValue().equals("Neue Prüfung")) {
                            notenListe.add(new Fach("Neue Prüfung"));
                        }
                        t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setFachname(t.getNewValue(),notenTable);
                        //Ändern von Standartfachnamen erstellt neuen Eintrag

                    }
                }
        );
    }

    private void noteEvents() {
        //Editiertbarkeit der Note
        tc_note.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        tc_note.setOnEditCommit(
                new EventHandler<CellEditEvent<Fach, Double>>() {
                    @Override
                    public void handle(CellEditEvent<Fach, Double> t) {
                        t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setNote(t.getNewValue());

                        lbl_note.setText(String.valueOf(FachRechnungen.rechneNote(notenTable, tc_note, tc_cp)));
                    }
                }
        );
    }

    private void cpEvents() {
        //Editiertbarkeit der CP
        tc_cp.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tc_cp.setOnEditCommit(
                new EventHandler<CellEditEvent<Fach, Integer>>() {
                    @Override
                    public void handle(CellEditEvent<Fach, Integer> t) {
                        t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setCp(t.getNewValue());
                        lbl_note.setText(String.valueOf(FachRechnungen.rechneNote(notenTable, tc_note, tc_cp)));
                        lbl_cp.setText(String.valueOf(FachRechnungen.rechneCP(notenTable, tc_cp)));
                    }
                }
        );
    }

    private void versuchEvents() {
        //Editiertbarkeit der Versuche
        tc_versuch.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tc_versuch.setOnEditCommit(
                new EventHandler<CellEditEvent<Fach, Integer>>() {
                    @Override
                    public void handle(CellEditEvent<Fach, Integer> t) {
                        t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setVersuch(t.getNewValue());
                    }
                }
        );
    }
//TODO: was passiert mit versuchen beim beim löschen?
    private void rowEvents() {
        // Zeilen löschen
        notenTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent keyEvent) {
                final int selectedIndex = notenTable.getSelectionModel().getSelectedIndex();
                if (notenTable.getItems().size() > 1) {
                    if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                        notenTable.getItems().remove(selectedIndex);
                    }
                    lbl_cp.setText(String.valueOf(FachRechnungen.rechneCP(notenTable, tc_cp)));
                    lbl_note.setText(String.valueOf(FachRechnungen.rechneNote(notenTable, tc_note, tc_cp)));
                }
            }
        });
    }


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        setListe();
    }

    public void setListe() {

        // Add observable list data to the table
        notenListe = this.mainApp.getNotenListe();
        notenTable.setItems(notenListe);
    }

}
