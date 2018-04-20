package game;

import aI.AIReversi;
import aI.AITicTacToe;
import aI.Points;
import framework.Board;
import framework.Framework;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lobby.Lobby;
import org.ini4j.Wini;
import reversi.Reversi;
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
    private TicTacToe ticTacToe;
    private int[] move;
    private int calculatedMove;
    private Board board;
    private java.lang.reflect.Method method;
    private boolean ticTacToeAiIsPlaying;
    private boolean reversiAiIsPlaying;
    private AITicTacToe ticTacToeAI;
    private AIReversi aiReversi;
    public GameEngine(Map<String, String> optionlist, CommandCenter commandCenter, boolean start, Stage stage, boolean ok, String string, boolean playAsTicTacToeAI, boolean playAsReversiAI) {
        ticTacToeAiIsPlaying = playAsTicTacToeAI;
        reversiAiIsPlaying = playAsReversiAI;
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
            if(mode.contains("ai")||reversiAiIsPlaying){
                reversiAiIsPlaying = true;
                aiReversi = new AIReversi(getField());
            }
            reversi = new Reversi();
        } else if (game.contains("Tic-tac-toe")) {
            setField(3, 3);
            board = new Board();
            try {
                String name = optionlist.get("name");
                board.start(stage, field, name, game);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(mode.contains("ai")||ticTacToeAiIsPlaying) {
                ticTacToeAiIsPlaying = true;
                ticTacToeAI = new AITicTacToe(getField());
            }

            ticTacToe = new TicTacToe(field, board);
            framework = new TicTacToe(field, board);
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
                    //System.out.println(s);
                    String parse = jack.commandHandling(s, name);
                    if(s.contains("GAME MATCH") && wieBenIk || fuckHanzeKanNietFatsoenlijkServersBouwen){
                        if(game.equals("Reversi")) {
                            if (s.contains(name) || string.contains(name)) {
                                setPlayerField(3,3,2);
                                setPlayerField(4,4,2);
                                setPlayerField(4,3,1);
                                setPlayerField(3,4,1);
                                doDrawBoard("Your turn");
                                //System.out.println("hij komt hier");
                            } else {
                                setPlayerField(3,3,1);
                                setPlayerField(4,4,1);
                                setPlayerField(4,3,2);
                                setPlayerField(3,4,2);
                                doDrawBoard("Opponent's turn");
                                //System.out.println("Hier ook");
                            }
                            fuckHanzeKanNietFatsoenlijkServersBouwen = false;
                            wieBenIk = false;

                        }
                    }
                    if(s.contains("WIN") ){
                        if(s.contains("Illegal")) {
                            PinUp pinUp = new PinUp(stage, "win because opponent made a wrong move");
                        }else if(s.contains("timelimit")){
                            PinUp pinUp = new PinUp(stage, "win because opponent timed out");
                        }else{
                            PinUp pinUp = new PinUp(stage, "win");
                        }
                    }else if(s.contains("LOSS")){
                        if(s.contains("Illegal")){
                            PinUp pinUp = new PinUp(stage, "lose beause you made a wrong move");
                        }else if(s.contains("timelimit")){
                            PinUp pinUp = new PinUp(stage, "lose because you timed out");
                        }else{
                            PinUp pinUp = new PinUp(stage, "lose");
                        }
                    }else if(s.contains("DRAW")){
                        PinUp pinUp = new PinUp(stage, "Tied");
                    }
                    if(s.contains("SVR GAME YOURTURN")) {
                        Platform.runLater(() -> board.drawBoard(field, game, "It is your turn"));
                       // System.out.println("-=DEZE BEURT!=-");
                        if(ticTacToeAiIsPlaying) {
                            //   System.out.println("-=DEZE AI GAAT NU EEN ZET MAKEN=-");
                            // showField();
                            ticTacToeAI.updateField(getField());
                            Points theAIMove = ticTacToeAI.decideMove();
                            //  System.out.println("-=DEZE AI DOET NU: MOVE x:" + theAIMove.getX() + ", y:" + theAIMove.getY());
                            board.setMove(theAIMove.getX(), theAIMove.getY());
                        } else if(reversiAiIsPlaying){
                            reversionAIMove();
                            aIWait();
                        }
                    }


                    if (parse != null) {
                        parse = parse.replaceAll("[^0-9]", "");
                        int pos = Integer.valueOf(parse);
                        int[] work = calculateMoveFromServer(pos);
                        // System.out.println("Komt in valid: " + pos);
                        // System.out.println(parse);
                        //pos = pos % (int) Math.pow(10, (int) Math.log10(pos));
                        //if(field[work[0]][work[1]] == 0){
                        boolean valid = checkState(work[0], work[1], 2);
                        valid = true;
                        if (valid){
                            System.out.println("Position receive: " + pos);
                            setPlayerField(work[0], work[1], 2);
                            if(game.equals("Reversi")){
                                doMoveReversi(work);
                            }
                            doDrawBoard("Your turn");
                        //}
                        }
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(gamestart) {
                    if(!reversiAiIsPlaying) {
                        try {
                            method = board.getClass().getMethod("getMoveMade");
                            try {
                                boolean mup = (boolean) method.invoke(board);
                                if (mup) {
                                    //    System.out.println("Mup true");
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
            }
        }).start();
    }

    private void reversionAIMove(){
        new Thread(new Runnable() {
            public void run() {
                aiReversi.calculateBestMove(field);
            }
        }).start();
    }

    private synchronized void aIWait(){
        int remainingTime = 3;
        long timeout = System.currentTimeMillis() + (remainingTime * 1000);
        new Thread(new Runnable() {
            public void run() {
                while (System.currentTimeMillis() < timeout) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                reversi.Points theAIMove = aiReversi.getBestMove();
                System.out.println("Hij koos slim: " + theAIMove.getX() + " : " + theAIMove.getY() + "translation: ");
                board.setMove(theAIMove.getX(), theAIMove.getY());
                try {
                    doMove();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void doMove() throws IOException {

        //   System.out.println("DoMove");
        int[] coordinates = board.getMove();
        boolean exec = checkState(coordinates[0], coordinates[1], 1);
        if (exec) {
            if(game.equals("Reversi") ){ ;
                //showField();
                setPlayerField(coordinates[0], coordinates[1], 1);
                doMoveReversi(coordinates);
                //jack.doMove(calculatedMove);
                sendMoveToServer(coordinates);//send to jack
                doDrawBoard("Opponent's turn");
            }else {
                setPlayerField(coordinates[0], coordinates[1], 1);
                sendMoveToServer(coordinates);
                doDrawBoard("Opponent's turn");

            }
        }
    }

    public void setField(int x, int y) {
        field = new int[x][y];
    }

    public void setPlayerField(int x, int y, int player){
        this.field[x][y] = player;
    }

    private void sendMoveToServer(int[] move) throws IOException {
        System.out.print("I send: " + calculateMoveToServer(move));
        jack.doMove(calculateMoveToServer(move));
    }

    private int[] calculateMoveFromServer(int move){
        int y = move/field.length;
        int x = move%field.length;
        return new int[]{x,y};
    }

    private int calculateMoveToServer(int[] move){
        int moveS = move[1] * field.length + move[0];
        return moveS;
    }

    private void doMoveReversi(int[] move){
        int lastMove = move[0] * field.length + move[1];
        field = reversi.doMove(field, lastMove);
    }

    private void doDrawBoard(String text){
        Platform.runLater(() -> board.drawBoard(field, game, text));
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
                        return false;
                    } else {
                        if (value == getState(x, y)) {
                            System.out.println("!: Dit vakje is al van jou, probeer een ander vakje");
                            //board.setMove(aiReversi.getAvailableMovesArry(2).getX(), aiReversi.getAvailableMovesArry(2).getY());
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