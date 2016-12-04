package de.fh.swf.inf.se.model;

import javafx.beans.property.*;
import de.fh.swf.inf.se.InfoWindows;
import javafx.collections.ObservableList;

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
    private ObservableList <Fach> liste;


    public Fach(String fachname, ObservableList <Fach> liste) {
        this.fachname = new SimpleStringProperty(fachname);
        this.note = new SimpleDoubleProperty(0.0);
        this.cp = new SimpleIntegerProperty(0);
        this.versuch = new SimpleIntegerProperty(0);
        this.liste = liste;
    }


    // Fachname
    public String getFachname() {
        return fachname.get();
    }

    public void setFachname(String fachname) {
        int i = 1;
        int oldCP = 0;
        for (Fach data : liste) {
            if (data.getFachname().equals(fachname))
                if (data.getNote() == 5.0) {
                    oldCP = data.getCp();
                    i++;
                } else {
                    new InfoWindows("INFO", null, "Note ist " + data.getNote() + "\nBereits bestanden!");
                    throw new IllegalArgumentException();
                }

        }

        try {
            this.fachname.set(fachname);
            this.setVersuch(i);
            this.setCp(oldCP);
        } catch (IllegalArgumentException e) {
            new InfoWindows("INFO", null, "Du kannst keine weiteren Noten für das eingegebene Fach");
        }
    }

    private void setListe(ObservableList <Fach> liste) {
        this.liste = liste;
    }

    private ObservableList <Fach> getListe() {
        return liste;
    }

    public StringProperty fachnameProperty() {
        return fachname;
    }

    // Note
    public Double getNote() {
        return note.get();
    }

    public void setNote(Double note) {
        if (!(note == 1.0 || note == 1.3 || note == 1.7
                || note == 2.0 || note == 2.3 || note == 2.7
                || note == 3.0 || note == 3.3 || note == 3.7
                || note == 4.0 || note == 5.0)) {
            new InfoWindows("INFO", null, "Bitte note im gültigen Notenbereich halten");
        } else this.note.set(note);
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
        if (versuch >= 1 && versuch <= 3) {
            this.versuch.set(versuch);
        } else throw new IllegalArgumentException();
    }

    public IntegerProperty versuchProperty() {
        return versuch;
    }

    // max versuche
    public Integer getMaxVersuche() {
        return MAXVERSUCHE;
    }
}
