[![Build Status](https://travis-ci.org/englishtown/vertx-when.png)](https://travis-ci.org/englishtown/vertx-when)

# when-when

Provides when.java wrappers for standard vert.x 3 objects to return promises.

## Getting Started

Add a dependency to vertx-when

```xml
<dependency>
    <groupId>com.englishtown.vertx</groupId>
    <artifactId>vertx-when</artifactId>
    <version>4.0.0-SNAPSHOT</version>
</dependency>
```

Use dependency injection (hk2 and guice binders provided) to get an instance of:
* `com.englishtown.promises.When`
* `com.englishtown.vertx.promises.WhenEventBus`
* `com.englishtown.vertx.promises.WhenHttpClient`
* `com.englishtown.vertx.promises.WhenVertx`

If not using DI, you can manually construct the default implementations like this:
```java
        // Create the vert.x executor for callbacks to run on the vert.x event loop
        VertxExecutor executor = new VertxExecutor(vertx);
        when = WhenFactory.createFor(() -> executor);

        whenVertx = new DefaultWhenVertx(vertx, when);
        whenEventBus = new DefaultWhenEventBus(vertx, when);
        whenHttpClient = new DefaultWhenHttpClient(vertx, when);
```
(See the `com.englishtown.vertx.promises.integration.simple.NoDIIntegrationTest` integration test for an example.)


NOTE: If running vert.x 2.x then you should use module vertx-mod-when 3.0.1.  See earlier README.md files for details.

```xml
<dependency>
    <groupId>com.englishtown</groupId>
    <artifactId>vertx-mod-when</artifactId>
    <version>3.0.1</version>
</dependency>
```


## WhenVertx Examples

### Deploy 1 Verticle

```java

whenContainer.deployVerticle("com.englishtown.vertx.TestVerticle")
    .then(deploymentID -> {
        // On success
        return null;
    })
    .otherwise(t -> {
        // On fail
        return null;
    });

```

### Deploy 2 Verticles

```java

List<Promise<String>> promises = new ArrayList<>();

promises.add(whenContainer.deployVerticle("com.englishtown.vertx.TestVerticle1"));
promises.add(whenContainer.deployVerticle("com.englishtown.vertx.TestVerticle2"));

when.all(promises)
    .then(deploymentIDs -> {
        // Handle success
        return null;
    })
    .otherwise(t -> {
        // Handle failure
        return null;
    });

```


## WhenEventBus Examples

### Send 2 messages

```java

List<Promise<Message<JsonObject>>> promises = new ArrayList<>();

promises.add(whenEventBus.<JsonObject>send("et.vertx.eb.1", new JsonObject().putString("message", "hello")));
promises.add(whenEventBus.<JsonObject>send("et.vertx.eb.2", new JsonObject().putString("message", "world")));

when.all(promises).then(
        replies -> {
            // On success
            return null;
        },
        t -> {
            // On fail
            return null;
        });

```


## WhenHttpClient Examples

### Send 2 http requests

```java

List<Promise<HttpClientResponse>> promises = new ArrayList<>();

promises.add(whenHttpClient.request(HttpMethod.GET, "http://test.englishtown.com/test1", new HttpClientOptions()));
promises.add(whenHttpClient.request(HttpMethod.POST, "http://test.englishtown.com/test2", new HttpClientOptions()));

when.all(promises).then(
        responses -> {
            // On success
            return null;
        },
        t -> {
            // On fail
            return null;
        }
);

```

### Get a response and body

```java

whenHttpClient.requestResponseBody(HttpMethod.GET, "http://localhost:8081/test", new HttpClientOptions()).then(
    result -> {
        HttpClientResponse response = result.getResponse();
        Buffer body = result.getBody();
    },
    value -> {
        // On fail
        return null;
    }
);

```

