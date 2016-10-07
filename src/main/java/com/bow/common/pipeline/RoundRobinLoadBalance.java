package com.bow.common.pipeline;

import com.bow.config.Name;
import com.bow.rpc.URL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vv
 * @since 2016/10/7.
 */
@Name("roundRobin")
public class RoundRobinLoadBalance implements LoadBalance {
    private Map<String,Integer> index = new HashMap<>();
    @Override
    public URL select(String serviceName, List<URL> urls) {
        if(index.get(serviceName)==null){
            index.put(serviceName,0);
            return urls.get(0);
        }else {
            int lastPicked = index.get(serviceName);
            //上次已经选到最后一个了
            if(lastPicked>=urls.size()-1){
                return urls.get(0);
            }else{
                return urls.get(lastPicked+1);
            }
        }
    }
}
