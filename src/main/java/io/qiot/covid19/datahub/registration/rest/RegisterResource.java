package io.qiot.covid19.datahub.registration.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.spi.NotImplementedYetException;
import org.slf4j.Logger;

import io.qiot.covid19.datahub.registration.client.StationServiceClient;
import io.qiot.covid19.datahub.registration.rest.beans.RegisterRequest;
import io.qiot.covid19.datahub.registration.rest.beans.RegisterResponse;
import io.qiot.covid19.datahub.registration.service.CertificateService;

@Path("/register")
@ApplicationScoped
public class RegisterResource {

    @Inject
    @RestClient
    StationServiceClient stationServiceClient;
    @Inject
    CertificateService certificateService;

    @Inject
    Logger LOGGER;

    /**
     * Creates a new instance of a `RegisterRequest`.
     */
    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(RegisterRequest data) throws Exception {
        LOGGER.debug("Received registerRequest: {}", data);

        RegisterResponse response = certificateService.provision(data);
        LOGGER.debug(
                "Successfully provisioned certificates for the registration request \n{}",
                data);

        response.setId(stationServiceClient.add(data.getSerial(),
                data.getName(), data.getLongitude(), data.getLongitude()));
        LOGGER.debug(
                "Successfully provisioned a new station ID ({}) for the registration request \n{}",
                response.getId(), data);

        LOGGER.debug("Create response: {}", response);
        return Response.ok(response, MediaType.APPLICATION_JSON).build();
    }

    // /**
    // * Gets the details of a single instance of a `RegisterRequest`.
    // */
    // @Path("/{id}")
    // @GET
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response getRegister(String id) {
    // throw new NotImplementedYetException();
    // }

    // /**
    // * Updates an existing `RegisterRequest`.
    // */
    // @Path("/{id}")
    // @PUT
    // @Consumes(MediaType.APPLICATION_JSON)
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response updateRegisterRequest(RegisterRequest data) {
    // throw new NotImplementedYetException();
    // }

    // /**
    // * Deletes an existing `RegisterRequest`.
    // */
    // @Path("/{id}")
    // @DELETE
    // public void deleteRegisterRequest() {
    // throw new NotImplementedYetException();
    // }

}
