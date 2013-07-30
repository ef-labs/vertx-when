package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Done2;
import com.englishtown.promises.Promise;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Container;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: adriangonzalez
 * Date: 7/26/13
 * Time: 5:26 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultWhenContainerTest {

    private DefaultWhenContainer whenContainer;
    private Promise<String, Void> promise;
    private Done2<String> done = new Done2<>();

    @Mock
    Container container;
    @Mock
    AsyncResult<String> result;
    @Captor
    ArgumentCaptor<Handler<AsyncResult<String>>> handlerCaptor;

    String main = "com.englishtown.test.Verticle";
    String moduleName = "com.englishtown~vertx-mod-when~1.0.0";

    @Before
    public void setUp() {
        whenContainer = new DefaultWhenContainer(container);
    }

    @Test
    public void testDeployVerticle_Success() throws Exception {
        when(result.succeeded()).thenReturn(true);

        promise = whenContainer.deployVerticle(main);
        verify(container).deployVerticle(eq(main), any(JsonObject.class), eq(1), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertSuccess();
    }

    @Test
    public void testDeployVerticle_Fail() throws Exception {
        when(result.succeeded()).thenReturn(false);

        promise = whenContainer.deployVerticle(main);
        verify(container).deployVerticle(eq(main), any(JsonObject.class), eq(1), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertFailed();
    }

    @Test
    public void testDeployVerticle_Success2() throws Exception {
        when(result.succeeded()).thenReturn(true);
        JsonObject config = new JsonObject();

        promise = whenContainer.deployVerticle(main, config);
        verify(container).deployVerticle(eq(main), eq(config), eq(1), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertSuccess();
    }

    @Test
    public void testDeployVerticle_Fail2() throws Exception {
        when(result.succeeded()).thenReturn(false);
        JsonObject config = new JsonObject();

        promise = whenContainer.deployVerticle(main, config);
        verify(container).deployVerticle(eq(main), eq(config), eq(1), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertFailed();
    }

    @Test
    public void testDeployVerticle_Success3() throws Exception {
        when(result.succeeded()).thenReturn(true);

        promise = whenContainer.deployVerticle(main, 5);
        verify(container).deployVerticle(eq(main), any(JsonObject.class), eq(5), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertSuccess();
    }

    @Test
    public void testDeployVerticle_Fail3() throws Exception {
        when(result.succeeded()).thenReturn(false);

        promise = whenContainer.deployVerticle(main, 5);
        verify(container).deployVerticle(eq(main), any(JsonObject.class), eq(5), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertFailed();
    }

    @Test
    public void testDeployModule_Success() throws Exception {
        when(result.succeeded()).thenReturn(true);

        promise = whenContainer.deployModule(moduleName);
        verify(container).deployModule(eq(moduleName), any(JsonObject.class), eq(1), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertSuccess();
    }

    @Test
    public void testDeployModule_Fail() throws Exception {
        when(result.succeeded()).thenReturn(false);

        promise = whenContainer.deployModule(moduleName);
        verify(container).deployModule(eq(moduleName), any(JsonObject.class), eq(1), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertFailed();
    }

    @Test
    public void testDeployModule_Success2() throws Exception {
        when(result.succeeded()).thenReturn(true);
        JsonObject config = new JsonObject();

        promise = whenContainer.deployModule(moduleName, config);
        verify(container).deployModule(eq(moduleName), eq(config), eq(1), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertSuccess();
    }

    @Test
    public void testDeployModule_Fail2() throws Exception {
        when(result.succeeded()).thenReturn(false);
        JsonObject config = new JsonObject();

        promise = whenContainer.deployModule(moduleName, config);
        verify(container).deployModule(eq(moduleName), eq(config), eq(1), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertFailed();
    }

    @Test
    public void testDeployModule_Success3() throws Exception {
        when(result.succeeded()).thenReturn(true);

        promise = whenContainer.deployModule(moduleName, 5);
        verify(container).deployModule(eq(moduleName), any(JsonObject.class), eq(5), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertSuccess();
    }

    @Test
    public void testDeployModule_Fail3() throws Exception {
        when(result.succeeded()).thenReturn(false);

        promise = whenContainer.deployModule(moduleName, 5);
        verify(container).deployModule(eq(moduleName), any(JsonObject.class), eq(5), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertFailed();
    }

    @Test
    public void testDeployWorkerVerticle_Success() throws Exception {
        when(result.succeeded()).thenReturn(true);

        promise = whenContainer.deployWorkerVerticle(main, true);
        verify(container).deployWorkerVerticle(eq(main), any(JsonObject.class), eq(1), eq(true), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertSuccess();
    }

    @Test
    public void testDeployWorkerVerticle_Fail() throws Exception {
        when(result.succeeded()).thenReturn(false);

        promise = whenContainer.deployWorkerVerticle(main, true);
        verify(container).deployWorkerVerticle(eq(main), any(JsonObject.class), eq(1), eq(true), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertFailed();
    }

    @Test
    public void testDeployWorkerVerticle_Success2() throws Exception {
        when(result.succeeded()).thenReturn(true);
        JsonObject config = new JsonObject();

        promise = whenContainer.deployWorkerVerticle(main, config, true);
        verify(container).deployWorkerVerticle(eq(main), eq(config), eq(1), eq(true), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertSuccess();
    }

    @Test
    public void testDeployWorkerVerticle_Fail2() throws Exception {
        when(result.succeeded()).thenReturn(false);
        JsonObject config = new JsonObject();

        promise = whenContainer.deployWorkerVerticle(main, config, true);
        verify(container).deployWorkerVerticle(eq(main), eq(config), eq(1), eq(true), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertFailed();
    }

    @Test
    public void testDeployWorkerVerticle_Success3() throws Exception {
        when(result.succeeded()).thenReturn(true);

        promise = whenContainer.deployWorkerVerticle(main, 5, true);
        verify(container).deployWorkerVerticle(eq(main), any(JsonObject.class), eq(5), eq(true), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertSuccess();
    }

    @Test
    public void testDeployWorkerVerticle_Fail3() throws Exception {
        when(result.succeeded()).thenReturn(false);

        promise = whenContainer.deployWorkerVerticle(main, 5, true);
        verify(container).deployWorkerVerticle(eq(main), any(JsonObject.class), eq(5), eq(true), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertFailed();
    }

}
