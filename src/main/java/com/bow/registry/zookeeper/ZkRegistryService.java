package com.bow.registry.zookeeper;

import com.bow.common.Version;
import com.bow.common.utils.NetUtil;
import com.bow.common.utils.ShineUtils;
import com.bow.config.ServiceConfig;
import com.bow.config.ShineConfig;
import com.bow.registry.RegistryService;
import com.bow.rpc.Request;
import com.bow.rpc.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author vv
 * @since 2016/8/30.
 */
public class ZkRegistryService implements RegistryService {

    private final Logger logger = LoggerFactory.getLogger(ZkRegistryService.class);

    private String root = "/shine";

    private ZookeeperClient zookeeperClient;

    /**
     * key:service所在的全路径
     */
    private Map<String, List<String>> subscribed = new ConcurrentHashMap<String, List<String>>();

    public ZkRegistryService() {
        InetSocketAddress address = NetUtil.toSocketAddress(ShineConfig.getRegistryUrl());
        URL url = new URL(address.getHostName(), address.getPort());
        zookeeperClient = ZooKeeperClientFactory.getClient(url);
        // 根节点必须为永久的，这样才可以添加子节点
        if (!zookeeperClient.exists(root)) {
            zookeeperClient.create(root, false);
        }
    }

    /**
     * 客户端调用
     *
     *
     * 注意：版本号1.2 能够匹配到1.2.1
     *
     * @return
     */
    @Override
    public List<URL> lookup(Request request) {
        String nodePath = root + ShineUtils.SLASH + ShineUtils.getServiceName(request);
        List<String> urlStrs = subscribed.get(nodePath);
        Version requestVersion = new Version(request.getVersion());
        List<URL> result = new ArrayList<>();
        for(String urlStr:urlStrs){
            String v = new String(zookeeperClient.getData(nodePath+ShineUtils.SLASH +urlStr));
            Version v1 = new Version(v);

            if(requestVersion.imply(v1)){
                URL url = NetUtil.toURL(urlStr);
                url.setParameter("version",v);
                result.add(url);
            }
        }
        return result;
    }

    @Override
    public boolean register(ServiceConfig serviceConfig, URL providerUrl) {
        String nodePath = root + ShineUtils.SLASH + ShineUtils.getServiceName(serviceConfig);
        if (!zookeeperClient.exists(nodePath)) {
            zookeeperClient.create(nodePath, false);
        }
        // url 是短暂节点
        nodePath += ShineUtils.SLASH + providerUrl.getAddress();
        //版本号放到url节点的数据区里
        byte[] data = serviceConfig.getVersion()==null?null:serviceConfig.getVersion().getBytes();
        zookeeperClient.create(nodePath, true,data);
        return true;
    }

    /**
     * referenceBean初始化完后调用
     * 
     * @param serviceName
     *            服务名
     */
    @Override
    public void subscribe(String serviceName) {
        ChildListener childListener = new ChildListener() {
            /**
             * 子节点变化后，需要更新本地缓存
             */
            @Override
            public void childChanged(String path, List<String> children) {
                subscribed.put(path, children);
                //FIXME 当children.size=0,触发删除path
            }
        };
        // 给root添加监听的时候，会获得root下的子节点
        String servicePath = root + ShineUtils.SLASH + serviceName;
        List<String> children = zookeeperClient.addChildListener(servicePath, childListener);
        if (children != null) {
            subscribed.put(servicePath, children);
        } else {
            logger.warn("no url for service: " + servicePath);
        }

    }

    @Override
    public String getName() {
        return "zookeeper";
    }
}
