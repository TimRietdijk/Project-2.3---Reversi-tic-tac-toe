import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.DateFormat.Field;
import java.util.ArrayList;
import java.util.Collection;



public class Framework extends Application {

	int[][] field;
	int numberofstates = 3;
	int tileWidth = 90;
	int tileHeight = 90;

	int fieldLength;
	int fieldWidth;

	ArrayList<StackPane> stackPanes = new ArrayList<StackPane>();
	GridPane gridpane = new GridPane();


	public void setField(int length, int width){
		field = new int[length][width];
		makeField();
	}

	private void buttonAction(Button button){
        Image image = new Image(getClass().getResourceAsStream("weekopdrTicTacToe\\o.gif"));
        ImageView iv = new ImageView(image);
        button.setGraphic(iv);
	}

	private void makeField(){

		for(int i=0; i<field[1].length; i++) {
			for(int j=0; j<field.length; j++) {
				Button button = new Button();
				button.setOnAction((e) -> buttonAction(button));
				button.setPrefSize(tileWidth, tileHeight);
				StackPane stackPane = new StackPane(button);
				stackPane.setPrefSize(tileWidth, tileHeight);
				stackPanes.add(stackPane);
				stackPane.setStyle("-fx-border-color: black");
				gridpane.add(stackPane, i, j);
			}
		}

	}
	public void updateField(int length, int width, int state) {
		int position = ((width-1)*field.length)+(length-1);
		StackPane stackPane = new StackPane();
		stackPane = stackPanes.get(position);
		stackPane.getChildren().remove(0);
		if(stackPane.getChildren().isEmpty()) {}
    	else {
    		stackPane.getChildren().remove(0);
    	}     
    	Image im = new Image("file:\\D:\\eclipse projects\\project 2.3\\Project-2.3-Reversi-tic-tac-toe\\src\\weekopdrTicTacToe\\o.gif");
		ImageView imageView = new ImageView(im);
		stackPane.getChildren().add(imageView);


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

	public void setFieldLength(int fieldLength) {
		this.fieldLength = fieldLength;
	}

	public void setFieldWidth(int fieldWidth) {
		this.fieldWidth = fieldWidth;
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox vbox = new VBox();
		vbox.getChildren().addAll(gridpane);
		Scene scene = new Scene(vbox);
		scene.getStylesheets().add("TicTacToe.css");
		setField(fieldLength,fieldWidth);	
		primaryStage.setScene(scene);
		primaryStage.show();

	}
}
