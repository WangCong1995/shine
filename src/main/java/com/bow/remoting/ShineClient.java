package com.bow.remoting;

import com.bow.rpc.Message;
import com.bow.rpc.Result;

import java.net.URL;


/**
 * Created by vv on 2016/8/19.
 */
public interface ShineClient {
    Result call(URL url, Message message);
}
