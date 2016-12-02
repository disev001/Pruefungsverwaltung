package de.fh.swf.inf.se.model;

import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by dsee on 02.12.2016.
 */
public final class FachRechnungen {

    public void FachRechnungen() {

    }

    public static int rechneCP(TableView<Fach> tb, TableColumn<Fach,Integer> col) {
        final int[] ges = {0};

        tb.getItems().stream().forEach((o) -> {
            ges[0] += (col.getCellData(o));
        });

        return ges[0];
    }
}