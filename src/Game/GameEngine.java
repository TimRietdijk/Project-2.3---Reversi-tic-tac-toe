package Game;

import Reversi.Reversi;
import TicTacToe.TicTacToe;
import javafx.scene.control.Button;
import Game.CommandCenter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Map;




public class GameEngine {

    private Wini ini;
    private String game;
    protected int[][] field;
    private int numberofstates = 3;
    private int tileWidth = 90;
    private int tileHeight = 90;
    private int fieldLength;
    private String lastIp;
    private Integer lastPort;
    private int fieldWidth;
    private String fieldColor;
    protected String[] states = new String[100];
    private CommandCenter Jack;

    private Boolean myTurn = false;

    ArrayList<StackPane> stackPanes = new ArrayList<StackPane>();
    GridPane gridpane = new GridPane();

    public GameEngine(Map<String, String> optionlist, CommandCenter commandCenter){
        String s = optionlist.get("Game");
        if (s.contains("Reversi")){
            Reversi reversi = new Reversi();
        }else if(s.contains("TicTacToe")){
            TicTacToe ticTacToe = new TicTacToe();
        }
    }

    public void enemyMove(int position, int state){
        int width = position / (field.length-1);
        int length = position%field.length;

    }


    public void setState(int length, int width, int value) {
        if(length >= field.length) {
            System.out.println("error: the given position does not exist on this board");
        }else {
            if(width >= field[1].length) {
                System.out.println("error: the given position does not exist on this board");
            }else {
                if(value >= numberofstates)
                {
                    System.out.println("Error: given state is not supported");
                }else {
                    if(value == getState(length, width))
                    {
                        System.out.println("!: Dit vakje is al van jou, probeer een ander vakje");
                    }else {
                        field[length][width] = value;
                    }
                }
            }
        }
    }
    public int getState(int length, int width) {
        return field[length][width];
    }

    public void setStates(String[] states) {
        this.states = states;
    }
    private int sendMove(int move){
        return move;
    }
    private void makeMove(Button button){
        int x = button.translateXProperty().intValue();
        int y = button.translateYProperty().intValue();
        sendMove(((y)*field.length)+x);
    }
}