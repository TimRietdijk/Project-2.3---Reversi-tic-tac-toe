package Lobby;

import Game.CommandCenter;
import Game.GameEngine;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import java.io.IOException;

public class Lobby extends Application{
    private String game;
    private TextField textField;
    private ComboBox comboBox2;
    private ComboBox comboBox1;
    private String[] optionList;
    private Stage fright;
    private CommandCenter commandCenter;
    private String[] playerList;
    public void start(Stage start) {
        try {
            commandCenter = new CommandCenter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            commandCenter.doGetPlayerList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BorderPane root = new BorderPane();
             commandCenter.ReadReceived();
             commandCenter.ReadReceived();
            String read2 = commandCenter.ReadReceived();

                System.out.println(read2);
            if (read2.contains("SVR PLAYERLIST [")) {
                System.out.println(read2);
                updatePlayerList(read2);
                root.setRight(options());
                System.out.println("dit werkt");
            }


            root.setBottom(addHBox());
            root.setLeft(addFlowPane());
            root.setCenter(nameset());


            final Scene s = new Scene(root, 1000, 600);
            fright = new Stage();
            fright.setTitle("Lobby");
            fright.setScene(s);
            fright.show();

            new Thread(new Runnable() {
                public void run() {
                    // receivedCommand houdt het ontvangen command van de server
                    while (fright.isShowing()) {
                        String s = commandCenter.ReadReceived();
                        // Dit stuk vereist nog te veel tijd doordat commands gecheckt worden met if statements
                        if(s.contains("SVR GAME CHALLENGE {")) {
                            PopUp challengePopUp = new PopUp();
                            try {
                                challengePopUp.start(s, commandCenter);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        System.out.println(s);
                    }
                }
            }).start();
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
            btn.setText("challenge player");
            btn.setPrefSize(100, 20);



            hbox.setAlignment(Pos.CENTER);
            hbox.getChildren().addAll(btn);
            btn.setOnAction(e -> startgame());


            BorderPane.setAlignment(hbox, Pos.CENTER);
            hbox.setPrefHeight(100);
            return hbox;
        }


    private void startgame() {
        if(game != null && comboBox1.getValue().toString() != null && comboBox2.getValue().toString() != null){
            System.out.println("Starting: " + game);
            String name = textField.getCharacters().toString();
            String option1 = comboBox1.getValue().toString();
            String option2 = comboBox2.getValue().toString();
            Map<String, String> optionlist = new HashMap<String, String>();
            optionlist.put("name", name);
            optionlist.put("Game", game);
            optionlist.put("option1", option1);
            optionlist.put("option2", option2);
            fright.close();
            try {
                commandCenter.doChallenge(option1, game);
            } catch (IOException e) {
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
                game = "Reversi";
            }
        });
            ImageView page2 = new ImageView(
                new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/3/32/Tic_tac_toe.svg/2000px-Tic_tac_toe.svg.png" , 200 , 200 , false, false));
        Button tic = new Button(null, page2);
        tic.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                game = "Tic-tac-toe";
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
        Button tic = new Button("submit");
        tic.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            commandCenter.doLogin(textField.getCharacters().toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    hb.getChildren().addAll(label, textField, tic);
    hb.setSpacing(10);
    hb.setAlignment(Pos.CENTER);
    hb.setStyle("-fx-background-color: #519292;");
    hb.setPrefHeight(150);

    return hb;
    }

    private VBox options(){

    VBox elevator = new VBox();
    Label labell = new Label("players");
    ObservableList<String> options1 =
            FXCollections.observableArrayList(
                    "Player vs Player",
                    "Player vs AI",
                    "AI vs AI"
            );
    System.out.println("dit is playerList:  "+ playerList);
    ObservableList<String> playerOptions1 = FXCollections.observableArrayList(playerList);
        comboBox1 = new ComboBox(playerOptions1);
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
}

    private void updatePlayerList(String playerListReceived) {
        playerListReceived = playerListReceived.replaceAll("\"", "");
        playerList = playerListReceived.replace("SVR PLAYERLIST [", "").replace("]", "").split(", ");
        for (String player:playerList) {
            System.out.println("player: "+player);
        }
    }

}

class PopUp {

    private boolean accept = false;
    public void start(String challenge, CommandCenter commandCenter) throws IOException {
        String challengeNumber = challenge.substring(challenge.indexOf("CHALLENGENUMBER: \"") + 18, challenge.indexOf("\", GAMETYPE:"));
        String challenger = challenge.substring(challenge.indexOf("CHALLENGER: \"") + 13, challenge.indexOf("\", CHALLENGENUMBER:"));
        String gameType = challenge.substring(challenge.indexOf("GAMETYPE: \"") + 11, challenge.indexOf("\"}"));

        GridPane pane = new GridPane();
        // Informatie
        Label label1 = new Label();
        pane.add(label1, 0, 0);

        label1.setText("You received a "+gameType+" challenge from "+challenger);

        Button button1 = new Button();
        button1.setText("Accept");
        button1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                //Hier wordt een challenge geaccepteerd
                try {
                    commandCenter.doChallengeAccept(challengeNumber);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        });
        pane.add(button1, 0, 1);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                Stage primaryStage = new Stage();
                Scene scene = new Scene(pane, 250, 70);
                primaryStage.setScene(scene);
                primaryStage.setTitle("Challenge "+challengeNumber);
                primaryStage.setMinHeight(70);
                primaryStage.setMinWidth(250);
                primaryStage.show();

            }
        });
    }
    public boolean accepted() {
        return accept;
    }
}