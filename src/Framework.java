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

    ArrayList<StackPane> stackPanes = new ArrayList<StackPane>();
    GridPane gridpane = new GridPane();

    
    public void setField(int length, int width){
        field = new int[length][width];
        makeField();
    }

    private void makeField(){
    	for(int i=0; i<field.length; i++) {
  	      for(int j=0; j<field[i].length; j++) {
  	    	StackPane stackPane = new StackPane();
  	    	Image im = new Image("file:\\C:\\Users\\glubb\\Google Drive\\HBO\\Jaar 2\\Periode 3\\Project\\src\\weekopdrTicTacToe\\x.gif");
            ImageView imageView = new ImageView(im);
			stackPane.setPrefSize(tileWidth, tileHeight);
			stackPanes.add(stackPane);
			stackPane.getChildren().add(imageView);
			stackPane.setStyle("-fx-border-color: black");
			gridpane.add(stackPane, i, j);
  	      }
  	  }
				
	}
    public void updateField(int length, int width, int state) {
    	int position = length*width;
 
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
        setField(4,6);
        primaryStage.show();
        
	}
}

class main{
    public static void main(String[] args){   	
    	Application.launch(Framework.class, args);       
    }
}