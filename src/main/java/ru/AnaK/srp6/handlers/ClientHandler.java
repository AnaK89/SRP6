package ru.AnaK.srp6.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import ru.AnaK.srp6.dataModel.ClientData;
import ru.AnaK.srp6.OperationsSRP6;
import ru.AnaK.srp6.NettySerializer;
import ru.AnaK.srp6.dataModel.TransferData;

import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Logger;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = Logger.getLogger(ClientHandler.class.getName());
    private final ClientData data;
    private final OperationsSRP6 operationsSRP6 = new OperationsSRP6();
    private final NettySerializer nettySerializer = new NettySerializer();

    public ClientHandler(final String name, final String password, final String s, final BigInteger g, final BigInteger N){
        data = new ClientData(name, password, s, g, N);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        TransferData transferData = new TransferData();
        transferData.setMessage("Client connect...");
        log.info("Client connect...");
        nettySerializer.writeToChannel(ctx, transferData);
        log.info("Client wrote message");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object o) throws IOException, ClassNotFoundException {
        TransferData transferData = nettySerializer.deserializeObject(o);
        if ("ClientM NOT equals with serverM".equals(transferData.getMessage())){
            return;
        }
        if ("Connection is active".equals(transferData.getMessage())){
            log.info("Connection is active");
            transferData.setMessage("");
        }

        if (transferData.getStatus().isEmpty()){
            log.info("-------------- Registration start --------------");
            firstStageRegistration(ctx);
        } else if ( ! data.getStatus().contains("K")){
            secondStageRegistration(transferData);
            log.info("-------------- Registration end --------------");
            authentication(ctx, transferData);
        } else {
            authentication(ctx, transferData);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause){
        cause.printStackTrace();
        channelHandlerContext.close();
    }

    private void firstStageRegistration(ChannelHandlerContext ctx){
        data.setX(operationsSRP6.hashFunction(data.getSs(), data.getPassword()));
        data.setV(operationsSRP6.generateV(data.getG(), data.getX(), data.getN()));

        TransferData transferData = new TransferData();
        transferData.addName(data.getName())
                .addSs(data.getSs())
                .addV(data.getV())
                .addN(data.getN())
                .addG(data.getG());

        data.setClientA(operationsSRP6.generateRandomNumber());
        BigInteger A = operationsSRP6.generateA(transferData.getG(), data.getClientA(), data.getN());
        data.setA(A);
        transferData.addA(A);
        nettySerializer.writeToChannel(ctx, transferData);
        log.info("Client generated x: " + data.getX()
                + "\nClient generated V: " + data.getV()
                + "\nClient generated a: " + data.getClientA()
                + "\nClient generated A: " + data.getA()
                + "\nClient wrote transferData");
    }

    private void secondStageRegistration(TransferData transferData){
        if (( ! data.getStatus().contains("B")) && (transferData.getStatus().contains("B"))){
            if ( ! (transferData.getB().equals(BigInteger.valueOf(0)))){
                data.setB(transferData.getB());
                log.info("Client received B: " + data.getB());
                BigInteger u = operationsSRP6.hashFunction(data.getA(), data.getB());
                if ( ! ( u.equals(BigInteger.valueOf(0)))){
                    BigInteger S = operationsSRP6.clientCalculateS(data.getB(), data.getG(), data.getX(), data.getClientA(), u, data.getN());
                    data.setS(S);
                    BigInteger K = operationsSRP6.hashFunction(S);
                    data.setK(K);
                    log.info("Client generated u = " + u
                            + "\nClient generated S: " + S
                            + "\nClient generated K: " + K);
                } else {
                    log.info("Error u == 0");
                }
            }else{
                log.info("Error\nServer received B == 0");
            }
        }
    }

    private void authentication(ChannelHandlerContext ctx, TransferData transferData){
        log.info("-------------- Authentication start --------------");
        if ( ! transferData.getStatus().contains("M")){
            BigInteger M = operationsSRP6.calculateM(data.getN(), data.getG(), data.getName(), data.getS(), data.getA(), data.getB());
            transferData.addClientM(M);
            log.info("Client calculated M: " + M);
            nettySerializer.writeToChannel(ctx, transferData);
        } else {
            BigInteger R = operationsSRP6.calculateR(data.getA(), transferData.getClientM());
            if (R.equals(transferData.getServerR())){
                log.info("ServerR equals clientR"
                        + "-------------- Authentication success --------------");
                TransferData trD = new TransferData();
                trD.setMessage("ServerR equals clientR");
                nettySerializer.writeToChannel(ctx, trD);
            }
        }

    }
}