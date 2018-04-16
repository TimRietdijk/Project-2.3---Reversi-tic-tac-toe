package game;

import aI.AITicTacToe;
import aI.Points;
import framework.Board;
import framework.Framework;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import lobby.Lobby;
import reversi.Reversi;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.ini4j.Wini;
import ticTacToe.TicTacToe;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;




public class GameEngine {
    private boolean fuckHanzeKanNietFatsoenlijkServersBouwen;
    private boolean wieBenIk;
    private  boolean gamestart;
    private String game;
    private Wini ini;
    private String name;
    private String mode;
    protected int[][] field;
    private int numberofstates = 3;
    protected String[] states = new String[100];
    private CommandCenter jack;
    private Framework framework;
    private Reversi reversi;
    private int[] move;
    private int calculatedMove;
    private Board board;
    private java.lang.reflect.Method method;
    private boolean ticTacToeAiIsPlaying;
    private AITicTacToe ticTacToeAI;
    public GameEngine(Map<String, String> optionlist, CommandCenter commandCenter, boolean start, Stage stage, boolean ok, String string) {
        this.fuckHanzeKanNietFatsoenlijkServersBouwen = ok;
        game = optionlist.get("game");
        name = optionlist.get("name");
        mode = optionlist.get("option2");
        if (game.contains("Reversi")) {
            setField(8, 8);
            wieBenIk = true;
            board = new Board();
            String name = optionlist.get("name");
            stage.setTitle(name);
            try {
                board.start(stage, field, name, game);
            } catch (Exception e) {
                e.printStackTrace();
            }
            reversi = new Reversi(field, board);
        } else if (game.contains("Tic-tac-toe")) {
            setField(3, 3);
            board = new Board();
            try {
                String name = optionlist.get("name");
                board.start(stage, field, name, game);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(mode.contains("3")) {
                ticTacToeAiIsPlaying = true;
                ticTacToeAI = new AITicTacToe(getField());
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
                    String parse = jack.commandHandling(s, name);
                    if(s.contains("GAME MATCH") && wieBenIk || fuckHanzeKanNietFatsoenlijkServersBouwen){
                        if(game.equals("Reversi")) {
                            if (s.contains(name) || string.contains(name)) {
                                setPlayerField(3,3,2);
                                setPlayerField(4,4,2);
                                setPlayerField(4,3,1);
                                setPlayerField(3,4,1);
                                System.out.println("hij komt hier");
                            } else {
                                setPlayerField(3,3,1);
                                setPlayerField(4,4,1);
                                setPlayerField(4,3,2);
                                setPlayerField(3,4,2);
                                System.out.println("Hier ook");
                            }
                            fuckHanzeKanNietFatsoenlijkServersBouwen = false;
                            wieBenIk = false;
                            Platform.runLater(() -> board.drawBoard(field, game));
                        }
                    }
                    if(s.contains("WIN") ){
                        PinUp pinUp = new PinUp(stage, "win");
                    }else if(s.contains("LOSS")){
                        PinUp pinUp = new PinUp(stage, "lose");
                    }
                    if(s.contains("SVR GAME YOURTURN")) {
                        System.out.println("als difficulty 3 staat geselecteerd hoort er nu een ai move te komen");
                        if(ticTacToeAiIsPlaying) {
                            System.out.println("-=AI GAAT NU EEN ZET MAKEN=-");
                            ticTacToeAI.updateField(getField());
                            Points theAIMove = ticTacToeAI.decideMove();
                            board.setMove(theAIMove.getY(), theAIMove.getX());
                        }
                    }



                    if (parse != null) {
                        int pos = Integer.valueOf(parse);
                        System.out.println("Komt in valid: " + pos);
                        System.out.println(parse);
                        //pos = pos % (int) Math.pow(10, (int) Math.log10(pos));
                        int[] work = calculateMoveToCoordinates(pos);
                        //if(field[work[0]][work[1]] == 0){
                        boolean valid = checkState(work[0], work[1], 2);
                        if (valid){
                            field[work[0]][work[1]] = 2;
                            field = reversi.doMove(field, pos);
                            Platform.runLater(() -> board.drawBoard(field, game));
                        }
                        //}
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
        boolean exec = checkState(coordinates[0], coordinates[1], 1);
        if (exec) {
            if(game.equals("Reversi") ){ ;
                showField();
                setPlayerField(coordinates[0], coordinates[1], 1);
                this.field = reversi.doMove(field, calculatedMove);
                jack.doMove(calculatedMove);
                Platform.runLater(() -> board.drawBoard(field, game));
            }else {
                field[coordinates[0]][coordinates[1]] = 1;
                jack.doMove(calculatedMove);
                Platform.runLater(() -> board.drawBoard(field, game));

            }
        }
    }

    public void setField(int x, int y) {
        field = new int[x][y];
    }

    public void setPlayerField(int x, int y, int player){
        this.field[x][y] = player;
    }


    private int calculateMoveToPosition(int[] move) {
        return (((move[1]) * field.length) + move[0]);
    }

    private int[] calculateMoveToCoordinates(int move) {
        int y = (move/(field.length));
        int x = move%(field.length);
        return new int[] {x, y};
    }



    public int[][] getField() {
        return field;
    }

    public boolean checkState(int x, int y, int value) {
        if (x >= field.length) {
            System.out.println("error: the given position does not exist on this board");
            return false;
        } else {
            if (y >= field[1].length) {
                System.out.println("error: the given position does not exist on this board");
                return false;
            } else {
                if (value >= numberofstates) {
                    System.out.println("Error: given state is not supported");
                    return false;
                } else {
                    if (getState(x, y) == 2) {
                        System.out.println("vijandig");
                        field[x][y] = 2;
                        return false;
                    } else {
                        if (value == getState(x, y)) {
                            System.out.println("!: Dit vakje is al van jou, probeer een ander vakje");
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
    }

    public int getState(int x, int y) {
        return field[x][y];
    }



    public void showField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                System.out.println("Values at arr[" + i + "][" + j + "] is " + field[i][j]);
            }
        }
    }
}

class PinUp{

    public PinUp(Stage primaryStage, String outcome){
        GridPane pane = new GridPane();
        // Informatie
        Label label1 = new Label();
        pane.add(label1, 0, 0);

        label1.setText("you " + outcome);

        Button button1 = new Button();
        button1.setText("Accept");
        button1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Lobby lobby = new Lobby();
                        lobby.start(primaryStage, false);
                    }
                });
            }
        });
        pane.add(button1, 0, 1);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                Scene scene = new Scene(pane, 250, 70);
                primaryStage.setScene(scene);
                primaryStage.setTitle("you" + outcome);
                primaryStage.setMinHeight(70);
                primaryStage.setMinWidth(250);
                primaryStage.show();
            }

        });

    }
}