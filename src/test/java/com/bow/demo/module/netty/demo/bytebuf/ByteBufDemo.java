package com.bow.demo.module.netty.demo.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

/**
 * @author vv
 * @since 2016/12/11.
 */
public class ByteBufDemo {


    @Test
    public void declare(){
        ByteBuf heapBuffer = Unpooled.buffer();
        System.out.println(heapBuffer);

        ByteBuf directBuffer = Unpooled.directBuffer();
        System.out.println(directBuffer);

        ByteBuf wrappedBuffer = Unpooled.wrappedBuffer(new byte[256]);
        System.out.println(wrappedBuffer);

        ByteBuf copiedBuffer = Unpooled.copiedBuffer(new byte[256]);
        System.out.println(copiedBuffer);
    }

    @Test
    public void usage(){
        ByteBuf heapBuffer = Unpooled.buffer(8);
        heapBuffer.writeBytes("test".getBytes());
        byte[] b = new byte[heapBuffer.readableBytes()];
        heapBuffer.readBytes(b);
        String str = new String(b);
        System.out.println(str);
    }
}
