import java.util.ArrayList;

public class ServerMain {
    public static ArrayList<ServerWorker> workerList;

    public static void main(String[] args) {
        int port = 1337;
        Server server = new Server(port, workerList);

        server.run();
    }

    public ArrayList<ServerWorker> getWorkerListMain() {
        return workerList;
    }

    public static void setWorkerListMain(ArrayList<ServerWorker> workerList) {
        ServerMain.workerList = workerList;
    }
}



