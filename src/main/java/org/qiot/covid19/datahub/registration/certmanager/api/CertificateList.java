package org.qiot.covid19.datahub.registration.certmanager.api;

import org.qiot.covid19.datahub.registration.certmanager.api.model.Certificate;

import io.fabric8.kubernetes.client.CustomResourceList;

/**
 * @author mmascia
 */
public class CertificateList extends CustomResourceList<Certificate> {
    private static final long serialVersionUID = 1L;
}
