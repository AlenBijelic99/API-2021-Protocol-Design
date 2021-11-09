package ch.heigvd.api.calc;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calculator worker implementation
 */
public class ServerWorker implements Runnable {

    private final static Logger LOG = Logger.getLogger(ServerWorker.class.getName());
    private Socket clientSocket;
    private BufferedReader reader = null;
    private BufferedWriter writer = null;

    /**
     * Instantiation of a new worker mapped to a socket
     *
     * @param clientSocket connected to worker
     */
    public ServerWorker(Socket clientSocket){
        // Log output on a single line
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

        try {
            this.clientSocket = clientSocket;
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.toString(), e);
        }
    }

    /**
     * Run method of the thread.
     */
    @Override
    public void run() {

        try {
            writer.write("Vous pouvez effectuer les opérations suivantes +, -, *, /, %, ^ entre deux nombres.");
            writer.flush();

            while (!clientSocket.isClosed()) {
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
                clientSocket.close();
                writer.close();
                reader.close();
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.toString(), e);
        }

    }
}