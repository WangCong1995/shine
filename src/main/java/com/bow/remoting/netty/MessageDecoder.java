package com.bow.remoting.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.jboss.netty.buffer.DynamicChannelBuffer;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.security.InvalidParameterException;
import java.util.List;

/**
 * Created by vv on 2016/9/1.
 */
public class MessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 0) {
            return;
        }
        byte[] data = new byte[in.readableBytes()];
        out.add(in.readBytes(data));
        in.readBytes(data);
    }
}
