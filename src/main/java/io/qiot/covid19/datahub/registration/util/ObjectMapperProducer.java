package io.qiot.covid19.datahub.registration.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class ObjectMapperProducer.
 *
 * @author andreabattaglia
 */
@ApplicationScoped
public class ObjectMapperProducer {

    /** The mapper. */
    private final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Gets the logger.
     *
     * @param ip the ip
     * @return the logger
     */
    @Produces
    public ObjectMapper getLogger(final InjectionPoint ip) {
        return MAPPER;
    }
}