package org.examples.echo.threads.client.sync;

import java.io.IOException;

/**
 * SyncThreadsClientApplication
 * @author Lipatov Nikita
 */
public class SyncTCApplication {

    // telnet localhost 8075
    public static void main(String[] args) throws IOException {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8075;
        }

        new SyncTClient("localhost", port).run();
    }
}
