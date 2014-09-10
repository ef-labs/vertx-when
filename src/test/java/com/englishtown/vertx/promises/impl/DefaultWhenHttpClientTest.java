package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.*;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpClient;
import org.vertx.java.core.http.HttpClientRequest;
import org.vertx.java.core.http.HttpClientResponse;
import org.vertx.java.core.streams.WriteStream;

import java.lang.Runnable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
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
    @Captor
    ArgumentCaptor<Handler<HttpClientResponse>> handlerCaptor;
    @Captor
    ArgumentCaptor<Handler<Void>> endHandlerCaptor;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        When when = WhenFactory.createSync();
        when(vertx.createHttpClient()).thenReturn(client);
        when(client.request(anyString(), anyString(), any(Handler.class))).thenReturn(request);
        when(client.setHost(anyString())).thenReturn(client);
        when(client.setPort(anyInt())).thenReturn(client);
        when(client.setConnectTimeout(anyInt())).thenReturn(client);
        when(client.exceptionHandler(any(Handler.class))).thenReturn(client);
        whenHttpClient = new DefaultWhenHttpClient(vertx, when);
    }

    @Test
    public void testRequest_1Success() throws Exception {
        when(response.statusCode()).thenReturn(HttpResponseStatus.OK.code());
        URI url = URI.create("http://test.englishtown.com");
        whenHttpClient.request(HttpMethod.GET.name(), url).then(done.onFulfilled, done.onRejected);
        verify(client).request(eq(HttpMethod.GET.name()), anyString(), handlerCaptor.capture());
        handlerCaptor.getValue().handle(response);
        done.assertFulfilled();
    }

    @Test
    public void testRequest_1Failed() throws Exception {
        when(response.statusCode()).thenReturn(HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
        URI url = URI.create("http://test.englishtown.com");
        whenHttpClient.request(HttpMethod.GET.name(), url).then(done.onFulfilled, done.onRejected);
        verify(client).request(eq(HttpMethod.GET.name()), anyString(), handlerCaptor.capture());
        handlerCaptor.getValue().handle(response);
        done.assertRejected();
    }

    @Test
    public void testRequest_2() throws Exception {
        when(response.statusCode()).thenReturn(HttpResponseStatus.OK.code());
        URI url = URI.create("http://test.englishtown.com");
        whenHttpClient.request(HttpMethod.GET.name(), url, req -> assertEquals(request, req)).then(done.onFulfilled, done.onRejected);
        verify(client).request(eq(HttpMethod.GET.name()), anyString(), handlerCaptor.capture());
        handlerCaptor.getValue().handle(response);
        done.assertFulfilled();
    }

    @Test
    public void testRequest_3() throws Exception {
        when(response.statusCode()).thenReturn(HttpResponseStatus.OK.code());
        String url = "";
        whenHttpClient.request(HttpMethod.GET.name(), url, client).then(done.onFulfilled, done.onRejected);
        verify(client).request(eq(HttpMethod.GET.name()), anyString(), handlerCaptor.capture());
        handlerCaptor.getValue().handle(response);
        done.assertFulfilled();
    }

    @Test
    public void testRequest_4() throws Exception {
        when(response.statusCode()).thenReturn(HttpResponseStatus.OK.code());
        String url = "";
        whenHttpClient.request(HttpMethod.GET.name(), url, client, req -> assertEquals(request, req)).then(done.onFulfilled, done.onRejected);
        verify(client).request(eq(HttpMethod.GET.name()), anyString(), handlerCaptor.capture());
        handlerCaptor.getValue().handle(response);
        done.assertFulfilled();
    }

    @Test
    public void testRequest_with_WriteStream() throws Exception {
        WriteStream<?> writeStream = mock(WriteStream.class);
        when(response.statusCode()).thenReturn(HttpResponseStatus.OK.code());
        String url = "";
        whenHttpClient.request(HttpMethod.GET.name(), url, client, null, writeStream).then(done.onFulfilled, done.onRejected);
        verify(client).request(eq(HttpMethod.GET.name()), anyString(), handlerCaptor.capture());
        handlerCaptor.getValue().handle(response);
        verify(response).endHandler(endHandlerCaptor.capture());
        endHandlerCaptor.getValue().handle(null);
        done.assertFulfilled();
    }

}
