import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server1 implements Runnable {
    private static Socket clientDialog;

    public Server1(Socket client) {
        Server1.clientDialog = client;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("hn");
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientDialog.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientDialog.getOutputStream()));
                char[] array = reader.readLine().toCharArray();
                System.out.println(array);
                System.out.println("a");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}