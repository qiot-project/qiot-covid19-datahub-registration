package com.redhat.qiot.datahub.endpoint.service;


import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NoResultException;


import com.redhat.qiot.datahub.endpoint.domain.MeasurementStation;


import io.quarkus.hibernate.orm.panache.PanacheRepository;


@ApplicationScoped
public class MeasurementStationRepository
        implements PanacheRepository<MeasurementStation> {

    public MeasurementStation findBySerial(String serial) {
        try {
            return find("serial", serial).singleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public int save(String serial, double longitude,
            double latitude) {
        MeasurementStation ms = new MeasurementStation();
        ms.setSerial(serial);
        ms.setLongitude(longitude);
        ms.setLatitude(latitude);
        ms.setActive(true);
        persist(ms);
        return ms.getId();
    }

    public void setActive(int id) {
        update("active='true' where id=?1", id);
    }

    public void setInactive(int id) {
        update("active='false' where id=?1", id);
    }
}
