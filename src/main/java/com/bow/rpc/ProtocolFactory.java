package com.bow.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Created by vv on 2016/8/20.
 */
public class ProtocolFactory {

    private static Map<String,Protocol> protocolMap = new HashMap<>();

    static {
        init();
    }

    private static void init(){
        ServiceLoader<Protocol> sl = ServiceLoader.load(Protocol.class);
        Iterator<Protocol> it = sl.iterator();
        while(it.hasNext()){
            Protocol service = it.next();
            protocolMap.put(service.getName(),service);
        }
    }

    public static Protocol getProtocol(String protocol){
        return protocolMap.get(protocol);
    }

}
