package com.bow.demo.unit;

import com.bow.common.Version;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author vv
 * @since 2016/12/10.
 */
public class VersionTest {

    @Test
    public void matches(){
        Version v1 = new Version("3.2.1");
        Version v2 = new Version("3.2.2");
        Version v3 = new Version("3.2");
        Version v4 = new Version();//没指定版本
        Version v5 = new Version("3.2.1.1.1");//非法，同没指定版本
        Version v6 = new Version("1.1.1");

        Assert.assertFalse(v1.matches(v2));
        Assert.assertFalse(v1.matches(v3));
        //v3版本 能调用v1版本的服务
        Assert.assertTrue(v3.matches(v1));
        Assert.assertTrue(v4.matches(v1));
        Assert.assertTrue(v5.matches(v1));
        Assert.assertTrue(v5.matches(v4));
        Assert.assertTrue(v4.matches(v5));
        Assert.assertFalse(v1.matches(v4));
        Assert.assertFalse(v1.matches(v5));
        //不同版本之间不能相互调用
        Assert.assertFalse(v6.matches(v1));
        Assert.assertFalse(v1.matches(v6));
    }
}
