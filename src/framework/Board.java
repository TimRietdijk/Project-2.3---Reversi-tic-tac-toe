package framework;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Board {

    GridPane gridPane;
    ArrayList<Image> images = new ArrayList<Image>();
    int[] move = new int[3];
    boolean moveMade;

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

    public boolean getMoveMade(){
        return moveMade;
    }

    public void setMove(int moveX, int moveY) {
        this.move[0] = moveX;
        this.move[1] = moveY;
        moveMade = true;
        System.out.println(moveMade);
    }

    private void fieldClicked(Rectangle rect){
        int x = (int) rect.getX();
        int y = (int) rect.getY();
        setMove(x, y);
    }


    private void setImage(Rectangle rect, int player){
        ImagePattern imagePattern = new ImagePattern(images.get(player-1));
        rect.setFill(imagePattern);
    }

    public void drawBoard(int[][] field){

        for(int y = 0; y < field.length; y++){
            for(int x = 0; x < field[1].length; x++) {                          // later nog even terug komen om te kijken of de field.length en de field[1].length op de juiste plaats staan
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


    public void start(Stage primaryStage, int[][] field, String name) throws Exception {
        moveMade = false;
        gridPane = new GridPane();
        Scene scene = new Scene(gridPane);
        drawBoard(field);
        primaryStage.setTitle(name);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

