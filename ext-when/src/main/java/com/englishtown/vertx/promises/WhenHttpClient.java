package com.englishtown.vertx.promises;

import com.englishtown.promises.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;

/**
 * When.java wrapper for vert.x {@link HttpClient} methods
 */
public interface WhenHttpClient {

    Promise<HttpClientResponse> request(HttpMethod method, String absoluteURI);

    Promise<HttpClientResponse> request(HttpMethod method, String absoluteURI, RequestOptions options);

    Promise<HttpClientResponse> request(HttpMethod method, int port, String host, String requestURI);

    Promise<HttpClientResponse> request(HttpMethod method, int port, String host, String requestURI, RequestOptions options);

    Promise<Buffer> body(HttpClientResponse response);

}
