/**
 * 
 */
package org.qiot.covid19.datahub.registration.service;

import java.util.Date;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.qiot.covid19.datahub.registration.domain.action.MeasurementStationAction;
import org.qiot.covid19.datahub.registration.domain.action.MeasurementStationActionLevelEnum;
import org.qiot.covid19.datahub.registration.domain.action.MeasurementStationActionTypeEnum;
import org.qiot.covid19.datahub.registration.domain.station.MeasurementStation;
import org.qiot.covid19.datahub.registration.persistence.MeasurementStationActionRepository;
import org.qiot.covid19.datahub.registration.persistence.MeasurementStationRepository;

/**
 * @author abattagl
 *
 */
@ApplicationScoped
public class DataStoreServiceImpl implements DataStoreService {

    @Inject
    MeasurementStationRepository msRepository;
    @Inject
    MeasurementStationActionRepository msaRepository;

    @Override
    public MeasurementStation getMeasurementStation(int id)  {
        return msRepository.findById(id);
    }

    @Override
    public MeasurementStation getMeasurementStation(String serial)
             {
        return msRepository.findBySerial(serial);
    }

    @Override
    public Set<MeasurementStation> getAllMeasurementStations()  {
        return msRepository.findAll();
    }

    @Override
    public int register(String serial, String name, double longitude,
            double latitude) {
        MeasurementStation ms = msRepository.findBySerial(serial);
        int stationId;
        if (ms == null) {
            stationId = msRepository.save(serial, name, longitude, latitude);
        } else {
            stationId = ms.id;
            if (!ms.active)
                msRepository.setActive(ms.id);
        }
        recordRegister(stationId);
        return stationId;
    }

    @Override
    public void unregister(int id) {
        msRepository.setInactive(id);
        recordUnregister(id, true);
    }

    void recordRegister(int stationId) {
        MeasurementStationAction msa = new MeasurementStationAction();
        msa.stationId = stationId;
        msa.time = new Date();
        msa.action = MeasurementStationActionLevelEnum.register;
        msa.actionType = MeasurementStationActionTypeEnum.manual;
        msaRepository.save(msa);
    }

    void recordUnregister(int stationId, boolean manual) {
        MeasurementStationAction msa = new MeasurementStationAction();
        msa.stationId = stationId;
        msa.time = new Date();
        msa.action = MeasurementStationActionLevelEnum.unregister;
        msa.actionType = manual ? MeasurementStationActionTypeEnum.manual
                : MeasurementStationActionTypeEnum.auto;
        msaRepository.save(msa);
    }
}
