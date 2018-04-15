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
    private ArrayList<Points> availableMoves = new ArrayList<>();
    private ArrayList<Output> calculatedMoves = new ArrayList<>();
    private Boolean done = false;
    Reversi reversi;

    Points bestMove;

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

    public void addAvailableMoves(int x, int y){
        availableMoves.add(new Points(x, y));
    }

    public AIReversi(int[][] field, Board board){
        reversi = new Reversi(field, board);
        this.field = field;
        availableMoves = reversi.calculatingPossibleMoves(field, 1, 2);
        defineBadMoves(); // defines bad moves
        //bestMove = (availableMoves.get(0)); // the best move has a starting value
        //removeBadMoves(); // removes bad move as possebility
        //defineCorners();
    }

    public void calculateBestMove(){
        /*checkForObligatedMove();
        checkForBadMoves(); // and remove
        checkForObligatedMove();*/
        calculateDepth();


    }

    private void defineBadMoves(){
        //corners
        badMoves.add(new Points(0,0));
        badMoves.add(new Points(0,7));
        badMoves.add(new Points(7,7));
        badMoves.add(new Points(7,0));

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
                System.out.println(availableMoves.size());
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
                System.out.println("lopje2");
                //System.out.println("aaaa" + this.availableMoves.get(i).getX() + " : " + this.availableMoves.get(i).getY());
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

    private synchronized ArrayList<Points> getPossibleEnemyMoves(int[][] field, int x, int y){
        ArrayList<Points> t = reversi.calculatingPossibleMoves(field, 2, 1);
        int counter = 0;
        for(int[] f : field){
            for(int fi :f){
                System.out.println("field: " + counter + " " + fi);
                counter++;
            }
        }
        for(Points p : t){
            System.out.println("Krijg binnen via: " + x + " : " + y + "     " + p.getX() + " : " + p.getY());
        }
        return reversi.calculatingPossibleMoves(field, 2, 1);
    }

    public class AICalculation implements Runnable {
        Points move;
        int[][] tempField;
        Output output;
        int x;
        int y;

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
            ownAvailableMoves();
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
            ArrayList<Points> enemyAvailableMoves = getPossibleEnemyMoves(tempField, x, y);
            /*for(Points p : enemyAvailableMoves){
                System.out.println("For move " + x + " : " + y + " Move: " + p.getX() + " : " + p.getY());
            }*/
            System.out.println("Enemies move: " + enemyAvailableMoves.size());
        }
    }

}

class Main{
    public static void main(String[] args){
        System.out.println("lopje");
        int[][] field = new int[8][8];
        Board board = new Board();
        field[3][3] = 2;
        field[4][3] = 2;
        field[5][3] = 1;
        field[6][3] = 2;
        AIReversi ai = new AIReversi(field, board);
        ai.calculateBestMove();
    }
}