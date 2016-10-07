package com.bow.remoting;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.rpc.Request;
import com.bow.rpc.Response;
import com.bow.rpc.URL;
import com.caucho.hessian.client.HessianProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;

/**
 * Created by vv on 2016/8/21.
 */
public class HessianClient implements ShineClient {
    private static final Logger logger = LoggerFactory.getLogger(HessianClient.class);

    private URL url;
    public HessianClient(URL url){
        this.url = url;
    }
    @Override
    public Response call(Request message) {

        String serverLocation = "http://"+url.getHost()+url.getPort();
        HessianProxyFactory factory = new HessianProxyFactory();
        try {
            HessianCallService callService =(HessianCallService)factory.create(HessianCallService.class,serverLocation);
            return callService.call(message);
        } catch (MalformedURLException e) {
            throw new ShineException(ShineExceptionCode.fail,e);
        }
    }
}
