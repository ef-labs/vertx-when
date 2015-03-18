package com.englishtown.vertx.examples.integration;

import com.englishtown.promises.Promise;
import com.englishtown.promises.When;
import com.englishtown.promises.WhenFactory;
import com.englishtown.vertx.examples.WhenExampleVerticle;
import com.englishtown.vertx.promises.RequestOptions;
import com.englishtown.vertx.promises.WhenEventBus;
import com.englishtown.vertx.promises.WhenHttpClient;
import com.englishtown.vertx.promises.WhenVertx;
import com.englishtown.vertx.promises.impl.DefaultWhenEventBus;
import com.englishtown.vertx.promises.impl.DefaultWhenHttpClient;
import com.englishtown.vertx.promises.impl.DefaultWhenVertx;
import com.englishtown.vertx.promises.impl.VertxExecutor;
import io.vertx.core.http.HttpMethod;
import io.vertx.test.core.VertxTestBase;
import org.junit.Test;

/**
 * Integration test for {@link com.englishtown.vertx.examples.WhenExampleVerticle}
 */
public class IntegrationTest extends VertxTestBase {

    private When when;
    private WhenVertx whenVertx;
    private WhenEventBus whenEventBus;
    private WhenHttpClient whenHttpClient;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        when = WhenFactory.createFor(() -> new VertxExecutor(vertx));
        whenVertx = new DefaultWhenVertx(vertx, when);
        whenEventBus = new DefaultWhenEventBus(vertx, when);
        whenHttpClient = new DefaultWhenHttpClient(vertx, when);

    }

    private Promise<String> deployVerticle() {

        String identifier = "groovy:" + WhenExampleVerticle.class.getName();
        return whenVertx.deployVerticle(identifier);

    }

    private Promise<Void> onRejected(Throwable t) {
        t.printStackTrace();
        fail();
        return null;
    }

    @Test
    public void testWhenEventBus() throws Exception {

        deployVerticle()
                .then(id -> whenEventBus.<String>send(WhenExampleVerticle.ADDRESS, "ping"))
                .then(reply -> {
                    assertEquals("pong", reply.body());
                    testComplete();
                    return null;
                })
                .otherwise(this::onRejected);

        await();
    }

    @Test
    public void testWhenHttpClient() throws Exception {

        RequestOptions options = new RequestOptions().setPauseResponse(true);

        deployVerticle()
                .then(id -> whenHttpClient.request(HttpMethod.GET, 8080, "localhost", "/ping", options))
                .then(response -> {
                    assertEquals(200, response.statusCode());
                    return whenHttpClient.body(response);
                })
                .then(body -> {
                    assertEquals("pong", body.toString());
                    testComplete();
                    return null;
                })
                .otherwise(this::onRejected);

        await();

    }

}
