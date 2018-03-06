package com.bow.demo.module.resteasy;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author vv
 * @since 2018/3/5.
 */
@Path("cal")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public interface ICalc {

    String MEDIA_TYPE_JSON_UTF8 = MediaType.APPLICATION_JSON + ";charset=UTF-8";

    @GET
    @Path("{id}")
    @Produces(MEDIA_TYPE_JSON_UTF8)
    int print(int id);
}
