import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.swing.*;
import java.util.ArrayList;

public class Lobby extends Application{
    private String[] Gamelist;

    public void start(Stage fright) {
        try {

            BorderPane root = new BorderPane();

            root.setBottom(addHBox());
            root.setLeft(addFlowPane());
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
    public FlowPane addFlowPane() {
        FlowPane flow = new FlowPane();
        flow.setPadding(new Insets(5, 0, 5, 0));
        flow.setVgap(4);
        flow.setHgap(4);
        flow.setPrefWrapLength(170); // preferred width allows for two columns
        flow.setStyle("-fx-background-color: DAE6F3;");
       // for (int i = 0; i < Gamelist.length; i++ ) {
            ImageView page = new ImageView(
                    new Image("http://www.pressibus.org/reversi/gen/images/depart.gif", 200 , 200 , false , false));
        ImageView page2 = new ImageView(
        new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/3/32/Tic_tac_toe.svg/2000px-Tic_tac_toe.svg.png" , 200 , 200 , false, false));

            flow.getChildren().addAll(page, page2);
       // }

        return flow;
    }
    }
