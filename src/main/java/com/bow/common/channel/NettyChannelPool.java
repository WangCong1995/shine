package com.bow.common.channel;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.remoting.netty.NettyClient;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NettyChannel Pool<br/>
 * 对于每个server会有一个client. 一个client对应一个连接池
 * 
 * @author vv
 * @since 2016/12/4.
 */
public class NettyChannelPool implements ChannelPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyChannelPool.class);

    private GenericObjectPoolConfig config;

    private PooledObjectFactory factory;

    private GenericObjectPool pool;

    public NettyChannelPool(NettyClient client) {
        config = new GenericObjectPoolConfig();
        config.setMinIdle(ChannelPoolConfig.get().getMinIdle());
        config.setMaxIdle(ChannelPoolConfig.get().getMaxIdle());
        config.setMaxTotal(ChannelPoolConfig.get().getMaxTotal());
        config.setLifo(ChannelPoolConfig.get().isLifo());
        config.setMaxWaitMillis(ChannelPoolConfig.get().getMaxWaitMillis());
        config.setMinEvictableIdleTimeMillis(ChannelPoolConfig.get().getMinEvictableIdleTimeMillis());
        config.setSoftMinEvictableIdleTimeMillis(ChannelPoolConfig.get().getSoftMinEvictableIdleTimeMillis());
        config.setTimeBetweenEvictionRunsMillis(ChannelPoolConfig.get().getTimeBetweenEvictionRunsMillis());
        this.factory = new NettyChannelFactory(client);
        this.pool = new GenericObjectPool(factory, config);
    }

    /**
     * 获取Channel
     *
     * @return Channel
     */
    @Override
    public Channel borrowChannel() {

        NettyChannel nettyChannel;
        try {
            nettyChannel = (NettyChannel) pool.borrowObject();
            if (nettyChannel != null && nettyChannel.isActive()) {
                if(LOGGER.isDebugEnabled()){
                    LOGGER.debug("borrowChannel "+nettyChannel.toString());
                }
                return nettyChannel;
            }
        } catch (Exception e) {
            throw new ShineException(ShineExceptionCode.connectionException, e);
        }
        // 注销channel
        invalidateChannel(nettyChannel);
        throw new ShineException(ShineExceptionCode.connectionException, "fail to borrowChannel");
    }

    /**
     * 注销 channel
     *
     * @param channel
     */
    @Override
    public void invalidateChannel(Channel channel) {
        if (channel == null) {
            return;
        }
        try {
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("ready to invalidateChannel "+channel.toString());
            }
            pool.invalidateObject(channel);
        } catch (Exception e) {
            throw new ShineException(ShineExceptionCode.connectionException, "fail to invalidateChannel");
        }
    }

    /**
     * 归还channel
     *
     * @param channel
     */
    @Override
    public void returnChannel(Channel channel) {
        if (channel == null) {
            return;
        }
        try {
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("ready to returnChannel "+channel.toString());
            }
            pool.returnObject(channel);
        } catch (Exception e) {
            throw new ShineException(ShineExceptionCode.connectionException, "fail to returnChannel",e);
        }
    }
}
