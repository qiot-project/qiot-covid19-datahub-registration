/**
 * 
 */
package org.qiot.covid19.datahub.registration.service;

import org.qiot.covid19.datahub.registration.rest.beans.RegisterRequest;
import org.qiot.covid19.datahub.registration.rest.beans.RegisterResponse;


/**
 * @author mmascia
 *
 **/
public interface CertificateService {

    public RegisterResponse provision(RegisterRequest registerRequest);
}
