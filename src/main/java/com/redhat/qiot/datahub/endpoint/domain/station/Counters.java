package com.redhat.qiot.datahub.endpoint.domain.station;

import org.bson.codecs.pojo.annotations.BsonId;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Counters {
    @BsonId
    public String id;
    public int seq;

}
