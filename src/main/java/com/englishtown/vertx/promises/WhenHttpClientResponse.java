package com.englishtown.vertx.promises;

import com.englishtown.promises.Promise;
import org.vertx.java.core.http.HttpClientResponse;

/**
 *
 */
public interface WhenHttpClientResponse {

    Promise<HttpClientResponseAndBody> body(Promise<HttpClientResponse> promise);

}
