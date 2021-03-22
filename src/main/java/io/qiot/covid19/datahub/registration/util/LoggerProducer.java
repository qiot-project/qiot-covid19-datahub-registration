package io.qiot.covid19.datahub.registration.util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class LoggerProducer.
 *
 * @author andreabattaglia
 */
@ApplicationScoped
public class LoggerProducer {

    /**
     * Gets the logger.
     *
     * @param ip the ip
     * @return the logger
     */
    @Produces
    public Logger getLogger(final InjectionPoint ip) {
        return LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
    }
}