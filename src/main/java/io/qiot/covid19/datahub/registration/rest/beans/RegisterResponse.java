
package io.qiot.covid19.datahub.registration.rest.beans;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyDescription;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.ToString;

/**
 * 
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonbPropertyOrder({ "id", "truststore", "keystore" })
@Builder
@ToString
public class RegisterResponse {

    /**
     * 
     * (Required)
     * 
     */
    @JsonbProperty("id")
//    @JsonbPropertyDescription("")
    private String id;
    /**
     * 
     * (Required)
     * 
     */
    @JsonbProperty("truststore")
//    @JsonbPropertyDescription("")
    private String truststore;
    /**
     * 
     * (Required)
     * 
     */
    @JsonbProperty("keystore")
//    @JsonbPropertyDescription("")
    private String keystore;

    /**
     * 
     * (Required)
     * 
     */
    @JsonbProperty("id")
    public String getId() {
        return id;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonbProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonbProperty("truststore")
    public String getTruststore() {
        return truststore;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonbProperty("truststore")
    public void setTruststore(String truststore) {
        this.truststore = truststore;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonbProperty("keystore")
    public String getKeystore() {
        return keystore;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonbProperty("keystore")
    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }

}
