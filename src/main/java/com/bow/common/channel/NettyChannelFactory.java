package com.bow.common.channel;

import com.bow.remoting.netty.NettyClient;
import com.bow.rpc.URL;
import io.netty.bootstrap.Bootstrap;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * 负责为 {@link #nettyClient} 创建 {@link NettyChannel}
 *
 * @author vv
 * @since 2016/12/4.
 */
public class NettyChannelFactory extends BasePooledObjectFactory<NettyChannel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyChannelFactory.class);

    private String factoryName = "";

    private NettyClient nettyClient;

    public NettyChannelFactory(NettyClient nettyClient) {
        super();
        this.nettyClient = nettyClient;
        this.factoryName = "NettyChannelFactory_" + nettyClient.getUrl().getHost() + "_"
                + nettyClient.getUrl().getPort();
    }

    /**
     * 为连接池创建一个NettyChannel
     * 
     * @return NettyChannel
     * @throws Exception
     *             e
     */
    @Override
    public NettyChannel create() throws Exception {
        Bootstrap bootstrap = nettyClient.getBootstrap();
        URL url = nettyClient.getUrl();
        InetSocketAddress socketAddress = new InetSocketAddress(url.getHost(), url.getPort());
        NettyChannel channel = new NettyChannel(bootstrap, socketAddress);
        channel.open();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(factoryName + " create channel, id " + channel.id());
        }
        return channel;
    }

    @Override
    public PooledObject<NettyChannel> wrap(NettyChannel obj) {
        return new DefaultPooledObject(obj);
    }

    /**
     * destroyObject
     * 
     * @param p
     *            PooledObject
     * @throws Exception
     *             e
     */
    @Override
    public void destroyObject(PooledObject<NettyChannel> p) throws Exception {
        if (p instanceof DefaultPooledObject) {
            DefaultPooledObject pooledObject = (DefaultPooledObject) p;
            Object obj = pooledObject.getObject();
            if (obj instanceof NettyChannel) {
                NettyChannel channel = (NettyChannel) obj;
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(factoryName + " ready to destroy channel " + channel.toString());
                }
                channel.close();
            }
        }
    }

    @Override
    public boolean validateObject(PooledObject<NettyChannel> p) {
        boolean result = false;
        if (p instanceof DefaultPooledObject) {
            DefaultPooledObject pooledObject = (DefaultPooledObject) p;
            Object obj = pooledObject.getObject();
            if (obj instanceof NettyChannel) {
                NettyChannel channel = (NettyChannel) obj;
                try {
                    result = channel.isActive();
                } catch (Exception e) {
                    LOGGER.error("validateObject ", e);
                }
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("validateObject channel " + channel.id() + " result " + result);
                }
            }
        }
        return result;
    }

    @Override
    public void activateObject(PooledObject<NettyChannel> p) throws Exception {
        if (p instanceof DefaultPooledObject) {
            DefaultPooledObject pooledObject = (DefaultPooledObject) p;
            Object obj = pooledObject.getObject();
            if (obj instanceof NettyChannel) {
                final NettyChannel channel = (NettyChannel) obj;
                if (!channel.isActive()) {
                    channel.open();
                }
            }
        }
    }

    @Override
    public void passivateObject(PooledObject<NettyChannel> p) throws Exception {
    }
}
