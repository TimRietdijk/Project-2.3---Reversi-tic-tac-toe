package game;

import framework.Board;
import framework.Framework;
import Reversi.Reversi;
import TicTacToe.TicTacToe;
import javafx.stage.Stage;
import org.ini4j.Wini;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;




public class GameEngine {

    private  boolean gamestart;
    private Wini ini;
    private String game;
    protected int[][] field;
    private int numberofstates = 3;
    protected String[] states = new String[100];
    private CommandCenter jack;
    private Framework framework;
    private int[] move;
    private int calculatedMove;
    private Board board;
    private java.lang.reflect.Method method;
    public GameEngine(Map<String, String> optionlist, CommandCenter commandCenter, boolean start, Stage stage) {
        String s = optionlist.get("game");

        if (s.contains("Reversi")) {
            setField(8, 8);
            board = new Board();
            try {
                board.start(stage, field);
            } catch (Exception e) {
                e.printStackTrace();
            }
            framework = new Reversi(field, board);
        } else if (s.contains("Tic-tac-toe")) {
            setField(3, 3);
            board = new Board();
            try {
                board.start(stage, field);
            } catch (Exception e) {
                e.printStackTrace();
            }
            framework = new TicTacToe(board);
            }else{
            System.out.println("Hopscotch");
        }

        gamestart = start;
        //showField();
        jack = commandCenter;
        new Thread(new Runnable() {
            public void run() {
                // receivedCommand houdt het ontvangen command van de server0
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (gamestart) {
                    String s = jack.ReadReceived();
                    System.out.println(s);
                    String parse = jack.commandHandling(s);

                    if (parse != null) {
                        int pos = Integer.valueOf(parse);
                        int[] work = calculateMoveToCoordinates(pos);
                        if(field[work[0]][work[1]] == 0){
                            //do traitors move
                        }
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(gamestart) {

                    try {
                        method = board.getClass().getMethod("getMoveMade");
                        try {
                            boolean mup = (boolean) method.invoke(board);
                            if (mup) {
                                try {
                                    doMove();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    public void doMove() throws IOException {
        System.out.println("hij doet dit");
        int[] coordinates = board.getMove();
        calculatedMove = calculateMoveToPosition(coordinates);
        setState(coordinates[0], coordinates[1], 1);
            jack.doMove(calculatedMove);

    }

    public void setField(int x, int y) {
        field = new int[x][y];
    }



    private int calculateMoveToPosition(int[] move) {
        return (((move[1]) * field.length) + move[0]);
    }
    private int[] calculateMoveToCoordinates(int move) {
        int x = (move/(field.length));
        int y = move%(field.length);
        return new int[] {x, y};
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
