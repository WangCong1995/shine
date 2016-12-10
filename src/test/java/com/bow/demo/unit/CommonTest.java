package com.bow.demo.unit;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author vv
 * @since 2016/12/10.
 */
public class CommonTest {

    @Test
    public void getName(){
        String name = this.getClass().getSimpleName();
        String first = name.substring(0,1);
        String remain = name.substring(1);
        name = first.toLowerCase()+remain;
        System.out.println(name);
    }
}
