package com.bow.registry.zookeeper;

import java.util.List;

import com.bow.rpc.URL;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.data.Stat;

public class CuratorZookeeperClient extends AbstractZookeeperClient {

    private final CuratorFramework client;

    /**
     * 创建客户端
     * 
     * @param url
     *            zookeeper地址
     */
    public CuratorZookeeperClient(URL url) {
        super(url);
        Builder builder = CuratorFrameworkFactory.builder().connectString(url.getAddress())
                .retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).connectionTimeoutMs(5000)
                .sessionTimeoutMs(15000);
        client = builder.build();
        // 监控当前连接的状态
        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            public void stateChanged(CuratorFramework client, ConnectionState state) {
                if (state == ConnectionState.LOST) {
                    CuratorZookeeperClient.this.stateChanged(StateListener.DISCONNECTED);
                } else if (state == ConnectionState.CONNECTED) {
                    CuratorZookeeperClient.this.stateChanged(StateListener.CONNECTED);
                } else if (state == ConnectionState.RECONNECTED) {
                    CuratorZookeeperClient.this.stateChanged(StateListener.RECONNECTED);
                }
            }
        });
        client.start();
    }

    public void createPersistent(String path) {
        try {
            client.create().forPath(path);
        } catch (NodeExistsException e) {
            logger.warn(path +" node already exists");
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * 短暂的节点
     * 
     * @param path
     */
    public void createEphemeral(String path) {
        try {
            client.create().withMode(CreateMode.EPHEMERAL).forPath(path);
        } catch (NodeExistsException e) {
            logger.warn(path +"node already exists");
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public boolean exists(String path) {
        try {
            return client.checkExists().forPath(path) == null ? false : true;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public void create(String path,boolean ephemeral, byte[] data) {
        try {
            if(ephemeral){
                client.create().withMode(CreateMode.EPHEMERAL).forPath(path,data);
            }else{
                client.create().withMode(CreateMode.PERSISTENT).forPath(path,data);
            }

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public byte[] getData(String path){
        try {
            return client.getData().forPath(path);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void delete(String path) {
        try {
            client.delete().forPath(path);
        } catch (NoNodeException e) {
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public List<String> getChildren(String path) {
        try {
            return client.getChildren().forPath(path);
        } catch (NoNodeException e) {
            return null;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * 给path的每个子节点绑定一个监听器；子节点发生变化会触发ChildListener<br/>
     * 注意client.getChildren().forPath(path)获取到了子节点，usingWatcher(watcher)绑定监听
     * @param path path
     * @param listener listener
     * @return path的子节点
     */
    @Override
    public List<String> addChildListener(String path, ChildListener listener) {
        CuratorWatcher watcher = new CuratorWatcherImpl(listener);
        try {
            //监控path的子节点变化
            return client.getChildren().usingWatcher(watcher).forPath(path);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public void removeChildListener(String path, ChildListener listener) {

    }

    public boolean isConnected() {
        return client.getZookeeperClient().isConnected();
    }

    public void doClose() {
        client.close();
    }

    /**
     * 将上层的ChildListener包装为CuratorWatcher
     */
    private class CuratorWatcherImpl implements CuratorWatcher {

        private volatile ChildListener listener;

        public CuratorWatcherImpl(ChildListener listener) {
            this.listener = listener;
        }

        public void unwatch() {
            this.listener = null;
        }

        public void process(WatchedEvent event) throws Exception {
            if (listener != null) {
                // 1.watcher是一次性的，所以触发后需要重新绑定
                // 2.client.getChildren().usingWatcher表明监控的是path的子节点是否发生变化
                List<String> children = client.getChildren().usingWatcher(this).forPath(event.getPath());
                listener.childChanged(event.getPath(), children);
            }
        }
    }

}
