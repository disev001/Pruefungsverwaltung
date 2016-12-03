package de.fh.swf.inf.se;

import javafx.scene.control.Alert;

/**
 * Created by dsee on 03.12.2016.
 */
public final class InfoWindows {

 public InfoWindows(String info, String header, String text)
 {
     Alert alert = new Alert(Alert.AlertType.INFORMATION);
     alert.setTitle(info);
     alert.setHeaderText(header);
     alert.setContentText(text);

     alert.showAndWait();
 }

}
