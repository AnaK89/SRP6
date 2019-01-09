package ru.AnnaK.srp6;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import ru.AnnaK.srp6.handlers.ClientHandler;


import java.net.InetSocketAddress;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class Client {
    private static final Logger log = Logger.getLogger(Client.class.getName());
    private final EventLoopGroup group = new NioEventLoopGroup();
    private final ClientHandler clientHandler;

    public Client(String name, String password, String S, double g, int N){
        clientHandler = new ClientHandler(name, password, S, g, N);

        log.info("Name: " + name
                + "\nPassword: " + password
                + "\nS: " + S
                + "\ng: " + g
                + "\nN: " + N);
    }

    public void run() throws InterruptedException{
        log.info("-------------------- start --------------------");
        Bootstrap clientBootstrap = new Bootstrap();
        clientBootstrap.group(group);
        clientBootstrap.channel(NioSocketChannel.class);
        clientBootstrap.remoteAddress(new InetSocketAddress("localhost", 8080));
        clientBootstrap.handler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel socketChannel){
                socketChannel.pipeline().addLast(clientHandler);
            }
        });

        ChannelFuture channelFuture = clientBootstrap.connect().sync();
        channelFuture.channel().closeFuture().sync();
        log.info("-------------------- complete --------------------");
        group.shutdownGracefully().sync();
    }
}
