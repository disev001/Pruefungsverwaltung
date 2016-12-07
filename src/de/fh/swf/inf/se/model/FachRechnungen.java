package de.fh.swf.inf.se.model;

import javafx.collections.ObservableList;

import java.text.DecimalFormat;

/**
 * Created by dsee on 02.12.2016.
 */
public final class FachRechnungen {

    public static int rechneCP(ObservableList<Fach> liste) {
        Integer cp = 0;

        for (Fach data : liste) {
            if(data.getNote()>=1.0 && data.getNote()<= 4.0 && data.getCreditPoints()!= 0)
            cp += data.getCreditPoints();
        }
        return cp;
    }

    public static String rechneNote(ObservableList<Fach> liste) {
        Double ges = 0.0;
        int gesCP = rechneCP(liste);
        for (Fach data : liste) {
            double cp = (double)data.getCreditPoints()/(double) gesCP;
            if(data.getNote()>=1.0 && data.getNote()<= 4.0)
            ges += data.getNote() * cp;
        }
        DecimalFormat d = new DecimalFormat(".#");
        return d.format(ges);
    }
}