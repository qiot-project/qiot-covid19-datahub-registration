package org.qiot.covid19.datahub.registration.config;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.qiot.covid19.datahub.registration.certmanager.client.CertificateOperation;
import org.qiot.covid19.datahub.registration.certmanager.client.SecretOperation;
import org.qiot.covid19.datahub.registration.service.CertificateService;
import org.qiot.covid19.datahub.registration.service.impl.CertManagerCertificateService;
import org.qiot.covid19.datahub.registration.service.impl.DefaultCertificateService;
import org.slf4j.Logger;

import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.properties.IfBuildProperty;

/**
 * @author mmascia 
 */
@Dependent
public class CertificateServiceConfiguration {

    @ConfigProperty(name = "qiot.cert-manager.issuer")
    String issuer;
    @ConfigProperty(name = "qiot.cert-manager.domain")
    String domain;
    @Inject
    Logger LOGGER;

    @Produces
    @IfBuildProperty(name = "qiot.cert-manager.enabled", stringValue = "true")
    public CertificateService certManagerCertificateService(CertificateOperation certificateOperation, SecretOperation secretOperation) {
        return new CertManagerCertificateService(certificateOperation, secretOperation, LOGGER, issuer, domain);
    }

    @Produces
    @DefaultBean
    public CertificateService defaultCertificateService() {
        return new DefaultCertificateService(LOGGER);
    }
}
