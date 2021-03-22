package io.qiot.covid19.datahub.registration.certmanager.api.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author mmascia
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "organizations", "countries", "organizationalUnits",
        "localities", "provinces", "streetAddresses", "postalCodes",
        "serialNumber" })
@EqualsAndHashCode
@Builder
@RegisterForReflection
@NoArgsConstructor
@AllArgsConstructor
public class X509SubjectSpec
        implements UnknownPropertyPreserving, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private List<String> organizations;
    private List<String> countries;
    private List<String> organizationalUnits;
    private List<String> localities;
    private List<String> provinces;
    private List<String> streetAddresses;
    private List<String> postalCodes;
    private List<String> serialNumber;

    private Map<String, Object> additionalProperties = new HashMap<>(0);

    @Override
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getLocalities() {
        return localities;
    }

    public void setLocalities(List<String> localities) {
        this.localities = localities;
    }

    public List<String> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<String> provinces) {
        this.provinces = provinces;
    }

    public List<String> getStreetAddresses() {
        return streetAddresses;
    }

    public void setStreetAddresses(List<String> streetAddresses) {
        this.streetAddresses = streetAddresses;
    }

    public List<String> getPostalCodes() {
        return postalCodes;
    }

    public void setPostalCodes(List<String> postalCodes) {
        this.postalCodes = postalCodes;
    }

    public List<String> getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(List<String> serialNumber) {
        this.serialNumber = serialNumber;
    }

    public List<String> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<String> organizations) {
        this.organizations = organizations;
    }

    public List<String> getOrganizationalUnits() {
        return organizationalUnits;
    }

    public void setOrganizationalUnits(List<String> organizationalUnits) {
        this.organizationalUnits = organizationalUnits;
    }

    @Override
    public String toString() {
        YAMLMapper mapper = new YAMLMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
