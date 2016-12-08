package com.bow.rpc;

import com.bow.common.ExtensionLoader;
import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.common.utils.NetUtil;
import com.bow.common.utils.ShineUtils;
import com.bow.config.ServiceConfig;
import com.bow.config.ShineConfig;
import com.bow.registry.RegistryService;
import com.bow.remoting.ShineClient;
import com.bow.remoting.ShineServer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Protocol template
 * 
 * @author vv
 * @since 2016/9/3.
 */
public abstract class AbstractProtocol implements Protocol {

    private Map<String,ShineClient> clients = new HashMap<String,ShineClient>();

    private RegistryService registry;

    private ShineServer server;

    /**
     * 所有被暴露的服务
     */
    protected Map<String, ServiceConfig> exportedMap = new ConcurrentHashMap<String, ServiceConfig>();

    public AbstractProtocol() {
        initRegistryService();
    }

    /**
     * 收到请求后，反射调用服务实现类获取结果
     */
    protected RequestHandler requestHandler = new RequestHandler() {
        @Override
        public Response handle(Request message) {
            String serviceName = ShineUtils.getServiceName(message);
            ServiceConfig serviceConfig = exportedMap.get(serviceName);
            Object proxy = serviceConfig.getRef();

            Response result = new Response(message.getId());
            try {
                Method method = proxy.getClass().getMethod(message.getMethodName(), message.getParameterTypes());
                Object r = method.invoke(proxy, message.getParameters());
                result.setValue(r);
            } catch (Throwable e) {
                result.setCause(e);
            }
            return result;
        }
    };

    /**
     * export service
     * @param serviceConfig configured in spring xml
     * @return boolean
     */
    @Override
    public boolean export(ServiceConfig serviceConfig) {
        String serviceName = ShineUtils.getServiceName(serviceConfig);
        exportedMap.put(serviceName, serviceConfig);
        //开服务
        ensureServerInitialized();

        URL providerUrl = new URL(this.getName(), NetUtil.getLocalHostAddress(), ShineConfig.getServicePort());
        //设置暴露的接口
        providerUrl.setResource(serviceConfig.getInterfaceName());
        providerUrl.setParameter(URL.GROUP, serviceConfig.getGroup());
        providerUrl.setParameter(URL.VERSION, serviceConfig.getVersion());
        // 注册
        registry.register(providerUrl);

        return true;
    }

    private void ensureServerInitialized() {
        if (server == null) {
            server = doInitializeServer();
            server.setRequestHandler(requestHandler);
            server.start();
        }
    }

    /**
     * client send message to remote server with rpc;
     * 
     * @param request
     *            method name , parameters
     * @return Result,server response
     */
    @Override
    public Response refer(Request request) {
        URL serverLocation = request.getServerUrl();
        if (serverLocation == null) {
            throw new ShineException(ShineExceptionCode.noExistsService,request.toString());
        }
        ShineClient client = getClient(serverLocation);
        return client.call(request);
    }

    private ShineClient getClient(URL serverLocation) {
        String address = serverLocation.getAddress()+":"+serverLocation.getPort();
        synchronized (this){
            if (clients.get(address) == null) {
                ShineClient client = doInitializeClient(serverLocation);
                clients.put(address,client);
            }
            return clients.get(address);
        }
    }

    protected abstract ShineServer doInitializeServer();

    protected abstract ShineClient doInitializeClient(URL serverLocation);

    private void initRegistryService() {
        registry = ExtensionLoader.getExtensionLoader(RegistryService.class)
                .getExtension(ShineConfig.getRegistryType());
    }

}
