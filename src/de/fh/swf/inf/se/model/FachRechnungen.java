package de.fh.swf.inf.se.model;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by dsee on 02.12.2016.
 */
public final class FachRechnungen {

    public static int rechneCP(TableView<Fach> tb, TableColumn<Fach,Integer> col) {
        final int[] ges = {0};

        tb.getItems().stream().forEach((o) -> {
            ges[0] += (col.getCellData(o));
        });

        return ges[0];
    }

    //
    public static Double rechneNote(TableView<Fach> tb, TableColumn<Fach, String> colName,
                                    TableColumn<Fach, Double> colNote, TableColumn<Fach, Integer> colCP,
                                    TableColumn<Fach, Integer> colVersuch) {
        final Double[] ges = {0.0};
        tb.getItems().stream().forEach((o) -> {
            if ((colNote.getCellData(o)) <= 4.0) {
                ges[0] += (colNote.getCellData(o));
            }
        });

        return ges[0];

    }

    public void FachRechnungen() {

    }
}