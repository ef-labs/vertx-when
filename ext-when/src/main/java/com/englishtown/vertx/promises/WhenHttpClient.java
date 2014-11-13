package com.englishtown.vertx.promises;

import com.englishtown.promises.Promise;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.*;
import io.vertx.core.streams.WriteStream;

import java.util.Set;

/**
 * When.java wrapper for vert.x {@link HttpClient} methods
 */
public interface WhenHttpClient {

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method      the http method (GET, PUT, POST etc.)
     * @param absoluteUri the {@link java.net.URI} to send the request to
     * @return a promise for the HttpClientResponse
     */
    Promise<HttpClientResponse> request(
            HttpMethod method,
            String absoluteUri,
            HttpClientOptions options);

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param absoluteUri  the absolute {@link java.net.URI} to send the request to
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link io.vertx.core.http.HttpClientRequest}
     * @return a promise for the HttpClientResponse
     */
    Promise<HttpClientResponse> request(
            HttpMethod method,
            String absoluteUri,
            HttpClientOptions options,
            Handler<HttpClientRequest> setupHandler);

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param absoluteUri  the absolute {@link java.net.URI} to send the request to
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link io.vertx.core.http.HttpClientRequest}
     * @param writeStream  optional write stream to write response data to
     * @return a promise for the HttpClientResponse
     */
    Promise<HttpClientResponse> request(
            HttpMethod method,
            String absoluteUri,
            HttpClientOptions options,
            Handler<HttpClientRequest> setupHandler,
            WriteStream<Buffer> writeStream);

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method           the http method (GET, PUT, POST etc.)
     * @param absoluteUri      the absolute {@link java.net.URI} to send the request to
     * @param setupHandler     optional setup handler.  If provided it must call end() on the {@link io.vertx.core.http.HttpClientRequest}
     * @param writeStream      optional write stream to write response data to
     * @param expectedStatuses optional set of expected statuses that will trigger the fulfilled callback
     * @return a promise for the HttpClientResponse
     */
    Promise<HttpClientResponse> request(
            HttpMethod method,
            String absoluteUri,
            HttpClientOptions options,
            Handler<HttpClientRequest> setupHandler,
            WriteStream<Buffer> writeStream,
            Set<Integer> expectedStatuses);

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method      the http method (GET, PUT, POST etc.)
     * @param absoluteUri the url to send the request to
     * @param client      the vertx http client to use
     * @return a promise for the HttpClientResponse
     */
    Promise<HttpClientResponse> request(
            HttpMethod method,
            String absoluteUri,
            HttpClient client);

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param absoluteUri  the url to send the request to
     * @param client       the vertx http client to use
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link io.vertx.core.http.HttpClientRequest}
     * @return a promise for the HttpClientResponse
     */
    Promise<HttpClientResponse> request(
            HttpMethod method,
            String absoluteUri,
            HttpClient client,
            Handler<HttpClientRequest> setupHandler);

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param absoluteUri  the url to send the request to
     * @param client       the vertx http client to use
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link io.vertx.core.http.HttpClientRequest}
     * @param writeStream  optional write stream to write response data to
     * @return a promise for the HttpClientResponse
     */
    Promise<HttpClientResponse> request(
            HttpMethod method,
            String absoluteUri,
            HttpClient client,
            Handler<HttpClientRequest> setupHandler,
            WriteStream<Buffer> writeStream);

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method           the http method (GET, PUT, POST etc.)
     * @param absoluteUri      the url to send the request to
     * @param client           the vertx http client to use
     * @param setupHandler     optional setup handler.  If provided it must call end() on the {@link io.vertx.core.http.HttpClientRequest}
     * @param writeStream      optional write stream to write response data to
     * @param expectedStatuses optional set of expected statuses that will trigger the fulfilled callback
     * @return a promise for the HttpClientResponse
     */
    Promise<HttpClientResponse> request(
            HttpMethod method,
            String absoluteUri,
            HttpClient client,
            Handler<HttpClientRequest> setupHandler,
            WriteStream<Buffer> writeStream,
            Set<Integer> expectedStatuses);

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method      the http method (GET, PUT, POST etc.)
     * @param absoluteUri the {@link java.net.URI} to send the request to
     * @return a promise for the HttpClientResponseAndBody
     */
    Promise<HttpClientResponseAndBody> requestResponseBody(
            HttpMethod method,
            String absoluteUri,
            HttpClientOptions options);

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param absoluteUri  the absolute {@link java.net.URI} to send the request to
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link io.vertx.core.http.HttpClientRequest}
     * @return a promise for the HttpClientResponseAndBody
     */
    Promise<HttpClientResponseAndBody> requestResponseBody(
            HttpMethod method,
            String absoluteUri,
            HttpClientOptions options,
            Handler<HttpClientRequest> setupHandler);

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method           the http method (GET, PUT, POST etc.)
     * @param absoluteUri      the absolute {@link java.net.URI} to send the request to
     * @param setupHandler     optional setup handler.  If provided it must call end() on the {@link io.vertx.core.http.HttpClientRequest}
     * @param expectedStatuses optional set of expected statuses that will trigger the fulfilled callback
     * @return a promise for the HttpClientResponseAndBody
     */
    Promise<HttpClientResponseAndBody> requestResponseBody(
            HttpMethod method,
            String absoluteUri,
            HttpClientOptions options,
            Handler<HttpClientRequest> setupHandler,
            Set<Integer> expectedStatuses);

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method      the http method (GET, PUT, POST etc.)
     * @param absoluteUri the url to send the request to
     * @param client      the vertx http client to use
     * @return a promise for the HttpClientResponseAndBody
     */
    Promise<HttpClientResponseAndBody> requestResponseBody(
            HttpMethod method,
            String absoluteUri,
            HttpClient client);

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param absoluteUri  the url to send the request to
     * @param client       the vertx http client to use
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link io.vertx.core.http.HttpClientRequest}
     * @return a promise for the HttpClientResponseAndBody
     */
    Promise<HttpClientResponseAndBody> requestResponseBody(
            HttpMethod method,
            String absoluteUri,
            HttpClient client,
            Handler<HttpClientRequest> setupHandler);

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method           the http method (GET, PUT, POST etc.)
     * @param absoluteUri      the url to send the request to
     * @param client           the vertx http client to use
     * @param setupHandler     optional setup handler.  If provided it must call end() on the {@link io.vertx.core.http.HttpClientRequest}
     * @param expectedStatuses optional set of expected statuses that will trigger the fulfilled callback
     * @return a promise for the HttpClientResponseAndBody
     */
    Promise<HttpClientResponseAndBody> requestResponseBody(
            HttpMethod method,
            String absoluteUri,
            HttpClient client,
            Handler<HttpClientRequest> setupHandler,
            Set<Integer> expectedStatuses);

}
