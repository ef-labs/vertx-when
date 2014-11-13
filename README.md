[![Build Status](https://travis-ci.org/englishtown/vertx-mod-jersey.png)](https://travis-ci.org/englishtown/vertx-mod-jersey)

# ext-when

Provides when.java wrappers for standard vert.x objects to return promises.


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

