package org.qiot.covid19.datahub.registration.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.qiot.covid19.datahub.registration.service.CertificateService;
import org.qiot.covid19.datahub.registration.service.RegistrationService;

@Path("/register")
@ApplicationScoped
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class RegisterResource {

    @Inject
    RegistrationService registrationService;
    @Inject
    CertificateService certificateService;

    @Transactional
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public Response register(@QueryParam("serial") @NotNull String serial,
            @QueryParam("name") @NotNull String name,
            @QueryParam("longitude") double longitude,
            @QueryParam("latitude") double latitude) throws Exception {
        MultipartFormDataOutput output = new MultipartFormDataOutput();
        output.addFormData("ts", certificateService.provision(),
                MediaType.APPLICATION_OCTET_STREAM_TYPE);
        output.addFormData("id",
                registrationService.register(serial, name, longitude, latitude),
                MediaType.TEXT_PLAIN_TYPE);

        return Response.ok(output, MediaType.MULTIPART_FORM_DATA_TYPE).build();
    }

    // @Transactional
    // @PUT
    // @Consumes(MediaType.TEXT_PLAIN)
    // @Produces(MediaType.MULTIPART_FORM_DATA)
    // public MultipartBody register(@QueryParam("serial") @NotNull String
    // serial,
    // @QueryParam("name") @NotNull String name,
    // @QueryParam("longitude") double longitude,
    // @QueryParam("latitude") double latitude) throws Exception {
    // MultipartBody response = new MultipartBody();
    //
    // // Register the station through the station-service
    // response.id = registrationService.register(serial, name, longitude,
    // latitude);
    // // Provision the trust store through the cert-issuer
    // response.ts = certificateService.provision();
    //
    // return response;
    // }
}
