# registration

A microservice exposed to the IoT devices over secured http.

The connection to the service is secured through the implementation of a mutual authentication mechanism making sure only certified devices have access to this service and vice-versa.

Scope of this microservice is to accept incoming registration requests from the devices provision certificates using the vault and generate a unique registration ID for the devide.
