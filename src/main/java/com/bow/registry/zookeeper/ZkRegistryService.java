package com.bow.registry.zookeeper;

import com.bow.common.utils.NetUtil;
import com.bow.common.utils.ShineUtils;
import com.bow.config.ServiceConfig;
import com.bow.config.ShineConfig;
import com.bow.registry.RegistryService;
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
    private Map<String, List<URL>> subscribed = new ConcurrentHashMap<String, List<URL>>();

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
     * @param serviceName
     *            组名#接口全限定名#版本号 如 vv#com.bow.shine.IHello#0.1.1
     * @return
     */
    @Override
    public List<URL> lookup(String serviceName) {
        String nodePath = root + ShineUtils.SLASH + serviceName;
        return subscribed.get(nodePath);
    }

    @Override
    public boolean register(ServiceConfig serviceConfig, URL providerUrl) {
        String nodePath = root + ShineUtils.SLASH + ShineUtils.getServiceName(serviceConfig);
        if (!zookeeperClient.exists(nodePath)) {
            zookeeperClient.create(nodePath, false);
        }
        // url 是短暂节点
        nodePath += ShineUtils.SLASH + providerUrl.getAddress();
        zookeeperClient.create(nodePath, true);
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
                List<URL> urls = new ArrayList<>();
                if (children != null) {
                    for (String urlStr : children) {
                        urls.add(NetUtil.toURL(urlStr));
                    }
                }
                subscribed.put(path, urls);
            }
        };
        // 给root添加监听的时候，会获得root下的子节点
        String servicePath = root + ShineUtils.SLASH + serviceName;
        List<String> children = zookeeperClient.addChildListener(servicePath, childListener);
        List<URL> urls = new ArrayList<>();
        if (children != null) {
            for (String urlStr : children) {
                urls.add(NetUtil.toURL(urlStr));
            }
            subscribed.put(servicePath, urls);
        } else {
            logger.warn("no url for service: " + servicePath);
        }

    }

    @Override
    public String getName() {
        return "zookeeper";
    }
}
