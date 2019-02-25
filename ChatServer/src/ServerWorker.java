import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ServerWorker extends Thread {
    private static Socket clientSocket;

    public ServerWorker(Socket clientSocket) {
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
        OutputStream outputStream = clientSocket.getOutputStream();
        InputStream inputStream = clientSocket.getInputStream();
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

    private void handleLogin(OutputStream outputStream, String[] tokens) {
    }
}
