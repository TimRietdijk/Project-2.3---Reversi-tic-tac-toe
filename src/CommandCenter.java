/**
 * Deze class zet de verbinding op met de server
 * en regelt alle mogelijke inkomende en uitgaande commando's
 */

import javafx.stage.Stage;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;


public class CommandCenter extends Framework{

    private String lastIp;
    private Integer lastPort;
    static Socket s;

    public CommandCenter(Map<String, String> options) throws IOException {

        File inioutfile = new File("test.ini");
        if (inioutfile.exists()) {
            Wini ini = new Wini(new File(inioutfile.getAbsolutePath()));
            lastIp = ini.get("connection", "server ip", String.class);
            String parse = ini.get("connection", "server port", String.class);
            lastPort = Integer.valueOf(parse);
            System.out.println(lastIp + lastPort);
        }
        String L = options.get("name");
        setupConnection(lastIp, lastPort);
        doLogin(L);
        consoleCommandTyping();
        //doChallenge("Kaaas", "Tic-tac-toe");
        Scanner sc1 = new Scanner(s.getInputStream());
        ReadReceived(sc1);
        Stage stage = new Stage();
        try {
            super.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    -=Communicatie met server=-
     */

    // Connectie opzetten met server
    public void setupConnection(String host, int port) throws IOException {
        s = new Socket(host, port);
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

    /*
    -=Uitgaande commando's=-
     */

    // Vrij commando's uitvoeren in console
    public void consoleCommandTyping() {
        new Thread(new Runnable() {
            public void run() {
                String sendingText;
                Scanner sc = new Scanner(System.in);
                while(true) {
                    sendingText = sc.nextLine();
                    PrintStream p = null;
                    try {
                        p = new PrintStream(s.getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    p.println(sendingText);

                }
            };
        }).start();
    }

    // Commando om in te loggen op server
    public void doLogin(String player) throws IOException {
        System.out.println(player);
        sendCommand("login " + player);
    }

    // Commando om te uit te loggen van server
    public void doLogout() throws IOException {
        sendCommand("logout");
    }

    // Commando om game lijst op te vragen
    public void doGetGameList() throws IOException {
        sendCommand("get gamelist");
    }

    // Commando om speler lijst op te vragen
    public void doGetPlayerList() throws IOException {
        sendCommand("get playerlist");
    }

    // Commando om in te schrijven voor spel type
    public void doSubscribe(String gametype) throws IOException {
        sendCommand("subscribe " + gametype);
    }

    // Commando om zet te doen
    public void doMove(String move) throws IOException {
        sendCommand("move " + move);
    }

    // Commando om op te geven
    public void doForfeit() throws IOException {
        sendCommand("forfeit");
    }

    // Commando om speler uit te dagen voor spel
    public void doChallenge(String player, String gametype) throws IOException {
        sendCommand("challenge \"" + player + "\" \"" + gametype + "\"");
    }

    // Commando om ontvangen uitdaging te accepteren
    public void doChallengeAccept(String challengeNumber) throws IOException {
        sendCommand("challenge accept " + challengeNumber);
    }

    // Commando om hulp te vragen
    public void doHelp() throws IOException {
        sendCommand("help");
    }

    // Commando om hulp op te vragen bij specifiek commando
    public void doHelpCommand(String commandName) throws IOException {
        sendCommand("help " + commandName);
    }

    /*
    -=Inkomende commando's=-
     */
}
