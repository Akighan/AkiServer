package telegrambot.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class AndrClient extends Thread {
    private Socket socket;
    private BufferedReader in;

    public AndrClient (Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        start();
    }

    @Override
    public void run() {
        String word;
        try {

            while (true) {
                word = in.readLine();
                if(word.equals("stop")) {
                    break;                }
                for (AndrClient vr : Server.andrClients) {
                    vr.send(word); // отослать принятое сообщение с
                    // привязанного клиента всем остальным включая его
                }
            }

        } catch (IOException e) {
        }
    }
}
