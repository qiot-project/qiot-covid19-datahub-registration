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
    public RegisterResponse provision(RegisterRequest data) {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try (InputStream certFile = loader.getResourceAsStream("client.ts")) {
            LOGGER.debug("input stream of the trust store: {}", certFile);

            String content = Base64.getEncoder()
                    .encodeToString(IOUtils.toByteArray(certFile));
            return RegisterResponse.builder().keystore(content)
                    .truststore(content).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
