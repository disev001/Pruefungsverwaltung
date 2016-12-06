package de.fh.swf.inf.se.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by dsee on 06.12.2016.
 */
public class AbschlussnotenController {
    @FXML
    private TextField tfAB;
    @FXML
    private TextField tfK;
    @FXML
    private Label lblAn;
    @FXML
    private Label lblGCP;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnOK;
    private Stage dialogStage;
    boolean okClicked;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        okClicked = true;
        dialogStage.close();
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
