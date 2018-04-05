import javafx.application.Application;

public class TicTacToe extends Framework {
    int length = 3;
    int width = 3;
<<<<<<< HEAD
    public TicTacToe() {
    	fieldLength = length;
    	fieldWidth = width;
    }
    
}

class main{
    public static void main(String args[]){ 
    	TicTacToe ttt = new TicTacToe();
    	Application.launch(TicTacToe.class, args);       
=======
    String[] states = {"", "x", "O"};

    public TicTacToe(){
        super();
        setFieldLength(length);
        setFieldWidth(width);
    }
}


class main{
    public static void main(String[] args){
        Application.launch(TicTacToe.class, args);
>>>>>>> f516ef42a2c14655bb4e88d5de7b3d1ac6f4d2f0
    }
}