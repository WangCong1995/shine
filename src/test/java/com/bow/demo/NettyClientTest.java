package com.bow.demo;

import com.bow.remoting.netty.NettyClient;
import com.bow.rpc.Request;
import com.bow.rpc.Response;
import com.bow.rpc.URL;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author vv
 * @since 2016/10/3.
 */
public class NettyClientTest {

    @Test
    public void call() throws InterruptedException {
        URL url = new URL("127.0.0.1",9000);
        NettyClient client = new NettyClient(url);
        Request m = new Request();
        m.setInterfaceName("com.bow.demo.Calculator");
        m.setMethodName("calculate");
        Class[] ary = new Class[]{Integer.class,Integer.class};
        m.setParameterTypes(ary);
        m.setParameters(new Integer[]{1,2});
        Response result = client.call(m);
        System.out.println(result.getId()+"--"+result.getValue());
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }
}
