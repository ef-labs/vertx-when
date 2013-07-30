package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.*;
import com.englishtown.promises.Runnable;
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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
    Done2<HttpClientResponse> done = new Done2<>();

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

    @Before
    public void setUp() throws Exception {
        when(vertx.createHttpClient()).thenReturn(client);
        when(client.request(anyString(), anyString(), any(Handler.class))).thenReturn(request);
        when(client.setHost(anyString())).thenReturn(client);
        when(client.setPort(anyInt())).thenReturn(client);
        when(client.setConnectTimeout(anyInt())).thenReturn(client);
        when(client.exceptionHandler(any(Handler.class))).thenReturn(client);
        whenHttpClient = new DefaultWhenHttpClient(vertx);
    }

    @Test
    public void testRequest_1Success() throws Exception {
        when(response.statusCode()).thenReturn(HttpResponseStatus.OK.code());
        URI url = URI.create("http://test.englishtown.com");
        whenHttpClient.request(HttpMethod.GET.name(), url).then(done.onSuccess, done.onFail);
        verify(client).request(eq(HttpMethod.GET.name()), anyString(), handlerCaptor.capture());
        handlerCaptor.getValue().handle(response);
        done.assertSuccess();
    }

    @Test
    public void testRequest_1Failed() throws Exception {
        when(response.statusCode()).thenReturn(HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
        URI url = URI.create("http://test.englishtown.com");
        whenHttpClient.request(HttpMethod.GET.name(), url).then(done.onSuccess, done.onFail);
        verify(client).request(eq(HttpMethod.GET.name()), anyString(), handlerCaptor.capture());
        handlerCaptor.getValue().handle(response);
        done.assertFailed();
    }

    @Test
    public void testRequest_2() throws Exception {
        when(response.statusCode()).thenReturn(HttpResponseStatus.OK.code());
        URI url = URI.create("http://test.englishtown.com");
        whenHttpClient.request(HttpMethod.GET.name(), url, new Handler<HttpClientRequest>() {
            @Override
            public void handle(HttpClientRequest req) {
                assertEquals(request, req);
            }
        }).then(done.onSuccess, done.onFail);
        verify(client).request(eq(HttpMethod.GET.name()), anyString(), handlerCaptor.capture());
        handlerCaptor.getValue().handle(response);
        done.assertSuccess();
    }

    @Test
    public void testRequest_3() throws Exception {
        when(response.statusCode()).thenReturn(HttpResponseStatus.OK.code());
        String url = "";
        whenHttpClient.request(HttpMethod.GET.name(), url, client).then(done.onSuccess, done.onFail);
        verify(client).request(eq(HttpMethod.GET.name()), anyString(), handlerCaptor.capture());
        handlerCaptor.getValue().handle(response);
        done.assertSuccess();
    }

    @Test
    public void testRequest_4() throws Exception {
        when(response.statusCode()).thenReturn(HttpResponseStatus.OK.code());
        String url = "";
        whenHttpClient.request(HttpMethod.GET.name(), url, client, new Handler<HttpClientRequest>() {
            @Override
            public void handle(HttpClientRequest req) {
                assertEquals(request, req);
            }
        }).then(done.onSuccess, done.onFail);
        verify(client).request(eq(HttpMethod.GET.name()), anyString(), handlerCaptor.capture());
        handlerCaptor.getValue().handle(response);
        done.assertSuccess();
    }

    public void test() {

        List<Promise<HttpClientResponse, Void>> promises = new ArrayList<>();
        When<HttpClientResponse, Void> when = new When<>();

        promises.add(whenHttpClient.request(HttpMethod.GET.name(), URI.create("http://test.englishtown.com/test1")));
        promises.add(whenHttpClient.request(HttpMethod.POST.name(), URI.create("http://test.englishtown.com/test2")));

        when.all(promises,
                new Runnable<Promise<List<HttpClientResponse>, Void>, List<HttpClientResponse>>() {
                    @Override
                    public Promise<List<HttpClientResponse>, Void> run(List<HttpClientResponse> value) {
                        // On success
                        return null;
                    }
                },
                new Runnable<Promise<List<HttpClientResponse>, Void>, Value<List<HttpClientResponse>>>() {
                    @Override
                    public Promise<List<HttpClientResponse>, Void> run(Value<List<HttpClientResponse>> value) {
                        // On fail
                        return null;
                    }
                }
        );
    }
}
