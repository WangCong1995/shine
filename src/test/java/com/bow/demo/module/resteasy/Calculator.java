package com.bow.demo.module.resteasy;


import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author vv
 * @since 2016/12/12.
 */
@Component
@Path("cal")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class Calculator implements ICalc{

    @GET
    @Path("{id : \\d+}")
    @Produces(MEDIA_TYPE_JSON_UTF8)
    public int print(@PathParam("id")int id) {
        return id;
    }


}
