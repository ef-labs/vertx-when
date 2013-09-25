package com.englishtown.vertx.promises.integration;

import com.englishtown.promises.Promise;
import com.englishtown.promises.Runnable;
import com.englishtown.promises.Value;
import com.englishtown.vertx.promises.WhenContainer;
import com.englishtown.vertx.promises.WhenEventBus;
import com.englishtown.vertx.promises.WhenHttpClient;
import com.englishtown.vertx.promises.impl.DefaultWhenContainer;
import com.englishtown.vertx.promises.impl.DefaultWhenEventBus;
import com.englishtown.vertx.promises.impl.DefaultWhenHttpClient;
import io.netty.handler.codec.http.HttpMethod;
import org.junit.Test;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpClientResponse;
import org.vertx.java.core.json.JsonObject;
import org.vertx.testtools.TestVerticle;

import java.net.URI;

import static org.vertx.testtools.VertxAssert.assertEquals;
import static org.vertx.testtools.VertxAssert.fail;
import static org.vertx.testtools.VertxAssert.testComplete;

/**
 * Integration tests
 */
public class IntegrationTest extends TestVerticle {

    @Test
    public void testDeployVerticle() {

        WhenContainer whenContainer = new DefaultWhenContainer(container);

        whenContainer.deployVerticle(com.englishtown.vertx.promises.integration.TestVerticle.class.getName()).then(
                new Runnable<Promise<String, Void>, String>() {
                    @Override
                    public Promise<String, Void> run(String value) {
                        testComplete();
                        return null;
                    }
                },
                new Runnable<Promise<String, Void>, Value<String>>() {
                    @Override
                    public Promise<String, Void> run(Value<String> value) {
                        fail();
                        return null;
                    }
                }
        );

    }

    @Test
    public void testEventBusSend() {

        final WhenEventBus whenEventBus = new DefaultWhenEventBus(vertx, container);

        container.deployVerticle(EventBusVerticle.class.getName(), new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> result) {
                if (result.succeeded()) {
                    whenEventBus.send(EventBusVerticle.ADDRESS, new JsonObject()).then(
                            new Runnable<Promise<Message<Object>, Void>, Message<Object>>() {
                                @Override
                                public Promise<Message<Object>, Void> run(Message<Object> value) {
                                    testComplete();
                                    return null;
                                }
                            },
                            new Runnable<Promise<Message<Object>, Void>, Value<Message<Object>>>() {
                                @Override
                                public Promise<Message<Object>, Void> run(Value<Message<Object>> value) {
                                    fail();
                                    return null;
                                }
                            }
                    );
                } else {
                    fail();
                }
            }
        });
    }

    @Test
    public void testHttpRequest() {

        final WhenHttpClient whenHttpClient = new DefaultWhenHttpClient(vertx);

        container.deployVerticle(HttpServerVerticle.class.getName(), new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> result) {
                if (result.succeeded()) {
                    whenHttpClient.request(HttpMethod.GET.name(), URI.create("http://localhost:8888/test")).then(
                            new Runnable<Promise<HttpClientResponse, Void>, HttpClientResponse>() {
                                @Override
                                public Promise<HttpClientResponse, Void> run(HttpClientResponse response) {
                                    assertEquals(200, response.statusCode());
                                    testComplete();
                                    return null;
                                }
                            },
                            new Runnable<Promise<HttpClientResponse, Void>, Value<HttpClientResponse>>() {
                                @Override
                                public Promise<HttpClientResponse, Void> run(Value<HttpClientResponse> value) {
                                    fail();
                                    return null;
                                }
                            }
                    );
                } else {
                    fail();
                }
            }
        });
    }

}
