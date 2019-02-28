import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{

    private final int serverPort;
    private static ArrayList<ServerWorker> workerList = new ArrayList<>();

    public Server(int serverPort, ArrayList<ServerWorker> workerList) {
        this.serverPort = serverPort;
        this.workerList = workerList;

    }

    public ArrayList<ServerWorker> getWorkerList() {

        return workerList;
    }

    public void setWorkerList(ArrayList<ServerWorker> workerList) {
        this.workerList =
        this.workerList = workerList;
        //return  workerList;
    }

    public void run() {
        try {
            ServerSocket ss = new ServerSocket(serverPort);
            while (true) {
                System.out.println("Accepting connection...");
                Socket clientSocket = ss.accept();

                System.out.println("Accepted connection from port " + clientSocket);
                ServerWorker serverWorker;
                workerList.add(serverWorker = new ServerWorker(this,clientSocket));

                serverWorker.start();
                for (ServerWorker test: workerList) {
                    System.out.println("hey "+ test.getLogin());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
