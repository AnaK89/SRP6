package ru.AnaK.srp6.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import ru.AnaK.srp6.NettySerializer;
import ru.AnaK.srp6.OperationsSRP6;
import ru.AnaK.srp6.dataModel.TransferData;
import ru.AnaK.srp6.dataModel.UserContext;

import java.io.IOException;
import java.math.BigInteger;
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
        log.info("read");
        TransferData transferData = nettySerializer.deserializeObject(o);
        if ("Client connect...".equals(transferData.getMessage())){
            transferData.setMessage("Connection is active");
            log.info("Connection is active");
            nettySerializer.writeToChannel(ctx, transferData);
            log.info("Server wrote message");
        }

        if("ServerR equals clientR".equals(transferData.getMessage())){
            log.info("ServerR equals clientR"
                    + "-------------- Authentication success --------------");
        }

        if ( ! transferData.getStatus().isEmpty()){
            if ( ! clients.containsKey(transferData.getName())){
                registration(ctx, transferData);
            }
            authentication(ctx, transferData);
        }

        //String received = inBuffer.toString(CharsetUtil.UTF_8);
        //ctx.write(Unpooled.copiedBuffer("Hello " + received, CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }

    private void registration(ChannelHandlerContext ctx, TransferData transferData){
        log.info("-------------- Registration start --------------");
        clients.put(transferData.getName(), new UserContext(transferData.getName(), transferData.getSs(), transferData.getV(), transferData.getN(), transferData.getG()));
        UserContext userContext = clients.get(transferData.getName());
        log.info("Received:\nName: " + transferData.getName()
                + "\nS: " + userContext.getSs()
                + "\nV: " + userContext.getV()
                + "\ng: " + userContext.getG()
                + "\nN: " + userContext.getN());
        if ( (! userContext.getStatus().contains("A")) && (transferData.getStatus().contains("A"))){
            if ( ! (transferData.getA().equals(BigInteger.valueOf(0)))){
                userContext.setA(transferData.getA());
                log.info("Server received A: " + userContext.getA());
                userContext.setServerB(operationsSRP6.generateRandomNumber());
                BigInteger B = operationsSRP6.generateB(userContext.getV(), userContext.getG(), userContext.getServerB(), userContext.getN());
                userContext.setB(B);
                transferData.addB(B);
                nettySerializer.writeToChannel(ctx, transferData);
                log.info("Server generated b: " + userContext.getServerB()
                        + "\nServer generated B: " + userContext.getB()
                        + "\nServer wrote transferData");
                BigInteger u = operationsSRP6.hashFunction(userContext.getA(), userContext.getB());
                if ( ! ( u.equals(BigInteger.valueOf(0)))){
                    BigInteger S = operationsSRP6.serverCalculateS(userContext.getA(), userContext.getV(), u, userContext.getServerB(), userContext.getN());
                    userContext.setS(S);
                    BigInteger K = operationsSRP6.hashFunction(S);
                    userContext.setK(K);
                    log.info("Server generated u = "+ u
                            + "\nServer generated S: " + S
                            + "\nServer generated K: " + K
                            + "\n-------------- Registration end --------------");
                } else {
                    log.info("Error u == 0");
                }
            }else {
                log.info("Error\nServer received A == 0");
            }
        }
    }

    private void authentication(ChannelHandlerContext ctx, TransferData transferData){
        log.info("-------------- Authentication start --------------");
        UserContext user = clients.get(transferData.getName());
        BigInteger M = operationsSRP6.calculateM(user.getN(), user.getG(), user.getName(), user.getS(), user.getA(), user.getB());
        log.info("Server calculated M: " + M);
        if (M.equals(transferData.getClientM())){
            log.info("ClientM equals with serverM");
            transferData = new TransferData();
            transferData.addServerR(operationsSRP6.calculateR(user.getA(), M));
            nettySerializer.writeToChannel(ctx, transferData);
        } else {
            log.info("ClientM NOT equals with serverM");
            TransferData trD = new TransferData();
            trD.setMessage("ClientM NOT equals with serverM");
            nettySerializer.writeToChannel(ctx, trD);
        }
    }
}
