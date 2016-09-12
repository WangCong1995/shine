package com.bow.rpc;

import com.bow.common.utils.NetUtil;
import com.bow.common.utils.ShineUtils;
import com.bow.config.ServiceConfig;
import com.bow.config.ShineConfig;
import com.bow.registry.RegistryFactory;
import com.bow.registry.RegistryService;
import com.bow.remoting.ShineClient;
import com.bow.remoting.ShineServer;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Protocol template
 * @author vv
 * @since 2016/9/3.
 */
public abstract class AbstractProtocol implements Protocol {

    private ShineServer server;

    private ShineClient client;

    private RegistryService registry;

    protected Map<String, ServiceConfig> exportedMap = new ConcurrentHashMap<>();

    public AbstractProtocol(){
        initRegistryService();
    }

    protected RequestHandler requestHandler = new RequestHandler() {
        @Override
        public Result handle(Message message) {
            String serviceName = ShineUtils.getServiceName(message);
            ServiceConfig serviceConfig = exportedMap.get(serviceName);
            Object proxy = serviceConfig.getRef();

            Result result = new Result(message.getId());
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
        String serviceKey = ShineUtils.getServiceName(serviceConfig);
        exportedMap.put(serviceKey, serviceConfig);
        ensureServerInitialized();
        registry.register(serviceConfig,
                new URL(NetUtil.getLocalHostAddress(), ShineConfig.getServicePort()));

        return true;
    }

    private void ensureServerInitialized(){
        if (server == null) {
            server = doInitializeServer();
            server.setRequestHandler(requestHandler);
            server.start();
        }
    }

    /**
     * client send message to remote server with rpc;
     * @param message method name , parameters
     * @return Result server response
     */
    @Override
    public Result refer(Message message) {
        // 从注册中心拿到服务器地址，发送请求
        registry.lookup(ShineUtils.getServiceName(message));
        URL serverLocation = new URL("http", "127.0.0.1", 9000, "/");
        ensureClientInitialized();
        return client.call(serverLocation, message);
    }

    private void ensureClientInitialized(){
        if (client == null) {
            client = doInitializeClient();
        }
    }



    protected abstract ShineServer doInitializeServer();
    protected abstract ShineClient doInitializeClient();

    private void initRegistryService(){
        registry = RegistryFactory.getRegistryService(ShineConfig.getRegistryType());
    }


}
