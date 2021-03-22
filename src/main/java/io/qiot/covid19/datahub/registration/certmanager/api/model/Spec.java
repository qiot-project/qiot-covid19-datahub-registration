package io.qiot.covid19.datahub.registration.certmanager.api.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import static java.util.Collections.emptyMap;

import java.io.Serializable;

/**
 * @author mmascia
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@ToString
public abstract class Spec implements UnknownPropertyPreserving, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Map<String, Object> additionalProperties = new HashMap<>(0);

    @Override
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties != null ? this.additionalProperties
                : emptyMap();
    }

    @Override
    public void setAdditionalProperty(String name, Object value) {
        if (this.additionalProperties == null) {
            this.additionalProperties = new HashMap<>(1);
        }
        this.additionalProperties.put(name, value);
    }
}
