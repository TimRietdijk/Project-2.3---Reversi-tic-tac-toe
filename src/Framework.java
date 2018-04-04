import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.text.DateFormat.Field;
import java.util.ArrayList;
import java.util.Collection;

public class Framework extends Application {

    int[][] field;
    int numberofstates = 3;
    int tileWidth = 50;
    int tileHeight = 50;

    Collection<StackPane> stackPanes = new ArrayList<StackPane>();
    GridPane gridpane = new GridPane();

    
    public void setField(int length, int width){
        field = new int[length][width];

    }

    private void makeField(int length, int width){
        for (int i = 1; i <= length; i++) {
		    for (int j = 1; j <= width; j++) {
				StackPane stackPane = new StackPane();
				stackPane.setPrefSize(tileWidth, tileHeight);
				stackPanes.add(stackPane);
				stackPane.setStyle("-fx-border-color: black");
				gridpane.add(stackPane, i, j);
			}
		}
	}

    public int getState(int length, int width) {
    	return field[length][width];
    }
    public void setSate(int length, int width, int value) {
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

    public void showField() {
	      for(int i=0; i<field.length; i++) {
	      for(int j=0; j<field[i].length; j++) {
	          System.out.println("Values at arr["+i+"]["+j+"] is "+field[i][j]);
	      }
	  }
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(gridpane);
        primaryStage.setScene(scene);
        makeField(8,8);
        primaryStage.show();
	}
}

class main{
    public static void main(String[] args){
        Framework framework = new Framework();
        framework.setField(3,3);   
        framework.setSate(1, 3, 1);
        framework.showField();
        
    }
}