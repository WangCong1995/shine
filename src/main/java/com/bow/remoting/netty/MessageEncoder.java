package com.bow.remoting.netty;

import com.bow.rpc.Message;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.InvalidParameterException;
import java.util.List;

/**
 * netty4
 * Created by vv on 2016/9/1.
 */
public class MessageEncoder extends MessageToMessageEncoder {

    public MessageEncoder() {
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
        if(msg instanceof Message) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            try {
                ObjectOutputStream os = new ObjectOutputStream(bos);
                os.writeObject(msg);
                byte[] bytes = bos.toByteArray();
                out.add(Unpooled.wrappedBuffer(bytes));
            } catch (IOException e) {
                throw new InvalidParameterException("param can not be serialized");
            }
        }
    }
}