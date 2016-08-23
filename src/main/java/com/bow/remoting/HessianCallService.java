package com.bow.remoting;

import com.bow.rpc.Message;
import com.bow.rpc.Result;

/**
 * Created by vv on 2016/8/21.
 */
public interface HessianCallService {
    Result call(Message message);
}
