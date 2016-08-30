package com.bow.registry.zookeeper;

import com.bow.rpc.URL;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vv on 2016/8/30.
 */
public class ZooKeeperClientFactory {

    private static Map<URL,ZookeeperClient> clientMap = new HashMap<>();
    public static ZookeeperClient getClient(URL url){
        ZookeeperClient client = null;
        for(URL existsUrl:clientMap.keySet()){
            if(existsUrl.equals(url)){
                client = clientMap.get(existsUrl);
            }
        }
        if(client==null){
            client = new CuratorZookeeperClient(url);
            clientMap.put(url,client);
        }
        return client;
    }
}
