package com.bow.common.pipeline;

import com.bow.common.ExtensionLoader;
import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.common.utils.ShineUtils;
import com.bow.config.ShineConfig;
import com.bow.registry.RegistryService;
import com.bow.rpc.Request;
import com.bow.rpc.URL;

import java.util.List;

/**
 * 从注册中心上取到该服务的URL
 * 
 * @author vv
 * @since 2016/10/7.
 */
public class RouterClientHandler extends ShineHandlerAdapter{

    private RegistryService registry;
    private LoadBalance loadBalance;

    public RouterClientHandler(){
        registry = ExtensionLoader.getExtensionLoader(RegistryService.class)
                .getExtension(ShineConfig.getRegistryType());
        loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class)
                .getExtension(ShineConfig.getLoadBalance());
    }
    @Override
    public void onSendRequest(ShineHandlerContext context, PipelineMessage message) {
        Request request = message.getRequest();
        String serviceName = ShineUtils.getServiceName(request);
        if(request.getServerUrl()!=null){
            //用户配置了直连
            context.nextSendRequest(message);
            return;
        }
        List<URL> urls = registry.lookup(request);
        if(urls == null || urls.size()==0){
            String errorMsg = ShineUtils.getServiceName(request) + " version " + request.getVersion();
            throw new ShineException(ShineExceptionCode.noExistsService, errorMsg);
        }
        URL url= loadBalance.select(serviceName,urls);
        request.setServerUrl(url);
        context.nextSendRequest(message);
    }


}
