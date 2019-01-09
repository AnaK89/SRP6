package ru.AnnaK.srp6.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import ru.AnnaK.srp6.OperationsSRP6;
import ru.AnnaK.srp6.dataModel.ClientData;
import ru.AnnaK.srp6.dataModel.TransferData;

import java.util.logging.Logger;

public class ClientHandler extends SimpleChannelInboundHandler {
    private static final Logger log = Logger.getLogger(ClientHandler.class.getName());
    private final ClientData data;
    private final OperationsSRP6 operationsSRP6 = new OperationsSRP6();

    public ClientHandler(final String name, final String password, final String S, final Double g, final Integer N){
        data = new ClientData(name, password, S, g, N);
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext){
        registration(channelHandlerContext);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) {
        if (ctx instanceof TransferData){
            TransferData transferData = (TransferData) o;

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause){
        cause.printStackTrace();
        channelHandlerContext.close();
    }

    private void registration(ChannelHandlerContext ctx){
        data.setX(operationsSRP6.hashFunction(data.getS(), data.getPassword()));
        log.info("Client generate x: " + data.getX());
        data.setV(operationsSRP6.generateV(data.getG(), data.getX(), data.getN()));
        log.info("Client generate V: " + data.getV());

        TransferData transferData = new TransferData();
        transferData.addName(data.getName())
                .addS(data.getS())
                .addV(data.getV())
                .addN(data.getN())
                .addG(data.getG());
        ctx.writeAndFlush(transferData);

        data.setClientA(operationsSRP6.generateRandomNumber());
        Double A = operationsSRP6.generateA(transferData.getG(), data.getClientA(), data.getN());
        data.setA(A);
        transferData.addA(A);
        ctx.writeAndFlush(transferData);
    }

}