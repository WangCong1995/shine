package com.bow.remoting.hessian;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.common.pipeline.DefaultServerPipeline;
import com.bow.common.pipeline.DefaultShinePipeline;
import com.bow.common.pipeline.InvokeServiceHandler;
import com.bow.common.utils.NetUtil;
import com.bow.config.ShineConfig;
import com.bow.remoting.ShineServer;
import com.bow.rpc.Request;
import com.bow.rpc.RequestHandler;
import com.bow.rpc.Response;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vv
 * @since 2016/8/20.
 */
public class HessianServer implements ShineServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HessianServer.class);

    private RequestHandler requestHandler;

    private int port;

    public HessianServer(int port){
        this.port = port;
    }

    public void start() {
        if(requestHandler == null){
            throw new ShineException(ShineExceptionCode.fail,"requestHandler must not be null in jetty server");
        }

        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
//        connector.setHost(NetUtil.getLocalHostAddress());
        connector.setPort(port);
        server.addConnector(connector);

        // 添加servlet
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ServletHolder servletHolder = new ServletHolder(new HessianDispatcherServlet());
        context.addServlet(servletHolder,"/*");


        try {
            server.start();
            LOGGER.info("----- success to start jetty server -----");
        } catch (Exception e) {
            throw new ShineException(ShineExceptionCode.fail,"fail to start jetty server");
        }
    }

    public void stop() {
        //no-op
    }


    @Override
    public void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
        //在管道的最后加上响应handler
        InvokeServiceHandler invokeHandler = new InvokeServiceHandler(requestHandler);
        DefaultServerPipeline.getInstance().addLast(invokeHandler);
    }
}
