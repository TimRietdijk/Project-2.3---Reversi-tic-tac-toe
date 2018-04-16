package game; /**
 * Deze class zet de verbinding op met de server
 * en regelt alle mogelijke inkomende en uitgaande commando's
 */

import javafx.stage.Stage;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;


public class CommandCenter {
    private String name;
    private String lastIp;
    private Integer lastPort;
    static Socket s;
    private Scanner sc1;

    public CommandCenter() throws IOException {
        File inioutfile = new File("test.ini");
       if (inioutfile.exists()) {
            Wini ini = new Wini(new File(inioutfile.getAbsolutePath()));
            lastIp = ini.get("connection", "server ip", String.class);
            String parse = ini.get("connection", "server port", String.class);
            lastPort = Integer.valueOf(parse);
            System.out.println(lastIp + lastPort);
        }else {
           lastIp = "145.33.255.170";
           lastPort = 7789;
           System.out.println("het werkt niet");
       }

        setupConnection(lastIp, lastPort);

        consoleCommandTyping();
        //doChallenge("Kaaas", "Tic-tac-toe");
        sc1 = new Scanner(s.getInputStream());
        ReadReceived();
        Stage stage = new Stage();
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
        System.out.println(sendingCommand);
        p.println(sendingCommand);
    }

    // Ontvangst commandos van server
    public String ReadReceived() {
                // receivedCommand houdt het ontvangen command van de server
                String receivedCommand;
                try {
                    receivedCommand = sc1.nextLine();
                } catch (IndexOutOfBoundsException iob) {
                    return null;
                }
                    System.out.println("dit is de volgende lijn: "+receivedCommand);
                    return receivedCommand;
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
                while (true) {
                    sendingText = sc.nextLine();
                    PrintStream p = null;
                    try {
                        p = new PrintStream(s.getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    p.println(sendingText);

                }
            }

            ;
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
    public void doMove(int move) throws IOException {
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

    // Functie om inkomende commando's af te handelen in een aparte thread
   public String commandHandling(String command, String name) {
       System.out.println(name);
        if (command.contains("GAME MOVE" )&& !command.contains(name)) {
            StringBuilder build = new StringBuilder();
            int length = command.length();
            for (int i = 0; i < length; i++) {
                Character character = command.charAt(i);
                if (Character.isDigit(character)) {
                    build.append(character);
                }
            }
            String parse = build.toString();
            int s = Integer.valueOf(parse);
            return parse;

        } else if (command.contains("YOURTURN")) {
            // Het is jouw beurt
            System.out.println("YOURTURN detected");
            // Move maken binnen tien seconden



            new Thread(new Runnable() {
                @Override
                public void run() {
                    int remainingTime = 10;
                    long timeout = System.currentTimeMillis() + (remainingTime * 1000);
                    while (System.currentTimeMillis() < timeout) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //System.out.println("You have : " + (timeout - System.currentTimeMillis()) / 1000 + " seconds left");
                    }
                }
            }).start();




        } else if (command.contains("SVR GAME CHALLENGE {")) {
            // Er is een challenge
            System.out.println("CHALLENGE detected");
            // Challenge accepteren/afwijzen dmv popup?

        } else if (command.contains("SVR PLAYERLIST [")) {

        } else if (command.contains("GAME")) {
            if (command.contains("WIN")) {
                // Gewonnen, doe een popup
            } else if (command.contains("LOSS")) {
                // Verloren, doe een popup
            } else if (command.contains("DRAW")) {
                // Gelijk gespeeld, doe een popup
            }
        }
        return null;
    }
}