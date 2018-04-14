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
        for(Points p : availableMoves){
            System.out.println(p.getX() + " : " + p.getY());
        }
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
                //System.out.println("aaaa" + this.availableMoves.get(i).getX() + " : " + this.availableMoves.get(i).getY());
                e.submit(new AICalculation(this.availableMoves.get(i)));
            }
            e.shutdown();
        }
    }

    private int calculateMoveToPosition(int[] move) {
        return (((move[1]) * field.length) + move[0]);
    }

    private synchronized ArrayList<Points> getPeacesToTurn(int position){
        System.out.println("I get : " + position);
        return reversi.getPiecesTurnedByMove(field, position);
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
            this.tempField = field;
            field[move.getX()][move.getY()] = 1;
            output = new Output(move.getX(), move.getY());
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
            piecesTurned = getPeacesToTurn(position);
            output.setPeacesTurnedArray(piecesTurned);
            output.setPeacesTurned(piecesTurned.size());
            System.out.println("Move: " + move.getX() + " : " + move.getY() + " == " + position + " hier worden " + piecesTurned.size() + " mee omgedraaid");
        }

        private void ownAvailableMoves(){

        }
    }

}

class Main{
    public static void main(String[] args){
        int[][] field = new int[8][8];
        Board board = new Board();
        field[3][3] = 2;
        field[4][3] = 2;
        field[5][3] = 2;
        field[6][3] = 2;
        field[7][3] = 1;
        AIReversi ai = new AIReversi(field, board);
        /*ai.addAvailableMoves(2,3);
        ai.addAvailableMoves(3,3);
        ai.addAvailableMoves(4,6);
        ai.addAvailableMoves(5,3);
        ai.addAvailableMoves(6,3);*/
       /* ai.addAvailableMoves(0,7);
        ai.addAvailableMoves(1,1);
        ai.addAvailableMoves(7,6);
        ai.addAvailableMoves(3,3);
        ai.addAvailableMoves(1,3);
        ai.addAvailableMoves(2,3);
        ai.addAvailableMoves(3,4);
        ai.addAvailableMoves(3,5);*/
        ai.calculateBestMove();
    }
}