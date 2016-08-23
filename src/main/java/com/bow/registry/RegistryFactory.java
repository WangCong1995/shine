package com.bow.registry;

import com.bow.rpc.Protocol;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Created by vv on 2016/8/21.
 */
public class RegistryFactory {
    private static Map<String,RegistryService> RegistryServiceMap = new HashMap<>();

    static {
        init();
    }

    private static void init(){
        ServiceLoader<RegistryService> sl = ServiceLoader.load(RegistryService.class);
        Iterator<RegistryService> it = sl.iterator();
        while(it.hasNext()){
            RegistryService service = it.next();
            RegistryServiceMap.put(service.getName(),service);
        }
    }

    public static RegistryService getRegistryService(String registryServiceName){
        return RegistryServiceMap.get(registryServiceName);
    }
}
