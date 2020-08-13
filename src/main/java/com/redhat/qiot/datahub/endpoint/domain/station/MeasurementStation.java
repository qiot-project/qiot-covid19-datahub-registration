package com.redhat.qiot.datahub.endpoint.domain.station;

import org.bson.codecs.pojo.annotations.BsonId;

import com.mongodb.client.model.geojson.Point;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
// @MongoEntity(collection = "measurementstation")
public class MeasurementStation implements Comparable<MeasurementStation> {
    @BsonId
    public int id;
    public String serial;
    public String name;
    public Point location;
    public boolean active;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((serial == null) ? 0 : serial.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MeasurementStation other = (MeasurementStation) obj;
        if (serial == null) {
            if (other.serial != null)
                return false;
        } else if (!serial.equals(other.serial))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "MeasurementStation [id=" + id + ", serial=" + serial
                + ", coordinates=" + location + ", active=" + active + "]";
    }

    @Override
    public int compareTo(MeasurementStation o) {
        return Integer.compare(id, o.id);
    }

}
