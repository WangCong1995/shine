package com.bow.common.pipeline;

/**
 * 管道
 * @author vv
 * @since 2016/10/4.
 */
public interface ShinePipeline {
    /**
     * 在管道的前面增加handler
     * @param handler handler
     */
    void addFirst(ShineHandler handler);

    /**
     * 在管道的後面增加handler
     * @param handler handler
     */
    void addLast(ShineHandler handler);

    void add(ShineHandler handler);
}
