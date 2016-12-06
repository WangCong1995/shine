package com.bow.demo.unit;

import com.bow.common.pipeline.DefaultShinePipeline;
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
                try {
                    //模拟处理比较耗时
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
