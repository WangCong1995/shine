package com.bow.demo;

import com.bow.remoting.netty.NettyServer;
import com.bow.rpc.Request;
import com.bow.rpc.RequestHandler;
import com.bow.rpc.Response;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author vv
 * @since 2016/10/3.
 */
public class NettyServerTest {

    @Test
    public void start() throws InterruptedException {
        NettyServer server = new NettyServer();
        server.setRequestHandler(new RequestHandler() {
            @Override
            public Response handle(Request message) {
                System.out.println(message.getId()+"--"+message);
                Response result = new Response(message.getId());
                result.setValue(3);
                return result;
            }
        });
        server.start();
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
//        server.stop();

    }
}
