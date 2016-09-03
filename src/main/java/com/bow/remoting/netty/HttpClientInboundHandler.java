package com.bow.remoting.netty;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpVersion;

public class HttpClientInboundHandler extends ChannelInboundHandlerAdapter
{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
        throws Exception
    {
        if (msg instanceof HttpResponse)
        {
            HttpResponse response = (HttpResponse)msg;
            System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaders.Names.CONTENT_TYPE));
        }
        if (msg instanceof HttpContent)
        {
            HttpContent content = (HttpContent)msg;
            ByteBuf buf = content.content();
            System.out.println(buf.toString(io.netty.util.CharsetUtil.UTF_8));
            buf.release();
        }
    }
    
    /**
     * tcp 连接建立时就会触发 {@inheritDoc}
     * 
     * @throws URISyntaxException
     * @throws UnsupportedEncodingException
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx)
        throws URISyntaxException, UnsupportedEncodingException
    {
        System.out.println(">> tcp connected...");
        URI uri = new URI("http://127.0.0.1:8844");
        String msg = "Are you ok?";
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
            uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));
            
        // 构建http请求
        // request.headers().set(HttpHeaders.Names.HOST, host);
        request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
        ctx.write(request);
        ctx.flush();
    }
}
