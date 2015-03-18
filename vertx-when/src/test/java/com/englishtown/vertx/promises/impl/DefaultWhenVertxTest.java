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

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link DefaultWhenVertx}
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultWhenVertxTest {

    private DefaultWhenVertx whenContainer;
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
    @Captor
    ArgumentCaptor<Handler<AsyncResult<String>>> handlerCaptor;
    @Captor
    ArgumentCaptor<Handler<AsyncResult<Void>>> voidHandlerCaptor;


    @Before
    public void setUp() {
        When when = WhenFactory.createSync();
        whenContainer = new DefaultWhenVertx(vertx, when);
    }

    @Test
    public void testDeployVerticle_Identifier_Success1() throws Exception {
        when(result.succeeded()).thenReturn(true);

        whenContainer.deployVerticle(identifier).then(done.onFulfilled, done.onRejected);
        verify(vertx).deployVerticle(eq(identifier), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);
        done.assertFulfilled();
    }

    @Test
    public void testDeployVerticle_Identifier_Fail1() throws Exception {
        when(result.succeeded()).thenReturn(false);

        whenContainer.deployVerticle(identifier).then(done.onFulfilled, done.onRejected);
        verify(vertx).deployVerticle(eq(identifier), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);
        done.assertRejected();
    }

    @Test
    public void testDeployVerticle_Identifier_Success2() throws Exception {
        when(result.succeeded()).thenReturn(true);

        whenContainer.deployVerticle(identifier, options).then(done.onFulfilled, done.onRejected);
        verify(vertx).deployVerticle(eq(identifier), eq(options), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);
        done.assertFulfilled();
    }

    @Test
    public void testDeployVerticle_Identifier_Fail2() throws Exception {
        when(result.succeeded()).thenReturn(false);

        whenContainer.deployVerticle(identifier, options).then(done.onFulfilled, done.onRejected);
        verify(vertx).deployVerticle(eq(identifier), eq(options), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);
        done.assertRejected();
    }

    @Test
    public void testDeployVerticle_Verticle_Success1() throws Exception {
        when(result.succeeded()).thenReturn(true);

        whenContainer.deployVerticle(verticle).then(done.onFulfilled, done.onRejected);
        verify(vertx).deployVerticle(eq(verticle), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);
        done.assertFulfilled();
    }

    @Test
    public void testDeployVerticle_Verticle_Fail1() throws Exception {
        when(result.succeeded()).thenReturn(false);

        whenContainer.deployVerticle(verticle).then(done.onFulfilled, done.onRejected);
        verify(vertx).deployVerticle(eq(verticle), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);
        done.assertRejected();
    }

    @Test
    public void testDeployVerticle_Verticle_Success2() throws Exception {
        when(result.succeeded()).thenReturn(true);

        whenContainer.deployVerticle(verticle, options).then(done.onFulfilled, done.onRejected);
        verify(vertx).deployVerticle(eq(verticle), eq(options), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);
        done.assertFulfilled();
    }

    @Test
    public void testDeployVerticle_Verticle_Fail2() throws Exception {
        when(result.succeeded()).thenReturn(false);

        whenContainer.deployVerticle(verticle, options).then(done.onFulfilled, done.onRejected);
        verify(vertx).deployVerticle(eq(verticle), eq(options), handlerCaptor.capture());

        handlerCaptor.getValue().handle(result);
        done.assertRejected();
    }

    @Test
    public void testUndeploy() throws Exception {
        String deploymentID = "id";
        Done<Void> done = new Done<>();
        when(voidResult.succeeded()).thenReturn(true);

        whenContainer.undeploy(deploymentID).then(done.onFulfilled, done.onRejected);
        verify(vertx).undeploy(eq(deploymentID), voidHandlerCaptor.capture());

        voidHandlerCaptor.getValue().handle(voidResult);
        done.assertFulfilled();
    }

    @Test
    public void testUndeploy_Fail() throws Exception {
        String deploymentID = "id";
        Done<Void> done = new Done<>();

        whenContainer.undeploy(deploymentID).then(done.onFulfilled, done.onRejected);
        verify(vertx).undeploy(eq(deploymentID), voidHandlerCaptor.capture());

        voidHandlerCaptor.getValue().handle(voidResult);
        done.assertRejected();
    }

}
