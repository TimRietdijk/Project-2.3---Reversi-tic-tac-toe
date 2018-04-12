package reversi;

import java.util.ArrayList;

public class Reversi{     //extends framework!!

    ArrayList<Points> piecesToTurn = new ArrayList<>();

    public Reversi(int[][] field){
        //super(field);
    }

    public int[][] doMove(int[][] field, int lastMove){ // moet aangeroepen worden van buitenaf
        return calculating(field, lastMove);
    }

    private int getPlayerState(int[][] field, int[] coordinates){
        int value = field[coordinates[0]][coordinates[1]];
        return value;
    }

    private void addPiecesToTurn(int x, int y){
        piecesToTurn.add(new Points(x,y));
    }

    private int[] calculateMoveToCoordinates(int[][] field, int move) {
        int x = (move / (field.length));
        int y = move%(field.length);
        return new int[]{x, y};

    }

    private int[][] calculating(int[][] field, int move){
        int[] coordinates = calculateMoveToCoordinates(field, move);
        int player = getPlayerState(field, coordinates);
        north(field, player, coordinates);
        south(field, player, coordinates);
        east(field, player, coordinates);
        west(field, player, coordinates);
        northEast(field,player,coordinates);
        southEast(field,player,coordinates);
        northWest(field,player,coordinates);
        southWest(field,player,coordinates);
        return createUpdatedField(field, player);
    }

    private int[][] createUpdatedField(int[][] field, int player){
        int[][] tempField = field;
        for(Points p : piecesToTurn) {
            tempField[p.getX()][p.getY()] = player;
        }
        return tempField;
    }

    private void north(int[][] field, int player, int[] coordinates) {
        int counter = 0;
        int currentX = coordinates[0];
        int currentY = coordinates[1];
        if (currentY != 0) {
            outerloop:
            for (int i = 1; i <= currentY; i++) {
                if (field[currentX][currentY-i] != player && field[currentX][currentY-i] != 0) {
                    counter++;
                } else if (field[currentX][currentY-i] == player) {
                    if (counter > 0) {
                        for (int j = 1; j <= counter; j++) {
                            addPiecesToTurn(currentX, (currentY-j));
                        }
                    }
                    break outerloop;
                }
                else if(field[i][currentY] == 0){
                    break outerloop;
                }
            }
        }
    }
    private void northEast(int[][] field, int player, int[] coordinates) {
        int counter = 0;
        int currentX = coordinates[0];
        int currentY = coordinates[1];
        try {
            if (currentY != field.length-1 && currentX != 0)
                outerloop:
                        for (int i = 1; i <= field.length; i++) {
                            if (field[currentX + i][currentY - i] != player && field[currentX + i][currentY - i] != 0) {
                                counter++;
                            } else if (field[currentX + i][currentY - i] == player) {
                                if (counter > 0) {
                                    for (int j = 1; j <=  counter; j++) {
                                        addPiecesToTurn(currentX+j, currentY-j);
                                    }
                                }
                                break outerloop;
                            }
                            else if(field[currentX+i][currentY-i] == 0){
                                break outerloop;
                            }
                        }
        }catch (ArrayIndexOutOfBoundsException e){}

    }
    private void east(int[][] field, int player, int[] coordinates){
        int counter = 0;
        int currentX = coordinates[0];
        int currentY = coordinates[1];
        if (currentX != field.length-1) {
            outerloop:
            for (int i = 1; i < field.length; i++) {
                if (field[currentX+i][currentY] != player && field[currentX+i][currentY] != 0) {
                    counter++;
                } else if (field[currentX+i][currentY] == player) {
                    if (counter > 0) {
                        for (int j = 1; j <= counter; j++) {
                            addPiecesToTurn((currentX+j), currentY);
                        }
                    }
                    break outerloop;
                }
                else if(field[i][currentY] == 0){
                    break outerloop;
                }
            }
        }
    }
    private void southEast(int[][] field, int player, int[] coordinates){
        int counter = 0;
        int currentX = coordinates[0];
        int currentY = coordinates[1];
        try {
            if (currentY != field.length-1 && currentX != field.length-1) {
                outerloop:
                for (int i = 1; i <= field.length; i++) {
                    if (field[currentX + i][currentY + i] != player && field[currentX + i][currentY + i] != 0) {
                        counter++;
                    } else if (field[currentX + i][currentY + i] == player) {
                        if (counter > 0) {
                            for (int j = 1; j <=  counter; j++) {
                                addPiecesToTurn(currentX+j, currentY+j);
                            }
                        }
                        break outerloop;
                    }
                    else if(field[currentX+i][currentY+i] == 0){
                        break outerloop;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e){}

    }
    private void south(int[][] field, int player, int[] coordinates){
        int counter = 0;
        int currentX = coordinates[0];
        int currentY = coordinates[1];
        if (currentY != field[1].length-1) {
            outerloop:
            for (int i = 1; i < field[1].length; i++) {
                if (field[currentX][currentY+i] != player && field[currentX][currentY+i] != 0) {
                    counter++;
                } else if (field[currentX][currentY+i] == player) {
                    if (counter > 0) {
                        for (int j = 1; j <= counter; j++) {
                            addPiecesToTurn(currentX, (currentY+j));
                        }
                    }
                    break outerloop;
                }
                else if(field[i][currentY] == 0){
                    break outerloop;
                }
            }
        }
    }
    private void southWest(int[][] field, int player, int[] coordinates){
        int counter = 0;
        int currentX = coordinates[0];
        int currentY = coordinates[1];
        try {
            if (currentY != field.length-1 && currentX != field.length-1) {
                outerloop:
                for (int i = 1; i <= field.length; i++) {
                    if (field[currentX - i][currentY + i] != player && field[currentX - i][currentY + i] != 0) {
                        counter++;
                    } else if (field[currentX - i][currentY + i] == player) {
                        if (counter > 0) {
                            for (int j = 1; j <=  counter; j++) {
                                addPiecesToTurn(currentX-j, currentY+j);
                            }
                        }
                        break outerloop;
                    }
                    else if(field[currentX-i][currentY+i] == 0){
                        break outerloop;
                    }
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){

        }

    }
    private void west(int[][] field, int player, int[] coordinates){
        int counter = 0;
        int currentX = coordinates[0];
        int currentY = coordinates[1];
        if (currentX != 0) {
            outerloop:
            for (int i = 1; i <= currentX; i++) {
                if (field[currentX-i][currentY] != player && field[currentX-i][currentY] != 0) {
                    counter++;
                } else if (field[currentX-i][currentY] == player) {
                    if (counter > 0) {
                        for (int j = 1; j <= counter; j++) {
                            addPiecesToTurn((currentX-j), currentY);
                        }
                    }
                    break outerloop;
                }
                else if(field[i][currentY] == 0){
                    break outerloop;
                }
            }
        }
    }
    private void northWest(int[][] field, int player, int[] coordinates){
        int counter = 0;
        int currentX = coordinates[0];
        int currentY = coordinates[1];
        try {
            if (currentY != 0 && currentX != 0) {
                outerloop:
                for (int i = 1; i <= field.length; i++) {
                    if (field[currentX - i][currentY - i] != player && field[currentX - i][currentY - i] != 0) {
                        counter++;
                    } else if (field[currentX - i][currentY - i] == player) {
                        if (counter > 0) {
                            for (int j = 1; j <= counter; j++) {
                                addPiecesToTurn(currentX - j, currentY - j);
                            }
                        }
                        break outerloop;
                    } else if (field[currentX - i][currentY - i] == 0) {
                        break outerloop;
                    }
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
        }


    }
}
/*class Main{

    public static void main(String args[]) {
        int[][] board = new int[8][8];
        board[5][2] = 1;
        board[5][3] = 2;
        board[5][0] = 1;
        board[5][1] = 2;
        board[6][2] = 2;
        board[7][2] = 1;
        board[5][3] = 2;
        board[5][4] = 2;
        board[5][5] = 2;
        board[5][6] = 2;
        board[5][7] = 1;
        board[4][2] = 2;
        board[3][2] = 2;
        board[2][2] = 2;
        board[1][2] = 2;
        board[0][2] = 1;
        board[6][1] = 2;
        board[7][0] = 1;
        board[6][3] = 2;
        board[7][4] = 1;
        board[4][3] = 2;
        board[3][4] = 2;
        board[2][5] = 2;
        board[1][6] = 2;
        board[0][7] = 1;
        board[4][1] = 2;
        board[3][0] = 1;


        reversi reversi = new reversi(board);
        reversi.doMove(board);
    }
}*/

