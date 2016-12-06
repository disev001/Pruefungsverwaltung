package de.fh.swf.inf.se.model;

import de.fh.swf.inf.se.InfoWindows;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by dsee on 28.11.2016.
 */

@XmlType(propOrder = {"fach", "note", "creditPoints", "versuch"})
public class Fach {


    private ObservableList<Fach> noteliste = FXCollections.observableArrayList();

    private StringProperty fach;
    private DoubleProperty note;
    private IntegerProperty creditPoints;
    private IntegerProperty versuch;


    public Fach() {
        this.fach = new SimpleStringProperty("");
        this.note = new SimpleDoubleProperty(0.0);
        this.creditPoints = new SimpleIntegerProperty(0);
        this.versuch = new SimpleIntegerProperty(0);
    }

    public Fach(String fach,ObservableList<Fach> noteliste) {
        this.fach = new SimpleStringProperty(fach);
        this.note = new SimpleDoubleProperty(0.0);
        this.creditPoints = new SimpleIntegerProperty(0);
        this.versuch = new SimpleIntegerProperty(0);
        this.noteliste = noteliste;
    }

    public ObservableList<Fach> getNoteliste() {
        return noteliste;
    }
    public String getFach() {
        return fach.get();
    }

    public void setFach(String fach) {
        int i = 1;
        int oldCP = 0;
        if (noteliste == null)
            this.fach.set(fach);
        else {
            for (Fach data : noteliste) {
                if (data.getFach().equals(fach))
                    if (data.getNote() == 5.0) {
                        oldCP = data.getCreditPoints();
                        i++;
                    } else {
                        new InfoWindows("INFO", null, "Note ist " + data.getNote() + "\nBereits bestanden!");
                        throw new IllegalArgumentException();
                    }

            }

            try {
                this.fach.set(fach);
                this.setVersuch(i);
                this.setCreditPoints(oldCP);
            } catch (IllegalArgumentException e) {
                new InfoWindows("INFO", null, "Du kannst keine weiteren Noten für das eingegebene Fach");
            }
        }
    }

    public StringProperty fachProperty() {
        return fach;
    }

    public Double getNote() {
        return note.get();
    }

    public void setNote(Double note) {
        if (!(note == 0.0 || note == 1.0 || note == 1.3 || note == 1.7
                || note == 2.0 || note == 2.3 || note == 2.7
                || note == 3.0 || note == 3.3 || note == 3.7
                || note == 4.0 || note == 5.0)) {
            new InfoWindows("INFO", null, "Bitte note im gültigen Notenbereich halten");
        } else this.note.set(note);
    }

    public DoubleProperty noteProperty() {
        return note;
    }


    public Integer getCreditPoints() {
        return creditPoints.get();
    }

    public void setCreditPoints(Integer creditPoints) {
        this.creditPoints.set(creditPoints);
    }

    public IntegerProperty creditPointsProperty() {
        return creditPoints;
    }

    public Integer getVersuch() {
        return versuch.get();
    }

    public void setVersuch(Integer versuch) {
        if (versuch >= 0 && versuch <= 3) {
            this.versuch.set(versuch);
        } else throw new IllegalArgumentException();
    }

    public IntegerProperty versuchProperty() {
        return versuch;
    }

}
