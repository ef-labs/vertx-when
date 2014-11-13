package com.englishtown.vertx.promises.impl;

import com.englishtown.vertx.promises.HttpClientResponseAndBody;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientResponse;

/**
 * Default implementation of {@link DefaultHttpClientResponseAndBody}
 */
public class DefaultHttpClientResponseAndBody implements HttpClientResponseAndBody {

    private final HttpClientResponse response;
    private final Buffer body;

    public DefaultHttpClientResponseAndBody(HttpClientResponse response, Buffer body) {
        this.response = response;
        this.body = body;
    }

    @Override
    public HttpClientResponse getResponse() {
        return response;
    }

    @Override
    public Buffer getBody() {
        return body;
    }
}
