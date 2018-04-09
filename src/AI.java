import weekopdrTicTacToe.Points;

import java.util.ArrayList;

public class AI {
    AITicTacToe ai = new AITicTacToe();
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
        //int scoreX = 0; // player 1 (AI)
        //int scoreO = 0; // player 2
        int[] scorePlayers = {0, 0};

        ArrayList<Points> AvailableMoves = new ArrayList<>();

        public AITicTacToe() {
            setMove(0,0, 1);
            setMove(0, 1, 1);
            setMove(2, 1, 1);
            possibleMoves();
            calculateBestMove();

        }

        private void setMove(int x, int y, int player){
            field[x][y] = player;
            scorePlayers[player] += fieldInt[x][y];
        }

        private void possibleMoves(){
            for(int i = 0; i < field.length; i++){
                for(int j = 0; j < field[i].length; j++){
                    if(field[i][j] == 0) {
                        //System.out.println("Place " + i + " = empty");
                        AvailableMoves.add(new Points(i, j));
                    }
                }
            }
        }

        private void calculateBestMove(){
            for(Points p: AvailableMoves) {
                if(canWin(p.getX(), p.getY(), 1)){
                    System.out.println("can win");
                } else {
                    System.out.println("cant");
                }
            }
        }

        private boolean canWin(int x, int y, int player){
            int counter[][];
            int tempScore = scorePlayers[player];

            for(int[] i : field){
                for(int j : i){
                    if(j == player){
                        counter[i][j] = ;
                    }
                }
            }
            System.out.println(player + " has " + counter.length);
            for (int n : score) {
                if(counter > 2){
                    for(int i = 0; i < counter; i++){
                        tempScore
                    }
                }
                if (scorePlayers[player]+fieldInt[x][y] == n) {
                    return true;
                }
            } return false;
        }


    }
}

class main {
    public static void main(String[] args) {
        AI a = new AI();
    }
}
