package io.qiot.covid19.datahub.registration.certmanager.client;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.kubernetes.api.model.SecretList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.Resource;

/**
 * @author mmascia
 */
@ApplicationScoped
public class SecretOperation {

    @ConfigProperty(name = "quarkus.kubernetes-client.namespace")
    String namespace;

    @Inject
    DefaultKubernetesClient kubernetesClient;

    public MixedOperation<Secret, SecretList, Resource<Secret>> operation() {
        return kubernetesClient.inNamespace(namespace).secrets();
    }

}
