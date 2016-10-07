package com.bow.remoting;

import com.bow.rpc.Request;
import com.bow.rpc.Response;

/**
 * Created by vv on 2016/8/21.
 */
public interface HessianCallService {
    Response call(Request message);
}
