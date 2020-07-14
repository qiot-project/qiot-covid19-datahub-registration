package com.redhat.qiot.datahub.endpoint.domain.action;

import java.util.Date;

import org.bson.codecs.pojo.annotations.BsonProperty;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class MeasurementStationAction {
    public int stationId;
    public Date time;
    public MeasurementStationActionLevelEnum action;
    @BsonProperty(value = "type")
    public MeasurementStationActionTypeEnum actionType;

    @Override
    public String toString() {
        return "MeasurementStationHistory [stationId=" + stationId
                + ", timestamp=" + time + ", action=" + action + ", actionType="
                + actionType + "]";
    }

}
