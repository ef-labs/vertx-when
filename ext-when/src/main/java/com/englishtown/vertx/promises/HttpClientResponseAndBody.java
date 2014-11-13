package com.englishtown.vertx.promises;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientResponse;

/**
 * Container for an {@link HttpClientResponse} and the response body
 */
public interface HttpClientResponseAndBody {

    HttpClientResponse getResponse();

    Buffer getBody();

}
