package com.bow.remoting.hessian;

import com.bow.rpc.Request;
import com.bow.rpc.Response;

/**
 *
 * @author vv
 * @since 2016/8/21.
 */
public interface HessianCallService {
    Response call(Request request);
}
