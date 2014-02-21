# vertx-mod-when

Provides when.java wrappers for standard vert.x objects to return promises.


## WhenContainer Examples

### Deploy 1 Verticle

```java

whenContainer.deployVerticle("com.englishtown.vertx.TestVerticle")
    .then(
            new FulfilledRunnable<String>() {
                @Override
                public Promise<String> run(String value) {
                    // On success
                    return null;
                }
            },
            new RejectedRunnable<String>() {
                @Override
                public Promise<String> run(Value<String> value) {
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

promises.add(whenContainer.deployModule("com.englishtown~vertx-mod-hk2~1.5.0-final"));
promises.add(whenContainer.deployModule("com.englishtown~vertx-mod-jersey~2.3.0-final"));

when.all(promises,
        new FulfilledRunnable<List<String>>() {
            @Override
            public Promise<List<String>> run(List<String> value) {
                // On success
                return null;
            }
        },
        new RejectedRunnable<List<String>>() {
            @Override
            public Promise<List<String>> run(Value<List<String>> value) {
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
        new FulfilledRunnable<List<Message<JsonObject>>>() {
            @Override
            public Promise<List<Message<JsonObject>>> run(List<Message<JsonObject>> value) {
                // On success
                return null;
            }
        },
        new RejectedRunnable<List<Message<JsonObject>>>, Value<List<Message<JsonObject>>>>() {
            @Override
            public Promise<List<Message<JsonObject>>> run(Value<List<Message<JsonObject>>> value) {
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
        new FulfilledRunnable<List<HttpClientResponse>>() {
            @Override
            public Promise<List<HttpClientResponse>> run(List<HttpClientResponse> value) {
                // On success
                return null;
            }
        },
        new RejectedRunnable<List<HttpClientResponse>>() {
            @Override
            public Promise<List<HttpClientResponse>> run(Value<List<HttpClientResponse>> value) {
                // On fail
                return null;
            }
        }
);

```
