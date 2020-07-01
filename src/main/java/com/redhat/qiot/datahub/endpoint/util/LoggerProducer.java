package com.redhat.qiot.datahub.endpoint.util;


import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ApplicationScoped
public class LoggerProducer {

    @Produces
    public Logger getLogger(final InjectionPoint ip) {
        return LoggerFactory
                .getLogger(ip.getMember().getDeclaringClass());
    }
}