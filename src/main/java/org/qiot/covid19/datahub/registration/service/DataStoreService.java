/**
 * 
 */
package org.qiot.covid19.datahub.registration.service;

import java.util.Set;

import org.qiot.covid19.datahub.registration.domain.station.MeasurementStation;

/**
 * @author abattagl
 *
 */
public interface DataStoreService {

    MeasurementStation getMeasurementStation(int id);

    MeasurementStation getMeasurementStation(String serial);

    Set<MeasurementStation> getAllMeasurementStations();

    int register(String serial, String name, double longitude, double latitude);

    void unregister(int id);

}
