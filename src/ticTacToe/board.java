package ticTacToe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;

public class board extends Application {

    class Actions{
        public void move(){
            System.out.println("Move!");
        }

        public void start(){
            //
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Tic tac toe");

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);

        Collection<StackPane> stackPanes = new ArrayList<StackPane>();
        GridPane gridpane = new GridPane();
        //ArrayList<StackPane> stackPanes = new ArrayList<StackPane>();

        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                StackPane stackPane = new StackPane();
                Image im = new Image("file:\\D:\\eclipse projects\\project 2.3\\Project-2.3-reversi-tic-tac-toe\\src\\weekopdrTicTacToe\\x.gif");
                ImageView imageView = new ImageView(im);
                stackPane.setPrefSize(50.0, 50.0);
                stackPanes.add(stackPane);
                stackPane.setStyle("-fx-border-color: blue");
                stackPane.setAccessibleText("hert");
                stackPane.getChildren().add(imageView);
                //stackPanes.add(stackPane);
                gridpane.add(stackPane, i, j);
            }
        }

        Button moveButton = new Button("Move");

        //moveButton.setOnAction((e) -> Actions.move());

        VBox root = new VBox();
        HBox hboxButtons = new HBox();
        Scene scene = new Scene(root, Color.WHITE);

        hboxButtons.getChildren().add(moveButton);
        root.getChildren().add(gridpane);
        root.getChildren().add(hboxButtons);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}

