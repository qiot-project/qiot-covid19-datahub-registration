/**
 * 
 */
package io.qiot.covid19.datahub.registration.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Typed;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

import io.qiot.covid19.datahub.registration.exception.CertificateProvisionException;
import io.qiot.covid19.datahub.registration.rest.beans.RegisterRequest;
import io.qiot.covid19.datahub.registration.rest.beans.RegisterResponse;
import io.qiot.covid19.datahub.registration.service.CertificateService;

/**
 * @author andreabattaglia
 * @author mmascia
 *
 */
@ApplicationScoped
@Typed(DefaultCertificateService.class)
public class DefaultCertificateService implements CertificateService {

    final Logger LOGGER;

    public DefaultCertificateService(Logger log) {
        this.LOGGER = log;
    }

    @Override
    public RegisterResponse provision(RegisterRequest data)
            throws CertificateProvisionException {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try (InputStream tsIs = loader.getResourceAsStream("client.ts");
                InputStream ksIs = loader.getResourceAsStream("client.ks")) {
            LOGGER.debug("input stream of the Client KEY store: {}", ksIs);
            LOGGER.debug("input stream of the Client TRUST store: {}", tsIs);

            return RegisterResponse.builder()
                    .keystore(Base64.getEncoder()
                            .encodeToString(IOUtils.toByteArray(ksIs)))
                    .truststore(Base64.getEncoder()
                            .encodeToString(IOUtils.toByteArray(tsIs)))
                    .build();
        } catch (IOException e) {
            throw new CertificateProvisionException(e);
        }
    }

}
