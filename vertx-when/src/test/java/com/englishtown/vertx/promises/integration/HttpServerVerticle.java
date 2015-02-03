package com.englishtown.vertx.promises.integration;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;

/**
 * Http server for integration tests
 */
public class HttpServerVerticle extends AbstractVerticle implements Handler<HttpServerRequest> {

    /**
     * Override this method to signify that start is complete sometime _after_ the start() method has returned
     * This is useful if your verticle deploys other verticles or modules and you don't want this verticle to
     * be considered started until the other modules and verticles have been started.
     *
     * @param startedResult When you are happy your verticle is started set the result
     */
    @Override
    public void start(final Future<Void> startedResult) {

        HttpServerOptions options = new HttpServerOptions()
                .setHost("localhost")
                .setPort(8081);

        vertx.createHttpServer(options)
                .requestHandler(this)
                .listen(result -> {
                    if (result.succeeded()) {
                        startedResult.complete();
                    } else {
                        startedResult.fail(result.cause());
                    }
                });

    }

    @Override
    public void handle(HttpServerRequest request) {
        JsonObject json = new JsonObject().put("msg", "Hello world!");
        request.response().end(json.encodePrettily());
    }
}
