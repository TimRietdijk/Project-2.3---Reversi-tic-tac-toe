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
    ArrayList<Image> imagesTicTacToe = new ArrayList<Image>();
    ArrayList<Image> imagesReversie = new ArrayList<Image>();
    int[] move = new int[3];
    boolean moveMade;
    String[] games = {"TicTacToe", "Reversi"};

    public Board(){
/*        Image player1 = new Image(getClass().getResourceAsStream("x.png"));
        Image player2 = new Image(getClass().getResourceAsStream("o.png"));
        imagesTicTacToe.add(player1);
        imagesTicTacToe.add(player2);
        player1 = new Image(getClass().getResourceAsStream("x.png"));
        player2 = new Image(getClass().getResourceAsStream("o.png"));
        imagesReversie.add(player1);
        imagesReversie.add(player2);*/
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

    private void fieldClicked(Rectangle rect, String game){
        setImage(rect, 1, game);
        int x = (int) rect.getX();
        int y = (int) rect.getY();
        setMove(x, y);
    }


    private void setImage(Rectangle rect, int player, String game){
        ImagePattern imagePattern;
        if(game.equals(games[0])) {
            imagePattern = new ImagePattern(imagesTicTacToe.get(player-1));
        }else {
            imagePattern = new ImagePattern(imagesReversie.get(player - 1));
        }
        rect.setFill(imagePattern);
    }

    public void drawBoard(int[][] field, String game){
        for(int y = 0; y < field.length; y++){
            for(int x = 0; x < field[1].length; x++) {                          // later nog even terug komen om te kijken of de field.length en de field[1].length op de juiste plaats staan
                Rectangle rect = new Rectangle(x, y, 200, 200);
                if(game.equals(games[1])) {
                    rect = new Rectangle(x, y, 80, 80);
                }
                Rectangle finalRect = rect;
                rect.setOnMouseClicked((e) -> fieldClicked(finalRect, game));
                rect.setFill(Color.WHITE);
                rect.setStroke(Color.BLACK);
                if(field[x][y] != 0){
                    setImage(rect, field[x][y], game);
                }
                gridPane.add(rect, x, y);
            }
        }
    }


    public void start(Stage primaryStage, int[][] field, String name, String game) throws Exception {
        moveMade = false;
        gridPane = new GridPane();
        Scene scene = new Scene(gridPane);
        drawBoard(field, game);
        primaryStage.setTitle(name);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

