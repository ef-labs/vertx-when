package com.englishtown.vertx.promises.integration;

import com.englishtown.promises.FulfilledRunnable;
import com.englishtown.promises.Promise;
import com.englishtown.promises.RejectedRunnable;
import com.englishtown.promises.Value;
import com.englishtown.vertx.promises.WhenContainer;
import com.englishtown.vertx.promises.WhenEventBus;
import com.englishtown.vertx.promises.WhenHttpClient;
import com.englishtown.vertx.promises.WhenStarter;
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

import static org.vertx.testtools.VertxAssert.*;

/**
 * Integration tests
 */
public class IntegrationTest extends TestVerticle {

    @Override
    public void start() {
        new WhenStarter(vertx).run();
        super.start();
    }

    @Test
    public void testDeployVerticle() {

        WhenContainer whenContainer = new DefaultWhenContainer(container);

        whenContainer.deployVerticle(com.englishtown.vertx.promises.integration.TestVerticle.class.getName()).then(
                new FulfilledRunnable<String>() {
                    @Override
                    public Promise<String> run(String value) {
                        testComplete();
                        return null;
                    }
                },
                new RejectedRunnable<String>() {
                    @Override
                    public Promise<String> run(Value<String> value) {
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
                            new FulfilledRunnable<Message<Object>>() {
                                @Override
                                public Promise<Message<Object>> run(Message<Object> value) {
                                    testComplete();
                                    return null;
                                }
                            },
                            new RejectedRunnable<Message<Object>>() {
                                @Override
                                public Promise<Message<Object>> run(Value<Message<Object>> value) {
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
                            new FulfilledRunnable<HttpClientResponse>() {
                                @Override
                                public Promise<HttpClientResponse> run(HttpClientResponse response) {
                                    assertEquals(200, response.statusCode());
                                    testComplete();
                                    return null;
                                }
                            },
                            new RejectedRunnable<HttpClientResponse>() {
                                @Override
                                public Promise<HttpClientResponse> run(Value<HttpClientResponse> value) {
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
