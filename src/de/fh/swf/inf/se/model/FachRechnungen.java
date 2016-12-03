package de.fh.swf.inf.se.model;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by dsee on 02.12.2016.
 */
public final class FachRechnungen {

    /*public static int rechneCP(TableView<Fach> tb, TableColumn<Fach, Integer> col) {
        final int[] ges = {0};

        tb.getItems().stream().forEach((o) -> {
            ges[0] += (col.getCellData(o));
        });

        return ges[0];
    } */
    public static int rechneCP(ObservableList<Fach> liste) {
        Integer cp = 0;

        for (Fach data : liste) {
            cp += data.getCp();
        }
        return cp;
    }
/*
    //TODO: Formatierung von Double - Rechnung mit 2 dezimalstellen, ausgeben von 1
    public static Double rechneNote(TableView<Fach> tb, TableColumn<Fach, Double> colNote, TableColumn<Fach, Integer> colCP) {
        final Double[] ges = {0.0};
        final int gesCP = rechneCP(tb, colCP);
        //TODO: Implementierung von Versuchupdate notwerndig
        tb.getItems().stream().forEach((o) -> {
            if ((colNote.getCellData(o)) <= 4.0) {
                double cp = (double) colCP.getCellData(o) / (double) gesCP;
                ges[0] += colNote.getCellData(o) * cp;
            }
        });
        return ges[0];
    } */

    public static Double rechneNote(ObservableList<Fach> liste) {
        Double ges = 0.0;
        int gesCP = rechneCP(liste);
        //TODO: Implementierung von Versuchupdate notwerndig
        for (Fach data : liste) {
            double cp = (double)data.getCp()/(double) gesCP;
            ges += data.getNote() * cp;
        }
        return ges;
    }

    public void FachRechnungen() {

    }
}