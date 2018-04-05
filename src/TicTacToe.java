import javafx.application.Application;

public class TicTacToe extends Framework {
    int length = 3;
    int width = 3;

    String[] states = {"", "x", "O"};

    public TicTacToe(){
        super();
        setFieldLength(length);
        setFieldWidth(width);
        setStates(states);
<<<<<<< Updated upstream
    }
    public void start() {
    	Application.launch(TicTacToe.class, args);

=======
>>>>>>> Stashed changes
    }
}
class main{
    public static void main(String[] args){
        Application.launch(TicTacToe.class, args);
    }
}