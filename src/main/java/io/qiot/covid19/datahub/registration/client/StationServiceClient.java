/**
 * 
 */

package io.qiot.covid19.datahub.registration.client;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * @author abattagl
 *
 */
@Path("/v1/station")
@RegisterRestClient(configKey = "station-api")
public interface StationServiceClient {

    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String add(@QueryParam("serial") @NotNull String serial,
            @QueryParam("name") @NotNull String name,
            @QueryParam("longitude") double longitude,
            @QueryParam("latitude") double latitude);

}
