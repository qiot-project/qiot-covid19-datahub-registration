package io.qiot.covid19.datahub.registration.certmanager.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import io.fabric8.kubernetes.api.model.Duration;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

/**
 * @author mmascia
 */
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "subject", "commonName", "durantion", "renewBefore",
        "renewBefore", "dnsNames", "ipAddresses", "uris", "emailAddresses",
        "secretName", "keystores", "issuerRef", "isCA", "usages", "privateKey",
        "encodeUsagesInRequest" })
@Builder
@RegisterForReflection
@NoArgsConstructor
@AllArgsConstructor
public class CertificateSpec extends Spec {
    private static final long serialVersionUID = 1L;

    private X509SubjectSpec subject;
    private String commonName;
    private Duration durantion;
    private Duration renewBefore;

    private List<String> dnsNames;
    private List<String> ipAddresses;
    private List<String> uris;

    private List<String> emailAddresses;

    private String secretName;

    private CertificateKeystoresSpec keystores;

    private ObjectReferenceSpec issuerRef;

    private Boolean isCA;

    private List<String> usages;

    private CertificatePrivateKeySpec privateKey;

    private String encodeUsagesInRequest;

    @JsonProperty(required = true)
    public String getSecretName() {
        return secretName;
    }

    public void setSecretName(String secretName) {
        this.secretName = secretName;
    }

    public Duration getDurantion() {
        return durantion;
    }

    public void setDurantion(Duration durantion) {
        this.durantion = durantion;
    }

    public X509SubjectSpec getSubject() {
        return subject;
    }

    public void setSubject(X509SubjectSpec subject) {
        this.subject = subject;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public Boolean getIsCA() {
        return isCA;
    }

    public void setIsCA(Boolean isCA) {
        this.isCA = isCA;
    }

    public CertificatePrivateKeySpec getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(CertificatePrivateKeySpec privateKey) {
        this.privateKey = privateKey;
    }

    public List<String> getUsages() {
        return usages;
    }

    public void setUsages(List<String> usages) {
        this.usages = usages;
    }

    @JsonProperty(required = true)
    public List<String> getDnsNames() {
        return dnsNames;
    }

    public void setDnsNames(List<String> dnsNames) {
        this.dnsNames = dnsNames;
    }

    public List<String> getUris() {
        return uris;
    }

    public void setUris(List<String> uris) {
        this.uris = uris;
    }

    public List<String> getIpAddresses() {
        return ipAddresses;
    }

    public void setIpAddresses(List<String> ipAddresses) {
        this.ipAddresses = ipAddresses;
    }

    @JsonProperty(required = true)
    public ObjectReferenceSpec getIssuerRef() {
        return issuerRef;
    }

    public void setIssuerRef(ObjectReferenceSpec issuerRef) {
        this.issuerRef = issuerRef;
    }

    public Duration getRenewBefore() {
        return renewBefore;
    }

    public void setRenewBefore(Duration renewBefore) {
        this.renewBefore = renewBefore;
    }

    public List<String> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(List<String> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public CertificateKeystoresSpec getKeystores() {
        return keystores;
    }

    public void setKeystores(CertificateKeystoresSpec keystores) {
        this.keystores = keystores;
    }

    public String getEncodeUsagesInRequest() {
        return encodeUsagesInRequest;
    }

    public void setEncodeUsagesInRequest(String encodeUsagesInRequest) {
        this.encodeUsagesInRequest = encodeUsagesInRequest;
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
