package com.bow.common.channel;

/**
 * 连接池
 * 
 * @author vv
 * @since 2016/12/4.
 */
public interface ChannelPool {

    /**
     * 获取Channel
     * 
     * @return Channel
     */
    Channel borrowChannel();

    /**
     * 注销 channel
     * 
     * @param channel
     */
    void invalidateChannel(Channel channel);

    /**
     * 归还channel
     * 
     * @param channel
     */
    void returnChannel(Channel channel);
}
