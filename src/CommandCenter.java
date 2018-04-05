import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class CommandCenter {

    public CommandCenter() throws IOException {
        // sendingCommand houdt het command dat naar de server verstuurd wordt
        String sendingCommand;
        Socket s = new Socket("127.0.0.1", 80);
        Scanner sc1 = new Scanner(s.getInputStream());
        System.out.println("Send command to server");


        ReadReceived(sc1);
        while(true) {
            // sendingCommand afhankelijk van gedrukte knop door gebruiker
            sendingCommand = "login <speler>"; // Bijvoorbeeld login commando
            PrintStream p = new PrintStream(s.getOutputStream());
            p.println(sendingCommand);
        }
    }

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
