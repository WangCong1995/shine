package com.bow.demo.unit;

import com.bow.common.utils.NetUtil;
import com.bow.rpc.URL;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author vv
 * @since 2016/12/8.
 */
public class URLTest {

    @Test
    public void parse(){
        URL url = new URL("netty","127.0.0.1",8080,"com.bow.demo.integration.api.Calculator");
        url.setParameter("group","vv");
        url.setParameter("version","1.2.3");
        String urlString = url.toString();
        URL parsed = NetUtil.parse(urlString);
        System.out.println(parsed);
        Assert.assertTrue(parsed.getPort()==8080);

    }

}
