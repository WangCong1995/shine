package com.bow.common.pipeline;

import com.bow.config.Name;
import com.bow.rpc.URL;

import java.util.List;
import java.util.Random;

/**
 * @author vv
 * @since 2016/10/7.
 */
@Name(LoadBalance.RANDOM)
public class RandomLoadBalance implements LoadBalance {
    private Random random = new Random();

    @Override
    public URL select(String serviceName, List<URL> urls) {
        int pickedIndex = random.nextInt(urls.size());
        return urls.get(pickedIndex);
    }
}
