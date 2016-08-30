package com.bow.rpc;

import com.bow.common.utils.NetUtil;
import com.bow.config.ServiceConfig;
import com.bow.config.ShineConfig;
import com.bow.registry.RegistryFactory;
import com.bow.registry.RegistryService;
import com.bow.remoting.HessianClient;
import com.bow.remoting.HessianServer;
import com.bow.remoting.ShineClient;
import com.bow.remoting.ShineServer;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用hessian公布和引用服务
 * Created by vv on 2016/8/20.
 */
public class HessianProtocol implements Protocol {
    private static final String DEFAULT_SEPERATOR = "#";

    private Map<String, ServiceConfig> exportedMap = new ConcurrentHashMap<>();

    private ShineServer server;

    private ShineClient client;

    private RegistryService registry = RegistryFactory.getRegistryService("hessian");

    private RequestHandler requestHandler = new RequestHandler() {
        @Override
        public Result handle(Message message) {
            String serviceKey = message.getGroup() + DEFAULT_SEPERATOR + message.getInterfaceName();
            ServiceConfig serviceConfig = exportedMap.get(serviceKey);
            Object proxy = serviceConfig.getRef();

            Result result = new Result();
            try {
                Method method = proxy.getClass().getMethod(message.getMethodName(), message.getParameterTypes());
                Object r = method.invoke(proxy, message.getParameters());
                result.setValue(r);
            } catch (Exception e) {
                result.setCause(e);
            }
            return result;
        }
    };

    @Override
    public boolean export(ServiceConfig serviceConfig) {
        String serviceKey = serviceConfig.getGroup() + DEFAULT_SEPERATOR + serviceConfig.getInterfaceName();
        exportedMap.put(serviceKey, serviceConfig);
        if (server == null) {
            server = new HessianServer();
            server.setRequestHandler(requestHandler);
            server.start();
        }
        registry.register(serviceConfig,
                new URL("hessian", NetUtil.getLocalHostAddress(), ShineConfig.getservicePort(), null));

        return true;
    }

    /**
     * 用client将参数发送到服务器并接收响应，返回结果
     * 
     * @param message
     *            消息
     * @return Result
     */
    @Override
    public Result refer(Message message) {
        // 从注册中心拿到服务器地址，发送请求
        URL serverLocation = new URL("http", "127.0.0.1", 9000, "/");
        if (client == null) {
            client = new HessianClient();
        }
        return client.call(serverLocation, message);
    }

    @Override
    public String getName() {
        return "hessian";
    }
}
