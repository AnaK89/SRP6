package ru.AnnaK.srp6.handlers;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import ru.AnnaK.srp6.OperationsSRP6;
import ru.AnnaK.srp6.NettySerializer;
import ru.AnnaK.srp6.dataModel.TransferData;
import ru.AnnaK.srp6.dataModel.UserContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = Logger.getLogger(ServerHandler.class.getName());
    private final Map<String, UserContext> clients = new HashMap<>();
    private OperationsSRP6 operationsSRP6 = new OperationsSRP6();
    private final NettySerializer nettySerializer = new NettySerializer();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object o) throws IOException, ClassNotFoundException {
        TransferData transferData = nettySerializer.deserializeObject(o);
        if ( ! transferData.getStatus().toString().isEmpty()){
            String name = transferData.getName();
            if ( ! clients.containsKey(name)){
                clients.put(name, new UserContext(name, transferData.getSs(), transferData.getV(), transferData.getN(), transferData.getG()));
            }

            UserContext userContext = clients.get(name);
            log.info("Received:\nName: " + name
                    + "\nS: " + userContext.getSs()
                    + "\nV: " + userContext.getV()
                    + "\ng: " + userContext.getG()
                    + "\nN: " + userContext.getN());
            if ( (! userContext.getStatus().contains("A")) && (transferData.getStatus().toString().contains("A"))){
                if (transferData.getA() == 0){
                    log.info("Error\nServer received A == 0");
                }else {
                    userContext.setA(transferData.getA());
                    log.info("Server received A: " + userContext.getA());
                    userContext.setServerB(operationsSRP6.generateRandomNumber());
                    log.info("Server generated b: " + userContext.getServerB());
                    Double B = operationsSRP6.generateB(userContext.getV(), userContext.getG(), userContext.getServerB(), userContext.getN());
                    userContext.setB(B);
                    log.info("Server generated B: " + userContext.getB());
                    transferData.addB(B);
                    nettySerializer.writeToChannel(ctx, transferData);
                    log.info("Server wrote transferData");
                }
            }




        } else {
            log.info("Serializer fall");
        }

        //String received = inBuffer.toString(CharsetUtil.UTF_8);
        //ctx.write(Unpooled.copiedBuffer("Hello " + received, CharsetUtil.UTF_8));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
