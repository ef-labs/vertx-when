package com.englishtown.vertx.promises.integration;

import com.englishtown.promises.Promise;
import com.englishtown.promises.When;
import com.englishtown.vertx.promises.impl.DefaultWhenEventBus;
import com.englishtown.vertx.promises.impl.DefaultWhenHttpClient;
import com.englishtown.vertx.promises.impl.DefaultWhenVertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.test.core.VertxTestBase;
import org.junit.Test;

import java.net.URI;

/**
 * Integration tests
 */
public abstract class IntegrationTestBase extends VertxTestBase {

    protected When when;
    protected DefaultWhenVertx whenVertx;
    protected DefaultWhenEventBus whenEventBus;
    protected DefaultWhenHttpClient whenHttpClient;

    protected Promise<Void> onRejected(Throwable t) {
        t.printStackTrace();
        fail(t.getMessage());
        return null;
    }

    @Test
    public void testDeployVerticle() {

        whenVertx.deployVerticle(TestVerticle.class.getName())
                .then(deploymentID -> {
                    assertNotNull(deploymentID);
                    testComplete();
                    return null;
                })
                .otherwise(this::onRejected);

        await();
    }

    @Test
    public void testEventBusSend() {

        whenVertx.deployVerticle(EventBusVerticle.class.getName())
                .then(deploymentID -> {
                    return whenEventBus.<JsonObject>send(EventBusVerticle.ADDRESS, new JsonObject());
                })
                .then(reply -> {
                    assertEquals("ok", reply.body().getString("status"));
                    testComplete();
                    return null;
                })
                .otherwise(this::onRejected);

        await();
    }

    @Test
    public void testHttpRequest() {

        whenVertx.deployVerticle(HttpServerVerticle.class.getName())
                .then(deploymentID -> {
                    return whenHttpClient.request(HttpMethod.GET, "http://localhost:8081/test", new HttpClientOptions());
                })
                .then(response -> {
                    assertEquals(200, response.statusCode());
                    testComplete();
                    return null;
                })
                .otherwise(this::onRejected);

        await();
    }

    @Test
    public void testHttpRequestResponseBody() {

        whenVertx.deployVerticle(HttpServerVerticle.class.getName())
                .then(deploymentID -> {
                    return whenHttpClient.requestResponseBody(HttpMethod.GET, "http://localhost:8081/test", new HttpClientOptions());
                })
                .then(responseAndBody -> {
                    HttpClientResponse response = responseAndBody.getResponse();
                    Buffer body = responseAndBody.getBody();

                    assertNotNull(response);
                    assertEquals(200, response.statusCode());

                    assertNotNull(body);
                    JsonObject json = new JsonObject(body.toString());
                    assertNotNull(json);

                    testComplete();
                    return null;
                })
                .otherwise(this::onRejected);

        await();
    }

}
