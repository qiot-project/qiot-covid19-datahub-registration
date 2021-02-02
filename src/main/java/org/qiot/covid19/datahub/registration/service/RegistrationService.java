/**
 * 
 */
package org.qiot.covid19.datahub.registration.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.qiot.covid19.datahub.registration.client.StationServiceClient;
import org.slf4j.Logger;

/**
 * @author abattagl
 *
 */
@ApplicationScoped
public class RegistrationService {

    @Inject
    Logger LOGGER;

    @Inject
    @RestClient
    StationServiceClient stationService;

    public String register(String serial, String name, double longitude,
            double latitude) {
        return stationService.add(serial, name, longitude, latitude);
    }
}
