package com.bow.registry.zookeeper;

import com.bow.common.Version;
import com.bow.common.utils.NetUtil;
import com.bow.common.utils.ShineUtils;
import com.bow.config.Name;
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
@Name("zookeeper")
public class ZkRegistryService implements RegistryService {

    private final Logger logger = LoggerFactory.getLogger(ZkRegistryService.class);

    private static final String ROOT = "/shine";

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
        if (!zookeeperClient.exists(ROOT)) {
            zookeeperClient.create(ROOT, false);
        }
    }

    /**
     * 客户端调用
     *
     *
     * 注意：版本号1.2 能够匹配到1.2.1
     *
     * @return 所有符合条件的url
     */
    @Override
    public List<URL> lookup(Request request) {
        String nodePath = ROOT + ShineUtils.SLASH + ShineUtils.getServiceName(request);
        String requestVersion = request.getVersion();
        List<URL> urls = subscribed.get(nodePath);
        return filterVersion(requestVersion, urls);
    }

    /**
     * 根据请求的版本号，过滤服务
     * 
     * @param requestVersion
     *            version
     * @param urls
     *            urls
     * @return List<URL>
     */
    private List<URL> filterVersion(String requestVersion, List<URL> urls) {
        // 注意：这里是重新建了一个list
        List<URL> available = new ArrayList();
        Version rv = new Version(requestVersion);
        for (URL url : urls) {
            String urlVersion = url.getStringParam(URL.VERSION);
            Version version = new Version(urlVersion);
            if (rv.matches(version)) {
                available.add(url);
            }
        }
        return available;
    }

    @Override
    public boolean register(URL providerUrl) {

        String nodePath = ROOT + ShineUtils.SLASH + ShineUtils.getServiceName(providerUrl);
        if (!zookeeperClient.exists(nodePath)) {
            zookeeperClient.create(nodePath, false);
        }
        // url 是短暂节点
        nodePath += ShineUtils.SLASH + providerUrl.getAddress();
        // 版本号放到url节点的数据区里
        byte[] data = providerUrl.toString().getBytes();
        zookeeperClient.create(nodePath, true, data);
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
            @Override
            public void childChanged(String path, List<String> children) {
                // 子节点变化后，需要更新本地缓存
                updateSubscribed(path, children);
            }
        };

        // 给path添加监听时，可以获取到其子节点
        String servicePath = ROOT + ShineUtils.SLASH + serviceName;
        List<String> children = zookeeperClient.addChildListener(servicePath, childListener);
        updateSubscribed(servicePath, children);

    }

    private void updateSubscribed(String servicePath, List<String> children) {
        if (children == null || children.size() == 0) {
            logger.warn("no url for service: " + servicePath);
            zookeeperClient.delete(servicePath);
            return;
        }

        List<URL> urls = new ArrayList();
        for (String urlStr : children) {
            String content = new String(zookeeperClient.getData(servicePath + ShineUtils.SLASH + urlStr));
            URL url = NetUtil.parse(content);
            urls.add(url);
        }
        subscribed.put(servicePath, urls);
    }

}
