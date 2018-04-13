package reversi;

import framework.Board;
import framework.Framework;

import java.util.ArrayList;

public class Reversi extends Framework{     //extends framework!!

    ArrayList<Points> piecesToTurn = new ArrayList<>();
    ArrayList<Points> enemyPieces = new ArrayList<>();
    ArrayList<Points> emptySpacesNeighbouringEnemy = new ArrayList<>();
    ArrayList<Points> possibleMoves = new ArrayList<>();

    public Reversi(int[][] field, Board board){
        super(board);
        field[3][3] = 2;
        field[4][4] = 2;
        field[4][3] = 1;
        field[3][4] = 1;
    }

    public int[][] doMove(int[][] field, int lastMove){ // moet aangeroepen worden van buitenaf
        int[][] temp = calculating(field, lastMove);
        return temp;
    }

    public ArrayList<Points> getPiecesTurnedByMove(int[][] field, int move){
        piecesToTurn = new ArrayList<>();
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
        return piecesToTurn;
    }

    private int getPlayerState(int[][] field, int[] coordinates){
        int value = field[coordinates[0]][coordinates[1]];
        return value;
    }

    private void addPiecesToTurn(int x, int y){
        piecesToTurn.add(new Points(x,y));
    }
    public void addEnemyPieces(int x, int y) {enemyPieces.add(new Points(x,y));}
    public void addPossibleEmptyPieces(int x, int y) {emptySpacesNeighbouringEnemy.add(new Points(x,y));}
    public void addPossibleMoves(int x, int y) {possibleMoves.add(new Points(x,y)); }


    private int[][] calculating(int[][] field, int move){
        piecesToTurn = new ArrayList<>();
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

    public void calculatingPossible(int[][] field, int move, int player){
        int[] coordinates = calculateMoveToCoordinates(field, move);
        //System.out.println("dit zijn de coordinaten die worden getest: "+ coordinates[0]+","+coordinates[1]);
        northpossible(field, player, coordinates);
        southpossible(field, player, coordinates);
        eastpossible(field, player, coordinates);
        westpossible(field, player, coordinates);
        northEastpossible(field,player,coordinates);
        southEastpossible(field,player,coordinates);
        northWestpossible(field,player,coordinates);
        southWestpossible(field,player,coordinates);
    }

    public void calculatingPossibleMoves(int[][]field, int player, int enemy){
        getEnemyPieces(field, enemy);
        findEmptyNeighbouringEnemy(field, enemy);
        for (int i = 0; i < emptySpacesNeighbouringEnemy.size(); i++) {
            //System.out.println("Dit is een mogelijke move, Mischien..."+EmptyspacesNeighbouringEnemy.get(i).getX()+" : "+EmptyspacesNeighbouringEnemy.get(i).getY());
        }
        for (int i = 0; i < emptySpacesNeighbouringEnemy.size(); i++) {
            int move = calculateMoveToPosition(field, i);
            //System.out.println("dit is move: "+ move);
            calculatingPossible(field, move, player);

        }
        for (int i = 0; i < possibleMoves.size(); i++) {
            System.out.println(possibleMoves.get(i).getX()+" : "+possibleMoves.get(i).getY());
        }
    }

    private int calculateMoveToPosition(int[][] field, int i) {
        return (((emptySpacesNeighbouringEnemy.get(i).getX()) * field.length) + emptySpacesNeighbouringEnemy.get(i).getY());
    }

    public ArrayList findEmptyNeighbouringEnemy(int[][] field, int enemy){
        for (int i = 0; i < enemyPieces.size(); i++) {
            try{
                if(field[enemyPieces.get(i).getX()][enemyPieces.get(i).getY()-1] == 0){     //north +1
                    addPossibleEmptyPieces(enemyPieces.get(i).getX(),enemyPieces.get(i).getY()-1);
                }
            }
            catch (ArrayIndexOutOfBoundsException e){};
            try {
                if (field[enemyPieces.get(i).getX() + 1][enemyPieces.get(i).getY()] == 0) {     //east +1
                    addPossibleEmptyPieces(enemyPieces.get(i).getX() + 1, enemyPieces.get(i).getY());
                }
            }
            catch(ArrayIndexOutOfBoundsException e){};
            try {
                if (field[enemyPieces.get(i).getX()][enemyPieces.get(i).getY() + 1] == 0) {     //south +1
                    addPossibleEmptyPieces(enemyPieces.get(i).getX(), enemyPieces.get(i).getY() + 1);
                }
            }
            catch(ArrayIndexOutOfBoundsException e){};
            try {
                if (field[enemyPieces.get(i).getX() - 1][enemyPieces.get(i).getY()] == 0) {     //west +1
                    addPossibleEmptyPieces(enemyPieces.get(i).getX() - 1, enemyPieces.get(i).getY());
                }
            }
            catch(ArrayIndexOutOfBoundsException e){};
            try {
                if (field[enemyPieces.get(i).getX() + 1][enemyPieces.get(i).getY() - 1] == 0) {     //northeast +1
                    addPossibleEmptyPieces(enemyPieces.get(i).getX() + 1, enemyPieces.get(i).getY() - 1);
                }
            }
            catch (ArrayIndexOutOfBoundsException e){};
            try {
                if (field[enemyPieces.get(i).getX() + 1][enemyPieces.get(i).getY() + 1] == 0) {     //southeast +1
                    addPossibleEmptyPieces(enemyPieces.get(i).getX() + 1, enemyPieces.get(i).getY() + 1);
                }
            }
            catch(ArrayIndexOutOfBoundsException e){};
            try {
                if (field[enemyPieces.get(i).getX() - 1][enemyPieces.get(i).getY() + 1] == 0) {     //southwest +1
                    addPossibleEmptyPieces(enemyPieces.get(i).getX() - 1, enemyPieces.get(i).getY() + 1);
                }
            }
            catch(ArrayIndexOutOfBoundsException e){};
            try {
                if (field[enemyPieces.get(i).getX() - 1][enemyPieces.get(i).getY() - 1] == 0) {     //northwest +1
                    addPossibleEmptyPieces(enemyPieces.get(i).getX() - 1, enemyPieces.get(i).getY() - 1);
                }
            }
            catch (ArrayIndexOutOfBoundsException e){};
        }
        return emptySpacesNeighbouringEnemy;
    }

    public void getEnemyPieces(int[][] field, int enemy){
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if(field[i][j] == enemy) {
                    addEnemyPieces(i, j);
                }
            }
        }
        //return enemyPieces;
    }

    private int[][] createUpdatedField(int[][] field, int player){
        int[][] tempField = field;
        for(Points p : piecesToTurn) {
            tempField[p.getX()][p.getY()] = player;
        }
        return tempField;
    }

    public void northpossible(int[][] field, int player, int[] coordinates) {
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
                            System.out.println("Possible move north: "+currentX+","+currentY);
                            addPossibleMoves(currentX, currentY);
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
    public void northEastpossible(int[][] field, int player, int[] coordinates) {
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
                                        System.out.println("Possible move northeast: "+currentX+","+currentY);
                                        addPossibleMoves(currentX, currentY);
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
    public void eastpossible(int[][] field, int player, int[] coordinates){
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
                            System.out.println("Possible move east: "+currentX+","+currentY);
                            addPossibleMoves(currentX, currentY);
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
    public void southEastpossible(int[][] field, int player, int[] coordinates){
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
                                System.out.println("Possible move southeast: "+currentX+","+currentY);
                                addPossibleMoves(currentX, currentY);
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
    public void southpossible(int[][] field, int player, int[] coordinates){
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

                            System.out.println("Possible move south: "+currentX+","+currentY);
                            addPossibleMoves(currentX, currentY);
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
    public void southWestpossible(int[][] field, int player, int[] coordinates){
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

                                System.out.println("Possible move southwest: "+currentX+","+currentY);
                                addPossibleMoves(currentX, currentY);
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
    public void westpossible(int[][] field, int player, int[] coordinates){
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

                            System.out.println("Possible move west: "+currentX+","+currentY);
                            addPossibleMoves(currentX, currentY);
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
    public void northWestpossible(int[][] field, int player, int[] coordinates){
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

                                System.out.println("Possible move northwest: "+currentX+","+currentY);
                                addPossibleMoves(currentX, currentY);
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

    public void north(int[][] field, int player, int[] coordinates) {
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


    private int[] calculateMoveToCoordinates(int[][] field, int move) {
        int x = (move / (field.length));
        int y = move%(field.length);
        return new int[]{x, y};

    }
    public void doMove(int[][] board){
        calculating(board, 20);
        calculatingPossibleMoves(board, 1,2);
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

