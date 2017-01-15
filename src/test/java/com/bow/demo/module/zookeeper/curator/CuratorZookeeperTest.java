package com.bow.demo.module.zookeeper.curator;

import com.bow.registry.zookeeper.CuratorZookeeperClient;
import com.bow.rpc.URL;
import org.junit.Test;

/**
 * Created by vv on 2016/8/30.
 */
public class CuratorZookeeperTest {
    @Test
    public void testCreate(){
        CuratorZookeeperClient client = new CuratorZookeeperClient(new URL("127.0.0.1",2181));
        client.create("/aa",false);
        client.close();
    }
}
