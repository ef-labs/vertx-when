# vertx-mod-when

Provides when.java wrappers for standard vert.x objects to return promises.


## WhenContainer Examples

### Deploy 1 Verticle

```java

whenContainer.deployVerticle("com.englishtown.vertx.TestVerticle")
    .then(
            new Runnable<Promise<String, Void>, String>() {
                @Override
                public Promise<String, Void> run(String value) {
                    // On success
                    return null;
                }
            },
            new Runnable<Promise<String, Void>, Value<String>>() {
                @Override
                public Promise<String, Void> run(Value<String> value) {
                    // On fail
                    return null;
                }
            }
    );

```

### Deploy 2 Modules

```java

List<Promise<String, Void>> promises = new ArrayList<>();
When<String, Void> when = new When<>();

promises.add(whenContainer.deployModule("com.englishtown~vertx-mod-hk2~1.3.0-final"));
promises.add(whenContainer.deployModule("com.englishtown~vertx-mod-jersey~2.1.0-final"));

when.all(promises,
        new Runnable<Promise<List<String>, Void>, List<String>>() {
            @Override
            public Promise<List<String>, Void> run(List<String> value) {
                // On success
                return null;
            }
        },
        new Runnable<Promise<List<String>, Void>, Value<List<String>>>() {
            @Override
            public Promise<List<String>, Void> run(Value<List<String>> value) {
                // On fail
                return null;
            }
        }
);

```


## WhenEventBus Examples

### Send 2 messages

```java

List<Promise<Message<JsonObject>, Void>> promises = new ArrayList<>();
When<Message<JsonObject>, Void> when = new When<>();

promises.add(whenEventBus.<JsonObject>send("et.vertx.eb.1", new JsonObject().putString("message", "hello")));
promises.add(whenEventBus.<JsonObject>send("et.vertx.eb.2", new JsonObject().putString("message", "world")));

when.all(promises,
        new Runnable<Promise<List<Message<JsonObject>>, Void>, List<Message<JsonObject>>>() {
            @Override
            public Promise<List<Message<JsonObject>>, Void> run(List<Message<JsonObject>> value) {
                // On success
                return null;
            }
        },
        new Runnable<Promise<List<Message<JsonObject>>, Void>, Value<List<Message<JsonObject>>>>() {
            @Override
            public Promise<List<Message<JsonObject>>, Void> run(Value<List<Message<JsonObject>>> value) {
                // On fail
                return null;
            }
        });

```


## WhenHttpClient Examples

### Send to http requests

```java

List<Promise<HttpClientResponse, Void>> promises = new ArrayList<>();
When<HttpClientResponse, Void> when = new When<>();

promises.add(whenHttpClient.request(HttpMethod.GET.name(), URI.create("http://test.englishtown.com/test1")));
promises.add(whenHttpClient.request(HttpMethod.POST.name(), URI.create("http://test.englishtown.com/test2")));

when.all(promises,
        new Runnable<Promise<List<HttpClientResponse>, Void>, List<HttpClientResponse>>() {
            @Override
            public Promise<List<HttpClientResponse>, Void> run(List<HttpClientResponse> value) {
                // On success
                return null;
            }
        },
        new Runnable<Promise<List<HttpClientResponse>, Void>, Value<List<HttpClientResponse>>>() {
            @Override
            public Promise<List<HttpClientResponse>, Void> run(Value<List<HttpClientResponse>> value) {
                // On fail
                return null;
            }
        }
);

```