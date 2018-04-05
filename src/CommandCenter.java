/**
 * Deze class zet de verbinding op met de server
 * en regelt alle mogelijke inkomende en uitgaande commando's
 */

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class CommandCenter {

    static Socket s;

    public CommandCenter() throws IOException {

        setupConnection("127.0.0.1", 80);
        Scanner sc1 = new Scanner(s.getInputStream());
        ReadReceived(sc1);
    }

    // Connectie opzetten met server
    public void setupConnection(String host, int port) throws IOException {
        Socket s = new Socket(host, port);
    }

    // Versturen van commandos naar server
    private static void sendCommand(String command) throws IOException {
        // sendingCommand houdt het command dat naar de server verstuurd wordt
        String sendingCommand = command; // Bijvoorbeeld login commando: "login <speler>"
        PrintStream p = new PrintStream(s.getOutputStream());
        p.println(sendingCommand);
    }

    // Ontvangst commandos van server
    private static void ReadReceived(Scanner sc1) {
        new Thread(new Runnable() {
            public void run(){
                // receivedCommand houdt het ontvangen command van de server
                String receivedCommand;
                while(true) {
                    receivedCommand = sc1.nextLine();
                    System.out.println(receivedCommand);
                }
            }
        }).start();
    }
}
