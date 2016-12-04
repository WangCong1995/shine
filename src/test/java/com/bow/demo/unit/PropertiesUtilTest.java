package com.bow.demo.unit;

import com.bow.common.utils.PropertiesUtil;
import org.junit.Test;

/**
 * @author vv
 * @since 2016/12/4.
 */
public class PropertiesUtilTest {

    @Test
    public void getInt() {
        int result = PropertiesUtil.getInt("minIdle", 0);
        int port = PropertiesUtil.getInt("service.port", 0);
        System.out.println(result);
        System.out.println(port);
    }
}
