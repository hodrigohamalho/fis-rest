# Spring Boot Camel REST / SQL Example

This example demonstrates how to use SQL via JDBC along with Camel's REST DSL to expose a RESTful API.

This example relies on the [Fabric8 Maven plugin](https://maven.fabric8.io) for its build configuration
and uses the [fabric8 Java base image](https://github.com/fabric8io/base-images#java-base-images).

### Building

The example can be built with:

    $ mvn install

This automatically generates the application resource descriptors and builds the Docker image, so it requires access to a Docker daemon, relying on the `DOCKER_HOST` environment variable by default.

### Running the example locally

The example can be run locally using the following Maven goal:

    $ mvn spring-boot:run

Alternatively, you can run the application locally using the executable JAR produced:

    $ java -jar -Dspring.profiles.active=dev target/fis-rest-1.0.jar

This uses an embedded in-memory HSQLDB database. You can use the default Spring Boot profile in case you have a MySQL server available for you to test.

You can check the api swagger documentation in:

http://0.0.0.0:8080

You can then access the REST API directly from your Web browser, e.g.:

- GET: http://0.0.0.0:8080/api/orders
- GET: http://0.0.0.0:8080/api/orders/{id}
- PUT: http://0.0.0.0:8080/api/orders
- DELETE: http://0.0.0.0:8080/api/orders
- POST: http://0.0.0.0:8080/api/orders

```
{
	"id": 0,
	"item": "Inserting a order",
	"amount": 2,
	"description": "Testing order insert",
	"processed": true
}
```

### Running the example in Kubernetes / OpenShift

Considering that you have *oc* client correctly configured in your environment:

```
    mvn fabric8:deploy
```
