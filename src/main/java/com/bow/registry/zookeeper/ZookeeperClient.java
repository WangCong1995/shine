package com.bow.registry.zookeeper;

import com.bow.rpc.URL;

import java.util.List;

public interface ZookeeperClient {

    /**
     * create a node /a/b/c 只有当/a/b存在时，才可以创建c
     * 
     * @param path
     *            path
     * @param ephemeral
     *            ephemeral
     */
    void create(String path, boolean ephemeral);

    /**
     * /a/b/c 当/a/b不存在时也能够创建
     * 
     * @param path
     *            /a/b/c
     * @param ephemeral
     *            临时节点，连接断开后session失效节点即删除
     */
    void forceCreate(String path, boolean ephemeral);

    boolean exists(String path);

    /**
     *
     * @param path
     *            node
     * @param data
     *            data
     */
    void create(String path, byte[] data);

    /**
     * delete a node
     * 
     * @param path
     *            path
     */
    void delete(String path);

    List<String> getChildren(String path);

    /**
     * 给path节点绑定一个监听器，其子节点发生变化会触发ChildListener
     * 
     * @param path
     *            path
     * @param listener
     *            listener
     * @return path的子节点
     */
    List<String> addChildListener(String path, ChildListener listener);

    void removeChildListener(String path, ChildListener listener);

    void addStateListener(StateListener listener);

    void removeStateListener(StateListener listener);

    boolean isConnected();

    void close();

    /**
     * @return zookeeper url client connect
     */
    URL getUrl();

}
