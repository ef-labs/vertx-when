# vertx-mod-when

Provides when.java wrappers for standard vert.x objects to return promises.


## WhenContainer Examples

### Deploy 1 Verticle

```java

whenContainer.deployVerticle("com.englishtown.vertx.TestVerticle")
    .then(
           value -> {
                // On success
                return null;
            },
            value -> {
                // On fail
                return null;
            }
    );

```

### Deploy 2 Modules

```java

List<Promise<String>> promises = new ArrayList<>();

promises.add(whenContainer.deployModule("com.englishtown~vertx-mod-hk2~1.5.0-final"));
promises.add(whenContainer.deployModule("com.englishtown~vertx-mod-jersey~2.3.0-final"));

when.all(promises,
        value -> {
            // On success
            return null;
        },
        value -> {
            // On fail
            return null;
        }
);

```


## WhenEventBus Examples

### Send 2 messages

```java

List<Promise<Message<JsonObject>>> promises = new ArrayList<>();

promises.add(whenEventBus.<JsonObject>send("et.vertx.eb.1", new JsonObject().putString("message", "hello")));
promises.add(whenEventBus.<JsonObject>send("et.vertx.eb.2", new JsonObject().putString("message", "world")));

when.all(promises).then(
        value -> {
            // On success
            return null;
        },
        value -> {
            // On fail
            return null;
        });

```


## WhenHttpClient Examples

### Send 2 http requests

```java

List<Promise<HttpClientResponse>> promises = new ArrayList<>();

promises.add(whenHttpClient.request(HttpMethod.GET.name(), URI.create("http://test.englishtown.com/test1")));
promises.add(whenHttpClient.request(HttpMethod.POST.name(), URI.create("http://test.englishtown.com/test2")));

when.all(promises).then(
        value -> {
            // On success
            return null;
        },
        value -> {
            // On fail
            return null;
        }
);

```

### Get a response and body

```java

whenHttpClient.requestResponseBody(HttpMethod.GET.name(), URI.create("http://localhost:8888/test")).then(
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

