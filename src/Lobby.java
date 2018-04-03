import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Lobby extends Application{

    public void start(Stage fright) {
        try {

            BorderPane root = new BorderPane();

            root.setBottom(addHBox());

            final Scene s = new Scene(root, 1000, 600);

            fright.setTitle("Lobby");
            fright.setScene(s);
            fright.show();

        } catch (IllegalStateException e) {
            System.out.println("welp");
        }
    }

        public HBox addHBox () {
            HBox hbox = new HBox();
            hbox.setPadding(new Insets(15, 12, 15, 12));
            hbox.setSpacing(10);
            hbox.setStyle("-fx-background-color: #708090;");

            Button btn = new Button();
            btn.setText("'start game'");
            btn.setPrefSize(100, 20);

            hbox.setAlignment(Pos.CENTER);
            hbox.getChildren().addAll(btn);
            btn.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    System.out.println("Hello World!");
                }
            });
            BorderPane.setAlignment(hbox, Pos.CENTER);
            return hbox;
        }
    }
