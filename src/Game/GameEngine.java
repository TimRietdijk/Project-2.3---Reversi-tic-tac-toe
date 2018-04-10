package Game;

import Reversi.Reversi;
import TicTacToe.TicTacToe;
import javafx.scene.control.Button;
import org.ini4j.Wini;

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
    private CommandCenter jack;


    public GameEngine(Map<String, String> optionlist, CommandCenter commandCenter){
        String s = optionlist.get("Game");
        if (s.contains("Reversi")){
            Reversi reversi = new Reversi();
            setField(8,8);
        }else if(s.contains("TicTacToe")){
            TicTacToe ticTacToe = new TicTacToe();
            setField(3,3);
        }
        showField();
        jack = commandCenter;
        new Thread(new Runnable() {
            public void run() {
                // receivedCommand houdt het ontvangen command van de server0
                while (true) {
                    String s = jack.ReadReceived();
                    System.out.println(s);
                    System.out.println("dicks");
                    String parse = jack.commandHandling(s);
                    if(parse != null){
                        int pos = Integer.valueOf(parse);
                        enemyMove(pos, 2);
                    }
                }
            }
        }).start();
    }
    public void setField(int length, int width) {
        field = new int[length][width];
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

    public void showField() {
        for(int i=0; i<field.length; i++) {
            for(int j=0; j<field[i].length; j++) {
                System.out.println("Values at arr["+i+"]["+j+"] is "+field[i][j]);
            }
        }
    }
}