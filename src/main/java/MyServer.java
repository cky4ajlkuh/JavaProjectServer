import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;

import java.net.*;
import java.io.*;
import java.util.LinkedList;

public class MyServer {

    public static final int PORT = 9999;
    public static LinkedList<MyServerRun> serverList = new LinkedList<>();
    public static LinkedList<Element> elements = new LinkedList<>();
    public static boolean finish = true;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        try {
            while (serverList.size() < 2) {
                Socket socket = server.accept();
                try {
                    serverList.add(new MyServerRun(socket));
                } catch (IOException | InterruptedException e) {
                    socket.close();
                }
            }
            play();
            //replay();
        } catch (IOException e) {
            e.printStackTrace();
            server.close();
        }
    }

    public static void play() throws IOException {
        int random_number = (int) (Math.random() * 2);
        if (random_number == 0) {
            serverList.get(0).sendWho(1);
            serverList.get(1).sendWho(0);
            while (finish) {
                serverList.get(0).run();
                serverList.get(1).send();
                serverList.get(1).run();
                serverList.get(0).send();
            }
        }
        if (random_number == 1) {
            serverList.get(0).sendWho(0);
            serverList.get(1).sendWho(1);
            while (finish) {
                serverList.get(1).run();
                serverList.get(0).send();
                serverList.get(0).run();
                serverList.get(1).send();
            }
        }

    }

    public static void replay() throws IOException {
        while (!finish) {
            if (serverList.get(0).playAgain()) {
                if (serverList.get(1).playAgain()) {
                    play();
                }
            }
        }
    }

    public static void end(String str) {
        serverList.get(0).sendWin(str);
        serverList.get(1).sendWin(str);
    }

    public static void finish() {
        int[] array = new int[]{
                0, 3, 6, 0, 6, 3, 3, 0, 6, 3, 6, 0, 6, 0, 3, 6, 3, 0,
                1, 4, 7, 1, 7, 4, 4, 1, 7, 4, 7, 1, 7, 4, 1, 7, 1, 4,
                2, 5, 8, 2, 8, 5, 5, 8, 2, 5, 2, 8, 8, 5, 2, 8, 2, 5,
                0, 1, 2, 0, 2, 1, 1, 0, 2, 1, 2, 0, 2, 1, 0, 2, 0, 1,
                3, 4, 5, 3, 5, 4, 4, 3, 5, 4, 5, 3, 5, 3, 4, 5, 4, 3,
                6, 7, 8, 6, 8, 7, 7, 6, 8, 7, 8, 6, 8, 7, 6, 8, 6, 7,
                0, 4, 8, 0, 8, 4, 4, 0, 8, 4, 8, 0, 8, 0, 4, 8, 4, 0,
                2, 4, 6, 2, 6, 4, 4, 2, 6, 4, 6, 2, 6, 2, 4, 6, 4, 2};

        if (elements.size() >= 5) {
            for (int j = 0; j < elements.size(); j++) {
                for (int k = j + 1; k < elements.size(); k++) {
                    for (int l = k + 1; l < elements.size(); l++) {
                        for (int i = 0; i < array.length - 2; i++) {
                            if (elements.get(j).getNumber() == array[i]) {
                                if (elements.get(k).getNumber() == array[i + 1]) {
                                    if (elements.get(l).getNumber() == array[i + 2]) {
                                        if (elements.get(j).getValue() == 'X' && elements.get(k).getValue() == 'X' && elements.get(l).getValue() == 'X') {
                                            end("Крестики");
                                        }
                                        if (elements.get(j).getValue() == 'O' && elements.get(k).getValue() == 'O' && elements.get(l).getValue() == 'O') {
                                            end("Нолики");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (elements.size() == 9) {
            end("Дружба");
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
            if (MyServer.finish) {
                MyServer.finish();
                String str = in.readLine();
                if (str != null) {
                    StringReader reader = new StringReader(str);
                    ObjectMapper mapper = new ObjectMapper();
                    MyServer.elements.add(mapper.readValue(reader, Element.class));
                    if (str.equals("close")) {
                        socket.close();
                    }
                }
            }
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

    public void sendWin(String s) {
        try {
            MyServer.finish = false;
            out.write(s + '\n');
            out.flush();
        } catch (IOException ignored) {
        }
    }

    public boolean playAgain() {
        try {
            String str = in.readLine();
            if (str != null) {
                if (str.equals("replay")) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
