package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Done;
import com.englishtown.promises.Promise;
import com.englishtown.promises.When;
import com.englishtown.promises.WhenFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Container;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DefaultWhenContainer}
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultWhenContainerTest {

    private DefaultWhenContainer whenContainer;
    private Promise<String> promise;
    private Done<String> done = new Done<>();

    @Mock
    Container container;
    @Mock
    AsyncResult<String> result;
    @Mock
    AsyncResult<Void> voidResult;
    @Captor
    ArgumentCaptor<Handler<AsyncResult<String>>> handlerCaptor;
    @Captor
    ArgumentCaptor<Handler<AsyncResult<Void>>> voidHandlerCaptor;

    String main = "com.englishtown.test.Verticle";
    String moduleName = "com.englishtown~vertx-mod-when~1.1.0-final";

    @Before
    public void setUp() {
        When when = WhenFactory.createSync();
        whenContainer = new DefaultWhenContainer(container, when);
    }

    @Test
    public void testDeployVerticle_Success() throws Exception {
        when(result.succeeded()).thenReturn(true);

        promise = whenContainer.deployVerticle(main);
        verify(container).deployVerticle(eq(main), any(JsonObject.class), eq(1), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onFulfilled, done.onRejected);
        done.assertFulfilled();
    }

    @Test
    public void testDeployVerticle_Fail() throws Exception {
        when(result.succeeded()).thenReturn(false);

        promise = whenContainer.deployVerticle(main);
        verify(container).deployVerticle(eq(main), any(JsonObject.class), eq(1), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onFulfilled, done.onRejected);
        done.assertRejected();
    }

    @Test
    public void testDeployVerticle_Success2() throws Exception {
        when(result.succeeded()).thenReturn(true);
        JsonObject config = new JsonObject();

        promise = whenContainer.deployVerticle(main, config);
        verify(container).deployVerticle(eq(main), eq(config), eq(1), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onFulfilled, done.onRejected);
        done.assertFulfilled();
    }

    @Test
    public void testDeployVerticle_Fail2() throws Exception {
        when(result.succeeded()).thenReturn(false);
        JsonObject config = new JsonObject();

        promise = whenContainer.deployVerticle(main, config);
        verify(container).deployVerticle(eq(main), eq(config), eq(1), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onFulfilled, done.onRejected);
        done.assertRejected();
    }

    @Test
    public void testDeployVerticle_Success3() throws Exception {
        when(result.succeeded()).thenReturn(true);

        promise = whenContainer.deployVerticle(main, 5);
        verify(container).deployVerticle(eq(main), any(JsonObject.class), eq(5), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onFulfilled, done.onRejected);
        done.assertFulfilled();
    }

    @Test
    public void testDeployVerticle_Fail3() throws Exception {
        when(result.succeeded()).thenReturn(false);

        promise = whenContainer.deployVerticle(main, 5);
        verify(container).deployVerticle(eq(main), any(JsonObject.class), eq(5), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onFulfilled, done.onRejected);
        done.assertRejected();
    }

    @Test
    public void testDeployModule_Success() throws Exception {
        when(result.succeeded()).thenReturn(true);

        promise = whenContainer.deployModule(moduleName);
        verify(container).deployModule(eq(moduleName), any(JsonObject.class), eq(1), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onFulfilled, done.onRejected);
        done.assertFulfilled();
    }

    @Test
    public void testDeployModule_Fail() throws Exception {
        when(result.succeeded()).thenReturn(false);

        promise = whenContainer.deployModule(moduleName);
        verify(container).deployModule(eq(moduleName), any(JsonObject.class), eq(1), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onFulfilled, done.onRejected);
        done.assertRejected();
    }

    @Test
    public void testDeployModule_Success2() throws Exception {
        when(result.succeeded()).thenReturn(true);
        JsonObject config = new JsonObject();

        promise = whenContainer.deployModule(moduleName, config);
        verify(container).deployModule(eq(moduleName), eq(config), eq(1), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onFulfilled, done.onRejected);
        done.assertFulfilled();
    }

    @Test
    public void testDeployModule_Fail2() throws Exception {
        when(result.succeeded()).thenReturn(false);
        JsonObject config = new JsonObject();

        promise = whenContainer.deployModule(moduleName, config);
        verify(container).deployModule(eq(moduleName), eq(config), eq(1), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onFulfilled, done.onRejected);
        done.assertRejected();
    }

    @Test
    public void testDeployModule_Success3() throws Exception {
        when(result.succeeded()).thenReturn(true);

        promise = whenContainer.deployModule(moduleName, 5);
        verify(container).deployModule(eq(moduleName), any(JsonObject.class), eq(5), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onFulfilled, done.onRejected);
        done.assertFulfilled();
    }

    @Test
    public void testDeployModule_Fail3() throws Exception {
        when(result.succeeded()).thenReturn(false);

        promise = whenContainer.deployModule(moduleName, 5);
        verify(container).deployModule(eq(moduleName), any(JsonObject.class), eq(5), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onFulfilled, done.onRejected);
        done.assertRejected();
    }

    @Test
    public void testDeployWorkerVerticle_Success() throws Exception {
        when(result.succeeded()).thenReturn(true);

        promise = whenContainer.deployWorkerVerticle(main, true);
        verify(container).deployWorkerVerticle(eq(main), any(JsonObject.class), eq(1), eq(true), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onFulfilled, done.onRejected);
        done.assertFulfilled();
    }

    @Test
    public void testDeployWorkerVerticle_Fail() throws Exception {
        when(result.succeeded()).thenReturn(false);

        promise = whenContainer.deployWorkerVerticle(main, true);
        verify(container).deployWorkerVerticle(eq(main), any(JsonObject.class), eq(1), eq(true), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onFulfilled, done.onRejected);
        done.assertRejected();
    }

    @Test
    public void testDeployWorkerVerticle_Success2() throws Exception {
        when(result.succeeded()).thenReturn(true);
        JsonObject config = new JsonObject();

        promise = whenContainer.deployWorkerVerticle(main, config, true);
        verify(container).deployWorkerVerticle(eq(main), eq(config), eq(1), eq(true), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onFulfilled, done.onRejected);
        done.assertFulfilled();
    }

    @Test
    public void testDeployWorkerVerticle_Fail2() throws Exception {
        when(result.succeeded()).thenReturn(false);
        JsonObject config = new JsonObject();

        promise = whenContainer.deployWorkerVerticle(main, config, true);
        verify(container).deployWorkerVerticle(eq(main), eq(config), eq(1), eq(true), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onFulfilled, done.onRejected);
        done.assertRejected();
    }

    @Test
    public void testDeployWorkerVerticle_Success3() throws Exception {
        when(result.succeeded()).thenReturn(true);

        promise = whenContainer.deployWorkerVerticle(main, 5, true);
        verify(container).deployWorkerVerticle(eq(main), any(JsonObject.class), eq(5), eq(true), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onFulfilled, done.onRejected);
        done.assertFulfilled();
    }

    @Test
    public void testDeployWorkerVerticle_Fail3() throws Exception {
        when(result.succeeded()).thenReturn(false);

        promise = whenContainer.deployWorkerVerticle(main, 5, true);
        verify(container).deployWorkerVerticle(eq(main), any(JsonObject.class), eq(5), eq(true), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onFulfilled, done.onRejected);
        done.assertRejected();
    }

    @Test
    public void testUndeployVerticle() throws Exception {
        String deploymentID = "id";
        Done<Void> done = new Done<>();
        when(voidResult.succeeded()).thenReturn(true);

        whenContainer.undeployVerticle(deploymentID).then(done.onFulfilled, done.onRejected);
        verify(container).undeployVerticle(eq(deploymentID), voidHandlerCaptor.capture());

        voidHandlerCaptor.getValue().handle(voidResult);
        done.assertFulfilled();
    }

    @Test
    public void testUndeployVerticle_Fail() throws Exception {
        String deploymentID = "id";
        Done<Void> done = new Done<>();

        whenContainer.undeployVerticle(deploymentID).then(done.onFulfilled, done.onRejected);
        verify(container).undeployVerticle(eq(deploymentID), voidHandlerCaptor.capture());

        voidHandlerCaptor.getValue().handle(voidResult);
        done.assertRejected();
    }

    @Test
    public void testUndeployModule() throws Exception {
        String deploymentID = "id";
        Done<Void> done = new Done<>();
        when(voidResult.succeeded()).thenReturn(true);

        whenContainer.undeployModule(deploymentID).then(done.onFulfilled, done.onRejected);
        verify(container).undeployModule(eq(deploymentID), voidHandlerCaptor.capture());

        voidHandlerCaptor.getValue().handle(voidResult);
        done.assertFulfilled();
    }

    @Test
    public void testUndeployModule_Fail() throws Exception {
        String deploymentID = "id";
        Done<Void> done = new Done<>();

        whenContainer.undeployModule(deploymentID).then(done.onFulfilled, done.onRejected);
        verify(container).undeployModule(eq(deploymentID), voidHandlerCaptor.capture());

        voidHandlerCaptor.getValue().handle(voidResult);
        done.assertRejected();
    }

    @Test
    public void testDeployModules() throws Exception {

        JsonArray modules = new JsonArray()
                .add(new JsonObject()
                                .putString("name", "com.englishtown~mod1~1.0")
                                .putNumber("instances", 2)
                                .putObject("config", new JsonObject().putString("test", "value"))
                )
                .add(new JsonObject()
                        .putString("name", "com.englishtown~mod2~1.0"))
                .add(new JsonObject());

        List<Promise<String>> promises = whenContainer.deployModules(modules);

        assertEquals(2, promises.size());
        verify(container, times(2)).deployModule(anyString(), any(JsonObject.class), anyInt(), handlerCaptor.capture());

    }

    @Test
    public void testDeployModules_Null() throws Exception {

        List<Promise<String>> promises = whenContainer.deployModules(null);

        assertEquals(0, promises.size());
        verifyZeroInteractions(container);

    }

}
