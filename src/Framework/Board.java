package Framework;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Board extends Application {

    GridPane gridPane;
    ArrayList<Image> images = new ArrayList<Image>();
//    int[][] field = new int[3][3];


    public Board(){
        Image player1 = new Image(getClass().getResourceAsStream("x.png"));
        Image player2 = new Image(getClass().getResourceAsStream("o.png"));
        images.add(player1);
        images.add(player2);
    }

    private void fieldClicked(Rectangle rect){
        int x = (int) rect.getX();
        int y = (int) rect.getY();
    }

    private void setImage(Rectangle rect, int player){
        ImagePattern imagePattern = new ImagePattern(images.get(player-1));
        rect.setFill(imagePattern);
    }

<<<<<<< HEAD
    private void drawBoard(int[][] field){
=======
    private void drawBoard(){
>>>>>>> 9a0e7f1d9c6d3ad82f68fd898bc2811a26981ff0
        for(int x = 0; x < 3; x++){
            for(int y = 0; y < 3; y++) {
                Rectangle rect = new Rectangle(x, y, 200, 200);
                rect.setOnMouseClicked((e) -> fieldClicked(rect));
                rect.setFill(Color.WHITE);
                rect.setStroke(Color.BLACK);
                if(field[x][y] != 0){
                    setImage(rect, field[x][y]);
                }
                gridPane.add(rect, x, y);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        gridPane = new GridPane();
<<<<<<< HEAD
        Canvas canvas = new Canvas(600, 600);
=======
>>>>>>> 9a0e7f1d9c6d3ad82f68fd898bc2811a26981ff0
        drawBoard();
        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
