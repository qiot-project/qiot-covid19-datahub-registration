package io.qiot.covid19.datahub.registration.certmanager.api.model.status;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.fabric8.kubernetes.api.model.Condition;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author mmascia
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "conditions", "lastFailureTime", "notBefore", "notAfter",
        "renewalTime", "revision", "nextPrivateKeySecretName" })
@EqualsAndHashCode
@ToString(callSuper = true)
@RegisterForReflection
@NoArgsConstructor
@AllArgsConstructor
public class CertificateStatus extends Status {

    private static final long serialVersionUID = 1L;

    private String lastFailureTime;
    private String notBefore;
    private String notAfter;
    private String renewalTime;
    private Integer revision;
    private String nextPrivateKeySecretName;
    private List<Condition> conditions;

    public String getLastFailureTime() {
        return lastFailureTime;
    }

    public void setLastFailureTime(String lastFailureTime) {
        this.lastFailureTime = lastFailureTime;
    }

    public String getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(String notBefore) {
        this.notBefore = notBefore;
    }

    public String getNotAfter() {
        return notAfter;
    }

    public void setNotAfter(String notAfter) {
        this.notAfter = notAfter;
    }

    public String getRenewalTime() {
        return renewalTime;
    }

    public void setRenewalTime(String renewalTime) {
        this.renewalTime = renewalTime;
    }

    public Integer getRevision() {
        return revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public String getNextPrivateKeySecretName() {
        return nextPrivateKeySecretName;
    }

    public void setNextPrivateKeySecretName(String nextPrivateKeySecretName) {
        this.nextPrivateKeySecretName = nextPrivateKeySecretName;
    }

    @Override
    public List<Condition> getConditions() {
        return this.conditions;
    }

    @Override
    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

}
