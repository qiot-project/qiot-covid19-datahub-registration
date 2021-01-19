package org.qiot.covid19.datahub.registration.domain.station;

import org.bson.codecs.pojo.annotations.BsonId;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Counters {
    @BsonId
    public String id;
    public int seq;

}
