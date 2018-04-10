package Framework;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
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
    int[] move;
    boolean moveMade = false;

    public Board(){
        Image player1 = new Image(getClass().getResourceAsStream("x.png"));
        Image player2 = new Image(getClass().getResourceAsStream("o.png"));
        images.add(player1);
        images.add(player2);
    }

    public int[] getMove() {
        moveMade = false;
        return move;
    }


    
    public void setMove(int moveX, int moveY) {
        this.move[0] = moveX;
        this.move[1] = moveY;
        moveMade = true;
    }

    private void fieldClicked(Rectangle rect){
        int x = (int) rect.getX();
        int y = (int) rect.getY();
    }


    private void setImage(Rectangle rect, int player){
        ImagePattern imagePattern = new ImagePattern(images.get(player-1));
        rect.setFill(imagePattern);
    }

    private void drawBoard(int[][] field){

        for(int x = 0; x < field.length; x++){
            for(int y = 0; y < field[1].length; y++) {                          // later nog even terug komen om te kijken of de field.length en de field[1].length op de juiste plaats staan
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
    public void start(Stage primaryStage) throws Exception {}
    public void start(Stage primaryStage, int[][] field) throws Exception {
        gridPane = new GridPane();
        Scene scene = new Scene(gridPane);
        drawBoard(field);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
