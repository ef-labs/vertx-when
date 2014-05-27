package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Done2;
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
import org.vertx.java.core.Vertx;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Container;

import java.util.concurrent.Executor;

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
    long timeout = 10;
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
    @Mock
    AsyncResult<Message<Object>> messageAsyncResult;
    @Captor
    ArgumentCaptor<Handler<Message>> messageCaptor;
    @Captor
    ArgumentCaptor<Handler<AsyncResult<Message<Object>>>> asyncResultCaptor;
    @Captor
    ArgumentCaptor<Handler<Message<JsonObject>>> jsonMessageCaptor;
    @Captor
    ArgumentCaptor<Handler<AsyncResult<Void>>> voidHandlerCaptor;

    @Before
    public void setUp() throws Exception {
        WhenProgress.setNextTick(new Executor() {
            @Override
            public void execute(Runnable command) {
                command.run();
            }
        });
        when(vertx.eventBus()).thenReturn(eventBus);
        whenEventBus = new DefaultWhenEventBus(vertx, container);
    }

    @Test
    public void testGetEventBus() throws Exception {

        assertEquals(eventBus, whenEventBus.getEventBus());

        EventBus mock = mock(EventBus.class);
        whenEventBus = new DefaultWhenEventBus(mock);
        assertEquals(mock, whenEventBus.getEventBus());

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
    public void testSendWithTimeout_Object() throws Exception {
        Object message = new Object();
        Done2<Message<Object>> done = new Done2<>();
        when(messageAsyncResult.succeeded()).thenReturn(true);
        whenEventBus.sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertSuccess();
    }

    @Test
    public void testSendWithTimeout_Object_Failed() throws Exception {
        Object message = new Object();
        Done2<Message<Object>> done = new Done2<>();
        whenEventBus.sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertFailed();
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
    public void testSendWithTimeout_JsonObject() throws Exception {
        JsonObject message = new JsonObject();
        when(messageAsyncResult.succeeded()).thenReturn(true);
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertSuccess();
    }

    @Test
    public void testSendWithTimeout_JsonObject_Failed() throws Exception {
        JsonObject message = new JsonObject();
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertFailed();
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
    public void testSendWithTimeout_JsonArray() throws Exception {
        JsonArray message = new JsonArray();
        when(messageAsyncResult.succeeded()).thenReturn(true);
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertSuccess();
    }

    @Test
    public void testSendWithTimeout_JsonArray_Failed() throws Exception {
        JsonArray message = new JsonArray();
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertFailed();
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
    public void testSendWithTimeout_Buffer() throws Exception {
        Buffer message = new Buffer();
        when(messageAsyncResult.succeeded()).thenReturn(true);
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertSuccess();
    }

    @Test
    public void testSendWithTimeout_Buffer_Failed() throws Exception {
        Buffer message = new Buffer();
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertFailed();
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
    public void testSendWithTimeout_Bytes() throws Exception {
        byte[] message = new byte[0];
        when(messageAsyncResult.succeeded()).thenReturn(true);
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertSuccess();
    }

    @Test
    public void testSendWithTimeout_Bytes_Failed() throws Exception {
        byte[] message = new byte[0];
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertFailed();
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
    public void testSendWithTimeout_String() throws Exception {
        String message = "";
        when(messageAsyncResult.succeeded()).thenReturn(true);
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertSuccess();
    }

    @Test
    public void testSendWithTimeout_String_Failed() throws Exception {
        String message = "";
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertFailed();
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
    public void testSendWithTimeout_Integer() throws Exception {
        Integer message = 1;
        when(messageAsyncResult.succeeded()).thenReturn(true);
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertSuccess();
    }

    @Test
    public void testSendWithTimeout_Buffer_Integer() throws Exception {
        Integer message = 1;
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertFailed();
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
    public void testSendWithTimeout_Long() throws Exception {
        Long message = 1l;
        when(messageAsyncResult.succeeded()).thenReturn(true);
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertSuccess();
    }

    @Test
    public void testSendWithTimeout_Buffer_Long() throws Exception {
        Long message = 1l;
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertFailed();
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
    public void testSendWithTimeout_Float() throws Exception {
        Float message = 1f;
        when(messageAsyncResult.succeeded()).thenReturn(true);
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertSuccess();
    }

    @Test
    public void testSendWithTimeout_Buffer_Float() throws Exception {
        Float message = 1f;
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertFailed();
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
    public void testSendWithTimeout_Double() throws Exception {
        Double message = 1d;
        when(messageAsyncResult.succeeded()).thenReturn(true);
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertSuccess();
    }

    @Test
    public void testSendWithTimeout_Buffer_Double() throws Exception {
        Double message = 1d;
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertFailed();
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
    public void testSendWithTimeout_Boolean() throws Exception {
        Boolean message = true;
        when(messageAsyncResult.succeeded()).thenReturn(true);
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertSuccess();
    }

    @Test
    public void testSendWithTimeout_Buffer_Boolean() throws Exception {
        Boolean message = true;
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertFailed();
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
    public void testSendWithTimeout_Short() throws Exception {
        Short message = 1;
        when(messageAsyncResult.succeeded()).thenReturn(true);
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertSuccess();
    }

    @Test
    public void testSendWithTimeout_Buffer_Short() throws Exception {
        Short message = 1;
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertFailed();
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
    public void testSendWithTimeout_Character() throws Exception {
        Character message = 1;
        when(messageAsyncResult.succeeded()).thenReturn(true);
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertSuccess();
    }

    @Test
    public void testSendWithTimeout_Buffer_Character() throws Exception {
        Character message = 1;
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertFailed();
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
    public void testSendWithTimeout_Byte() throws Exception {
        Byte message = 1;
        when(messageAsyncResult.succeeded()).thenReturn(true);
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertSuccess();
    }

    @Test
    public void testSendWithTimeout_Buffer_Byte() throws Exception {
        Byte message = 1;
        whenEventBus.<JsonObject>sendWithTimeout(address, message, timeout).then(done.onSuccess, done.onFail);
        verify(eventBus).sendWithTimeout(eq(address), eq(message), eq(timeout), asyncResultCaptor.capture());
        asyncResultCaptor.getValue().handle(messageAsyncResult);
        done.assertFailed();
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
