import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerWorker extends Thread {
    private final Socket clientSocket;
    private Server server;
    private String login = null;
    private OutputStream outputStream;
    private static ArrayList<ServerWorker> workerList;

    public ServerWorker(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            handleClient();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleClient() throws IOException, InterruptedException {
        InputStream inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;

        while((line = reader.readLine()) != null) {
            String[] tokens = line.split(" ");
            if(tokens != null && tokens.length>0) {
                String cmd = tokens[0];
                if ("quit".equalsIgnoreCase(cmd)) {
                    break;
                }
                else if("login".equalsIgnoreCase(cmd)) {
                    handleLogin(outputStream, tokens);
                }
                else {
                    String msg = "Unknown command: " + cmd + "\r\n";
                    outputStream.write(msg.getBytes());
                }
            }
        }

        clientSocket.close();
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    private void handleLogin(OutputStream outputStream, String[] tokens) throws IOException {
        if(tokens.length==3) {
            String login = tokens[1];
            String password = tokens[2];

            if(login.equals("guest") && password.equals("guest") || login.equals("niklas") && password.equals("niklas")) {
                String msg = "ok login";

                outputStream.write(msg.getBytes());

                setLogin(login);

                System.out.println("User logged in succesfully: "+getLogin());
                workerList = server.getWorkerList();

                //send current user have come online
                for(ServerWorker worker: workerList) {
                    String nowOnline = "\r\n"+getLogin()+" has come online \r\n";
                    worker.send(nowOnline);
                }
                workerList.clear();
                workerList = server.getWorkerList();
                //All online users sent
                String currentlyOnline = "\r\nCurrently online:\r\n";
                for (ServerWorker worker: workerList) {

                    currentlyOnline += ""+worker.getLogin()+"\r\n";
                    worker.send(currentlyOnline);
                    System.out.println(worker.getLogin());
                }

            }
            else {
                String msg = "Error login";
                outputStream.write(msg.getBytes());
            }
        }
    }

    private void send(String msg) throws IOException {
        outputStream.write(msg.getBytes());
    }
}
