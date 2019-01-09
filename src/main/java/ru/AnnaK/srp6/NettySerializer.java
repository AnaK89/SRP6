package ru.AnnaK.srp6;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import ru.AnnaK.srp6.dataModel.TransferData;

import java.io.*;
import java.util.logging.Logger;

public class NettySerializer implements Serializable{
    private static final Logger log = Logger.getLogger(NettySerializer.class.getName());

    public void writeToChannel(ChannelHandlerContext ctx, TransferData transferData) {
        ByteBuf byteBuf = ctx.alloc().buffer();
        byteBuf.resetWriterIndex();
        try {
            byteBuf.writeBytes(serialize( transferData));
            ctx.writeAndFlush(byteBuf);
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TransferData deserializeObject (Object o) throws IOException, ClassNotFoundException {
        if (o instanceof ByteBuf){
            ByteBuf byteBuf = (ByteBuf) o;
            byteBuf.resetReaderIndex();
            byte byteArray[] = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(byteArray);

            Object obj = deserialize(byteArray);
            if (obj instanceof TransferData){
                TransferData transferData =  (TransferData) obj;
                return transferData;
            }
        }
        return new TransferData();
    }

    private byte[] serialize(final Object o) throws IOException{
        log.info("Object serialize..");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(o);
        return byteArrayOutputStream.toByteArray();
    }

    private Object deserialize(final byte[] bytes) throws IOException, ClassNotFoundException {
        log.info("Object deserialize..");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return objectInputStream.readObject();
    }


}
