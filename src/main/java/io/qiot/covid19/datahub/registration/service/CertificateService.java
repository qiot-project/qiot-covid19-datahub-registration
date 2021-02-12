/**
 * 
 */
package io.qiot.covid19.datahub.registration.service;

import java.io.InputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;

import io.qiot.covid19.datahub.registration.domain.TruststoreBean;

/**
 * @author abattagl
 *
 */
@ApplicationScoped
public class CertificateService {

    @Inject
    Logger LOGGER;

    boolean notprod;

    @ConfigProperty(name = "app.certlocal")
    boolean certlocal;

    public TruststoreBean provision() {
        TruststoreBean truststore=new TruststoreBean();
        // // add the cert to the response body
        if (certlocal) {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream certFile = loader.getResourceAsStream("client.ts");
            LOGGER.debug("input stream of the trust store: {}", certFile);
            truststore.is=certFile;
            truststore.password="123456";
            return truststore;
        }
        // TODO: implement connection to cert-issuer
        return null;
    }
}
