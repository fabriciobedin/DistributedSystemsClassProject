package br.upf.sd.tcpserver;

import br.upf.sd.clientsoap.Carro;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

/**
 */
public class JSONServer {


    private ServerSocket serverSocket;
    private int port;
    public static int clients = 0;


    public void establish(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
        System.out.println("JSONServer has been established on port " + port);

    }


    public void accept() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            Runnable r = new MyThreadHandler(socket);
            Thread t = new Thread(r);
            t.start();
        }
    }

    private static class MyThreadHandler implements Runnable {
        private Socket socket;

        MyThreadHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            clients++;
            System.out.println(clients + " JSONClient(s) connected on port: " + socket.getPort());

            try {
                // For JSON Protocol
                JSONObject jsonObject = receiveJSON();
                sendJSON(jsonObject);

            } catch (IOException e) {

            } catch (JSONException ex) {
                Logger.getLogger(JSONServer.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    closeSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void closeSocket() throws IOException {
            socket.close();
        }


        /**
         * use the JSON Protocol to receive a json object as
         * String from the client and reconstructs that object
         * @return JSONObejct with the same state (data) as
         * the JSONObject the client sent as a String msg.
         * @throws IOException
         */
        public JSONObject receiveJSON() throws IOException, JSONException {
            InputStream in = socket.getInputStream();
            ObjectInputStream i = new ObjectInputStream(in);
            JSONObject line = null;
            try {
                line = (JSONObject) i.readObject();

            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                 e.printStackTrace();

            }

            JSONObject jsonObject = new JSONObject(line);
            System.out.println("Got from client on port " + socket.getPort() + " " + jsonObject.get("key").toString());
            return jsonObject;
        }


        public void sendJSON(JSONObject jsonObject) throws IOException, JSONException {
               JSONObject jsonObject2 = new JSONObject();
             jsonObject2.put("key", new Carro());

             OutputStream out = socket.getOutputStream();
              ObjectOutputStream o = new ObjectOutputStream(out);
             o.writeObject(jsonObject2);
              out.flush();
              System.out.println("Sent to server: " + " " + jsonObject2.get("key").toString());
    }
    }

    public void start(int port) throws IOException{
        establish(port);
        accept();
    }

    public static void main(String[] args) {
        JSONServer server = new JSONServer();

        try {
            server.start(7777);

        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println(e);
        }
    }
}