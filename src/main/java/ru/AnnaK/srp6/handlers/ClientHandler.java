package ru.AnnaK.srp6.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.AnnaK.srp6.OperationsSRP6;
import ru.AnnaK.srp6.NettySerializer;
import ru.AnnaK.srp6.dataModel.ClientData;
import ru.AnnaK.srp6.dataModel.TransferData;

import java.io.IOException;
import java.util.logging.Logger;

public class ClientHandler extends SimpleChannelInboundHandler {
    private static final Logger log = Logger.getLogger(ClientHandler.class.getName());
    private final ClientData data;
    private final OperationsSRP6 operationsSRP6 = new OperationsSRP6();
    private final NettySerializer nettySerializer = new NettySerializer();

    public ClientHandler(final String name, final String password, final String s, final Double g, final Integer N){
        data = new ClientData(name, password, s, g, N);
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext){
        start(channelHandlerContext);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws IOException, ClassNotFoundException {
        TransferData transferData = nettySerializer.deserializeObject(o);
        if ( ! transferData.getStatus().toString().isEmpty()){
            if (( ! data.getStatus().contains("B")) && (transferData.getStatus().toString().contains("B"))){
                if (transferData.getB() == 0){
                    log.info("Error\nServer received B == 0");
                }else{
                    data.setB(transferData.getB());
                    log.info("Client received B: " + data.getB());
                }
            }
        } else {
            log.info("Serializer fall");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause){
        cause.printStackTrace();
        channelHandlerContext.close();
    }

    private void start(ChannelHandlerContext ctx){
        data.setX(operationsSRP6.hashFunction(data.getSs(), data.getPassword()));
        log.info("Client generated x: " + data.getX());
        data.setV(operationsSRP6.generateV(data.getG(), data.getX(), data.getN()));
        log.info("Client generated V: " + data.getV());

        TransferData transferData = new TransferData();
        transferData.addName(data.getName())
                .addSs(data.getSs())
                .addV(data.getV())
                .addN(data.getN())
                .addG(data.getG());
        //nettySerializer.writeToChannel(ctx, transferData);

        data.setClientA(operationsSRP6.generateRandomNumber());
        log.info("Client generated a: " + data.getClientA());
        Double A = operationsSRP6.generateA(transferData.getG(), data.getClientA(), data.getN());
        data.setA(A);
        log.info("Client generated A: " + data.getA());
        transferData.addA(A);
        nettySerializer.writeToChannel(ctx, transferData);
        log.info("Client wrote transferData");
    }
}