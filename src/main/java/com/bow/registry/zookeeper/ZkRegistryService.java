package com.bow.registry.zookeeper;

import com.bow.common.utils.NetUtil;
import com.bow.common.utils.ShineUtils;
import com.bow.config.ServiceConfig;
import com.bow.config.ShineConfig;
import com.bow.registry.RegistryService;
import com.bow.rpc.URL;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * Created by vv on 2016/8/30.
 */
public class ZkRegistryService implements RegistryService {

    private String root;

    private ZookeeperClient zookeeperClient;

    public ZkRegistryService(){
        root = "/shine/instance/";
        InetSocketAddress address = NetUtil.toSocketAddress(ShineConfig.getRegistryUrl());
        URL url = new URL(address.getHostName(), address.getPort());
        zookeeperClient = ZooKeeperClientFactory.getClient(url);
    }

    @Override
    public List<URL> lookup(String serviceName) {
        zookeeperClient.getChildren(root);
        return null;
    }

    @Override
    public boolean register(ServiceConfig serviceConfig, URL providerUrl) {
        String path = root+ ShineUtils.getServiceName(serviceConfig)+ShineUtils.NUM_SIGN+providerUrl.getAddress();
        zookeeperClient.create(path,false);
        //此处可以将多余的数据序列化后存储
        return true;
    }

    @Override
    public String getName() {
        return "zookeeper";
    }
}
