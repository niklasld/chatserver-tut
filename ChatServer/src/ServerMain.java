import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerMain {
    public static void main(String[] args) {
        int port = 1337;
        try {
            ServerSocket ss = new ServerSocket(port);
            while (true) {
                System.out.println("About to accept connection...");
                Socket clientSocket = ss.accept();
                System.out.println("Accepted connection from port " + port);
                ServerWorker serverWorker = new ServerWorker(clientSocket);

                serverWorker.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



