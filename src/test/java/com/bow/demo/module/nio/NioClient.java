package com.bow.demo.module.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * TODO 添加类的描述
 *
 * @author acer
 * @version C10 2016年2月28日
 * @since SDP V300R003C10
 */
public class NioClient
{
    private Selector selector;
    
    private static final String ADDRESS = "localhost";
    
    private static final int PORT = 1099;
    
    private void init()
    {
        try
        {
            // 创建selector,负责通知channel相应的事件发生了
            selector = Selector.open();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            // channel跟selector注册一个连接事件
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress(ADDRESS, PORT));
            while (true)
            {
                // 阻塞直到有注册的事件发生
                selector.select();
                // 有感兴趣的事件发生则，循环处理这些事件
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
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
    
    private void handleSelectionKey(SelectionKey selectionKey)
        throws IOException
    {
        if (selectionKey.isConnectable())
        {
            SocketChannel channel = (SocketChannel)selectionKey.channel();
            System.out.println("connecting to server...");
            
            if (channel.isConnectionPending())
            {
                System.out.println("connect pending...");
                channel.finishConnect();
            }
            
            ByteBuffer buffer = ByteBuffer.allocate(100);
            buffer.put("hello server,this is client".getBytes());
            buffer.flip();
            channel.write(buffer);
            // channel向selector注册一个可读事件
            channel.register(selector, SelectionKey.OP_READ);
        }
        else if (selectionKey.isReadable())
        {
            ByteBuffer buffer = ByteBuffer.allocate(100);
            SocketChannel channel = (SocketChannel)selectionKey.channel();
            channel.read(buffer);
            System.out.println("client reveived>>" + new String(buffer.array()));
        }
    }
    
    public static void main(String[] args)
    {
        NioClient client = new NioClient();
        client.init();
    }

}
