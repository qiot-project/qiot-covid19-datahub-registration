package org.qiot.covid19.datahub.registration.certmanager.api.model;

/**
 * @author mmascia
 */
public class Constants {
    public static final String RESOURCE_GROUP_NAME = "cert-manager.io";
    public static final String VERSION = "v1";

    public static final String V1_API_VERSION = "apiextensions.k8s.io/" + VERSION;
    public static final String CRD_KIND = "CustomResourceDefinition";
}
