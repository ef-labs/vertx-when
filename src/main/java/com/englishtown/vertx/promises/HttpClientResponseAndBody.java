package com.englishtown.vertx.promises;

import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpClientResponse;

/**
 * Container for an {@link HttpClientResponse} and the response body
 */
public interface HttpClientResponseAndBody {

    HttpClientResponse getResponse();

    Buffer getBody();

}
