package Framework;

import Game.CommandCenter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
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

import java.util.ArrayList;
import java.util.Map;


public class Framework {
	private Wini ini;
	private String game;
	protected int[][] field;
	private int numberofstates = 3;
	private int tileWidth = 90;
	private int tileHeight = 90;
	private int fieldLength;
	private String lastIp;
	private Integer lastPort;
	private int fieldWidth;
	private String fieldColor;
	protected String[] states = new String[100];
	private CommandCenter Jack;

	private Boolean myTurn = false;

	ArrayList<StackPane> stackPanes = new ArrayList<StackPane>();
	GridPane gridpane = new GridPane();



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








	// Schrijven van poort en ip adres naar ini file. Als file niet bestaat, nieuwe file maken.
	/*
	length=lengte van bord
	width=breedte van bord
	player_one=teken speler 1 (bv x)
	player_two=teken speler 2 (bv O)
	path=bestandnaam (bv TicTacToe.ini)
	 */
	private void createIniFile(int length, int width, String player_one, String player_two, String path, String color) throws IOException {
		File inioutfile = new File(path);
		if (!inioutfile.exists()) {
			inioutfile.createNewFile();
		}
		Wini ini = new Wini(new File(inioutfile.getAbsolutePath()));
		ini.put("board", "color", color);
		ini.put("board", "length", length);
		ini.put("board", "width", width);
		ini.put("state", "empty", " 0");
		ini.put("state", "player_one", player_one);
		ini.put("state", "player_two", player_two);
		ini.store();
	}

	// Ini file uitlezen. Als file niet bestaat, nieuwe write met lege waarden.
	private String[] readIniFile() throws IOException {
		File inioutfile = new File(game + ".ini");
		if (inioutfile.exists()) {
			ini = new Wini(new File(inioutfile.getAbsolutePath()));
			String color = ini.get("board", "color", String.class);
			String length = ini.get("board", "length", Integer.class).toString();
			String width = ini.get("board", "width", Integer.class).toString();
			String state0 = ini.get("state", "empty", String.class);
			String state1 = ini.get("state", "player_one", String.class);
			String state2 = ini.get("state", "player_two", String.class);

			String[] s = {color, length, width, state0, state1, state2};
			return s;
		}
		return null;
	}

}
