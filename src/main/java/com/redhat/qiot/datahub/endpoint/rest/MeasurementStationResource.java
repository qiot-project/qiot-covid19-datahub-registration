package com.redhat.qiot.datahub.endpoint.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redhat.qiot.datahub.endpoint.service.DataStoreService;

@Path("/measurementstation")
@ApplicationScoped
public class MeasurementStationResource {

    @Inject
    Logger LOGGER;

    @Inject
    DataStoreService dsService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String getById(@QueryParam("stationId") String stationId)
            throws JsonProcessingException {
        LOGGER.info("Received an info request for measurement station {}",
                stationId);
        int id = Integer.parseInt(stationId);
        return dsService.getMeasurementStation(id);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String getBySerial(@QueryParam("serial") String serial)
            throws JsonProcessingException {
        LOGGER.info("Received an info request for measurement station {}",
                serial);
        return dsService.getMeasurementStation(serial);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String getById() throws JsonProcessingException {
        LOGGER.info("Received an info request for all measurement stations");
        return dsService.getAllMeasurementStations();
    }

}
