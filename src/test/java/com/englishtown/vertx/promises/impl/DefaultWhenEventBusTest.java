package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Done2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Container;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DefaultWhenEventBus}
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultWhenEventBusTest {

    DefaultWhenEventBus whenEventBus;
    String address = "et.test.eb";
    Done2<Message<JsonObject>> done = new Done2<>();

    @Mock
    Vertx vertx;
    @Mock
    Container container;
    @Mock
    EventBus eventBus;
    @Mock
    Message<JsonObject> jsonMessage;
    @Mock
    AsyncResult<Void> asyncResult;
    @Captor
    ArgumentCaptor<Handler<Message>> messageCaptor;
    @Captor
    ArgumentCaptor<Handler<Message<JsonObject>>> jsonMessageCaptor;
    @Captor
    ArgumentCaptor<Handler<AsyncResult<Void>>> voidHandlerCaptor;

    @Before
    public void setUp() throws Exception {
        when(vertx.eventBus()).thenReturn(eventBus);
        whenEventBus = new DefaultWhenEventBus(vertx, container);
    }

    @Test
    public void testSend_Object() throws Exception {
        Message<Object> mock = mock(Message.class);
        Object message = new Object();
        Done2<Message> done = new Done2<>();
        whenEventBus.send(address, message).then(done.onSuccess, done.onFail);
        verify(eventBus).send(eq(address), eq(message), messageCaptor.capture());
        messageCaptor.getValue().handle(mock);
        done.assertSuccess();
    }

    @Test
    public void testSend_JsonObject() throws Exception {
        JsonObject message = new JsonObject();
        whenEventBus.<JsonObject>send(address, message).then(done.onSuccess, done.onFail);
        verify(eventBus).send(eq(address), eq(message), jsonMessageCaptor.capture());
        jsonMessageCaptor.getValue().handle(jsonMessage);
        done.assertSuccess();
    }

    @Test
    public void testSend_JsonArray() throws Exception {
        JsonArray message = new JsonArray();
        whenEventBus.<JsonObject>send(address, message).then(done.onSuccess, done.onFail);
        verify(eventBus).send(eq(address), eq(message), jsonMessageCaptor.capture());
        jsonMessageCaptor.getValue().handle(jsonMessage);
        done.assertSuccess();
    }

    @Test
    public void testSend_Buffer() throws Exception {
        Buffer message = new Buffer();
        whenEventBus.<JsonObject>send(address, message).then(done.onSuccess, done.onFail);
        verify(eventBus).send(eq(address), eq(message), jsonMessageCaptor.capture());
        jsonMessageCaptor.getValue().handle(jsonMessage);
        done.assertSuccess();
    }

    @Test
    public void testSend_Bytes() throws Exception {
        byte[] message = new byte[0];
        whenEventBus.<JsonObject>send(address, message).then(done.onSuccess, done.onFail);
        verify(eventBus).send(eq(address), eq(message), jsonMessageCaptor.capture());
        jsonMessageCaptor.getValue().handle(jsonMessage);
        done.assertSuccess();
    }

    @Test
    public void testSend_String() throws Exception {
        String message = "";
        whenEventBus.<JsonObject>send(address, message).then(done.onSuccess, done.onFail);
        verify(eventBus).send(eq(address), eq(message), jsonMessageCaptor.capture());
        jsonMessageCaptor.getValue().handle(jsonMessage);
        done.assertSuccess();
    }

    @Test
    public void testSend_Integer() throws Exception {
        Integer message = 1;
        whenEventBus.<JsonObject>send(address, message).then(done.onSuccess, done.onFail);
        verify(eventBus).send(eq(address), eq(message), jsonMessageCaptor.capture());
        jsonMessageCaptor.getValue().handle(jsonMessage);
        done.assertSuccess();
    }

    @Test
    public void testSend_Long() throws Exception {
        Long message = 1l;
        whenEventBus.<JsonObject>send(address, message).then(done.onSuccess, done.onFail);
        verify(eventBus).send(eq(address), eq(message), jsonMessageCaptor.capture());
        jsonMessageCaptor.getValue().handle(jsonMessage);
        done.assertSuccess();
    }

    @Test
    public void testSend_Float() throws Exception {
        Float message = 1f;
        whenEventBus.<JsonObject>send(address, message).then(done.onSuccess, done.onFail);
        verify(eventBus).send(eq(address), eq(message), jsonMessageCaptor.capture());
        jsonMessageCaptor.getValue().handle(jsonMessage);
        done.assertSuccess();
    }

    @Test
    public void testSend_Double() throws Exception {
        Double message = 1d;
        whenEventBus.<JsonObject>send(address, message).then(done.onSuccess, done.onFail);
        verify(eventBus).send(eq(address), eq(message), jsonMessageCaptor.capture());
        jsonMessageCaptor.getValue().handle(jsonMessage);
        done.assertSuccess();
    }

    @Test
    public void testSend_Boolean() throws Exception {
        Boolean message = true;
        whenEventBus.<JsonObject>send(address, message).then(done.onSuccess, done.onFail);
        verify(eventBus).send(eq(address), eq(message), jsonMessageCaptor.capture());
        jsonMessageCaptor.getValue().handle(jsonMessage);
        done.assertSuccess();
    }

    @Test
    public void testSend_Short() throws Exception {
        Short message = 1;
        whenEventBus.<JsonObject>send(address, message).then(done.onSuccess, done.onFail);
        verify(eventBus).send(eq(address), eq(message), jsonMessageCaptor.capture());
        jsonMessageCaptor.getValue().handle(jsonMessage);
        done.assertSuccess();
    }

    @Test
    public void testSend_Character() throws Exception {
        Character message = 1;
        whenEventBus.<JsonObject>send(address, message).then(done.onSuccess, done.onFail);
        verify(eventBus).send(eq(address), eq(message), jsonMessageCaptor.capture());
        jsonMessageCaptor.getValue().handle(jsonMessage);
        done.assertSuccess();
    }

    @Test
    public void testSend_Byte() throws Exception {
        Byte message = 1;
        whenEventBus.<JsonObject>send(address, message).then(done.onSuccess, done.onFail);
        verify(eventBus).send(eq(address), eq(message), jsonMessageCaptor.capture());
        jsonMessageCaptor.getValue().handle(jsonMessage);
        done.assertSuccess();
    }

    @Test
    public void testUnregisterHandler_Success() throws Exception {
        when(asyncResult.succeeded()).thenReturn(true);
        Handler<Message> handler = mock(Handler.class);
        Done2<Void> done = new Done2<>();
        whenEventBus.unregisterHandler(address, handler).then(done.onSuccess, done.onFail);
        verify(eventBus).unregisterHandler(eq(address), eq(handler), voidHandlerCaptor.capture());
        voidHandlerCaptor.getValue().handle(asyncResult);
        done.assertSuccess();
    }

    @Test
    public void testUnregisterHandler_Failed() throws Exception {
        when(asyncResult.succeeded()).thenReturn(false);
        Handler<Message> handler = mock(Handler.class);
        Done2<Void> done = new Done2<>();
        whenEventBus.unregisterHandler(address, handler).then(done.onSuccess, done.onFail);
        verify(eventBus).unregisterHandler(eq(address), eq(handler), voidHandlerCaptor.capture());
        voidHandlerCaptor.getValue().handle(asyncResult);
        done.assertFailed();
    }

    @Test
    public void testRegisterHandler_Success() throws Exception {
        when(asyncResult.succeeded()).thenReturn(true);
        Handler<Message> handler = mock(Handler.class);
        Done2<Void> done = new Done2<>();
        whenEventBus.registerHandler(address, handler).then(done.onSuccess, done.onFail);
        verify(eventBus).registerHandler(eq(address), eq(handler), voidHandlerCaptor.capture());
        voidHandlerCaptor.getValue().handle(asyncResult);
        done.assertSuccess();
    }

    @Test
    public void testRegisterHandler_Failed() throws Exception {
        when(asyncResult.succeeded()).thenReturn(false);
        Handler<Message> handler = mock(Handler.class);
        Done2<Void> done = new Done2<>();
        whenEventBus.registerHandler(address, handler).then(done.onSuccess, done.onFail);
        verify(eventBus).registerHandler(eq(address), eq(handler), voidHandlerCaptor.capture());
        voidHandlerCaptor.getValue().handle(asyncResult);
        done.assertFailed();
    }

}
