package com.bow.demo.module.resteasy;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jboss.resteasy.spi.ResteasyDeployment;

/**
 * @author vv
 * @since 2018/3/5.
 */
public class RestServletDispatcher extends HttpServletDispatcher {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.getMediaTypeMappings().put("json", "application/json");
        deployment.getMediaTypeMappings().put("xml", "text/xml");
        deployment.getProviderClasses().add("com.bow.demo.module.resteasy.TraceFilter");
        deployment.start();
        deployment.getRegistry().addResourceFactory(new POJOResourceFactory( Calculator.class));

        ServletContext servletContext = servletConfig.getServletContext();
        servletContext.setAttribute(ResteasyDeployment.class.getName(), deployment);
        super.init(servletConfig);
    }
}
