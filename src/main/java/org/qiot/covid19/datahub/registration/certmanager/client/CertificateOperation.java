package org.qiot.covid19.datahub.registration.certmanager.client;

import java.time.Duration;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.qiot.covid19.datahub.registration.certmanager.api.CertificateList;
import org.qiot.covid19.datahub.registration.certmanager.api.model.Certificate;
import org.qiot.covid19.datahub.registration.certmanager.api.model.Constants;
import org.qiot.covid19.datahub.registration.certmanager.api.model.KeystoreSpec;
import org.qiot.covid19.datahub.registration.rest.beans.RegisterResponse;
import org.slf4j.Logger;

import io.fabric8.kubernetes.api.model.Condition;
import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.kubernetes.api.model.apiextensions.v1.CustomResourceDefinition;
import io.fabric8.kubernetes.api.model.apiextensions.v1.CustomResourceDefinitionBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import io.fabric8.kubernetes.internal.KubernetesDeserializer;
import io.smallrye.mutiny.Uni;

/**
 * @author mmascia
 */
@ApplicationScoped
public class CertificateOperation {


	@ConfigProperty(name = "quarkus.kubernetes-client.namespace")
    String namespace;

    @ConfigProperty(name = "qiot.cert-manager.wait.duration")
    Long duration;

    @Inject
    DefaultKubernetesClient kubernetesClient;

    @Inject
    Logger LOGGER;

    public static CustomResourceDefinition certificate() {
        return new CustomResourceDefinitionBuilder()
                .withApiVersion(Constants.V1_API_VERSION)
                .withKind(Constants.CRD_KIND).withNewMetadata()
                .withName(Certificate.RESOURCE_PLURAL + "." + Constants.RESOURCE_GROUP_NAME).endMetadata().withNewSpec()
                .withScope(Certificate.SCOPE).withGroup(Constants.RESOURCE_GROUP_NAME).withNewNames()
                .withSingular(Certificate.RESOURCE_SINGULAR).withPlural(Certificate.RESOURCE_PLURAL)
                .withKind(Certificate.RESOURCE_KIND).withListKind(Certificate.RESOURCE_LIST_KIND).endNames().endSpec()
                .build();
    }

    public MixedOperation<Certificate, CertificateList, Resource<Certificate>> operation() {

        CustomResourceDefinition crd = certificate();
        KubernetesDeserializer.registerCustomKind(Constants.RESOURCE_GROUP_NAME + "/" + Constants.VERSION,
                Certificate.RESOURCE_KIND, Certificate.class);
        return kubernetesClient.customResources(CustomResourceDefinitionContext.fromCrd(crd), Certificate.class,
                CertificateList.class);
    }

    public String getNamespace() {
        return namespace;
    }

    public RegisterResponse isReady(String name) {

        Uni<RegisterResponse> uni = Uni.createFrom().emitter( em -> {

            this.operation().withName(name).watch(new Watcher<Certificate>() {
                @Override
                public void eventReceived(Action action, Certificate resource) {
                    LOGGER.info("Watching: {}", resource);
                    List<Condition> conditions = resource.getStatus().getConditions();
                    if (conditions.size() > 0) {
                        Condition condition = conditions.get(0);
                        if ("True".equals(condition.getStatus())) {
                            Secret secret = kubernetesClient.secrets()
                                .inNamespace(namespace)
                                .withName(resource.getSpec().getSecretName())
                                .get();
        
                            RegisterResponse registerResponse = RegisterResponse.builder()
                                .keystore(secret.getData().get(KeystoreSpec.KEYSTORE_KEY_P12))
                                .truststore(secret.getData().get(KeystoreSpec.KEYSTORE_KEY_P12))
                            .build();
                            em.complete(registerResponse);
                            LOGGER.info("Certificate Ready: {}", resource);
                        }
                    }
                }
        
                @Override
                public void onClose(WatcherException cause) {
                    LOGGER.error("Watch Error: {}", cause);
                    em.fail(cause);
                }
            });
        });

        return uni.await().atMost(Duration.ofSeconds(duration));
    }

}