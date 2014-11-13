package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Done;
import com.englishtown.promises.When;
import com.englishtown.promises.WhenFactory;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.*;
import io.vertx.core.streams.WriteStream;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link DefaultWhenHttpClient}
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultWhenHttpClientTest {

    DefaultWhenHttpClient whenHttpClient;
    Done<HttpClientResponse> done = new Done<>();

    @Mock
    Vertx vertx;
    @Mock
    HttpClient client;
    @Mock
    HttpClientRequest request;
    @Mock
    HttpClientResponse response;
    @Mock
    HttpClientOptions options;
    @Mock
    WriteStream<Buffer> writeStream;
    @Captor
    ArgumentCaptor<Handler<HttpClientResponse>> handlerCaptor;
    @Captor
    ArgumentCaptor<Handler<Void>> endHandlerCaptor;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        When when = WhenFactory.createSync();
        when(vertx.createHttpClient(any())).thenReturn(client);
        when(client.request(any(), anyString(), any(Handler.class))).thenReturn(request);
        when(client.exceptionHandler(any(Handler.class))).thenReturn(client);
        whenHttpClient = new DefaultWhenHttpClient(vertx, when);
    }

    @Test
    public void testRequest_1Success() throws Exception {
        when(response.statusCode()).thenReturn(HttpResponseStatus.OK.code());
        String url = "http://test.englishtown.com";
        whenHttpClient.request(HttpMethod.GET, url, options).then(done.onFulfilled, done.onRejected);
        verify(client).request(eq(HttpMethod.GET), anyString(), handlerCaptor.capture());
        handlerCaptor.getValue().handle(response);
        done.assertFulfilled();
    }

    @Test
    public void testRequest_1Failed() throws Exception {
        when(response.statusCode()).thenReturn(HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
        String url = "http://test.englishtown.com";
        whenHttpClient.request(HttpMethod.GET, url, options).then(done.onFulfilled, done.onRejected);
        verify(client).request(eq(HttpMethod.GET), anyString(), handlerCaptor.capture());
        handlerCaptor.getValue().handle(response);
        done.assertRejected();
    }

    @Test
    public void testRequest_2() throws Exception {
        when(response.statusCode()).thenReturn(HttpResponseStatus.OK.code());
        String url = "http://test.englishtown.com";
        whenHttpClient.request(HttpMethod.GET, url, options, req -> assertEquals(request, req)).then(done.onFulfilled, done.onRejected);
        verify(client).request(eq(HttpMethod.GET), anyString(), handlerCaptor.capture());
        handlerCaptor.getValue().handle(response);
        done.assertFulfilled();
    }

    @Test
    public void testRequest_3() throws Exception {
        when(response.statusCode()).thenReturn(HttpResponseStatus.OK.code());
        String url = "";
        whenHttpClient.request(HttpMethod.GET, url, client).then(done.onFulfilled, done.onRejected);
        verify(client).request(eq(HttpMethod.GET), anyString(), handlerCaptor.capture());
        handlerCaptor.getValue().handle(response);
        done.assertFulfilled();
    }

    @Test
    public void testRequest_4() throws Exception {
        when(response.statusCode()).thenReturn(HttpResponseStatus.OK.code());
        String url = "";
        whenHttpClient.request(HttpMethod.GET, url, client, req -> assertEquals(request, req)).then(done.onFulfilled, done.onRejected);
        verify(client).request(eq(HttpMethod.GET), anyString(), handlerCaptor.capture());
        handlerCaptor.getValue().handle(response);
        done.assertFulfilled();
    }

    @Test
    public void testRequest_with_WriteStream() throws Exception {
        when(response.statusCode()).thenReturn(HttpResponseStatus.OK.code());
        String url = "";
        whenHttpClient.request(HttpMethod.GET, url, client, null, writeStream).then(done.onFulfilled, done.onRejected);
        verify(client).request(eq(HttpMethod.GET), anyString(), handlerCaptor.capture());
        handlerCaptor.getValue().handle(response);
        verify(response).endHandler(endHandlerCaptor.capture());
        endHandlerCaptor.getValue().handle(null);
        done.assertFulfilled();
    }

}
