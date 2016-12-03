package de.fh.swf.inf.se.model;

import javafx.beans.property.*;

import javafx.scene.control.Alert;
import  javafx.scene.control.TableView;

/**
 * Created by dsee on 28.11.2016.
 */

//TODO: Abfangen von Falschen eingaben wie ungültige Noten oder negativen CP
public class Fach {

    private final StringProperty fachname;
    private final Integer MAXVERSUCHE = 3;
    private DoubleProperty note;
    private IntegerProperty cp;
    private IntegerProperty versuch;

    public Fach() {
        this(null);
    }

    public Fach(String fachname) {
        this.fachname = new SimpleStringProperty(fachname);
        this.note = new SimpleDoubleProperty(0.0);
        this.cp = new SimpleIntegerProperty(0);
        this.versuch = new SimpleIntegerProperty(0);
    }


    // Fachname
    public String getFachname() {
        return fachname.get();
    }

    public void setFachname(String fachname,TableView<Fach> tb) {
        final int[] i = {1};
        tb.getItems().stream().forEach((o) ->{
            if(o.getFachname().equals(fachname))
            i[0]++;
            });
        try {
            this.setVersuch(i[0]);
            this.fachname.set(fachname);
        }
         catch (IllegalArgumentException e){
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("INFO");
             alert.setHeaderText(null);
             alert.setContentText("Du kannst keine weiteren Noten für dieses Fach eintragen");
             alert.showAndWait();
         }
    }

    public StringProperty fachnameProperty() {
        return fachname;
    }

    // Note
    public Double getNote() {
        return note.get();
    }

    public void setNote(Double note) {
        this.note.set(note);
    }

    public DoubleProperty noteProperty() {
        return note;
    }

    // CP
    public int getCp() {
        return cp.get();
    }

    public void setCp(int cp) {
        this.cp.set(cp);
    }

    public IntegerProperty cpProperty() {
        return cp;
    }

    // versuch
    public int getVersuch() {
        return versuch.get();
    }

    public void setVersuch(int versuch) {
        if(versuch >= 1 && versuch <=3){
            this.versuch.set(versuch);
        }else throw new IllegalArgumentException();
    }

    public IntegerProperty versuchProperty() {
        return versuch;
    }

    // max versuche
    public Integer getMaxVersuche() {
        return MAXVERSUCHE;
    }
}
