package com.bow.demo.module.zookeeper;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author vv
 * @since 2016/9/14.
 */
public class ZkDemoTest {

    private CuratorFramework client;

    @Before
    public void connect() {
        String path = "127.0.0.1:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

        // 连接断开sessionTimeoutMs后，短暂节点消失
        client = CuratorFrameworkFactory.builder().connectString(path).sessionTimeoutMs(5000).retryPolicy(retryPolicy)
                .build();
        client.start();
    }

    @Test
    public void testEphemeral() throws Exception {
        String path = "/instance";
        client.create().withMode(CreateMode.PERSISTENT).forPath(path);
        client.create().withMode(CreateMode.PERSISTENT).forPath(path + "/service1");
        client.create().withMode(CreateMode.EPHEMERAL).forPath(path + "/service1" + "/url1");
        attachWatcher(path);
        TimeUnit.SECONDS.sleep(30);
    }

    public void attachWatcher(final String path) throws Exception {
        CuratorWatcher curatorWatcher = new CuratorWatcher() {
            @Override
            public void process(WatchedEvent event) throws Exception {
                System.out.println(event.getType() + ">>" + event.getPath());

                client.getChildren().usingWatcher(this).forPath(event.getPath());
            }
        };
        // 监视path的子节点变化
        client.getChildren().usingWatcher(curatorWatcher).forPath(path);
        // 监视path节点数据变化
        // client.getData().usingWatcher(curatorWatcher).forPath(path);
    }


    @Test
    public void delete() throws Exception {
        client.delete().forPath("/instance");
    }
}
