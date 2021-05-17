import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;

import java.net.*;
import java.io.*;
import java.util.LinkedList;

public class MyServer {

    public static final int PORT = 9999;
    public static LinkedList<MyServerRun> serverList = new LinkedList<>();
    public static int random_number = (int) (Math.random() * 2);
    public static LinkedList<Element> elements = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("Рандомный номер получается " + random_number);
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
            if (random_number == 0) {
                serverList.get(0).sendWho(1);
                serverList.get(1).sendWho(0);
                while (!MyServerRun.socket.isClosed()) {
                    serverList.get(0).run();
                    serverList.get(1).send();
                    serverList.get(1).run();
                    serverList.get(0).send();
                }
            }
            if (random_number == 1) {
                serverList.get(0).sendWho(0);
                serverList.get(1).sendWho(1);
                while (!MyServerRun.socket.isClosed()) {
                    serverList.get(1).run();
                    serverList.get(0).send();
                    serverList.get(0).run();
                    serverList.get(1).send();
                }
            }
        } finally {
            server.close();
        }
    }
}

class MyServerRun {
    public static Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;

    MyServerRun(Socket socket) throws IOException, InterruptedException {
        MyServerRun.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void run() {
        try {
            String str = in.readLine();
            StringReader reader = new StringReader(str);
            ObjectMapper mapper = new ObjectMapper();
            MyServer.elements.add(mapper.readValue(reader, Element.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendWho(int p) throws IOException {

        XStream xmlWriter = new XStream();
        String xmlString = xmlWriter.toXML(p + "");
        out.write(xmlString + '\n');
        out.flush();
    }

    public void send() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            StringWriter stringWriter = new StringWriter();
            mapper.writeValue(stringWriter, MyServer.elements.getLast());
            out.write(String.valueOf(stringWriter) + '\n');
            out.flush();
        } catch (IOException ignored) {
        }
    }
}
