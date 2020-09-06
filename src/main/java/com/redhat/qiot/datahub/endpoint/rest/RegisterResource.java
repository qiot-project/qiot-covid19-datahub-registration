package com.redhat.qiot.datahub.endpoint.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import com.redhat.qiot.datahub.endpoint.service.DataStoreService;

@Path("/register")
@ApplicationScoped
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class RegisterResource {

    @Inject
    Logger LOGGER;

    @Inject
    DataStoreService dsService;

    // @PUT
    // public int register(@QueryParam("stationData") String stationData) {
    // if (LOGGER.isDebugEnabled()) {
    // LOGGER.debug("register(String) - start");
    // }
    //
    // String serial, name;
    // double longitude, latitude;
    //
    // JsonObject jsonObject = null;
    // try (JsonReader reader = Json
    // .createReader(new StringReader(stationData));) {
    // jsonObject = reader.readObject();
    // serial = jsonObject.getString("serial");
    // name = jsonObject.getString("name");
    // longitude = jsonObject.getJsonNumber("lon").doubleValue();
    // latitude = jsonObject.getJsonNumber("lat").doubleValue();
    // }
    // int returnint = dsService.register(serial, name, longitude, latitude);
    // if (LOGGER.isDebugEnabled()) {
    // LOGGER.debug("register(String) - end");
    // }
    // return returnint;
    // }

    @PUT
    @Path("/serial/{serial}/name/{name}/longitude/{longitude}/latitude/{latitude}")
    public int register(@PathParam("serial") String serial,
            @PathParam("name") String name,
            @PathParam("longitude") double longitude,
            @PathParam("latitude") double latitude) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("register(String) - start");
        }

        int returnint = dsService.register(serial, name, longitude, latitude);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("register(String) - end");
        }
        return returnint;
    }

    @DELETE
    @Path("/id/{id}")
    public void unregister(@PathParam("id") int id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("unregister(int) - start");
        }

        dsService.unregister(id);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("unregister(int) - end");
        }
    }
}
