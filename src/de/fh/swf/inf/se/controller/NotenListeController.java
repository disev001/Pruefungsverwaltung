package de.fh.swf.inf.se.controller;

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

import java.io.File;

public class NotenListeController {

    private ObservableList<Fach> notenListe;
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
    private Button btn_save;
    @FXML
    private Button btn_extra;

    private MainApp mainApp;
    private File f;

    public NotenListeController() {

    }

    /**
     * Initialisierung, wird vom fxml loader aufgerufen
     */
    @FXML
    private void initialize() {
        tc_fach.setCellValueFactory(cellData -> cellData.getValue().fachProperty());
        tc_note.setCellValueFactory(cellData -> cellData.getValue().noteProperty().asObject());
        tc_cp.setCellValueFactory(cellData -> cellData.getValue().creditPointsProperty().asObject());
        tc_versuch.setCellValueFactory(cellData -> cellData.getValue().versuchProperty().asObject());


        fachnameEvents();
        noteEvents();
        cpEvents();
        versuchEvents();
        rowEvents();
    }

    /**
     * Events welche den Fachnamen betreffen
     */
    private void fachnameEvents() {
        //Editiertbarkeit Fachname
        tc_fach.setCellFactory(TextFieldTableCell.forTableColumn());
        tc_fach.setOnEditCommit(
                new EventHandler<CellEditEvent<Fach, String>>() {
                    @Override
                    //Ändern von Fachname
                    public void handle(CellEditEvent<Fach, String> t) {
                        if (!t.getNewValue().equals("Neue Prüfung")) {
                            notenListe.add(new Fach("Neue Prüfung", notenListe));
                        }
                        t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setFach(t.getNewValue());
                    }
                }
        );
    }

    /**
     * Events welche die Noten betreffen
     */
    private void noteEvents() {
        //Editiertbarkeit der Note
        tc_note.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        tc_note.setOnEditCommit(
                new EventHandler<CellEditEvent<Fach, Double>>() {
                    @Override
                    public void handle(CellEditEvent<Fach, Double> t) {
                        t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setNote(t.getNewValue());
                        setLabels();
                    }
                }
        );
    }

    /**
     * Events welche die CP betreffen
     */
    private void cpEvents() {
        //Editiertbarkeit der CP
        tc_cp.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tc_cp.setOnEditCommit(
                new EventHandler<CellEditEvent<Fach, Integer>>() {
                    @Override
                    public void handle(CellEditEvent<Fach, Integer> t) {
                        t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setCreditPoints(t.getNewValue());
                        lbl_note.setText(FachRechnungen.rechneNote(notenListe));
                        setLabels();
                    }
                }
        );
    }

    /**
     * Events welche die Versuche betreffen
     */
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

    /**
     * Löschen von Zeilen events
     */
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
                    setLabels();

                }
            }
        });
    }


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        setListe();
    }

    public void setListe() {
        notenListe = this.mainApp.getNotenListe();
        notenTable.setItems(notenListe);
    }

    @FXML
    private void handleNew() {
        mainApp.getNotenListe().clear();
        mainApp.setFilePath(null);
    }

    @FXML
    private void handleSave() {
        File f = mainApp.getFilePath();
        if (f != null) {
            mainApp.saveDataToFile(f);
        }
    }

    @FXML
    private void handleAbschlussnoten() {
        this.mainApp.showAbschluss(this.notenListe);
    }

    /**
     * änderung der Übersichtlabels
     */
    public void setLabels(){
        lbl_cp.setText(String.valueOf(FachRechnungen.rechneCP(notenListe)));
        lbl_note.setText(String.valueOf(FachRechnungen.rechneNote(notenListe)));
    }


}
