import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class Lobby extends Application{
    private String Game;
    private TextField textField;
    private ComboBox comboBox2;
    private ComboBox comboBox1;
    private String[] optionList;
    private Stage fright;
    public void start(Stage start) {

        try {

            BorderPane root = new BorderPane();

            root.setBottom(addHBox());
            root.setLeft(addFlowPane());
            root.setCenter(nameset());
            root.setRight(options());

            final Scene s = new Scene(root, 1000, 600);
            fright = new Stage();
            fright.setTitle("Lobby");
            fright.setScene(s);
            fright.show();

        } catch (IllegalStateException e) {
            System.out.println("welp");
        }
    }

        private HBox addHBox () {
            HBox hbox = new HBox();
            hbox.setPadding(new Insets(15, 12, 15, 12));
            hbox.setSpacing(10);
            hbox.setStyle("-fx-background-color: #708090;");
            Button btn = new Button();
            btn.setText("'start game'");
            btn.setPrefSize(100, 20);

            Button connectBtn = new Button();
            connectBtn.setText("'connection'");
            connectBtn.setPrefSize(100, 20);

            hbox.setAlignment(Pos.CENTER);
            hbox.getChildren().addAll(btn, connectBtn);
            btn.setOnAction(e -> startgame());
            connectBtn.setOnAction(e -> Serverconnect());


            BorderPane.setAlignment(hbox, Pos.CENTER);
            hbox.setPrefHeight(100);
            return hbox;
        }
    private void Serverconnect() {
        ServerConnection connection = new ServerConnection();
        try {
            connection.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startgame() {
        if(Game != null && comboBox1.getValue().toString() != null && comboBox2.getValue().toString() != null){
        System.out.println("Starting: " + Game);
        String name = textField.getCharacters().toString();
        String option1 = comboBox1.getValue().toString();
        String option2 = comboBox2.getValue().toString();
            Map<String, String> optionlist = new HashMap<String, String>();
            optionlist.put("name", name);
            optionlist.put("Game", Game);
            optionlist.put("option1", option1);
            optionlist.put("option2", option2);
                fright.close();
                Stage s = new Stage();
                Framework so = new Framework();
                try {
                    so.start(s, optionlist);
                } catch (Exception e) {
                    e.printStackTrace();
                }

    }else{
            Pane root = new Pane();

            Label warning = new Label("Setting missing");
            warning.setAlignment(Pos.CENTER);
            root.getChildren().addAll(warning);
            final Scene s = new Scene(root, 100, 20);
            Stage fright = new Stage();
            fright.setTitle("Lobby");
            fright.setScene(s);
            fright.show();
        }
    }
    private FlowPane addFlowPane() {
        FlowPane river = new FlowPane();
        river.setHgap(4);
        river.setVgap(4);
        river.setPrefWrapLength(120);
        river.setStyle("-fx-background-color: #336666;");

            ImageView page = new ImageView(
                    new Image("http://www.pressibus.org/reversi/gen/images/depart.gif", 200 , 200 , false , false));
        Button rev = new Button(null, page);
        rev.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Game = "Reversi";
            }
        });
            ImageView page2 = new ImageView(
                new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/3/32/Tic_tac_toe.svg/2000px-Tic_tac_toe.svg.png" , 200 , 200 , false, false));
        Button tic = new Button(null, page2);
        tic.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Game = "TicTacToe";
            }
        });
            river.getChildren().addAll(rev, tic);
       // }
        tic.setPrefSize(200,200);
        rev.setPrefSize(200,200);
        tic.setBackground(river.getBackground());
        rev.setBackground(river.getBackground());
    river.setAlignment(Pos.CENTER_LEFT);
        return river;
    }
    private HBox nameset(){
    Label label = new Label("Name:");
    textField = new TextField ();
    HBox hb = new HBox();
    hb.getChildren().addAll(label, textField);
    hb.setSpacing(10);
    hb.setAlignment(Pos.CENTER);
    hb.setStyle("-fx-background-color: #519292;");
    hb.setPrefHeight(150);
    return hb;
    }

    private VBox options(){

    VBox elevator = new VBox();
    Label labell = new Label("mode");
    ObservableList<String> options1 =
            FXCollections.observableArrayList(
                    "Player vs Player",
                    "Player vs AI",
                    "AI vs AI"
            );
        comboBox1 = new ComboBox(options1);
        Label label2 = new Label("difficulty");
        ObservableList<String> options2 =
                FXCollections.observableArrayList(
                        "1",
                        "2",
                        "3"
                );
        comboBox2 = new ComboBox(options2);
        comboBox1.setPrefWidth(120);
        comboBox2.setPrefWidth(120);
        elevator.getChildren().addAll(labell, comboBox1, label2, comboBox2);
        elevator.setStyle("-fx-background-color: #336666;");
        elevator.setSpacing(10);
        elevator.setAlignment(Pos.CENTER);
        elevator.setPrefWidth(200);
        return elevator;
}}
