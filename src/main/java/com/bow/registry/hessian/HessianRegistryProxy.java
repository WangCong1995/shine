package com.bow.registry.hessian;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.config.Name;
import com.bow.config.ServiceConfig;
import com.bow.config.ShineConfig;
import com.bow.registry.RegistryService;
import com.bow.rpc.Request;
import com.bow.rpc.URL;
import com.caucho.hessian.client.HessianProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.util.List;

/**
 * Hessian Registry proxy
 * 
 * @author vv
 * @since 2016/8/21.
 */
@Name("hessian")
public class HessianRegistryProxy implements RegistryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HessianRegistryProxy.class);

    private RegistryService proxy;

    private static volatile boolean initialized = false;

    private void init() {
        String registryLocation = "http://" + ShineConfig.getRegistryUrl() + "/registry";
        HessianProxyFactory factory = new HessianProxyFactory();
        try {
            proxy = (RegistryService) factory.create(RegistryService.class, registryLocation);
            LOGGER.debug("hessian registry service initialized");
        } catch (MalformedURLException e) {
            throw new ShineException(ShineExceptionCode.fail, e);
        }
    }

    private void ensureInitialized() {
        if (!initialized) {
            init();
            initialized = true;
        }
    }

    @Override
    public List<URL> lookup(Request request) {
        ensureInitialized();
        return proxy.lookup(request);
    }

    @Override
    public boolean register(URL providerUrl) {
        ensureInitialized();
        return proxy.register(providerUrl);
    }

    @Override
    public void subscribe(String serviceName) {
        // TODO
    }

}
