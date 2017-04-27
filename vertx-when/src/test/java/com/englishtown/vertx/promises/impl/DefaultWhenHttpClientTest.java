package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.*;
import com.englishtown.vertx.promises.HttpClientResponseAndBody;
import com.englishtown.vertx.promises.RequestOptions;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link DefaultWhenHttpClient}
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultWhenHttpClientTest {

    private DefaultWhenHttpClient whenHttpClient;
    private String host = "other.host.org";
    private int port = 8080;
    private String requestURI = "/path?a=b";
    private String absoluteURI = "http://test.org/path?a=b";
    private Buffer body = Buffer.buffer();

    @Mock
    private Vertx vertx;
    @Mock
    private HttpClient client;
    @Mock
    private HttpClientRequest request;
    @Mock
    private HttpClientResponse response;
    @Mock
    private MultiMap headers;
    @Captor
    private ArgumentCaptor<Buffer> bufferCaptor;
    @Captor
    private ArgumentCaptor<MultiMap> headersCaptor;
    @Captor
    private ArgumentCaptor<Handler<HttpClientResponse>> responseHandlerCaptor;
    @Captor
    private ArgumentCaptor<Handler<Buffer>> bodyHandlerCaptor;
    @Captor
    private ArgumentCaptor<Handler<Void>> endHandlerCaptor;

    @Before
    public void setUp() throws Exception {

        When when = WhenFactory.createSync();

        when(vertx.createHttpClient()).thenReturn(client);
        when(vertx.createHttpClient(any())).thenReturn(client);
        when(client.request(any(), anyInt(), anyString(), anyString())).thenReturn(request);
        when(client.request(any(), anyString(), anyString())).thenReturn(request);
        when(client.request(any(), anyString())).thenReturn(request);
        when(client.requestAbs(any(), anyString())).thenReturn(request);
        when(request.handler(any())).thenReturn(request);
        when(request.exceptionHandler(any())).thenReturn(request);
        when(request.headers()).thenReturn(headers);
        whenHttpClient = new DefaultWhenHttpClient(vertx, when);
    }

    private void verifyResponse(Promise<HttpClientResponse> p) throws Exception {

        State<HttpClientResponse> state = p.inspect();
        assertEquals(HandlerState.PENDING, state.getState());

        verify(request).end();
        verify(request).handler(responseHandlerCaptor.capture());

        responseHandlerCaptor.getValue().handle(response);

        state = p.inspect();
        assertEquals(HandlerState.FULFILLED, state.getState());

        verify(response).endHandler(endHandlerCaptor.capture());
        endHandlerCaptor.getValue().handle(null);
        verify(client).close();

    }

    @Test
    public void testRequest1() throws Exception {

        Promise<HttpClientResponse> p = whenHttpClient.request(HttpMethod.POST, port, host, requestURI);
        verify(client).request(eq(HttpMethod.POST), eq(port), eq(host), eq(requestURI));
        verifyResponse(p);

    }

    @Test
    public void testRequest2() throws Exception {

        Promise<HttpClientResponse> p = whenHttpClient.request(HttpMethod.POST, host, requestURI);
        verify(client).request(eq(HttpMethod.POST), eq(host), eq(requestURI));
        verifyResponse(p);

    }

    @Test
    public void testRequest3() throws Exception {

        Promise<HttpClientResponse> p = whenHttpClient.request(HttpMethod.POST, requestURI);
        verify(client).request(eq(HttpMethod.POST), eq(requestURI));
        verifyResponse(p);

    }

    @Test
    public void testRequestAbs() throws Exception {

        Promise<HttpClientResponse> p = whenHttpClient.requestAbs(HttpMethod.GET, absoluteURI);
        verify(client).requestAbs(eq(HttpMethod.GET), eq(absoluteURI));
        verifyResponse(p);

    }

    @Test
    public void testRequestAbs_Client() throws Exception {

        HttpClient otherClient = mock(HttpClient.class);
        when(otherClient.requestAbs(any(), anyString())).thenReturn(request);
        RequestOptions options = new RequestOptions().setClient(otherClient);

        whenHttpClient.requestAbs(HttpMethod.GET, absoluteURI, options);
        verify(otherClient).requestAbs(any(), anyString());
        verify(request).end();

    }

    @Test
    public void testRequestAbs_ClientOptions() throws Exception {

        HttpClientOptions clientOptions = mock(HttpClientOptions.class);
        RequestOptions options = new RequestOptions().setClientOptions(clientOptions);

        whenHttpClient.requestAbs(HttpMethod.GET, absoluteURI, options);
        verify(vertx).createHttpClient(eq(clientOptions));
        verify(request).end();

    }

    @Test
    public void testRequestAbs_Data() throws Exception {

        Buffer buffer = mock(Buffer.class);
        RequestOptions options = new RequestOptions().setData(buffer);

        whenHttpClient.requestAbs(HttpMethod.GET, absoluteURI, options);
        verify(request).end(eq(buffer));

    }

    @Test
    public void testRequestAbs_Data2() throws Exception {

        String data = "hello";
        RequestOptions options = new RequestOptions().setData(data);

        whenHttpClient.requestAbs(HttpMethod.GET, absoluteURI, options);
        verify(request).end(bufferCaptor.capture());
        assertEquals("hello", bufferCaptor.getValue().toString());

    }

    @Test
    public void testRequestAbs_Header() throws Exception {

        String name = "X-Test-1";
        String value = "1";
        RequestOptions options = new RequestOptions().addHeader(name, value);

        whenHttpClient.requestAbs(HttpMethod.GET, absoluteURI, options);
        verify(headers).addAll(headersCaptor.capture());
        assertEquals(value, headersCaptor.getValue().get(name));
        verify(request).end();

    }

    @Test
    public void testRequestAbs_Headers() throws Exception {

        MultiMap headers2 = new CaseInsensitiveHeaders();
        RequestOptions options = new RequestOptions().setHeaders(headers2);

        whenHttpClient.requestAbs(HttpMethod.GET, absoluteURI, options);
        verify(headers).addAll(eq(headers2));
        verify(request).end();

    }

    @Test
    public void testRequestAbs_Chunked() throws Exception {

        RequestOptions options = new RequestOptions().setChunked(true);

        whenHttpClient.requestAbs(HttpMethod.GET, absoluteURI, options);
        verify(request).setChunked(eq(true));
        verify(request).end();

    }

    @Test
    public void testRequestAbs_Timeout() throws Exception {

        RequestOptions options = new RequestOptions().setTimeout(100);

        whenHttpClient.requestAbs(HttpMethod.GET, absoluteURI, options);
        verify(request).setTimeout(eq(100L));
        verify(request).end();

    }

    @Test
    public void testRequestAbs_WriteQueueMaxSize() throws Exception {

        RequestOptions options = new RequestOptions().setWriteQueueMaxSize(100);

        whenHttpClient.requestAbs(HttpMethod.GET, absoluteURI, options);
        verify(request).setWriteQueueMaxSize(eq(100));
        verify(request).end();

    }

    @Test
    public void testRequestAbs_PauseResponse() throws Exception {

        RequestOptions options = new RequestOptions().setPauseResponse(true);

        whenHttpClient.requestAbs(HttpMethod.GET, absoluteURI, options);
        verify(client).requestAbs(any(), any());
        verify(request).handler(responseHandlerCaptor.capture());
        verify(request).end();

        responseHandlerCaptor.getValue().handle(response);
        verify(response).pause();

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testRequestAbs_Setup() throws Exception {

        Function<HttpClientRequest, Promise<Void>> setup = mock(Function.class);
        RequestOptions options = new RequestOptions().setSetupHandler(setup);

        whenHttpClient.requestAbs(HttpMethod.GET, absoluteURI, options);

        verify(setup).apply(eq(request));
        verify(request).end();

    }

    @Test
    public void testBody() throws Exception {

        when(response.bodyHandler(any())).thenReturn(response);
        Done<Buffer> done = new Done<>();

        whenHttpClient.body(response).then(done.onFulfilled, done.onRejected);
        verify(response).resume();
        verify(response).bodyHandler(bodyHandlerCaptor.capture());
        bodyHandlerCaptor.getValue().handle(body);

        done.assertFulfilled();

    }

    private void verifyResponseAndBody(Promise<HttpClientResponseAndBody> p) throws Exception {

        State<HttpClientResponseAndBody> state = p.inspect();
        assertEquals(HandlerState.PENDING, state.getState());

        verify(request).end();
        verify(request).handler(responseHandlerCaptor.capture());

        responseHandlerCaptor.getValue().handle(response);
        verify(response).bodyHandler(bodyHandlerCaptor.capture());

        bodyHandlerCaptor.getValue().handle(body);
        verify(client).close();

        state = p.inspect();
        assertEquals(HandlerState.FULFILLED, state.getState());
        HttpClientResponseAndBody responseAndBody = state.getValue();

        assertEquals(response, responseAndBody.getResponse());
        assertEquals(body, responseAndBody.getBody());

    }

    @Test
    public void testRequestAndReadBody1() throws Exception {

        Promise<HttpClientResponseAndBody> p = whenHttpClient.requestAndReadBody(HttpMethod.GET, port, host, requestURI);
        verify(client).request(any(), eq(port), eq(host), eq(requestURI));
        verifyResponseAndBody(p);

    }

    @Test
    public void testRequestAndReadBody2() throws Exception {

        Promise<HttpClientResponseAndBody> p = whenHttpClient.requestAndReadBody(HttpMethod.GET, host, requestURI);
        verify(client).request(any(), eq(host), eq(requestURI));
        verifyResponseAndBody(p);

    }

    @Test
    public void testRequestAndReadBody3() throws Exception {

        Promise<HttpClientResponseAndBody> p = whenHttpClient.requestAndReadBody(HttpMethod.GET, requestURI);
        verify(client).request(any(), eq(requestURI));
        verifyResponseAndBody(p);

    }

    @Test(expected = IllegalStateException.class)
    public void testRequestAndReadBody_Pause_Fail() throws Exception {

        RequestOptions options = new RequestOptions().setPauseResponse(true);
        whenHttpClient.requestAndReadBody(HttpMethod.GET, requestURI, options);

    }

    @Test
    public void testRequestAbsAndReadBody() throws Exception {

        Promise<HttpClientResponseAndBody> p = whenHttpClient.requestAbsAndReadBody(HttpMethod.GET, absoluteURI);
        verify(client).requestAbs(any(), eq(absoluteURI));
        verifyResponseAndBody(p);

    }

}
