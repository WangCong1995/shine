package com.bow.common.pipeline;


/**
 * pipeline就是一个链表，其只记录first,last节点，节点间关系由节点自己保存
 * @author vv
 * @since 2016/10/4.
 */
public class StandardPipeline implements ShinePipeline {
    protected ShineHandlerContext first;

    protected ShineHandlerContext last;

    protected transient int size = 0;

    @Override
    public void addFirst(ShineHandler handler) {
        final ShineHandlerContext f = first;
        final ShineHandlerContext newNode = new ShineHandlerContext(null, handler, f);
        first = newNode;
        if (f == null) {
            last = newNode;
        } else {
            f.prev = newNode;
        }
        size++;
    }

    @Override
    public void addLast(ShineHandler handler) {
        final ShineHandlerContext l = last;
        final ShineHandlerContext newNode = new ShineHandlerContext(l, handler, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    @Override
    public void add(ShineHandler handler) {
        addLast(handler);
    }

}
