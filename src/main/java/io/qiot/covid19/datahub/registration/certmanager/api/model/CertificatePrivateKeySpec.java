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
@JsonPropertyOrder({ "rotationPolicy", "algorithm", "encoding", "size" })
@EqualsAndHashCode
@Builder
@RegisterForReflection
@NoArgsConstructor
@AllArgsConstructor
public class CertificatePrivateKeySpec
        implements UnknownPropertyPreserving, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String rotationPolicy;
    private String algorithm;
    private String encoding;
    private Integer size;

    private Map<String, Object> additionalProperties = new HashMap<>(0);

    @Override
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getRotationPolicy() {
        return rotationPolicy;
    }

    public void setRotationPolicy(String rotationPolicy) {
        this.rotationPolicy = rotationPolicy;
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
