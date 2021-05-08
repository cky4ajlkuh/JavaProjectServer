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
            while (serverList.size() < 2) {
                Socket socket = server.accept();
                try {
                    serverList.add(new MyServerRun(socket));
                } catch (IOException | InterruptedException e) {
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}

class MyServerRun extends Thread {
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    int random_number = (int) (Math.random() * 2);
    char[] str;

    MyServerRun(Socket socket) throws IOException, InterruptedException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write(1);
        out.flush();
        start();
    }

    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                str = in.readLine().toCharArray();
                System.out.println(str);
                for (MyServerRun server : MyServer.serverList) {
                    server.send(str);
                }
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

    private void sendWho(int p) {
        try {
            out.write(p);
            out.flush();
        } catch (IOException ignored) {
        }
    }

    private void send(char[] array) {
        try {
            String str = array[0] + " " + array[2] + " " + array[4];
            out.write(str + "\n");
            out.flush();
        } catch (IOException ignored) {
        }
    }
}
