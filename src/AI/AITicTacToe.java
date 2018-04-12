/**
 * AI voor TicTacToe
 * Geïnspireerd door: https://github.com/avianey/minimax4j/blob/master/minimax4j-sample/src/main/java/fr/avianey/minimax4j/sample/tictactoe
 */

package AI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AITicTacToe {

    private int[][] currentField;

    static final int empty = 0;
    static final int player1 = 1;
    static final int player2 = 2;

    private Points theMove;

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

    public Points decideMove(int[][] updatedField) {
        updateField(updatedField);
        List<Points> availableMoves = getPlayerMoves(0, currentField);

        for (Points move : availableMoves) {

            int[][] testField = currentField;
            testField[move.getX()][move.getY()] = player1;

            int score = score(testField);

            //MINIMAX
            if (score > 0) {
                //deze zet wint het spel
                return move;
            } else if (score == 0) {
                //deze zet wint niet het spel, maar de tegenstander kan nu misschien wel winnen
                //tegenstander wil ook winnen
                if(minMove(testField, player2) != null) { //als minMove voor speler2 niet null returned moeten wij deze zet maken om te voorkomen dat speler2 hiermee wint
                    return move;
                } else { //else betekent dat minimax nog niet toegepast kan worden
                    if (move.getX() == 1 && move.getY() == 1) { //het midden heeft dan prioriteit
                        return move;
                    } else if (move.getX() == 0 && move.getY() == 0) { //daarna de hoeken
                        return move;
                    } else if (move.getX() == 2 && move.getY() == 2) { //daarna de hoeken
                        return move;
                    } else if (move.getX() == 0 && move.getY() == 2) { //daarna de hoeken
                        return move;
                    } else if (move.getX() == 2 && move.getY() == 0) { //daarna de hoeken
                        return move;
                    } else { //het midden en alle hoeken zijn bezet en minimax kan niet toegepast worden dus doe maar random
                        System.out.println("AI: IK KIES NU RANDOM");
                        return randomMove(currentField);
                    }
                }
            } else {
                System.out.println("AI: SPEL IS OVER, ONZE AI VERLOOR :(");
                break;
            }

        }

        return null;


    }

    private Points minMove(int[][] testField, int player) {
        List<Points> testAvailableMoves = getPlayerMoves(0, testField);
        for (Points move : testAvailableMoves) {

            int[][] newTestField = testField;
            newTestField[move.getX()][move.getY()] = player;

            int score = score(testField);

            if (score > 0) {
                //deze zet is interessant voor de tegenspeler, met deze move wint speler 2
                return move;
            } else {
                return null;
            }
        }
        return null;
    }

    public Points randomMove(int[][] aField) {
        List<Points> availableMoves = getPlayerMoves(0, aField);

        if(availableMoves.size()<1) {

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
