package Lobby;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

public class ServerConnection {
    public void start() throws IOException {

        GridPane pane = new GridPane();

        // Titel
        Label label1 = new Label("Connect to a server");
        pane.add(label1, 0, 0);

        // Status
        Label label2 = new Label();
        pane.add(label2,0,4);

        TextField textField1 = new TextField();
        textField1.setPromptText("Server name");

        TextField textField2 = new TextField();
        textField2.setPromptText("Port number");

        // Waarden verkrijgen van laatste connectie
        String[] connectie = readIniFile();
        if (connectie != null) {
            textField1.setText(connectie[0]);
            textField2.setText(connectie[1]);
        }

        // Textfields toevoegen
        pane.add(textField1, 0, 1);
        pane.add(textField2, 0, 2);

        // Submit knop met schrijf functie naar ini
        Button submit = new Button("Submit");
        pane.add(submit, 0, 3);

        submit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                String ip = textField1.getText();
                String port = textField2.getText();
                //Schrijven van ip adres en poort naar ini bestand
                try {
                    writeIniFile(ip, port);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                label2.setText("Settings succesfully saved");
                label2.setTextFill(Color.GREEN);
            }
        });
        Stage primaryStage = new Stage();
        Scene scene = new Scene(pane, 200, 120);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Connection");
        primaryStage.setMinHeight(120);
        primaryStage.setMinWidth(200);
        primaryStage.show();
    }

    // Schrijven van poort en ip adres naar ini file. Als file niet bestaat, nieuwe file maken.
    private void writeIniFile(String ip, String port) throws IOException {
        File inioutfile = new File("test.ini");
        if (!inioutfile.exists()) {
            inioutfile.createNewFile();
        }
        Wini ini = new Wini(new File(inioutfile.getAbsolutePath()));

        ini.put("connection", "server ip", ip);
        ini.put("connection", "server port", port);
        ini.store();
    }

    // Ini file uitlezen. Als file niet bestaat, nieuwe write met lege waarden.
    private String[] readIniFile() throws IOException {
        File inioutfile = new File("test.ini");
        if (inioutfile.exists()) {
            Wini ini = new Wini(new File(inioutfile.getAbsolutePath()));
            String lastIp = ini.get("connection", "server ip", String.class);
            String lastPort = ini.get("connection", "server port", String.class);
            String[] connection = {lastIp, lastPort};
            return connection;
        } else {
            writeIniFile("", "");
        }
        return null;
    }
}