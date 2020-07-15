/**
 * 
 */
package com.redhat.qiot.datahub.endpoint.service;

import java.util.Date;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.qiot.datahub.endpoint.domain.action.MeasurementStationAction;
import com.redhat.qiot.datahub.endpoint.domain.action.MeasurementStationActionLevelEnum;
import com.redhat.qiot.datahub.endpoint.domain.action.MeasurementStationActionTypeEnum;
import com.redhat.qiot.datahub.endpoint.domain.station.MeasurementStation;
import com.redhat.qiot.datahub.endpoint.persistence.MeasurementStationActionRepository;
import com.redhat.qiot.datahub.endpoint.persistence.MeasurementStationRepository;

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

    @Inject
    ObjectMapper MAPPER;

    @Override
    public String getMeasurementStation(int id) throws JsonProcessingException {
        MeasurementStation ms = msRepository.findById(id);
        if (ms == null)
            return null;
        return MAPPER.writeValueAsString(ms);
    }

    @Override
    public String getMeasurementStation(String serial)
            throws JsonProcessingException {
        MeasurementStation ms = msRepository.findBySerial(serial);
        if (ms == null)
            return null;
        return MAPPER.writeValueAsString(ms);
    }

    @Override
    public String getAllMeasurementStations() throws JsonProcessingException {
        return MAPPER.writeValueAsString(msRepository.findAll());
    }

    // TODO
    @Override
    public String register(String serial, String name, double longitude,
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
        return Integer.toString(stationId);
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
