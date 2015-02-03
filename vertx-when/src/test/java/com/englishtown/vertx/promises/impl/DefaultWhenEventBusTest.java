package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Done;
import com.englishtown.promises.When;
import com.englishtown.promises.WhenFactory;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DefaultWhenEventBus}
 */
@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class DefaultWhenEventBusTest {

    DefaultWhenEventBus whenEventBus;
    String address = "et.test.eb";
    Done<Message<Object>> done = new Done<>();

    @Mock
    Vertx vertx;
    @Mock
    EventBus eventBus;
    @Mock
    Message<Object> replyMessage;
    @Mock
    AsyncResult<Void> voidResult;
    @Mock
    AsyncResult<Message<Object>> replyMessageResult;
    @Captor
    ArgumentCaptor<Handler<AsyncResult<Void>>> voidHandlerCaptor;
    @Captor
    ArgumentCaptor<Handler<AsyncResult<Message<Object>>>> resultCaptor;

    @Before
    public void setUp() throws Exception {
        When when = WhenFactory.createSync();
        when(vertx.eventBus()).thenReturn(eventBus);
        whenEventBus = new DefaultWhenEventBus(vertx, when);
    }

    @Test
    public void testGetEventBus() throws Exception {

        assertEquals(eventBus, whenEventBus.getEventBus());

        EventBus mock = mock(EventBus.class);
        whenEventBus = new DefaultWhenEventBus(mock, mock(When.class));
        assertEquals(mock, whenEventBus.getEventBus());

    }

    @Test
    public void testClose_Success() throws Exception {

        Done<Void> done = new Done<>();

        whenEventBus.close().then(done.onFulfilled, done.onRejected);

        verify(eventBus).close(voidHandlerCaptor.capture());

        when(voidResult.succeeded()).thenReturn(true);
        voidHandlerCaptor.getValue().handle(voidResult);

        done.assertFulfilled();
    }

    @Test
    public void testClose_Failed() throws Exception {

        Done<Void> done = new Done<>();

        whenEventBus.close().then(done.onFulfilled, done.onRejected);

        verify(eventBus).close(voidHandlerCaptor.capture());

        Throwable t = new Throwable();
        when(voidResult.cause()).thenReturn(t);
        voidHandlerCaptor.getValue().handle(voidResult);

        done.assertRejected();
        assertEquals(t, done.getCause());
    }

    @Test
    public void testSend_Succeeded() throws Exception {
        Object message = new Object();

        whenEventBus.send(address, message).then(done.onFulfilled, done.onRejected);
        verify(eventBus).send(eq(address), eq(message), resultCaptor.capture());

        when(replyMessageResult.succeeded()).thenReturn(true);
        when(replyMessageResult.result()).thenReturn(replyMessage);

        resultCaptor.getValue().handle(replyMessageResult);

        done.assertFulfilled();
        assertEquals(replyMessage, done.getValue());

    }

    @Test
    public void testSend_Failed() throws Exception {
        Object message = new Object();

        whenEventBus.send(address, message).then(done.onFulfilled, done.onRejected);
        verify(eventBus).send(eq(address), eq(message), resultCaptor.capture());

        Throwable t = new Throwable();
        when(replyMessageResult.failed()).thenReturn(true);
        when(replyMessageResult.cause()).thenReturn(t);

        resultCaptor.getValue().handle(replyMessageResult);

        done.assertRejected();
        assertEquals(t, done.getCause());

    }

    @Test
    public void testSend_WithOptions_Succeeded() throws Exception {
        Object message = new Object();
        DeliveryOptions options = new DeliveryOptions();

        whenEventBus.send(address, message, options).then(done.onFulfilled, done.onRejected);
        verify(eventBus).send(eq(address), eq(message), eq(options), resultCaptor.capture());

        when(replyMessageResult.succeeded()).thenReturn(true);
        when(replyMessageResult.result()).thenReturn(replyMessage);

        resultCaptor.getValue().handle(replyMessageResult);

        done.assertFulfilled();
        assertEquals(replyMessage, done.getValue());

    }

    @Test
    public void testSend_WithOptions_Failed() throws Exception {
        Object message = new Object();
        DeliveryOptions options = new DeliveryOptions();

        whenEventBus.send(address, message, options).then(done.onFulfilled, done.onRejected);
        verify(eventBus).send(eq(address), eq(message), eq(options), resultCaptor.capture());

        Throwable t = new Throwable();
        when(replyMessageResult.failed()).thenReturn(true);
        when(replyMessageResult.cause()).thenReturn(t);

        resultCaptor.getValue().handle(replyMessageResult);

        done.assertRejected();
        assertEquals(t, done.getCause());

    }

}
