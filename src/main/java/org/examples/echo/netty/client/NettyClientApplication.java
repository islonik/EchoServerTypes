package org.examples.echo.netty.client;

/**
 * @author Lipatov Nikita
 * Example was took from official documentation.
 */
public class NettyClientApplication {

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8076;
        }

        new NettyClient("localhost", port).run();
    }
}
