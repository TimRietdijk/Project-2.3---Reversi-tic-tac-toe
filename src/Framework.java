import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Framework extends Application {
	private Wini ini;
	protected int[][] field;
	private int numberofstates = 3;
	private int tileWidth = 90;
	private int tileHeight = 90;
	private int fieldLength;
	private int fieldWidth;
	protected String[] states = new String[100];

	private Boolean myTurn = false;

	ArrayList<StackPane> stackPanes = new ArrayList<StackPane>();
	GridPane gridpane = new GridPane();


	public void setField(int length, int width){
		field = new int[length][width];
		makeField();
	}

	private void makeMove(Button button){
		int x = button.translateXProperty().intValue();
		int y = button.translateYProperty().intValue();
		sendMove(((y)*field.length)+x);
		updateField(x, y, 1);
	}

	private void buttonAction(Button button){
		makeMove(button);
	}

	public void move(){
		myTurn = true;
	}

	private int sendMove(int move){
		return move;
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
		setState(length, width, state);
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
		createIniFile(3,3, "x", "O", "TicTacToe.ini");
		VBox vbox = new VBox();
		Button b = new Button();
		vbox.getChildren().addAll(gridpane,b);
		Scene scene = new Scene(vbox);
		scene.getStylesheets().add("TicTacToe.css");
		String[] work = readIniFile();
        int i = 0;
        for(String ss: work){
            if(i > 2){

            states[i-2] = ss;

            } i++;
        }
        fieldLength = Integer.valueOf(work[0]);
        fieldWidth = Integer.valueOf(work[1]);

		setField(fieldLength,fieldWidth);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	// Schrijven van poort en ip adres naar ini file. Als file niet bestaat, nieuwe file maken.
	/*
	length=lengte van bord
	width=breedte van bord
	player_one=teken speler 1 (bv x)
	player_two=teken speler 2 (bv O)
	path=bestandnaam (bv TicTacToe.ini)
	 */
	private void createIniFile(int length, int width, String player_one, String player_two, String path) throws IOException {
		File inioutfile = new File(path);
		if (!inioutfile.exists()) {
			inioutfile.createNewFile();
		}
		Wini ini = new Wini(new File(inioutfile.getAbsolutePath()));

		ini.put("board", "length", length);
		ini.put("board", "width", width);
		ini.put("state", "empty", " 0");
		ini.put("state", "player_one", player_one);
		ini.put("state", "player_two", player_two);
		ini.store();
	}

	// Ini file uitlezen. Als file niet bestaat, nieuwe write met lege waarden.
	private String[] readIniFile() throws IOException {
		File inioutfile = new File("TicTacToe.ini");
		if (inioutfile.exists()) {
			ini = new Wini(new File(inioutfile.getAbsolutePath()));
			String length = ini.get("board", "length", Integer.class).toString();
			String width = ini.get("board", "width", Integer.class).toString();
			String state0 = ini.get("state", "empty", String.class);
			String state1 = ini.get("state", "player_one", String.class);
			String state2 = ini.get("state", "player_two", String.class);

			String[] s = {length, width, state0, state1, state2};
			return s;
		}
		return null;
	}
}
