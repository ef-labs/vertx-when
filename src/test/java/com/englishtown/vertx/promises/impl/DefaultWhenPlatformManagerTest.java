package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Done2;
import com.englishtown.promises.Promise;
import com.englishtown.promises.WhenProgress;
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
import org.vertx.java.platform.PlatformManager;

import java.net.URL;
import java.util.concurrent.Executor;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultWhenPlatformManagerTest {

    private DefaultWhenPlatformManager defaultWhenPlatformManager;
    private Promise<String> promise;
    private Done2<String> done = new Done2<>();

    @Mock
    PlatformManager manager;
    @Mock
    AsyncResult<String> result;
    @Captor
    ArgumentCaptor<Handler<AsyncResult<String>>> handlerCaptor;

    String main = "com.englishtown.test.Verticle";
    String moduleName = "com.englishtown~vertx-mod-when~1.1.0-final";

    @Before
    public void setUp() {
        WhenProgress.setNextTick(new Executor() {
            @Override
            public void execute(Runnable command) {
                command.run();
            }
        });
        defaultWhenPlatformManager = new DefaultWhenPlatformManager(manager);
    }

    @Test
    public void testDeployVerticle_Success() throws Exception {
        when(result.succeeded()).thenReturn(true);

        promise = defaultWhenPlatformManager.deployVerticle(main, null, null, 1, null);
        verify(manager).deployVerticle(eq(main), any(JsonObject.class), any(URL[].class), eq(1), any(String.class), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertSuccess();
    }

    @Test
    public void testDeployVerticle_Fail() throws Exception {
        when(result.succeeded()).thenReturn(false);

        promise = defaultWhenPlatformManager.deployVerticle(main, null, null, 1, null);
        verify(manager).deployVerticle(eq(main), any(JsonObject.class), any(URL[].class), eq(1), any(String.class), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertFailed();
    }

    @Test
    public void testDeployModule_Success() throws Exception {
        when(result.succeeded()).thenReturn(true);

        promise = defaultWhenPlatformManager.deployModule(moduleName, null, 1);
        verify(manager).deployModule(eq(moduleName), any(JsonObject.class), eq(1), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertSuccess();
    }

    @Test
    public void testDeployModule_Fail() throws Exception {
        when(result.succeeded()).thenReturn(false);

        promise = defaultWhenPlatformManager.deployModule(moduleName, null, 1);
        verify(manager).deployModule(eq(moduleName), any(JsonObject.class), eq(1), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertFailed();
    }

    @Test
    public void testDeployWorkerVerticle_Success() throws Exception {
        when(result.succeeded()).thenReturn(true);

        promise = defaultWhenPlatformManager.deployWorkerVerticle(true, main, null, null, 1, null);
        verify(manager).deployWorkerVerticle(eq(true), eq(main), any(JsonObject.class), any(URL[].class), eq(1), any(String.class), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertSuccess();
    }

    @Test
    public void testDeployWorkerVerticle_Fail() throws Exception {
        when(result.succeeded()).thenReturn(false);

        promise = defaultWhenPlatformManager.deployWorkerVerticle(true, main, null, null, 1, null);
        verify(manager).deployWorkerVerticle(eq(true), eq(main), any(JsonObject.class), any(URL[].class), eq(1), any(String.class), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);

        promise.then(done.onSuccess, done.onFail);
        done.assertFailed();
    }
}
