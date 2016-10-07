package com.bow.demo;

import com.bow.common.ExtensionLoader;
import com.bow.common.pipeline.LoadBalance;
import com.bow.config.ShineConfig;
import com.bow.registry.RegistryService;
import com.bow.rpc.Protocol;
import org.junit.Test;

import javax.xml.stream.events.Characters;

/**
 * @author vv
 * @since 2016/10/2.
 */
public class ExtensionLoaderTest {
    @Test
    public void getClassName() {
        System.out.println(this.getClass().getSimpleName().toLowerCase());
    }

    @Test
    public void getExtension() {
        Calculator calculator = ExtensionLoader.getExtensionLoader(Calculator.class).getExtension();
        int result = calculator.calculate(1, 1);
        System.out.println(result);
    }

    @Test
    public void getProtocol(){
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension();
        System.out.println(protocol.getName());
    }

    @Test
    public void load2times(){
        RegistryService registry = ExtensionLoader.getExtensionLoader(RegistryService.class)
                .getExtension(ShineConfig.getRegistryType());
        RegistryService registry2 = ExtensionLoader.getExtensionLoader(RegistryService.class)
                .getExtension(ShineConfig.getRegistryType());
    }

    @Test
    public void loadBalance(){
        LoadBalance loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class)
                .getExtension(ShineConfig.getLoadBalance());
        System.out.println(loadBalance);
    }
}
