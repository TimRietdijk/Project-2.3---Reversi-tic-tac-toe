import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PopUp {
    public void start(String title, String info) throws IOException {

        GridPane pane = new GridPane();

        // Informatie
        Label label1 = new Label();
        pane.add(label1, 0, 4);

        label1.setText(info);

        Stage primaryStage = new Stage();
        Scene scene = new Scene(pane, 200, 120);
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
        primaryStage.setMinHeight(120);
        primaryStage.setMinWidth(200);
        primaryStage.show();
    }
}
