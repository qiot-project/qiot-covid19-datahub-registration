package org.qiot.covid19.datahub.registration.rest;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.qiot.covid19.datahub.registration.domain.station.MeasurementStation;
import org.qiot.covid19.datahub.registration.service.DataStoreService;
import org.slf4j.Logger;

@Path("/measurementstation")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.TEXT_PLAIN)
public class MeasurementStationResource {

    @Inject
    Logger LOGGER;

    @Inject
    DataStoreService dsService;

    @GET
    public Set<MeasurementStation> getAll() {
        LOGGER.info("Received an info request for all measurement stations");
        return dsService.getAllMeasurementStations();
    }

    @GET
    @Path("/id/{id}")
    public MeasurementStation getById(@PathParam("id") int id) {
        LOGGER.info("Received an info request for measurement station {}", id);
        return dsService.getMeasurementStation(id);
    }

    @GET
    @Path("/serial/{serial}")
    public MeasurementStation getBySerial(@PathParam("serial") String serial) {
        LOGGER.info("Received an info request for measurement station {}",
                serial);
        return dsService.getMeasurementStation(serial);
    }

}
