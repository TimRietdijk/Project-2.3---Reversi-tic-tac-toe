/**
 * aI voor ticTacToe
 * Geïnspireerd door: https://github.com/avianey/minimax4j/blob/master/minimax4j-sample/src/main/java/fr/avianey/minimax4j/sample/tictactoe
 */

package aI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AITicTacToe {

    private int[][] currentField;

    static final int empty = 0;
    static final int player1 = 1;
    static final int player2 = 2;

    private Points theMove;

    private int maxDepth = 4;

    public AITicTacToe(int[][] field) {
        //currentField houdt met een int (0, 1, 2) bij wat de status van een plek op het bord is
        currentField = field;
    }

    public void updateField(int[][] field) {
        currentField = field;
    }

    private boolean hasWon(int player, int[][] aField) {
        return
                (player == aField[0][1] && player == aField[0][2] && player == aField[0][0])
                        ||
                        (player == aField[1][1] && player == aField[1][2] && player == aField[1][0])
                        ||
                        (player == aField[2][1] && player == aField[2][2] && player == aField[2][0])
                        ||
                        (player == aField[1][0] && player == aField[2][0] && player == aField[0][0])
                        ||
                        (player == aField[1][1] && player == aField[2][1] && player == aField[0][1])
                        ||
                        (player == aField[1][2] && player == aField[2][2] && player == aField[0][2])
                        ||
                        (player == aField[1][1] && player == aField[2][2] && player == aField[0][0])
                        ||
                        (player == aField[1][1] && player == aField[2][0] && player == aField[0][2]);
    }

    private List<Points> getPlayerMoves(int player, int[][] aField) {
        List<Points> availableMoves = new ArrayList<>();
        for (int i = 0; i < aField.length; i++) {
            for (int j = 0; j < aField.length; j++) {
                if (aField[i][j] == player) {
                    availableMoves.add(new Points(i, j));
                }
            }
        }
        return availableMoves;
    }

    public Points decideMove() {
        List<Points> availableMoves = getPlayerMoves(0, currentField);

        for (Points move : availableMoves) {

            int[][] possibleFutureField = new int[3][3];

            //copying the array
            for(int i=0; i<currentField.length; i++) {
                for(int j=0; j<currentField[i].length; j++) {
                    possibleFutureField[i][j]=currentField[i][j];
                }
            }

            possibleFutureField[move.getX()][move.getY()] = player1;

            int score = score(possibleFutureField);

            //MINIMAX
            if (score > 0) {
                //deze zet wint het spel
                return move;
            } else if (score == 0) {
                System.out.println("IK KOM HIER 1");
                //deze zet wint niet het spel, maar de tegenstander kan nu misschien wel winnen
                //tegenstander wil ook winnen
                Points preventionMove = minMove(possibleFutureField, player2);
                if(preventionMove != null) { //als minMove voor speler2 niet null returned moeten wij deze zet maken om te voorkomen dat speler2 hiermee wint
                    return preventionMove;
                }
            } else if(score<0){
                //AI verloor
                break;
            }

        }
        if (currentField[1][1] == 0) { //nog geen minimax, dus begin met midden als midden vrij is
            return new Points(1, 1);
        }else if (currentField[2][0] == 0) { //daarna de hoeken
            return new Points(2, 0);
        } else if (currentField[2][2] == 0) { //daarna de hoeken
            return new Points(2, 2);
        } else if (currentField[0][0] == 0) { //daarna de hoeken
            return new Points(0, 0);
        } else if (currentField[0][2] == 0) { //daarna de hoeken
            return new Points(0, 2);
        }
        //Doe een random move als er geen mogelijke move is om welke reden dan ook
        return randomMove(currentField);


    }


    private Points minMove(int[][] testField, int player) {
        //Hier wordt bepaald wat mijn tegenstander kan aanrichten
        List<Points> testAvailableMoves = getPlayerMoves(0, testField);
        for (Points move : testAvailableMoves) {



            int[][] furtherPossibleFutureField = new int[3][3];
            //copying the array
            for(int i=0; i<testField.length; i++) {
                for(int j=0; j<testField[i].length; j++) {
                    furtherPossibleFutureField[i][j]=testField[i][j];
                }
            }

            furtherPossibleFutureField[move.getX()][move.getY()] = player;

            int score = score(furtherPossibleFutureField);

            if (score < 0) { //Tegenstander wil een zo minimaal mogelijke score
                //Deze zet is interessant voor de tegenspeler, met deze move wint speler 2 namelijk
                return move;
            } else if (score==0){
                /*Points aiMove = maxMove(furtherPossibleFutureField, player1);
                if(aiMove!=null) {
                    return move;
                }*/
            } else {
                //De AI zal winnen
            }
        }
        return null;
    }

    private Points maxMove(int[][] testField, int player) {
        //Hier wordt bepaald wat mijn tegenstander denkt dat ik daar (zie minMove()) op zal aanrichten
        List<Points> testAvailableMoves = getPlayerMoves(0, testField);
        for (Points move : testAvailableMoves) {

            int[][] furtherPossibleFutureField = new int[3][3];
            //copying the array
            for(int i=0; i<testField.length; i++) {
                for(int j=0; j<testField[i].length; j++) {
                    furtherPossibleFutureField[i][j]=testField[i][j];
                }
            }

            furtherPossibleFutureField[move.getX()][move.getY()] = player;

            int score = score(furtherPossibleFutureField);

            if (score > 0) {

            } /*else if (score==0){
                Points opponentMove = minMove(furtherPossibleFutureField, player2);
                if(opponentMove!=null) {
                    return move;
                }
            } else {
                return null;
            }*/
        }
        return null;
    }

    public Points randomMove(int[][] aField) {
        List<Points> availableMoves = getPlayerMoves(0, aField);

        if(!(availableMoves.size()<1)) {

            Random random = new Random();
            int randomMove = random.nextInt(availableMoves.size());

            return availableMoves.get(randomMove);
        } else {
            return null;
        }
    }

    private int score (int[][] testField) {
        if (hasWon(player1, testField)) {
            return 10;
        } else if (hasWon(player2, testField)) {
            return -10;
        } else {
            return 0;
        }
    }
}
