package de.fh.swf.inf.se.model;

import javafx.beans.property.*;

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
        this.versuch = new SimpleIntegerProperty(1);
    }


    // Fachname
    public String getFachname() {
        return fachname.get();
    }

    public void setFachname(String fachname) {

        this.fachname.set(fachname);

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
        this.versuch.set(versuch);
    }

    public IntegerProperty versuchProperty() {
        return versuch;
    }

    // max versuche
    public Integer getMaxVersuche() {
        return MAXVERSUCHE;
    }
}
