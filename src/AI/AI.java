package AI;


import java.util.ArrayList;

public class AI {
    public class Board {

        private Tile myTile0;
        private Tile myTile1;
        private Tile myTile2;
        private Tile myTile3;
        private Tile myTile4;
        private Tile myTile5;
        private Tile myTile6;
        private Tile myTile7;
        private Tile myTile8;
        Board() {
            setState(new Empty(), myTile0);
            setState(new Empty(), myTile1);
            setState(new Empty(), myTile2);
            setState(new Empty(), myTile3);
            setState(new Empty(), myTile4);
            setState(new Empty(), myTile5);
            setState(new Empty(), myTile6);
            setState(new Empty(), myTile7);
            setState(new Empty(), myTile8);
        }

        void setState(final Tile newState, Tile tile) {
            tile = newState;
        }
    }

    public interface Tile {

    }

    public class Empty implements Tile {

    }

    public class Ours implements Tile {

    }

    public class Traitors implements Tile {

    }

    public class AITicTacToe {
        int[][] field = new int[3][3];
        int[][] fieldInt = {{1, 2, 4} ,
                {8, 16, 32},
                {64, 128, 256}};

        int[] score = {7, 56, 746, 73, 146, 292, 273, 84};
        int scoreX = 17;
        int scoreO = 0;

        ArrayList<AI.Points> AvailableMoves = new ArrayList<>();

        public AITicTacToe() {
            possibleMoves();
            calculateBestMove();

        }

        public void possibleMoves(){
            for(int i = 0; i < field.length; i++){
                for(int j = 0; j < field[i].length; j++){
                    if(field[i][j] == 0) {
                        System.out.println("Place " + i + " = empty");
                        AvailableMoves.add(new AI.Points(i, j));
                    }
                }
            }
        }

        public void calculateBestMove(){
            for(AI.Points p: AvailableMoves) {
                if(canWinn(p.getX(), p.getY())){
                    System.out.println("can winn");
                } else {
                    System.out.println("cant");
                }
            }
        }

        public void doMove(){
        }

        public boolean canWinn(int x, int y){
            for (int n : score) {
                if (scoreX+fieldInt[x][y] == n) {
                    return true;
                }
            } return false;
        }


    }
}
