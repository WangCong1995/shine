package com.bow.demo.unit;

import com.alibaba.fastjson.JSON;
import com.bow.common.ExtensionLoader;
import com.bow.common.utils.NetUtil;
import com.bow.common.utils.ShineUtils;
import com.bow.config.ShineConfig;
import com.bow.registry.RegistryService;
import com.bow.rpc.Request;
import com.bow.rpc.URL;
import org.junit.Test;

import java.util.List;

/**
 * @author vv
 * @since 2016/12/8.
 */
public class ZkRegistryServiceTest {

    private RegistryService registryService = ExtensionLoader.getExtensionLoader(RegistryService.class).getExtension();

    @Test
    public void register() {
        URL url = new URL("netty", NetUtil.getLocalHostAddress(), ShineConfig.getServicePort());
        url.setResource("com.bow.demo.integration.api.Calculator");
        url.setParameter("group","vv");
        url.setParameter("version","1.2.3");
        registryService.register(url);
    }

    @Test
    public void lookup(){
        URL url = new URL("netty", NetUtil.getLocalHostAddress(), ShineConfig.getServicePort());
        url.setResource("com.bow.demo.integration.api.Calculator");
        url.setParameter("group","vv");
        url.setParameter("version","1.2.3");
        //注册
        registryService.register(url);
        //订阅
        registryService.subscribe(ShineUtils.getServiceName(url));
        Request request = new Request();
        request.setInterfaceName("com.bow.demo.integration.api.Calculator");
        request.setGroup("vv");
        //查找
        List<URL> urls = registryService.lookup(request);
        System.out.println(JSON.toJSONString(urls));
    }
}
