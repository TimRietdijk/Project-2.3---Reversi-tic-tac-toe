package aI;

import framework.Board;
import reversi.Points;
import reversi.Reversi;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AIReversi {
    //zo min mogelijk stukken pakken per keer.
    // meer mogelijkheden krijgen
    // tegenstander minder maken
    // zorgen dat de uiterste niet gedraaid wordt van de tegenstander
    // centrum terug pakken (de 4)
    //splitten tegenstanders stenen

    private int[][] field;
    private Board board = new Board();
    private ArrayList<Points> badMoves = new ArrayList<>(); // index 0 is worst
    private ArrayList<Points> goodMoves = new ArrayList<>();
    private ArrayList<Points> availableMoves = new ArrayList<>();
    private ArrayList<Points> centerPeaces = new ArrayList<>();
    private ArrayList<Output> calculatedMoves = new ArrayList<>();

    private Boolean done = false;
    Reversi reversi;

    Points bestMove;
    int bestMoveScore;

    int[][] eval_table = {
            {99,  -8,  8,  6,  6,  8,  -8, 99},
            {-8, -24, -4, -3, -3, -4, -24, -8},
            { 8,  -4,  7,  4,  4,  7,  -4,  8},
            { 6,  -3,  4,  0,  0,  4,  -3,  6},
            { 6,  -3,  4,  0,  0,  4,  -3,  6},
            { 8,  -4,  7,  4,  4,  7,  -4,  8},
            {-8, -24, -4, -3, -3, -4, -24, -8},
            {99,  -8,  8,  6,  6,  8,  -8, 99}
    };

    private synchronized void checkIfNewBestMove(int score, int x, int y){
        if(score > bestMoveScore){
            bestMoveScore = score;
            System.out.println("Best move = " + x + " : " + y + " score: " + bestMoveScore);
        }
    }

    public void addAvailableMoves(int x, int y){
        availableMoves.add(new Points(x, y));
    }

    public AIReversi(int[][] field, Board board){
        reversi = new Reversi(field, board);
        this.field = field;
        //availableMoves = reversi.calculatingPossibleMoves(field, 1, 2);
        defineBadMoves(); // defines bad moves
        defineGoodMoves();
        defineCenterPeaces();
    }

    public void calculateBestMove(int[][] field){
        this.field = field;
        availableMoves = reversi.calculatingPossibleMoves(field, 1, 2);
        for(Points p : availableMoves){
            System.out.println(p.getX() + " : " + p.getY());
        }
        checkForGoodMoves();
        if(!this.done){
            checkForObligatedMove();
            checkForBadMoves(); // and remove
            setBestMove(availableMoves.get(0).getX(), availableMoves.get(0).getY()); // set standard move
            checkForObligatedMove();
            calculateDepth();
        }
        System.out.println("Calculation = " + done);
    }

    private void defineCenterPeaces(){
        centerPeaces.add(new Points(3, 3));
        centerPeaces.add(new Points(3, 4));
        centerPeaces.add(new Points(4, 3));
        centerPeaces.add(new Points(4, 4));
    }

    private void defineGoodMoves(){
        goodMoves.add(new Points(0,0));
        goodMoves.add(new Points(0,7));
        goodMoves.add(new Points(7,7));
        goodMoves.add(new Points(7,0));

    }

    private void defineBadMoves(){
        //diagonal to corner
        badMoves.add(new Points(1, 1));
        badMoves.add(new Points(6, 1));
        badMoves.add(new Points(1, 6));
        badMoves.add(new Points(6, 6));

        //next to corner
        badMoves.add(new Points(1,0));
        badMoves.add(new Points(0,1));
        badMoves.add(new Points(6,0));
        badMoves.add(new Points(1,7));
        badMoves.add(new Points(0,6));
        badMoves.add(new Points(1,7));
        badMoves.add(new Points(7,6));
        badMoves.add(new Points(6,7));
    }

    private Boolean checkForGoodMoves(){
        for(Points p : availableMoves){
            for(Points po : goodMoves){
                if(p.getX() == po.getX() && p.getY() == po.getY()){
                    this.bestMove = new Points(p.getX(), p.getY());
                    this.done = true;
                    return true;
                }
            }
        }
        return false;
    }

    private void checkForBadMoves(){
        ArrayList<Points> tempBadMoves = new ArrayList<>();
        for(Points p : availableMoves){
            for(Points po : badMoves){
                if(p.getX() == po.getX() && p.getY() == po.getY()){
                    tempBadMoves.add(p);
                }
            }
        }
        System.out.println("Bad moves: " + tempBadMoves.size());
        System.out.println("Available moves: " + availableMoves.size());

        if(tempBadMoves.size() < availableMoves.size()){
            for(Points p : tempBadMoves) {
                availableMoves.remove(p);
                //System.out.println(availableMoves.size());
            }
        } else{
            Points ph = tempBadMoves.get(tempBadMoves.size()-1);
            setBestMove(ph.getX(), ph.getY());
        }
    }

    private void setBestMove(int x, int y){
        this.bestMove = new Points(x, y);
        System.out.println("Done");
        System.out.println("Best move: " + bestMove.getX() + " : " + bestMove.getY());
        this.done = true;
    }

    private void checkForObligatedMove(){
        if(availableMoves.size() == 1){
            Points ph = availableMoves.get(availableMoves.size()-1);
            setBestMove(ph.getX(), ph.getY());
        }
    }

    private void calculateDepth(){
        activateThreads(availableMoves.size());
    }

    private void activateThreads(int availableMoves) {
        if (availableMoves > 0){
            ExecutorService e = Executors.newFixedThreadPool(availableMoves);
            for (int i = 0; i < availableMoves; i++) {
                e.submit(new AICalculation(this.availableMoves.get(i)));
            }
            e.shutdown();
        }
    }

    private int calculateMoveToPosition(int[] move) {
        return (((move[1]) * field.length) + move[0]);
    }

    private synchronized ArrayList<Points> getPeacesToTurn(int[][] field, int position){
        return reversi.getPiecesTurnedByMove(field, position);
    }

    private synchronized int[][] getNewField(int field, int position ){
        return new int[][]{};
    }

    private synchronized int getPossibleEnemyMoves(int[][] field){
        return reversi.calculatingPossibleMoves(field, 2, 1).size();
    }

    private synchronized int getPossibleMoves(int[][] field){
        return reversi.calculatingPossibleMoves(field, 1, 2).size();
    }

    public class AICalculation implements Runnable {
        Points move;
        int[][] tempField;
        Output output;
        int x;
        int y;
        int score;

        public AICalculation(Points move){
            //System.out.println("Thread " + move.getX() + " : " + move.getY());
            this.x = move.getX();
            this.y = move.getY();
            this.move = move;
            tempField = field;
            tempField[x][y] = 1;
            output = new Output(x, y);
        }

        @Override
        public void run() {
            calculatePiecesTurend();
            enemyAvailableMoves();
            ownAvailableMoves();
            System.out.println("Why nut");
            checkForCenter();
            calculateScore();
            checkIfNewBestMove(score, x, y);
        }

        private void calculateScore(){
            this.score = output.calculateScore();
        }

        private void calculatePiecesTurend(){
            ArrayList<Points> piecesTurned;

            int[] moveint = {move.getX(), move.getY()};
            int position = calculateMoveToPosition(moveint);
            piecesTurned = getPeacesToTurn(tempField, position);
            output.setPeacesTurnedArray(piecesTurned);
            output.setPeacesTurned(piecesTurned.size());
            System.out.println("Move: " + move.getX() + " : " + move.getY() + " == " + position + " hier worden " + piecesTurned.size() + " mee omgedraaid");
        }

        private void ownAvailableMoves(){
            System.out.println("Test own");
            for(int[] i : tempField){
               for(int j : i){
                   System.out.println(j);
               }
            }
            int move = getPossibleMoves(tempField);
            System.out.println(move);
            System.out.println("Own moves: " + move);
            output.setAvailableMoves(move);
        }

        private void enemyAvailableMoves(){
            System.out.println("Enemy Test");
            int move = getPossibleEnemyMoves(tempField);
            System.out.println("Enemies moves: " + move);
            output.setAvailableMovesEnemy(move);
        }

        private void checkForCenter(){
            int centerPiecesTurned = 0;
            for(Points p : output.getPeacesTurnedArray()){
                for(Points po : centerPeaces) {
                    if (p.getX() == po.getX() && p.getY() == po.getY()) {
                        centerPiecesTurned++;
                    }
                }
            }
            output.setCenterPeaces(centerPiecesTurned);
        }
    }

    public class nextDeph implements Runnable{

        Points move;
        int[][] tempField;
        Output output;
        int x;
        int y;

        public nextDeph(Points move){
            this.move = move;
            tempField = field;
            tempField[x][y] = 1;
            this.x = move.getX();
            this.y = move.getY();
        }

        @Override
        public void run() {

        }

        private void getStuck(){

        }
    }

}

class Main{
    public static void main(String[] args){
        int[][] field = new int[8][8];
        Board board = new Board();
        field[0][0] = 0;
        field[0][1] = 0;
        field[0][2] = 0;
        field[0][3] = 0;
        field[0][4] = 0;
        field[0][5] = 0;
        field[0][6] = 0;
        field[0][7] = 0;

        field[1][0] = 0;
        field[1][1] = 0;
        field[1][2] = 1;
        field[1][3] = 0;
        field[1][4] = 0;
        field[1][5] = 0;
        field[1][6] = 0;
        field[1][7] = 0;

        field[2][0] = 2;
        field[2][1] = 0;
        field[2][2] = 0;
        field[2][3] = 1;
        field[2][4] = 2;
        field[2][5] = 1;
        field[2][6] = 0;
        field[2][7] = 0;

        field[3][0] = 2;
        field[3][1] = 1;
        field[3][2] = 1;
        field[3][3] = 2;
        field[3][4] = 1;
        field[3][5] = 1;
        field[3][6] = 0;
        field[3][7] = 0;

        field[4][0] = 2;
        field[4][1] = 2;
        field[4][2] = 2;
        field[4][3] = 1;
        field[4][4] = 2;
        field[4][5] = 1;
        field[4][6] = 1;
        field[4][7] = 0;

        field[5][0] = 0;
        field[5][1] = 2;
        field[5][2] = 1;
        field[5][3] = 1;
        field[5][4] = 1;
        field[5][5] = 0;
        field[5][6] = 0;
        field[5][7] = 0;

        field[6][0] = 0;
        field[6][1] = 0;
        field[6][2] = 0;
        field[6][3] = 1;
        field[6][4] = 0;
        field[6][5] = 0;
        field[6][6] = 0;
        field[6][7] = 0;

        field[7][0] = 0;
        field[7][1] = 0;
        field[7][2] = 0;
        field[7][3] = 0;
        field[7][4] = 0;
        field[7][5] = 0;
        field[7][6] = 0;
        field[7][7] = 0;

        AIReversi ai = new AIReversi(field, board);
        ai.calculateBestMove(field);
    }
}