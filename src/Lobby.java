import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class Lobby extends Application{
    private String[] Gamelist;

    public void start(Stage fright) {
        try {

            BorderPane root = new BorderPane();

            root.setBottom(addHBox());
            root.setLeft(addFlowPane());
            root.setCenter(nameset());
            root.setRight(options());

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

            Button connectBtn = new Button();
            connectBtn.setText("'connection'");
            connectBtn.setPrefSize(100, 20);

            hbox.setAlignment(Pos.CENTER);
            hbox.getChildren().addAll(btn, connectBtn);
            btn.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    System.out.println("Hello World!");
                }
            });
            connectBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ServerConnection connection = new ServerConnection();
                    try {
                        connection.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            BorderPane.setAlignment(hbox, Pos.CENTER);
            hbox.setPrefHeight(100);
            return hbox;
        }
    public FlowPane addFlowPane() {
        FlowPane river = new FlowPane();
        river.setHgap(4);
        river.setVgap(4);
        river.setPrefWrapLength(120);
       // preferred width allows for two columns
        river.setStyle("-fx-background-color: #336666;");
       // for (int i = 0; i < Gamelist.length; i++ ) {
            ImageView page = new ImageView(
                    new Image("http://www.pressibus.org/reversi/gen/images/depart.gif", 200 , 200 , false , false));
        Button rev = new Button(null, page);
        rev.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
            ImageView page2 = new ImageView(
                new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/3/32/Tic_tac_toe.svg/2000px-Tic_tac_toe.svg.png" , 200 , 200 , false, false));
        Button tic = new Button(null, page2);
        tic.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
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
    public HBox nameset(){
    Label label = new Label("Name:");
    TextField textField = new TextField ();
    HBox hb = new HBox();
    hb.getChildren().addAll(label, textField);
    hb.setSpacing(10);
    hb.setAlignment(Pos.CENTER);
    hb.setStyle("-fx-background-color: #519292;");
    hb.setPrefHeight(150);
    return hb;
    }

    public VBox options(){

    VBox elevator = new VBox();
    Label labell = new Label("mode");
    ObservableList<String> options1 =
            FXCollections.observableArrayList(
                    "Player vs Player",
                    "Player vs AI",
                    "AI vs AI"
            );
        final ComboBox comboBox1 = new ComboBox(options1);
        Label label2 = new Label("difficulty");
        ObservableList<String> options2 =
                FXCollections.observableArrayList(
                        "1",
                        "2",
                        "3"
                );
        final ComboBox comboBox2 = new ComboBox(options2);
        comboBox1.setPrefWidth(120);
        comboBox2.setPrefWidth(120);
        elevator.getChildren().addAll(labell, comboBox1, label2, comboBox2);
        elevator.setStyle("-fx-background-color: #336666;");
        elevator.setSpacing(10);
        elevator.setAlignment(Pos.CENTER);
        elevator.setPrefWidth(200);
        return elevator;
}}
