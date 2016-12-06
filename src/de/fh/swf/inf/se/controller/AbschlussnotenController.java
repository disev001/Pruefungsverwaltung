package de.fh.swf.inf.se.controller;

import de.fh.swf.inf.se.model.Abschlussnoten;
import de.fh.swf.inf.se.model.Fach;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Created by dsee on 06.12.2016.
 */
public class AbschlussnotenController {
    @FXML
    private TextField tfAB;
    @FXML
    private TextField tfK;
    @FXML
    private Label lblAn;
    @FXML
    private Label lblGCP;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnOK;
    @FXML
    private ComboBox<Double> cbAB;
    @FXML
    private ComboBox<Double> cbK;

    ObservableList<Double> notenauswahl = FXCollections.observableArrayList();

    private Stage dialogStage;
    boolean okClicked;
    ObservableList<Fach> list;
    Double abschluss;
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        okClicked = true;

        setKolloquium();
        setAbschlussnote();

        dialogStage.close();
    }


    @FXML
    private void initialize() {
        //Eintragen der Noten
        notenauswahl.addAll(1.0, 1.3, 1.7, 2.0, 2.3, 2.7, 3.0, 3.3, 3.7, 4.0, 4.3, 4.7, 5.0, 0.0);
        cbAB.setItems(notenauswahl);
        cbK.setItems(notenauswahl);

        //Auswahl Events

    }
    private void setAbschlussnote(){
        if (!cbAB.getValue().equals(0.0) ) list.add(new Abschlussnoten("Abschlussarbeit", cbAB.getValue()));
    }
    private void setKolloquium(){
        if (!cbK.getValue().equals(0.0) ) {
            list.add(new Abschlussnoten("Kolloquium",cbK.getValue()));
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public void setList(ObservableList<Fach> list) {
        this.list = list;
        for (Fach a : list)
        {
            if (a.getFach().equals("Abschlussnote")){
                cbAB.setValue(a.getNote());
            }
            if (a.getFach().equals("Kolloquium")){
                cbK.setValue(a.getNote());
            }
            else {
                cbAB.setValue(0.0);
                cbK.setValue(0.0);
            }
        }
    }
}
