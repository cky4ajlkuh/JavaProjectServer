import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import java.util.LinkedList;

public class MyServer {

    public static final int PORT = 9999;
    public static LinkedList<MyServerRun> serverList = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("Server Started");
        try {
            while (true) {
                Socket socket = server.accept();
                try {
                    serverList.add(new MyServerRun(socket));
                } catch (IOException e) {
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}

class MyServerRun extends Thread {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    MyServerRun(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
        // new readArray();
        //send(new char[]{'0','0','X'});
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()){
                char[] str = in.readLine().toCharArray();
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {
        }
    }

    private void send(char[] array) {
        try {
            String str = array[0] + " " + array[1] + " " +array[2];
            out.write( str + "\n");
            out.flush();
        } catch (IOException ignored) {
        }
    }
}
