package com.bow.demo.unit;

import com.bow.common.channel.ChannelPoolConfig;
import com.bow.remoting.netty.NettyClient;
import com.bow.rpc.Request;
import com.bow.rpc.Response;
import com.bow.rpc.URL;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * ChannelPoolTest<br/>
 * 装上连接池后的测试
 *
 * @author vv
 * @since 2016/12/4.
 */
public class ChannelPoolTest {

    @Test
    public void multiThreads() {
        URL url = new URL("127.0.0.1", 9000);
        NettyClient client = new NettyClient(url);

        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Response result = client.call(buildRequest());
                        System.out.println(result.getId() + "--" + result.getValue());
                        try {
                            TimeUnit.SECONDS.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        }


        try {
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Request buildRequest() {
        Request m = new Request();
        m.setInterfaceName("com.bow.demo.Calculator");
        m.setMethodName("calculate");
        Class[] ary = new Class[]{Integer.class, Integer.class};
        m.setParameterTypes(ary);
        m.setParameters(new Integer[]{1, 2});
        return m;
    }

    /**
     * 对连接池进行不同配置，测试其多线程下的性能
     */
    @Test
    public void config() {
        ChannelPoolConfig.get().setMaxIdle(3);
        ChannelPoolConfig.get().setMinIdle(1);
        ChannelPoolConfig.get().setMaxTotal(3);
        ChannelPoolConfig.get().setMaxWaitMillis(5000);
        ChannelPoolConfig.get().setTimeBetweenEvictionRunsMillis(1000*60);
        ChannelPoolConfig.get().setSoftMinEvictableIdleTimeMillis(1000*30);
        ChannelPoolConfig.get().setMinEvictableIdleTimeMillis(1000*30);

        URL url = new URL("127.0.0.1", 9000);
        NettyClient client = new NettyClient(url);

        for (int i = 0; i < 5; i++) {
            final int num = (i + 1) * 2;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    //1号线程循环2次就结束，2号循环4次，3号6次
                    for (int j = 0; j < num; j++) {
                        Response result = client.call(buildRequest());
                        System.out.println(result.getId() + "--" + result.getValue());
                        try {
                            TimeUnit.SECONDS.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        }

        try {
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
