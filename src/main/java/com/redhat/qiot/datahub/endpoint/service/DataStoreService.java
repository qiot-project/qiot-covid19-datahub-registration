/**
 * 
 */
package com.redhat.qiot.datahub.endpoint.service;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author abattagl
 *
 */
public interface DataStoreService {

    String getMeasurementStation(int id) throws JsonProcessingException;
    String getMeasurementStation(String serial) throws JsonProcessingException;

    String getAllMeasurementStations() throws JsonProcessingException;

    String register(String serial, double longitude, double latitude);

    void unregister(int id);

}
