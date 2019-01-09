package ru.AnnaK.srp6.handlers;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.AnnaK.srp6.OperationsSRP6;
import ru.AnnaK.srp6.dataModel.TransferData;
import ru.AnnaK.srp6.dataModel.UserContext;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = Logger.getLogger(ServerHandler.class.getName());
    private final Map<String, UserContext> clients = new HashMap<>();
    private OperationsSRP6 operationsSRP6 = new OperationsSRP6();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object o){
        if (o instanceof TransferData){
            TransferData client = (TransferData) o;
            String name = client.getName();

            if ( ! name.isEmpty()){
                if ( ! clients.containsKey(name)){
                    clients.put(name, new UserContext(name, client.getS(), client.getV(), client.getN(), client.getG()));
                }

                UserContext userContext = clients.get(name);
                if (client.getA() != 0){
                    log.info("A != 0");
                    userContext.setA(client.getA());

                    userContext.setServerB(operationsSRP6.generateRandomNumber());
                    Double B = operationsSRP6.generateB(userContext.getV(), userContext.getG(), userContext.getB(), userContext.getN());
                    userContext.setB(B);
                    client.addB(B);


                } else {
                    log.info("Error: A = 0!");
                }

            } else return;



                //ctx.writeAndFlush()


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
