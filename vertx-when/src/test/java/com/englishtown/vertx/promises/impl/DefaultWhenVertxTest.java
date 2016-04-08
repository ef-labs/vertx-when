package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Done;
import com.englishtown.promises.When;
import com.englishtown.promises.WhenFactory;
import io.vertx.core.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link DefaultWhenVertx}
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultWhenVertxTest {

    private DefaultWhenVertx whenVertx;
    private Done<String> done = new Done<>();
    private String identifier = "com.englishtown.test.Verticle";

    @Mock
    Vertx vertx;
    @Mock
    AsyncResult<String> result;
    @Mock
    AsyncResult<Void> voidResult;
    @Mock
    DeploymentOptions options;
    @Mock
    Verticle verticle;
    @Mock
    Handler<Future<String>> futureHandler;
    @Captor
    ArgumentCaptor<Handler<AsyncResult<String>>> handlerCaptor;
    @Captor
    ArgumentCaptor<Handler<AsyncResult<Void>>> voidHandlerCaptor;
    @Captor
    ArgumentCaptor<Handler<AsyncResult<String>>> stringHandlerCaptor;


    @Before
    public void setUp() {
        When when = WhenFactory.createSync();
        whenVertx = new DefaultWhenVertx(vertx, when);
    }

    @Test
    public void testDeployVerticle_Identifier_Success1() throws Exception {
        when(result.succeeded()).thenReturn(true);

        whenVertx.deployVerticle(identifier).then(done.onFulfilled, done.onRejected);
        verify(vertx).deployVerticle(eq(identifier), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);
        done.assertFulfilled();
    }

    @Test
    public void testDeployVerticle_Identifier_Fail1() throws Exception {
        when(result.succeeded()).thenReturn(false);

        whenVertx.deployVerticle(identifier).then(done.onFulfilled, done.onRejected);
        verify(vertx).deployVerticle(eq(identifier), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);
        done.assertRejected();
    }

    @Test
    public void testDeployVerticle_Identifier_Success2() throws Exception {
        when(result.succeeded()).thenReturn(true);

        whenVertx.deployVerticle(identifier, options).then(done.onFulfilled, done.onRejected);
        verify(vertx).deployVerticle(eq(identifier), eq(options), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);
        done.assertFulfilled();
    }

    @Test
    public void testDeployVerticle_Identifier_Fail2() throws Exception {
        when(result.succeeded()).thenReturn(false);

        whenVertx.deployVerticle(identifier, options).then(done.onFulfilled, done.onRejected);
        verify(vertx).deployVerticle(eq(identifier), eq(options), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);
        done.assertRejected();
    }

    @Test
    public void testDeployVerticle_Verticle_Success1() throws Exception {
        when(result.succeeded()).thenReturn(true);

        whenVertx.deployVerticle(verticle).then(done.onFulfilled, done.onRejected);
        verify(vertx).deployVerticle(eq(verticle), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);
        done.assertFulfilled();
    }

    @Test
    public void testDeployVerticle_Verticle_Fail1() throws Exception {
        when(result.succeeded()).thenReturn(false);

        whenVertx.deployVerticle(verticle).then(done.onFulfilled, done.onRejected);
        verify(vertx).deployVerticle(eq(verticle), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);
        done.assertRejected();
    }

    @Test
    public void testDeployVerticle_Verticle_Success2() throws Exception {
        when(result.succeeded()).thenReturn(true);

        whenVertx.deployVerticle(verticle, options).then(done.onFulfilled, done.onRejected);
        verify(vertx).deployVerticle(eq(verticle), eq(options), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);
        done.assertFulfilled();
    }

    @Test
    public void testDeployVerticle_Verticle_Fail2() throws Exception {
        when(result.succeeded()).thenReturn(false);

        whenVertx.deployVerticle(verticle, options).then(done.onFulfilled, done.onRejected);
        verify(vertx).deployVerticle(eq(verticle), eq(options), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);
        done.assertRejected();
    }

    @Test
    public void testUndeploy() throws Exception {
        String deploymentID = "id";
        Done<Void> done = new Done<>();
        when(voidResult.succeeded()).thenReturn(true);

        whenVertx.undeploy(deploymentID).then(done.onFulfilled, done.onRejected);
        verify(vertx).undeploy(eq(deploymentID), voidHandlerCaptor.capture());

        voidHandlerCaptor.getValue().handle(voidResult);
        done.assertFulfilled();
    }

    @Test
    public void testUndeploy_Fail() throws Exception {
        String deploymentID = "id";
        Done<Void> done = new Done<>();

        whenVertx.undeploy(deploymentID).then(done.onFulfilled, done.onRejected);
        verify(vertx).undeploy(eq(deploymentID), voidHandlerCaptor.capture());

        voidHandlerCaptor.getValue().handle(voidResult);
        done.assertRejected();
    }

    @Test
    public void testExecuteBlocking() throws Exception {

        whenVertx.executeBlocking(futureHandler).then(done.onFulfilled, done.onRejected);

        verify(vertx).<String>executeBlocking(any(), anyBoolean(), stringHandlerCaptor.capture());
        assertFalse(done.fulfilled());

        stringHandlerCaptor.getValue().handle(Future.succeededFuture());
        done.assertFulfilled();

    }

    @Test
    public void testExecuteBlocking_Fail() throws Exception {

        whenVertx.executeBlocking(futureHandler).then(done.onFulfilled, done.onRejected);

        verify(vertx).<String>executeBlocking(any(), anyBoolean(), stringHandlerCaptor.capture());
        assertFalse(done.fulfilled());

        stringHandlerCaptor.getValue().handle(Future.failedFuture(""));
        done.assertRejected();

    }

}
