package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Done2;
import com.englishtown.promises.Promise;
import com.englishtown.promises.Value;
import com.englishtown.vertx.promises.HttpClientResponseAndBody;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpClientResponse;

import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link DefaultWhenHttpClientResponse}
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultWhenHttpClientResponseTest {

    DefaultWhenHttpClientResponse whenHttpClientResponse = new DefaultWhenHttpClientResponse();
    Done2<HttpClientResponseAndBody> done = new Done2<>();

    @Mock
    Promise<HttpClientResponse, Void> promise;
    @Mock
    HttpClientResponse response;
    @Captor
    ArgumentCaptor<com.englishtown.promises.Runnable<Promise<HttpClientResponse, Void>, HttpClientResponse>> onSuccessCaptor;
    @Captor
    ArgumentCaptor<com.englishtown.promises.Runnable<Promise<HttpClientResponse, Void>, Value<HttpClientResponse>>> onFailCaptor;
    @Captor
    ArgumentCaptor<Handler<Buffer>> bodyHandlerCaptor;

    @Before
    public void setUp() {

    }

    @Test
    public void testBody_Success() throws Exception {

        whenHttpClientResponse.body(promise).then(done.onSuccess, done.onFail);
        verify(promise).then(onSuccessCaptor.capture(), onFailCaptor.capture());

        onSuccessCaptor.getValue().run(response);
        verify(response).bodyHandler(bodyHandlerCaptor.capture());
        bodyHandlerCaptor.getValue().handle(new Buffer());

        done.assertSuccess();
    }

    @Test
    public void testBody_Failed() throws Exception {

        whenHttpClientResponse.body(promise).then(done.onSuccess, done.onFail);
        verify(promise).then(onSuccessCaptor.capture(), onFailCaptor.capture());

        onFailCaptor.getValue().run(new Value<>(response));

        done.assertFailed();
    }

}
