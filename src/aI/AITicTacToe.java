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

    public Points decideMove(int[][] updatedField) {
        System.out.println();
        System.out.println();
        System.out.println();
        updateField(updatedField);
        List<Points> availableMoves = getPlayerMoves(0, currentField);





        //HIER WORDT CURRENTFIELD UITGEPRINT
        String stringCurrentField = "currentField: ";
        for(int[] i:currentField) {
            for(int j:i) {
                stringCurrentField+=j+", ";
            }
        }
        System.out.println(stringCurrentField);






        for (Points move : availableMoves) {
            System.out.println();

            //HIER WORDT CURRENTFIELD UITGEPRINT
            stringCurrentField = "currentField: ";
            for(int[] i:currentField) {
                for(int j:i) {
                    stringCurrentField+=j+", ";
                }
            }
            System.out.println(stringCurrentField);

            int[][] possibleFutureField = new int[3][3];

            //copying the array
            for(int i=0; i<currentField.length; i++) {
                for(int j=0; j<currentField[i].length; j++) {
                    possibleFutureField[i][j]=currentField[i][j];
                }
            }

            possibleFutureField[move.getX()][move.getY()] = player1;






            //HIER WORDT HET MOGELIJKE TOEKOMSTIGE FIELD GEPRINT
            String stringPossibleFutureField = "stringPossibleFutureField: ";
            for(int[] i:possibleFutureField) {
                for(int j:i) {
                    stringPossibleFutureField+=j+", ";
                }
            }
            System.out.println(stringPossibleFutureField);







            int score = score(possibleFutureField);

            System.out.println("score: "+score);

            //MINIMAX
            if (score > 0) {
                System.out.println("    Dit is een winnende zet voor mij");
                //deze zet wint het spel
                return move;
            } else if (score == 0) {
                //deze zet wint niet het spel, maar de tegenstander kan nu misschien wel winnen
                //tegenstander wil ook winnen
                Points preventionMove = minMove(possibleFutureField, player2);
                if(preventionMove != null) { //als minMove voor speler2 niet null returned moeten wij deze zet maken om te voorkomen dat speler2 hiermee wint
                    System.out.println("Deze zet zou winnend zijn voor tegenstander, maar ik doe hem om dat te voorkomen");
                    return preventionMove;
                }
            } else if(score<0){
                System.out.println("AI: SPEL IS OVER, ONZE aI VERLOOR :(");
                break;
            }

        }
        System.out.println("                Minimax kon niet toegepast worden");
        if (currentField[1][1] == 0) { //nog geen minimax, dus begin met midden als midden vrij is
            return new Points(1, 1);
        }else if (currentField[0][0] == 0) { //daarna de hoeken
            return new Points(0, 0);
        } else if (currentField[2][2] == 0) { //daarna de hoeken
            return new Points(2, 2);
        } else if (currentField[2][0] == 0) { //daarna de hoeken
            return new Points(2, 0);
        } else if (currentField[0][2] == 0) { //daarna de hoeken
             return new Points(0, 2);
        }
        //het midden en alle hoeken zijn bezet en minimax kan niet toegepast worden dus doe maar random
        System.out.println("AI: IK KIES NU RANDOM");
        return randomMove(currentField);


    }


    private Points minMove(int[][] testField, int player) {
        System.out.println("IK KIJK NU WAT MIJN TEGENSTANDER KAN AANRICHTEN");
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






            //HIER WORDT HET MOGELIJKE VERDERE TOEKOMSTIGE VELD GEPRINT
            String stringFurtherPossibleFutureField = "stringFurtherPossibleFutureField: ";
            for(int[] i:furtherPossibleFutureField) {
                for(int j:i) {
                    stringFurtherPossibleFutureField+=j+", ";
                }
            }
            System.out.println(stringFurtherPossibleFutureField);







            int score = score(furtherPossibleFutureField);

            System.out.println("MOGELIJKE SCORE: " + score +" , bij x "+ move.getX()+ " en y "+ move.getY());
            if (score < 0) { //tegenstander wil een zo minimaal mogelijke score
                //deze zet is interessant voor de tegenspeler, met deze move wint speler 2 namelijk
                return move;
            } else if (score==0){
                Points aiMove = maxMove(furtherPossibleFutureField, player1);
                if(aiMove!=null) {
                    return move;
                }
            } else {
                System.out.println("AI IS GOING TO WIN");
            }
        }
        return null;
    }

    private Points maxMove(int[][] testField, int player) {
        System.out.println("IK KIJK NU WAT MIJN TEGENSTANDER DENKT DAT IK DAAR KAN AANRICHTEN");
        List<Points> testAvailableMoves = getPlayerMoves(0, testField);
        for (Points move : testAvailableMoves) {



            int[][] furtherPossibleFutureField = new int[3][3];
            //copying the array
            for(int i=0; i<testField.length; i++) {
                for(int j=0; j<testField[i].length; j++) {
                    furtherPossibleFutureField[i][j]=testField[i][j];
                }
            }

            //furtherPossibleFutureField = testField;
            furtherPossibleFutureField[move.getX()][move.getY()] = player;






            //HIER WORDT HET MOGELIJKE VERDERE TOEKOMSTIGE VELD GEPRINT
            String stringFurtherPossibleFutureField = "stringFurtherPossibleFutureField: ";
            for(int[] i:furtherPossibleFutureField) {
                for(int j:i) {
                    stringFurtherPossibleFutureField+=j+", ";
                }
            }
            System.out.println(stringFurtherPossibleFutureField);







            int score = score(furtherPossibleFutureField);

            System.out.println("MOGELIJKE SCORE: " + score +" , bij x "+ move.getX()+ " en y "+ move.getY());
            if (score > 0) {
                System.out.println("Deze toekomstige zet is al winnend voor onze AI");
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

    public static void main(String[] args) {
        int[][] oefenveldje = {{0,0,0},{0,0,0},{0,0,0}};
        AITicTacToe aiTicTacToe = new AITicTacToe(oefenveldje);

        oefenveldje[1][1] = 2;
        Points moveAI1 = aiTicTacToe.decideMove(oefenveldje);
        oefenveldje[moveAI1.getX()][moveAI1.getY()] = 1;
        System.out.println(moveAI1.getX() + " " + moveAI1.getY());

        oefenveldje[0][2] = 2;
        Points moveAI2 = aiTicTacToe.decideMove(oefenveldje);
        oefenveldje[moveAI2.getX()][moveAI2.getY()] = 1;
        System.out.println(moveAI2.getX() + " " + moveAI2.getY());

        oefenveldje[1][0] = 2;
        Points moveAI3 = aiTicTacToe.decideMove(oefenveldje);
        oefenveldje[moveAI3.getX()][moveAI3.getY()] = 1;
        System.out.println(moveAI3.getX() + " " + moveAI3.getY());

        oefenveldje[0][0] = 2;
        Points moveAI4 = aiTicTacToe.decideMove(oefenveldje);
        oefenveldje[moveAI4.getX()][moveAI4.getY()] = 1;
        System.out.println(moveAI4.getX() + " " + moveAI4.getY());

        oefenveldje[0][1] = 2;
        Points moveAI5 = aiTicTacToe.decideMove(oefenveldje);
        oefenveldje[moveAI5.getX()][moveAI5.getY()] = 1;
        System.out.println(moveAI5.getX() + " " + moveAI5.getY());
    }
}
