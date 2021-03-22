package io.qiot.covid19.datahub.registration.service.impl;

import java.util.Arrays;
import java.util.Collections;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Typed;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;

import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.api.model.SecretBuilder;
import io.qiot.covid19.datahub.registration.certmanager.api.model.Certificate;
import io.qiot.covid19.datahub.registration.certmanager.api.model.CertificateKeystoresSpec;
import io.qiot.covid19.datahub.registration.certmanager.api.model.CertificateSpec;
import io.qiot.covid19.datahub.registration.certmanager.api.model.KeystoreSpec;
import io.qiot.covid19.datahub.registration.certmanager.api.model.ObjectReferenceSpec;
import io.qiot.covid19.datahub.registration.certmanager.api.model.PasswordSecretRefSpec;
import io.qiot.covid19.datahub.registration.certmanager.client.CertificateOperation;
import io.qiot.covid19.datahub.registration.certmanager.client.SecretOperation;
import io.qiot.covid19.datahub.registration.exception.CertificateProvisionException;
import io.qiot.covid19.datahub.registration.rest.beans.RegisterRequest;
import io.qiot.covid19.datahub.registration.rest.beans.RegisterResponse;
import io.qiot.covid19.datahub.registration.service.CertificateService;

/**
 * @author mmascia
 *
 */
@ApplicationScoped
@Typed(CertManagerCertificateService.class)
public class CertManagerCertificateService implements CertificateService {

    public static final String KEYSTORE_SECRET_PREFIX = "keystore-secret-";
    public static final String KEYSTORE_KEY_PASSWORD = "password";
    public static final String REGISTRATION_QIOT_IO_SERIAL = "registration.qiot.io/serial";
    final CertificateOperation certificateOperation;
    final SecretOperation secretOperation;
    final String issuer;
    final String domain;
    final Logger LOGGER;

    public CertManagerCertificateService(
            CertificateOperation certificateOperation,
            SecretOperation secretOperation, Logger log,
            @ConfigProperty(name = "qiot.cert-manager.issuer") String issuer,
            @ConfigProperty(name = "qiot.cert-manager.domain") String domain) {
        this.certificateOperation = certificateOperation;
        this.secretOperation = secretOperation;
        this.LOGGER = log;
        this.issuer = issuer;
        this.domain = domain;
    }

    @Override
    public RegisterResponse provision(RegisterRequest data)
            throws CertificateProvisionException {
        final String name = data.getName();
        final String commonName = name + "."
                + certificateOperation.getNamespace() + domain;

        final Certificate certificate = Certificate.builder()
                .metadata(new ObjectMetaBuilder().withName(name)
                        .withLabels(Collections.singletonMap(
                                REGISTRATION_QIOT_IO_SERIAL, data.getSerial()))
                        .build())
                .spec(CertificateSpec.builder().secretName(name)
                        .commonName(commonName)
                        .dnsNames(Arrays.asList(new String[] { commonName }))
                        .issuerRef(ObjectReferenceSpec.builder().name(issuer)
                                .build())
                        .keystores(createKeyStoreSecret(data)).build())
                .build();

        certificateOperation.operation().create(certificate);

        LOGGER.debug("Certificate creation {} ", certificate);

        return certificateOperation.isReady(name);
    }

    private CertificateKeystoresSpec
            createKeyStoreSecret(RegisterRequest data) {

        final String keyStorePassword = data.getKeyStorePassword();

        if (keyStorePassword != null && !"".equals(keyStorePassword)) {
            String secretName = KEYSTORE_SECRET_PREFIX + data.getName();

            LOGGER.debug("Secret Keystore creation {} ", secretName);

            secretOperation.operation().create(new SecretBuilder()
                    .withNewMetadata().withName(secretName)
                    .withLabels(Collections.singletonMap(
                            REGISTRATION_QIOT_IO_SERIAL, data.getSerial()))
                    .endMetadata().withStringData(Collections.singletonMap(
                            KEYSTORE_KEY_PASSWORD, keyStorePassword))
                    .build());

            return CertificateKeystoresSpec.builder()
                    .pkcs12(KeystoreSpec.builder().create(true)
                            .passwordSecretRef(PasswordSecretRefSpec.builder()
                                    .key(KEYSTORE_KEY_PASSWORD).name(secretName)
                                    .build())
                            .build())
                    .build();
        }
        throw new IllegalArgumentException(
                "KeyStorePassword is undedifined for serial: "
                        + data.getSerial() + ", name: " + data.getName());
    }

}
