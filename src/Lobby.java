import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Lobby extends Application{

    public void start(Stage fright) {
        try {

        GridPane root = new GridPane();
            Button btn = new Button();
            btn.setText("'start game'");
            btn.setAlignment(Pos.BASELINE_CENTER);
            btn.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    System.out.println("Hello World!");
                }
            });

            root.getChildren().add(btn);
        final Scene s = new Scene(root, 1000, 600, Color.DARKSLATEGRAY);
        fright.setScene(s);
        fright.setTitle("Lobby");
        fright.show();

        }catch(IllegalStateException e){
            System.out.println("welp");
        }
    }

}
