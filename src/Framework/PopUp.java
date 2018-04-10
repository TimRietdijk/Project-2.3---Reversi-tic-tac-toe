package Framework;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

public class PopUp {
    public void start() throws IOException {
        GridPane pane = new GridPane();
        // Informatie
        Label label1 = new Label();
        pane.add(label1, 0, 4);

        label1.setText("You received a challenge");

        Stage primaryStage = new Stage();
        Scene scene = new Scene(pane, 200, 120);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Challenge received");
        primaryStage.setMinHeight(120);
        primaryStage.setMinWidth(200);
        primaryStage.show();
    }
}
