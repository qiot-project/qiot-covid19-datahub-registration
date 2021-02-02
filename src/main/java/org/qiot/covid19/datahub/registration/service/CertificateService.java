/**
 * 
 */
package org.qiot.covid19.datahub.registration.service;

import java.io.InputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;

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

    public InputStream provision() {
        // // add the cert to the response body
        if (certlocal) {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream certFile = loader.getResourceAsStream("client.ts");
            LOGGER.debug("input stream of the trust store: {}", certFile);
            return certFile;
        }
        // TODO: implement connection to cert-issuer
        return null;
    }
}
