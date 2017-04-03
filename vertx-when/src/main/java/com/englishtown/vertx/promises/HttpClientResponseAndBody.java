package com.englishtown.vertx.promises;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientResponse;

/**
 * Facade for {@link HttpClientResponse} and a body {@link Buffer}
 */
public interface HttpClientResponseAndBody {

    HttpClientResponse getResponse();

    Buffer getBody();

}
