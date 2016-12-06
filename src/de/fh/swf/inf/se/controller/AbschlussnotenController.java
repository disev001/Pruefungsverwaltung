package de.fh.swf.inf.se.controller;

import de.fh.swf.inf.se.model.Fach;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by dsee on 06.12.2016.
 */
public class AbschlussnotenController {
    private static final double ABSCHLUSS_CP = 12.0;
    private static final double KOLLOQUIUM_CP = 3.0;
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
    Fach abschluss = new Fach();
    DecimalFormat d = new DecimalFormat(".#");

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        okClicked = true;
        setAbschlussnote(abschluss.getAbschlussNote());
        setKolloquium(abschluss.getKolloquiumNote());
        dialogStage.close();
    }


    @FXML
    private void initialize() {
        //Eintragen der Noten
        notenauswahl.addAll(1.0, 1.3, 1.7, 2.0, 2.3, 2.7, 3.0, 3.3, 3.7, 4.0, 4.3, 4.7, 5.0, 0.0);
        cbAB.setItems(notenauswahl);
        cbK.setItems(notenauswahl);

        //Auswahl Events
        cbAB.valueProperty().addListener(new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
                if (oldValue != newValue && (newValue != 0.0)) {
                    abschluss.setAbschlussNote(newValue);
                    abschluss.setAbschlussCP((int)ABSCHLUSS_CP);
                    lblAn.setText(d.format(rechneNote(abschluss.getAbschlussNote(),abschluss.getKolloquiumNote())));
                    lblGCP.setText(Double.toString(rechneCP(abschluss.getAbschlussNote(),abschluss.getKolloquiumNote())));
                }
            }
        });

        cbK.valueProperty().addListener(new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
                if (oldValue != newValue && (newValue != 0.0)) {
                    abschluss.setKolloquiumNote(newValue);
                    abschluss.setKolloquiumCP((int) KOLLOQUIUM_CP);
                    lblAn.setText(d.format(rechneNote(abschluss.getAbschlussNote(),abschluss.getKolloquiumNote())));
                    lblGCP.setText(Double.toString(rechneCP(abschluss.getAbschlussNote(),abschluss.getKolloquiumNote())));
                }
            }
        });
    }

    private void setAbschlussnote(Double note) {
     //   if (!cbAB.getValue().equals(0.0)) {
            for (Fach a : list) {
                a.setAbschlussNote(note);
                a.setAbschlussCP((int)ABSCHLUSS_CP);
            }
       // }
        lblAn.setText(d.format(rechneNote(abschluss.getAbschlussNote(),abschluss.getKolloquiumNote())));
        lblGCP.setText(Double.toString(rechneCP(abschluss.getAbschlussNote(),abschluss.getKolloquiumNote())));

    }

    private void setKolloquium(Double note) {
    //    if (!cbK.getValue().equals(0.0)) {
            for (Fach a : list) {
                a.setKolloquiumNote(note);
                a.setKolloquiumCP((int)KOLLOQUIUM_CP);
            }
     //   }
        lblAn.setText(d.format(rechneNote(abschluss.getAbschlussNote(),abschluss.getKolloquiumNote())));
        lblGCP.setText(Double.toString(rechneCP(abschluss.getAbschlussNote(),abschluss.getKolloquiumNote())));

    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public void setList(ObservableList<Fach> list) {
        this.list = list;
        boolean result = list.stream().anyMatch(obj -> {
            int i = 0;
            if (obj.getAbschlussNote() > 0.0) {
                cbAB.setValue(obj.getAbschlussNote());
                abschluss.setAbschlussNote(obj.getAbschlussNote());
                abschluss.setAbschlussCP((int)ABSCHLUSS_CP);
                i++;
            }
            if (obj.getKolloquiumNote() > 0.0) {
                cbK.setValue(obj.getKolloquiumNote());
                abschluss.setKolloquiumNote(obj.getKolloquiumNote());
                abschluss.setKolloquiumCP((int)KOLLOQUIUM_CP);
                i++;
            }
            if (i >0 ) {
                lblAn.setText(d.format(rechneNote(abschluss.getAbschlussNote(),abschluss.getKolloquiumNote())));
                lblGCP.setText(Double.toString(rechneCP(abschluss.getAbschlussNote(),abschluss.getKolloquiumNote())));
                return true;
            } else return false;
        });
        if (!result) {
            cbAB.setValue(0.0);
            cbK.setValue(0.0);
        }
    }

    private double rechneNote(double noteA, double noteK) {
        if (noteA != 0.0 && noteK != 0.0) {
            return ((noteA * (ABSCHLUSS_CP / (ABSCHLUSS_CP + KOLLOQUIUM_CP))) + (noteK * (KOLLOQUIUM_CP / (ABSCHLUSS_CP + KOLLOQUIUM_CP))));
        } else if (noteA != 0.0) {
            return noteA;
        } else if (noteK != 0.0) {
            return noteK;
        } else {
            return 0.0;
        }

    }

    private double rechneCP(double noteA, double noteK) {
        if (noteA != 0.0 && noteK != 0.0) {
            return ABSCHLUSS_CP + KOLLOQUIUM_CP;
        } else if (noteA != 0.0) {
            return ABSCHLUSS_CP;
        } else if (noteK != 0.0) {
            return KOLLOQUIUM_CP;
        } else {
            return 0;
        }

    }

}
