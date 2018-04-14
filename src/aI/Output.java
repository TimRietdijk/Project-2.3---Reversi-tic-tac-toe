package aI;

import reversi.Points;

import java.util.ArrayList;

public class Output {
    private int x;
    private int y;
    private int availableMoves;
    private int availableMovesEnemy;
    private int centerPeaces;
    private int peacesTurned;
    ArrayList<Points> peacesTurnedArray;

    public Output(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Output(int x, int y, int availableMoves, int availableMovesEnemy, int centerPeaces){
        this.x = x;
        this.y = y;
        this.availableMoves = availableMoves;
        this.availableMovesEnemy = availableMovesEnemy;
        this.centerPeaces = centerPeaces;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getAvailableMoves() {
        return availableMoves;
    }

    public void setAvailableMoves(int availableMoves) {
        this.availableMoves = availableMoves;
    }

    public int getAvailableMovesEnemy() {
        return availableMovesEnemy;
    }

    public void setAvailableMovesEnemy(int availableMovesEnemy) {
        this.availableMovesEnemy = availableMovesEnemy;
    }

    public int getCenterPeaces() {
        return centerPeaces;
    }

    public void setCenterPeaces(int centerPeaces) {
        this.centerPeaces = centerPeaces;
    }

    public int getPeacesTurned() {
        return peacesTurned;
    }

    public void setPeacesTurned(int peacesTurned) {
        this.peacesTurned = peacesTurned;
    }

    public ArrayList<Points> getPeacesTurnedArray() {
        return peacesTurnedArray;
    }

    public void setPeacesTurnedArray(ArrayList<Points> peacesTurnedArray) {
        this.peacesTurnedArray = peacesTurnedArray;
    }
}
