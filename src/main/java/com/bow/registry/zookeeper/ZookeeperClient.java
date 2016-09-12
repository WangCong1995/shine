package com.bow.registry.zookeeper;

import com.bow.rpc.URL;

import java.util.List;


public interface ZookeeperClient {

	/**
	 * create a node
	 * @param path path
	 * @param ephemeral ephemeral
     */
	void create(String path, boolean ephemeral);

	/**
	 * delete a node
	 * @param path path
     */
	void delete(String path);

	List<String> getChildren(String path);

	/**
	 * bind listener to path
	 * @param path path
	 * @param listener listener
     * @return List<String>
     */
	List<String> addServiceListener(String path, ServiceListener listener);

	void removeServiceListener(String path, ServiceListener listener);

	void addStateListener(StateListener listener);
	
	void removeStateListener(StateListener listener);

	boolean isConnected();

	void close();

	URL getUrl();

}
