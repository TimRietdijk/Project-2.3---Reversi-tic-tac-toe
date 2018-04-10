package Game;

import Framework.Framework;
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
    protected String[] states = new String[100];
    private CommandCenter jack;
    private Framework framework;
    private int[] move;
    private int calculatedMove;

    public GameEngine(Map<String, String> optionlist, CommandCenter commandCenter) {
        String s = optionlist.get("Game");
        if (s.contains("Reversi")) {
            setField(8, 8);
            framework = new Reversi(field);
        } else if (s.contains("TicTacToe")) {
            setField(3, 3);
            framework = new TicTacToe(field);
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
                    if (framework.getMoveMade()) {
                        doMove();
                    }
                    if (parse != null) {
                        int pos = Integer.valueOf(parse);
                        enemyMove(pos, 2);
                    }
                }
            }
        }).start();
    }

    public void doMove() {
        int[] coordinates = framework.getMove();
        calculatedMove = calculateMove(coordinates);
        setState(coordinates[0], coordinates[1], 1);
    }

    public void setField(int x, int y) {
        field = new int[x][y];
    }

    public void enemyMove(int position, int state) {
        int width = position / (field.length - 1);
        int length = position % field.length;

    }


    private int calculateMove(int[] move) {
        return (((move[1]) * field.length) + move[0]);
    }


    public int[][] getField() {
        return field;
    }

    public void setState(int length, int width, int value) {
        if (length >= field.length) {
            System.out.println("error: the given position does not exist on this board");
        } else {
            if (width >= field[1].length) {
                System.out.println("error: the given position does not exist on this board");
            } else {
                if (value >= numberofstates) {
                    System.out.println("Error: given state is not supported");
                } else {
                    if (value == 2) {
                        System.out.println("vijandig");
                    } else {
                        if (value == getState(length, width)) {
                            System.out.println("!: Dit vakje is al van jou, probeer een ander vakje");
                        } else {
                            field[length][width] = value;
                        }
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
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                System.out.println("Values at arr[" + i + "][" + j + "] is " + field[i][j]);
            }
        }
    }
}