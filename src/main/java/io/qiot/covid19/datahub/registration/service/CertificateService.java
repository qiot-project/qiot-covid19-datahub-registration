/**
 * 
 */
package io.qiot.covid19.datahub.registration.service;

import io.qiot.covid19.datahub.registration.exception.CertificateProvisionException;
import io.qiot.covid19.datahub.registration.rest.beans.RegisterRequest;
import io.qiot.covid19.datahub.registration.rest.beans.RegisterResponse;

/**
 * @author mmascia
 * @author andreabattaglia
 *
 **/
public interface CertificateService {

    public RegisterResponse provision(RegisterRequest registerRequest, String id)
            throws CertificateProvisionException;
}
