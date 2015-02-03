package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Done;
import com.englishtown.promises.Promise;
import com.englishtown.promises.When;
import com.englishtown.promises.WhenFactory;
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
    private ArgumentCaptor<Map<String, String>> headersCaptor;
    @Captor
    private ArgumentCaptor<Handler<HttpClientResponse>> responseHandlerCaptor;
    @Captor
    private ArgumentCaptor<Handler<Buffer>> bufferHandlerCaptor;

    @Before
    public void setUp() throws Exception {
        When when = WhenFactory.createSync();
        when(vertx.createHttpClient(any())).thenReturn(client);
        when(client.request(any(), anyInt(), anyString(), anyString(), any())).thenReturn(request);
        when(client.request(any(), anyString(), any())).thenReturn(request);
        when(request.exceptionHandler(any())).thenReturn(request);
        when(request.headers()).thenReturn(headers);
        whenHttpClient = new DefaultWhenHttpClient(vertx, when);
    }

    @Test
    public void testRequest1() throws Exception {

        whenHttpClient.request(HttpMethod.GET, absoluteURI);
        verify(client).request(eq(HttpMethod.GET), eq(absoluteURI), any());
        verify(request).end();

    }

    @Test
    public void testRequest2() throws Exception {

        whenHttpClient.request(HttpMethod.POST, port, host, requestURI);
        verify(client).request(eq(HttpMethod.POST), eq(port), eq(host), eq(requestURI), any());
        verify(request).end();

    }

    @Test
    public void testRequest_Client() throws Exception {

        HttpClient otherClient = mock(HttpClient.class);
        when(otherClient.request(any(), anyString(), any())).thenReturn(request);
        RequestOptions options = new RequestOptions().setClient(otherClient);

        whenHttpClient.request(HttpMethod.GET, absoluteURI, options);
        verify(otherClient).request(any(), anyString(), any());
        verify(request).end();

    }

    @Test
    public void testRequest_ClientOptions() throws Exception {

        HttpClientOptions clientOptions = mock(HttpClientOptions.class);
        RequestOptions options = new RequestOptions().setClientOptions(clientOptions);

        whenHttpClient.request(HttpMethod.GET, absoluteURI, options);
        verify(vertx).createHttpClient(eq(clientOptions));
        verify(request).end();

    }

    @Test
    public void testRequest_Data1() throws Exception {

        Buffer buffer = mock(Buffer.class);
        RequestOptions options = new RequestOptions().setData(buffer);

        whenHttpClient.request(HttpMethod.GET, absoluteURI, options);
        verify(request).end(eq(buffer));

    }

    @Test
    public void testRequest_Data2() throws Exception {

        String data = "hello";
        RequestOptions options = new RequestOptions().setData(data);

        whenHttpClient.request(HttpMethod.GET, absoluteURI, options);
        verify(request).end(bufferCaptor.capture());
        assertEquals("hello", bufferCaptor.getValue().toString());

    }

    @Test
    public void testRequest_Header() throws Exception {

        String name = "X-Test-1";
        String value = "1";
        RequestOptions options = new RequestOptions().addHeader(name, value);

        whenHttpClient.request(HttpMethod.GET, absoluteURI, options);
        verify(headers).addAll(headersCaptor.capture());
        assertEquals(value, headersCaptor.getValue().get(name));
        verify(request).end();

    }

    @Test
    public void testRequest_Headers() throws Exception {

        Map<String, String> headers2 = new HashMap<>();
        RequestOptions options = new RequestOptions().setHeaders(headers2);

        whenHttpClient.request(HttpMethod.GET, absoluteURI, options);
        verify(headers).addAll(eq(headers2));
        verify(request).end();

    }

    @Test
    public void testRequest_Chunked() throws Exception {

        RequestOptions options = new RequestOptions().setChunked(true);

        whenHttpClient.request(HttpMethod.GET, absoluteURI, options);
        verify(request).setChunked(eq(true));
        verify(request).end();

    }

    @Test
    public void testRequest_Timeout() throws Exception {

        RequestOptions options = new RequestOptions().setTimeout(100);

        whenHttpClient.request(HttpMethod.GET, absoluteURI, options);
        verify(request).setTimeout(eq(100L));
        verify(request).end();

    }

    @Test
    public void testRequest_WriteQueueMaxSize() throws Exception {

        RequestOptions options = new RequestOptions().setWriteQueueMaxSize(100);

        whenHttpClient.request(HttpMethod.GET, absoluteURI, options);
        verify(request).setWriteQueueMaxSize(eq(100));
        verify(request).end();

    }

    @Test
    public void testRequest_PauseResponse() throws Exception {

        RequestOptions options = new RequestOptions().setPauseResponse(true);

        whenHttpClient.request(HttpMethod.GET, absoluteURI, options);
        verify(client).request(any(), any(), responseHandlerCaptor.capture());
        verify(request).end();

        responseHandlerCaptor.getValue().handle(response);
        verify(response).pause();

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testRequest_Setup() throws Exception {

        Function<HttpClientRequest, Promise<Void>> setup = mock(Function.class);
        RequestOptions options = new RequestOptions().setSetupHandler(setup);

        whenHttpClient.request(HttpMethod.GET, absoluteURI, options);

        verify(setup).apply(eq(request));
        verify(request).end();

    }

    @Test
    public void testBody() throws Exception {

        when(response.bodyHandler(any())).thenReturn(response);
        Done<Buffer> done = new Done<>();

        whenHttpClient.body(response).then(done.onFulfilled, done.onRejected);
        verify(response).resume();
        verify(response).bodyHandler(bufferHandlerCaptor.capture());
        bufferHandlerCaptor.getValue().handle(Buffer.buffer());

        done.assertFulfilled();

    }

}
