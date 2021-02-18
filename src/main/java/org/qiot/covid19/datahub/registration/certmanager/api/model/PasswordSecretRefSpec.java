package org.qiot.covid19.datahub.registration.certmanager.api.model;

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
@JsonPropertyOrder({ "key", "name" })
@EqualsAndHashCode
@Builder
@RegisterForReflection
@NoArgsConstructor
@AllArgsConstructor
public class PasswordSecretRefSpec implements UnknownPropertyPreserving, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String key;

	private Map<String, Object> additionalProperties = new HashMap<>(0);

	@Override
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@Override
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		YAMLMapper mapper = new YAMLMapper().disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID);
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@JsonProperty(required = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty(required = true)
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}