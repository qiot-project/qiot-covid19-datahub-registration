package io.qiot.covid19.datahub.registration.certmanager.api.model;

import java.io.Serializable;
import java.util.HashMap;
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
@JsonPropertyOrder({ "jks", "pkcs12" })
@EqualsAndHashCode
@Builder
@RegisterForReflection
@NoArgsConstructor
@AllArgsConstructor
public class CertificateKeystoresSpec
        implements UnknownPropertyPreserving, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private KeystoreSpec jks;
    private KeystoreSpec pkcs12;

    private Map<String, Object> additionalProperties = new HashMap<>(0);

    @Override
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public KeystoreSpec getJks() {
        return jks;
    }

    public void setJks(KeystoreSpec jks) {
        this.jks = jks;
    }

    public KeystoreSpec getPkcs12() {
        return pkcs12;
    }

    public void setPkcs12(KeystoreSpec pkcs12) {
        this.pkcs12 = pkcs12;
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
