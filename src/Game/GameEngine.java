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


    private int sendMove(int move){
        return move;
    }
    private void makeMove(Button button){
        int x = button.translateXProperty().intValue();
        int y = button.translateYProperty().intValue();
        sendMove(((y)*field.length)+x);
    }
}