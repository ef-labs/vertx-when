package com.englishtown.vertx.promises.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;

import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link VertxExecutor}
 */
@RunWith(MockitoJUnitRunner.class)
public class VertxExecutorTest {

    VertxExecutor executor;

    @Mock
    Vertx vertx;
    @Mock
    Runnable command;
    @Captor
    ArgumentCaptor<Handler<Void>> handlerCaptor;

    @Before
    public void setUp() throws Exception {
        executor = new VertxExecutor(vertx);
    }

    @Test
    public void testExecute() throws Exception {

        executor.execute(command);

        verify(vertx).runOnContext(handlerCaptor.capture());
        handlerCaptor.getValue().handle(null);

        verify(command).run();

    }
}
