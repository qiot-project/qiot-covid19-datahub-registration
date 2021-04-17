
package io.qiot.covid19.datahub.registration.rest.beans;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyDescription;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.ToString;

/**
 * Root Type for RegisterRequest
 * <p>
 * 
 * 
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonbPropertyOrder({ "serial", "name", "longitude", "latitude",
        "keyStorePassword" })
@ToString
public class RegisterRequest {

    @JsonbProperty(value = "serial", nillable = false)
    private String serial;
    
    @JsonbProperty(value = "name", nillable = false)
    @NotNull
    @Pattern(regexp = "[a-z0-9]([-a-z0-9]*[a-z0-9])?(\\.[a-z0-9]([-a-z0-9]*[a-z0-9])?)*")
    private String name;
    
    @JsonbProperty(value = "longitude", nillable = false)
    @DecimalMin(value="-180.0")
    @DecimalMax(value="180.0")
    @NotNull
    private double longitude;
    
    @JsonbProperty(value = "latitude", nillable = false)
    @DecimalMin(value="-90.0")
    @DecimalMax(value="90.0")
    @NotNull
    private double latitude;
    /**
     * KeyStore Password
     * 
     */
    @JsonbProperty("keyStorePassword")
//    @JsonbPropertyDescription("KeyStore Password")
    private String keyStorePassword;

    @JsonbProperty("serial")
    public String getSerial() {
        return serial;
    }

    @JsonbProperty("serial")
    public void setSerial(String serial) {
        this.serial = serial;
    }

    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    @JsonbProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonbProperty("longitude")
    public double getLongitude() {
        return longitude;
    }

    @JsonbProperty("longitude")
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @JsonbProperty("latitude")
    public double getLatitude() {
        return latitude;
    }

    @JsonbProperty("latitude")
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * KeyStore Password
     * 
     */
    @JsonbProperty("keyStorePassword")
    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    /**
     * KeyStore Password
     * 
     */
    @JsonbProperty("keyStorePassword")
    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

}
