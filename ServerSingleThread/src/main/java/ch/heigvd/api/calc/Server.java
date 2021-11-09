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
    private final int LISTEN_PORT = 2424;

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
    private void start() {

        LOG.info("Starting server...");

        ServerSocket serverSocket;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(LISTEN_PORT);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.toString(), e);
            return;
        }

        while (true) {
            try {
                LOG.log(Level.INFO, "Waiting for client");
                clientSocket = serverSocket.accept();

                handleClient(clientSocket);
            } catch (IOException e) {
                LOG.log(Level.SEVERE, e.toString(), e);
                return;
            } finally {
                try {
                    if (clientSocket != null && !clientSocket.isClosed()) {
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    LOG.log(Level.SEVERE, e.toString(), e);
                }
            }
        }
    }

    /**
     * Handle a single client connection: receive commands and send back the result.
     *
     * @param clientSocket with the connection with the individual client.
     */
    private void handleClient(Socket clientSocket) {

        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            LOG.log(Level.INFO, "Getting a Reader and a Writer connected to the client socket...");
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));

            LOG.log(Level.INFO, "Starting my job... sending current time to the client for {0} ms", TEST_DURATION);
            writer.write("Vous pouvez effectuer les opérations suivantes +, -, *, /, %, ^ entre deux nombres.");
            writer.flush();
            while (true) {
                String input = reader.readLine();
                if (input.isEmpty()) {
                    writer.write("Vous pouvez effectuer les opérations suivantes +, -, *, /, %, ^ entre deux nombres.");
                    writer.flush();
                }
                if (input.equals("Bye")) {
                    break;
                }
                input = input.trim();
                String[] newInput = input.split("\\s+");
                int a = Integer.parseInt(newInput[0]);
                int b = Integer.parseInt(newInput[2]);
                switch (newInput[1]) {
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
                        if (b != 0) {
                            writer.write(a / b);

                        } else {
                            writer.write("Undefined");
                        }
                        writer.flush();
                        break;
                    case "%":
                        if (b != 0) {
                            writer.write(a % b);

                        } else {
                            writer.write("Undefined");
                        }
                        writer.flush();
                        break;
                    case "^":
                        writer.write((int) Math.pow(a, b));
                        writer.flush();
                        break;
                }
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.toString(), e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                LOG.log(Level.SEVERE, e.toString(), e);
            }
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                LOG.log(Level.SEVERE, e.toString(), e);
            }
        }
    }
}