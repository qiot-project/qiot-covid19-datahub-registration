package com.redhat.qiot.datahub.endpoint.rest;

import java.io.StringReader;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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

    @PUT
    public int register(@QueryParam("stationData") String stationData) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("register(String) - start"); //$NON-NLS-1$
        }

        String serial, name;
        double longitude, latitude;

        JsonObject jsonObject = null;
        try (JsonReader reader = Json
                .createReader(new StringReader(stationData));) {
            jsonObject = reader.readObject();
            serial = jsonObject.getString("serial");
            name = jsonObject.getString("name");
            longitude = jsonObject.getJsonNumber("lon").doubleValue();
            latitude = jsonObject.getJsonNumber("lat").doubleValue();
        }
        int returnint = dsService.register(serial, name, longitude, latitude);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("register(String) - end"); //$NON-NLS-1$
        }
        return returnint;
    }

    @DELETE
    public void unregister(@QueryParam("id") int id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("unregister(int) - start"); //$NON-NLS-1$
        }

        dsService.unregister(id);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("unregister(int) - end"); //$NON-NLS-1$
        }
    }
}
