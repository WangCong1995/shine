package com.bow.demo.unit;

import com.bow.demo.integration.entity.BigData;
import com.bow.remoting.hessian.HessianClient;
import com.bow.remoting.netty.NettyClient;
import com.bow.rpc.Request;
import com.bow.rpc.Response;
import com.bow.rpc.URL;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
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
        Request request = new Request();
        request.setInterfaceName("com.bow.demo.Calculator");
        request.setMethodName("calculate");
        Class[] ary = new Class[]{Integer.class,Integer.class};
        request.setParameterTypes(ary);
        request.setParameters(new Integer[]{1,2});
        Response result = client.call(request);
        System.out.println(result.getId()+"--"+result.getValue());
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    /**
     * TODO 准备测试单次请求大小的，没有写完整
     * @throws IOException e
     */
    @Test
    public void testBigData() throws IOException {
        URL url = new URL("127.0.0.1",9000);
        NettyClient client = new NettyClient(url);
        Request request = new Request();
        int num = 500*65*2;
        BigData[] dataAry = new BigData[num];
        for(int i=0;i<num;i++){
            //每个data100字节
            BigData data = new BigData();
            data.setF1("123456789012345");
            data.setF2("123456789012345");
            data.setF3("123456789012345");
            data.setF4("123456789012345");
            data.setF5("123456789012345");
            dataAry[i] = data;
        }
        request.setParameters(new Object[]{dataAry});
        size(request);
        Response response = client.call(request);
        System.out.println("result>>>"+response.getValue());
    }

    private void size(Object obj){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("object size "+bos.toByteArray().length);
    }
}
