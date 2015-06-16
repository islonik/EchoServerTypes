package org.examples.echo.threads.client.async;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AsyncThreadsClient
 * @author Lipatov Nikita
 */
public class AsyncTClient {

    private AsyncClientConnection asyncClientConnection;

    public AsyncTClient(String host, int port) {
        this.asyncClientConnection = new AsyncClientConnection(host, port);

        final ExecutorService executorService = Executors.newFixedThreadPool(1);
        // loop
        executorService.execute(this.asyncClientConnection);
    }

    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Starting AsyncTClient...");
            while (true) {
                String inputText = input.readLine().toLowerCase();

                this.asyncClientConnection.flush(inputText);

                if ("bye".equals(inputText.toLowerCase())) {
                    break;
                }
            }
        } catch(IOException error) {
            System.err.println(error.getMessage());
        } finally {
            System.exit(1);
        }
    }
}
