package org.examples.echo.single.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Lipatov Nikita
 */
public class SingleServerApplication {

    // telnet localhost 8077
    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8077;
        }

        ExecutorService es = Executors.newFixedThreadPool(1);
        es.submit(new SingleServer(port));

        es.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        es.shutdown();
    }


}
