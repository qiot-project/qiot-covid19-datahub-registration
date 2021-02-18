package org.qiot.covid19.datahub.registration.certmanager.api.model.status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.qiot.covid19.datahub.registration.certmanager.api.model.UnknownPropertyPreserving;

import io.fabric8.kubernetes.api.model.Condition;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import static java.util.Collections.emptyMap;

/**
 * @author mmascia
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@ToString
public abstract class Status implements UnknownPropertyPreserving, Serializable {
    private long observedGeneration;
    private Map<String, Object> additionalProperties;

    public abstract List<Condition> getConditions();

    public abstract void setConditions(List<Condition> conditions);

    private List<Condition> prepareConditionsUpdate() {
        List<Condition> oldConditions = getConditions();
        List<Condition> newConditions = oldConditions != null ? new ArrayList<>(oldConditions) : new ArrayList<>(0);
        return newConditions;
    }

    public void addCondition(Condition condition) {
        List<Condition> newConditions = prepareConditionsUpdate();
        newConditions.add(condition);
        setConditions(Collections.unmodifiableList(newConditions));
    }

    public void addConditions(Collection<Condition> conditions) {
        List<Condition> newConditions = prepareConditionsUpdate();
        newConditions.addAll(conditions);
        setConditions(Collections.unmodifiableList(newConditions));
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public long getObservedGeneration() {
        return observedGeneration;
    }

    public void setObservedGeneration(long observedGeneration) {
        this.observedGeneration = observedGeneration;
    }

    @Override
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties != null ? this.additionalProperties : emptyMap();
    }

    @Override
    public void setAdditionalProperty(String name, Object value) {
        if (this.additionalProperties == null) {
            this.additionalProperties = new HashMap<>(1);
        }
        this.additionalProperties.put(name, value);
    }
}
