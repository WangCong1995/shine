package com.bow.demo.module.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * TODO 添加类的描述
 *
 * @author acer
 * @version C10 2016年2月28日
 * @since SDP V300R003C10
 */
public class NioServer
{
    private Selector selector;
    
//    private Charset charset = Charset.forName("utf-8");
    
    // private CharsetEncoder encoder = charset.newEncoder();
    //
    // private CharsetDecoder decoder = charset.newDecoder();
    
    private static final int PORT = 1099;
    
    private static final String ADDRESS = "localhost";
    public void init()
    {
        try
        {
            selector = Selector.open();
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.socket().bind(new InetSocketAddress(ADDRESS, PORT));
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("server started");
            while (true)
            {
                selector.select();
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext())
                {
                    SelectionKey selectionKey = it.next();
                    it.remove();
                    handleSelectionKey(selectionKey);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private void handleSelectionKey(SelectionKey selectionKey) throws IOException{
        if(selectionKey.isAcceptable()){
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectionKey.channel();
            SocketChannel socketChannel= serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            System.out.println("client connected");
        }
        else if (selectionKey.isReadable())
        {
            ByteBuffer buffer = ByteBuffer.allocate(100);
            SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
            socketChannel.read(buffer);
            
            buffer.flip();
            System.out.println("server recceived>>" + new String(buffer.array()));
            socketChannel.register(selector, SelectionKey.OP_WRITE);
        }
        else if (selectionKey.isWritable())
        {
            ByteBuffer buffer = ByteBuffer.allocate(100);
            buffer.put("server response".getBytes());
            SocketChannel channel = (SocketChannel)selectionKey.channel();
            buffer.flip();
            channel.write(buffer);
            if (buffer.remaining() == 0)
            {
                selectionKey.interestOps(SelectionKey.OP_READ);
            }
        }
    }
    
    public static void main(String[] args)
    {
        NioServer server = new NioServer();
        server.init();
    }

}
