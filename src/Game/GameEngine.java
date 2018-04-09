package Game;

import javafx.scene.control.Button;

public class GameEngine {





    public void enemyMove(int position, int state){
        int width = position / (field.length-1);
        int length = position%field.length;
        updateField(length, width, state);
    }
}

    public void setState(int length, int width, int value) {
        if(length >= field.length) {
            System.out.println("error: the given position does not exist on this board");
        }else {
            if(width >= field[1].length) {
                System.out.println("error: the given position does not exist on this board");
            }else {
                if(value >= numberofstates)
                {
                    System.out.println("Error: given state is not supported");
                }else {
                    if(value == getState(length, width))
                    {
                        System.out.println("!: Dit vakje is al van jou, probeer een ander vakje");
                    }else {
                        field[length][width] = value;
                    }
                }
            }
        }
    }
    public int getState(int length, int width) {
        return field[length][width];
    }

    public void setStates(String[] states) {
        this.states = states;
    }
    private int sendMove(int move){
        return move;
    }
    private void makeMove(Button button){
        int x = button.translateXProperty().intValue();
        int y = button.translateYProperty().intValue();
        sendMove(((y)*field.length)+x);
        updateField(x, y, 1);
    }
}