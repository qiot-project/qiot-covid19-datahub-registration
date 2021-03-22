# QIoT Datahub Registration

This microservice offer an endpoint to register a new Device on Cloud getting the X509 Client Certificate (both private and public key in a p12 package).
With the Client Certificate the device can to connect service offered by the Cloud like MQTT or Gelf. 

Example: 
  Register a device: curl "http://localhost:5034/v1/location?longitude=9&latitude=45"


## Choose one of:

### To build and create container image

$ ./mvnw clean package

### To build, create container image and push it to registry

$ ./mvnw -Dhttps.protocols=TLSv1.2 clean package -Dquarkus.container-image.push=true

### To build native and create container image

$ ./mvnw clean package -Pnative

### To build native, create container image and push it to registry

$ ./mvnw -Dhttps.protocols=TLSv1.2 clean package -Pnative -Dquarkus.container-image.push=true
