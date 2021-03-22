package io.qiot.covid19.datahub.registration.certmanager.api.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
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
@JsonPropertyOrder({ "create", "passwordSecretRef" })
@EqualsAndHashCode
@Builder
@RegisterForReflection
@NoArgsConstructor
@AllArgsConstructor
public class KeystoreSpec implements UnknownPropertyPreserving, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Boolean create;
    private PasswordSecretRefSpec passwordSecretRef;
    public static final String JKS = "jks";
    public static final String P12 = "p12";
    public static final String KEYSTORE = "keystore";
    public static final String TRUSTSTORE = "truststore";
    public static final String KEYSTORE_KEY_JKS = KEYSTORE + "." + JKS;
    public static final String KEYSTORE_KEY_P12 = KEYSTORE + "." + P12;
    public static final String TRUSTSTORE_KEY_JKS = TRUSTSTORE + "." + JKS;
    public static final String TRUSTSTORE_KEY_P12 = TRUSTSTORE + "." + P12;

    private Map<String, Object> additionalProperties = new HashMap<>(0);

    @Override
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @JsonProperty(required = true)
    public Boolean getCreate() {
        return create;
    }

    public void setCreate(Boolean create) {
        this.create = create;
    }

    @JsonProperty(required = true)
    public PasswordSecretRefSpec getPasswordSecretRef() {
        return passwordSecretRef;
    }

    public void setPasswordSecretRef(PasswordSecretRefSpec passwordSecretRef) {
        this.passwordSecretRef = passwordSecretRef;
    }

    @Override
    public String toString() {
        YAMLMapper mapper = new YAMLMapper()
                .disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
