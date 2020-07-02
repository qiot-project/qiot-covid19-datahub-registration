package com.redhat.qiot.datahub.endpoint.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@Entity
@Table(name = "measurementstation")
public class MeasurementStation extends PanacheEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 16, unique = true)
    private String serial;
    private double longitude;
    private double latitude;
    private boolean active;

    /**
     * @return the id
     */
    public int getId() {
	return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
	this.id = id;
    }

    /**
     * @return the serial
     */
    public String getSerial() {
	return serial;
    }

    /**
     * @param serial the serial to set
     */
    public void setSerial(String serial) {
	this.serial = serial;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
	return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
	this.longitude = longitude;
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
	return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
	this.latitude = latitude;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
	return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
	this.active = active;
    }

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
	return "MeasurementStation [id=" + id + ", serial=" + serial + ", longitude=" + longitude + ", latitude="
		+ latitude + ", active=" + active + "]";
    }

}
