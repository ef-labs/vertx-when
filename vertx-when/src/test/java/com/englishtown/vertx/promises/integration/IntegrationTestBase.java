package com.englishtown.vertx.promises.integration;

import com.englishtown.promises.Promise;
import com.englishtown.promises.When;
import com.englishtown.vertx.promises.*;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.test.core.VertxTestBase;
import org.junit.Test;

import java.util.UUID;

/**
 * Integration tests
 */
public abstract class IntegrationTestBase extends VertxTestBase {

    protected When when;
    protected WhenVertx whenVertx;
    protected WhenEventBus whenEventBus;
    protected WhenHttpClient whenHttpClient;
    protected WhenFileSystem whenFileSystem;

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

                    RequestOptions options = new RequestOptions()
                            .setSetupHandler(request -> {
                                assertNotNull(request);
                                return null;
                            })
                            .addHeader("X-TEST-1", "1")
                            .addHeader("X-TEST-2", "2");

                    return whenHttpClient.requestAbs(HttpMethod.GET, "http://localhost:8081/test", options);
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
                    RequestOptions options = new RequestOptions().setPauseResponse(true);
                    return whenHttpClient.requestAbs(HttpMethod.GET, "http://localhost:8081/test", options);
                })
                .then(response -> {
                    assertNotNull(response);
                    assertEquals(200, response.statusCode());
                    return whenHttpClient.body(response);
                })
                .then(body -> {
                    assertNotNull(body);
                    JsonObject json = new JsonObject(body.toString());
                    assertNotNull(json);

                    testComplete();
                    return null;
                })
                .otherwise(this::onRejected);

        await();
    }

    @Test
    public void testHttpRequestResponseAndReadBody() {

        whenVertx.deployVerticle(HttpServerVerticle.class.getName())
                .then(deploymentID -> {
                    RequestOptions options = new RequestOptions();
                    return whenHttpClient.requestAbsAndReadBody(HttpMethod.GET, "http://localhost:8081/test", options);
                })
                .then(responseAndBody -> {
                    assertNotNull(responseAndBody);

                    HttpClientResponse response = responseAndBody.getResponse();
                    assertNotNull(response);
                    assertEquals(200, response.statusCode());

                    Buffer body = responseAndBody.getBody();
                    assertNotNull(body);
                    JsonObject json = new JsonObject(body.toString());
                    assertNotNull(json);

                    testComplete();
                    return null;
                })
                .otherwise(this::onRejected);

        await();
    }

    @Test
    public void testFileSystem() throws Exception {

        String path = UUID.randomUUID().toString();
        String text = "Hello world!";

        whenFileSystem.exists(path)
                .then(exists -> {
                    assertFalse(exists);
                    return whenFileSystem.createFile(path);
                })
                .then(aVoid -> whenFileSystem.exists(path))
                .then(exists -> {
                    assertTrue(exists);
                    return whenFileSystem.readDir(".");
                })
                .then(contents -> {
                    assertTrue(contents.size() > 0);
                    return whenFileSystem.props(path);
                })
                .then(props -> {
                    assertTrue(props.isRegularFile());
                    return whenFileSystem.writeFile(path, Buffer.buffer(text));
                })
                .then(aVoid -> whenFileSystem.readFile(path))
                .then(buffer -> {
                    assertEquals(text, buffer.toString());
                    return whenFileSystem.delete(path);
                })
                .then(aVoid -> {
                    testComplete();
                    return null;
                })
                .otherwise(this::onRejected);

        await();
    }

    @Test
    public void testExecuteBlocking() throws Exception {

        String expected = "expected";

        whenVertx
                .<String>executeBlocking(future -> {
                    assertTrue(Thread.currentThread().getName().startsWith("vert.x-worker-"));
                    future.complete(expected);
                })
                .then(s -> {
                    assertTrue(Thread.currentThread().getName().startsWith("vert.x-eventloop-"));
                    assertEquals(expected, s);
                    testComplete();
                    return null;
                })
                .otherwise(this::onRejected);

        await();

    }

    @Test
    public void testExecuteBlocking_Fail() throws Exception {

        RuntimeException expected = new RuntimeException();

        whenVertx
                .<String>executeBlocking(
                        future -> {
                            assertTrue(Thread.currentThread().getName().startsWith("vert.x-worker-"));
                            future.fail(expected);
                        })
                .then(
                        s -> {
                            fail("Expected rejected promise");
                            return null;
                        },
                        t -> {
                            assertTrue(Thread.currentThread().getName().startsWith("vert.x-eventloop-"));
                            assertEquals(expected, t);
                            testComplete();
                            return null;
                        })
                .otherwise(this::onRejected);

        await();

    }

}
