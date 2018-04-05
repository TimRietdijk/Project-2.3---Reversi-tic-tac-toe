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
<<<<<<< HEAD
	int tileWidth = 90;
	int tileHeight = 90;
=======
	int tileWidth = 80;
	int tileHeight = 80;
>>>>>>> 49203e0e2d2c81d52f5081c0a3f77440e8a52fd2

	int fieldLength;
	int fieldWidth;
	String[] states;

	int changeState = 1; //for testing different symbols

	ArrayList<StackPane> stackPanes = new ArrayList<StackPane>();
	GridPane gridpane = new GridPane();


	public void setField(int length, int width){
		field = new int[length][width];
		makeField();
	}

	private void buttonAction(Button button){
		updateField(button.translateXProperty().intValue(), button.translateYProperty().intValue(), 1);
	}

	private void makeField(){

		for(int i=0; i<field[1].length; i++) {
			for(int j=0; j<field.length; j++) {
				Button button = new Button();
				button.setTranslateX(j);
				button.setTranslateY(i);
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
		setSate(length, width, state);
		int position = ((width)*field.length)+(length);
		StackPane stackPane;
		stackPane = stackPanes.get(position);

		Button button;
		button = (Button) stackPane.getChildren().get(0);

		Image image = new Image(getClass().getResourceAsStream("weekopdrTicTacToe\\" + states[state] + ".gif"));
		ImageView iv = new ImageView(image);
		button.setGraphic(iv);
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

	public void setStates(String[] states) {
		this.states = states;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox vbox = new VBox();
		vbox.getChildren().addAll(gridpane);
		Scene scene = new Scene(vbox);
		scene.getStylesheets().add("TicTacToe.css");
		setField(fieldLength,fieldWidth);
		b.setOnAction((e) -> showField());
		primaryStage.setScene(scene);
		primaryStage.show();

	}
}
