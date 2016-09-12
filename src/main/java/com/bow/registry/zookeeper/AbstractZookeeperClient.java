package com.bow.registry.zookeeper;

import com.bow.rpc.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * TODO 该层是一个中间层，他的子层是curator底层操作zookeeper，serviceListeners有必要缓存着吗？
 */
public abstract class AbstractZookeeperClient implements ZookeeperClient {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractZookeeperClient.class);

    private final URL url;

    private final Set<StateListener> stateListeners = new CopyOnWriteArraySet<StateListener>();

    private final ConcurrentMap<String, ServiceListener> serviceListeners = new ConcurrentHashMap<String, ServiceListener>();

    private volatile boolean closed = false;

    public AbstractZookeeperClient(URL url) {
        this.url = url;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public void create(String path, boolean ephemeral) {
        int i = path.lastIndexOf('/');
        if (i > 0) {
            create(path.substring(0, i), false);
        }
        if (ephemeral) {
            createEphemeral(path);
        } else {
            createPersistent(path);
        }
    }

    @Override
    public void addStateListener(StateListener listener) {
        stateListeners.add(listener);
    }

    @Override
    public void removeStateListener(StateListener listener) {
        stateListeners.remove(listener);
    }

    public Set<StateListener> getSessionListeners() {
        return stateListeners;
    }

    @Override
    public List<String> addServiceListener(String path, ServiceListener listener) {
        return null;
    }

    @Override
    public void removeServiceListener(String path, ServiceListener listener) {
    }


    @Override
    public void close() {
        if (closed) {
            return;
        }
        closed = true;
        try {
            doClose();
        } catch (Throwable t) {
            logger.warn(t.getMessage(), t);
        }
    }

    /**
     * 供子类调用，监控当前连接的状态
     * @param state
     */
    protected void stateChanged(int state) {
        for (StateListener sessionListener : getSessionListeners()) {
            sessionListener.stateChanged(state);
        }
    }

    protected abstract void doClose();

    protected abstract void createPersistent(String path);

    protected abstract void createEphemeral(String path);


}
