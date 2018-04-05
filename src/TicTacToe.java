import javafx.application.Application;

public class TicTacToe extends Framework {
    int length = 3;
    int width = 3;
    public TicTacToe() {
    	fieldLength = length;
    	fieldWidth = width;
    }
    
}

class main{
    public static void main(String args[]){ 
    	TicTacToe ttt = new TicTacToe();
    	Application.launch(TicTacToe.class, args);       
    }
}