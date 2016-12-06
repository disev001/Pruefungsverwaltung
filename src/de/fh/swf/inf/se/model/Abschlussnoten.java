package de.fh.swf.inf.se.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 * Created by dsee on 06.12.2016.
 */
public class Abschlussnoten extends Fach {

    public Abschlussnoten()
    {
        super();
    }


    @Override
    public void setFach(String fach) {
        super.setFach(fach);
        if (fach.equals("Abschlussarbeit"))
        {
            setCreditPoints(15);
        }
        if(fach.equals("Kolloquium")){
            setCreditPoints(5);
        }

    }

    @Override
    public String getFach() {
        return super.getFach();
    }

    @Override
    public Double getNote() {
        return super.getNote();
    }

    @Override
    public void setNote(Double note) {

        super.setNote(note);
    }

    @Override
    public Integer getCreditPoints() {
        return super.getCreditPoints();
    }

    @Override
    public void setCreditPoints(Integer creditPoints) {
        super.setCreditPoints(creditPoints);
    }


    @Override
    public Integer getVersuch() {
        return super.getVersuch();
    }

    @Override
    public void setVersuch(Integer versuch) {
        super.setVersuch(versuch);
    }

    @Override
    public ObservableList<Fach> getNoteliste() {
        return super.getNoteliste();
    }

    @Override
    public DoubleProperty noteProperty() {
        return super.noteProperty();
    }

    @Override
    public IntegerProperty creditPointsProperty() {
        return super.creditPointsProperty();
    }

    @Override
    public IntegerProperty versuchProperty() {
        return super.versuchProperty();
    }

    @Override
    public StringProperty fachProperty() {
        return super.fachProperty();
    }
}
