//package org.qiot.covid19.datahub.registration.rest;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;
//import javax.transaction.Transactional;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//
//import org.qiot.covid19.datahub.registration.domain.MultipartBody;
//import org.qiot.covid19.datahub.registration.service.CertificateService;
//import org.qiot.covid19.datahub.registration.service.RegistrationService;
//
//@Path("/echo")
//@ApplicationScoped
//public class EchoService {
//
//    @Inject
//    RegistrationService registrationService;
//    @Inject
//    CertificateService certificateService;
//
//    @Transactional
//    @PUT
//    @Path("/serial/{serial}/name/{name}/longitude/{longitude}/latitude/{latitude}")
//    @Consumes(MediaType.TEXT_PLAIN)
//    @Produces(MediaType.MULTIPART_FORM_DATA)
//    public MultipartBody echo(@PathParam("serial") String serial,
//            @PathParam("name") String name,
//            @PathParam("longitude") double longitude,
//            @PathParam("latitude") double latitude) throws Exception {
//        MultipartBody response = new MultipartBody();
//
//        // Register the station through the station-service
//        response.id = registrationService.register(serial, name, longitude,
//                latitude);
//        // Provision the trust store through the cert-issuer
//        response.ts = certificateService.provision();
//
//        return response;
//    }
//}