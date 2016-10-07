package com.bow.demo;

import com.bow.rpc.Request;
import org.junit.Test;

/**
 * @author vv
 * @since 2016/10/3.
 */
public class RequestTest {

    @Test
    public void newMessage(){
        for(int i=0;i<3;i++){
            Request message = new Request();
            message.setInterfaceName("com.bow.demo.Calculator");
            message.setMethodName("calculate");
            message.setParameters(new Integer[]{1,2});

            System.out.println(message.getId()+"--"+message);
        }

    }
}
