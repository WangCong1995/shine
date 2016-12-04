package com.bow.demo.unit;

import com.bow.demo.integration.entity.BigData;
import com.bow.remoting.hessian.HessianClient;
import com.bow.rpc.Request;
import com.bow.rpc.Response;
import com.bow.rpc.URL;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vv
 * @since 2016/11/3.
 */
public class HessianClientTest {

    @Test
    public void call(){
        URL url = new URL("192.168.56.1",9000);
        HessianClient client = new HessianClient(url);
        Request request = new Request();
        Response response = client.call(request);
        System.out.println(response.getValue());
    }


    @Test
    public void testBigData(){
        URL url = new URL("192.168.56.1",9000);
        HessianClient client = new HessianClient(url);
        Request request = new Request();
        List dataList = new ArrayList();
        for(int i=0;i<500;i++){
            //每个data100字节
            BigData data = new BigData();
            data.setF1("123456789012345");
            data.setF2("123456789012345");
            data.setF3("123456789012345");
            data.setF4("123456789012345");
            data.setF5("123456789012345");
            dataList.add(data);
        }
        request.setParameters(new Object[]{dataList});
        Response response = client.call(request);
        List list = (List) response.getValue();
        System.out.println(list.size());
    }
}
