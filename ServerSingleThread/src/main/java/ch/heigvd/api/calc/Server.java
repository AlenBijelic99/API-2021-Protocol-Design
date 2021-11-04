package ch.heigvd.api.calc;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calculator server implementation - single threaded
 */
public class Server {

    private final static Logger LOG = Logger.getLogger(Server.class.getName());

    private final int TEST_DURATION = 5000;
    private final int PAUSE_DURATION = 1000;
    private final int NUMBER_OF_ITERATIONS = TEST_DURATION / PAUSE_DURATION;
    private final int LISTEN_PORT = 9999;

    /**
     * Main function to start the server
     */
    public static void main(String[] args) throws IOException {
        // Log output on a single line
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

        (new Server()).start();
    }

    /**
     * Start the server on a listening socket.
     */
    private void start() throws IOException {
        /* TODO: implement the receptionist server here.
         *  The receptionist just creates a server socket and accepts new client connections.
         *  For a new client connection, the actual work is done by the handleClient method below.
         */
        LOG.info("Starting server...");

        ServerSocket serverSocket;
        Socket clientSocket;

        LOG.log(Level.INFO, "Creating a server socket and binding it on any of the available network interfaces and on port {0}", new Object[]{Integer.toString(LISTEN_PORT)});
        serverSocket = new ServerSocket(LISTEN_PORT);
        clientSocket = serverSocket.accept();

        handleClient(clientSocket);

        clientSocket.close();

    }

    /**
     * Handle a single client connection: receive commands and send back the result.
     *
     * @param clientSocket with the connection with the individual client.
     */
    private void handleClient(Socket clientSocket) throws IOException {

        BufferedReader reader;
        BufferedWriter writer;

        LOG.log(Level.INFO, "Getting a Reader and a Writer connected to the client socket...");
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
        writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));

        LOG.log(Level.INFO, "Starting my job... sending current time to the client for {0} ms", TEST_DURATION);
        writer.write("Vous pouvez effectuer les opérations suivantes +, -, *, /, %, ^ entre deux nombres.");
        while (true) {
            String input = reader.readLine();
            if(input.isEmpty()){
                writer.write("Vous pouvez effectuer les opérations suivantes +, -, *, /, %, ^ entre deux nombres.");
                writer.flush();
            }
            if(input.equals("bye")){
                break;
            }
            input = input.trim();
            String [] newInput = input.split("\\s+");
            int a = Integer.parseInt(newInput[0]);
            int b = Integer.parseInt(newInput[2]);
            switch (newInput[1]){
                case "+":
                    writer.write(a + b);
                    writer.flush();
                    break;
                case "-":
                    writer.write(a - b);
                    writer.flush();
                    break;
                case "*":
                    writer.write(a * b);
                    writer.flush();
                    break;
                case "/":
                    if(b != 0){
                        writer.write(a / b);

                    }else{
                        writer.write("Undefined");
                    }
                    writer.flush();
                    break;
                case "%":
                    if(b != 0){
                        writer.write(a % b);

                    }else{
                        writer.write("Undefined");
                    }
                    writer.flush();
                    break;
                case "^":
                    writer.write((int) Math.pow(a,b));
                    writer.flush();
                    break;
            }

        }
        writer.close();
        reader.close();


        /* TODO: implement the handling of a client connection according to the specification.
         *   The server has to do the following:
         *   - initialize the dialog according to the specification (for example send the list
         *     of possible commands)
         *   - In a loop:
         *     - Read a message from the input stream (using BufferedReader.readLine)
         *     - Handle the message
         *     - Send to result to the client
         */

    }
}