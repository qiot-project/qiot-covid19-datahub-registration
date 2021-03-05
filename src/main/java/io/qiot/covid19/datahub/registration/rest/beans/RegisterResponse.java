
package io.qiot.covid19.datahub.registration.rest.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.ToString;

/**
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "truststore", "keystore" })
@Builder
@ToString
public class RegisterResponse {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("")
    private String id;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("truststore")
    @JsonPropertyDescription("")
    private String truststore;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("keystore")
    @JsonPropertyDescription("")
    private String keystore;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("truststore")
    public String getTruststore() {
        return truststore;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("truststore")
    public void setTruststore(String truststore) {
        this.truststore = truststore;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("keystore")
    public String getKeystore() {
        return keystore;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("keystore")
    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }

}
