package com.englishtown.vertx.promises.integration;

import com.englishtown.promises.When;
import com.englishtown.vertx.promises.WhenContainer;
import com.englishtown.vertx.promises.WhenEventBus;
import com.englishtown.vertx.promises.WhenHttpClient;
import com.englishtown.vertx.promises.hk2.HK2WhenBinder;
import com.englishtown.vertx.promises.impl.DefaultWhenContainer;
import com.englishtown.vertx.promises.impl.DefaultWhenEventBus;
import com.englishtown.vertx.promises.impl.DefaultWhenHttpClient;
import io.netty.handler.codec.http.HttpMethod;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Test;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpClientResponse;
import org.vertx.java.core.json.JsonObject;
import org.vertx.testtools.TestVerticle;

import java.net.URI;

import static org.vertx.testtools.VertxAssert.*;

/**
 * Integration tests
 */
public class IntegrationTest extends TestVerticle {

    private When when;

    @Override
    public void start() {

        ServiceLocator locator = ServiceLocatorFactory.getInstance().create(null);
        ServiceLocatorUtilities.bind(locator, new HK2WhenBinder());

        when = locator.getService(When.class);
        super.start();
    }

    @Test
    public void testDeployVerticle() {

        WhenContainer whenContainer = new DefaultWhenContainer(container, when);

        whenContainer.deployVerticle(com.englishtown.vertx.promises.integration.TestVerticle.class.getName()).then(
                value -> {
                    testComplete();
                    return null;
                },
                value -> {
                    fail();
                    return null;
                }
        );

    }

    @Test
    public void testEventBusSend() {

        WhenEventBus whenEventBus = new DefaultWhenEventBus(vertx, container, when);

        container.deployVerticle(EventBusVerticle.class.getName(), result -> {
            if (result.succeeded()) {
                whenEventBus.send(EventBusVerticle.ADDRESS, new JsonObject()).then(
                        value -> {
                            testComplete();
                            return null;
                        },
                        value -> {
                            fail();
                            return null;
                        }
                );
            } else {
                fail();
            }
        });
    }

    @Test
    public void testHttpRequest() {

        WhenHttpClient whenHttpClient = new DefaultWhenHttpClient(vertx, when);

        container.deployVerticle(HttpServerVerticle.class.getName(), result -> {
            if (result.succeeded()) {
                whenHttpClient.request(HttpMethod.GET.name(), URI.create("http://localhost:8888/test")).then(
                        response -> {
                            assertEquals(200, response.statusCode());
                            testComplete();
                            return null;
                        },
                        value -> {
                            fail();
                            return null;
                        }
                );
            } else {
                fail();
            }
        });
    }

    @Test
    public void testHttpRequestResponseBody() {

        WhenHttpClient whenHttpClient = new DefaultWhenHttpClient(vertx, when);

        container.deployVerticle(HttpServerVerticle.class.getName(), result -> {
            if (result.succeeded()) {

                whenHttpClient.requestResponseBody(HttpMethod.GET.name(), URI.create("http://localhost:8888/test")).then(
                        responseAndBody -> {
                            HttpClientResponse response = responseAndBody.getResponse();
                            Buffer body = responseAndBody.getBody();

                            assertNotNull(response);
                            assertEquals(200, response.statusCode());

                            assertNotNull(body);
                            JsonObject json = new JsonObject(body.toString());
                            assertNotNull(json);

                            testComplete();
                            return null;
                        },
                        value -> {
                            fail();
                            return null;
                        }
                );
            } else {
                fail();
            }
        });
    }

}
