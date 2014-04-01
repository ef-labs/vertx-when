package com.englishtown.vertx.promises;

import com.englishtown.promises.Promise;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpClient;
import org.vertx.java.core.http.HttpClientRequest;
import org.vertx.java.core.http.HttpClientResponse;
import org.vertx.java.core.streams.WriteStream;

import java.net.URI;

/**
 * When.java wrapper for vert.x {@link HttpClient} methods
 */
public interface WhenHttpClient {

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method the http method (GET, PUT, POST etc.)
     * @param url    the {@link java.net.URI} to send the request to
     * @return a promise for the HttpClientResponse
     */
    Promise<HttpClientResponse> request(
            String method,
            URI url);

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param url          the absolute {@link java.net.URI} to send the request to
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link org.vertx.java.core.http.HttpClientRequest}
     * @return a promise for the HttpClientResponse
     */
    Promise<HttpClientResponse> request(
            String method,
            URI url,
            Handler<HttpClientRequest> setupHandler);

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param url          the absolute {@link java.net.URI} to send the request to
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link org.vertx.java.core.http.HttpClientRequest}
     * @param writeStream  optional write stream to write response data to
     * @return a promise for the HttpClientResponse
     */
    Promise<HttpClientResponse> request(
            String method,
            URI url,
            Handler<HttpClientRequest> setupHandler,
            WriteStream<?> writeStream);

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method the http method (GET, PUT, POST etc.)
     * @param url    the url to send the request to
     * @param client the vertx http client to use
     * @return a promise for the HttpClientResponse
     */
    Promise<HttpClientResponse> request(
            String method,
            String url,
            HttpClient client);

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param url          the url to send the request to
     * @param client       the vertx http client to use
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link org.vertx.java.core.http.HttpClientRequest}
     * @return a promise for the HttpClientResponse
     */
    Promise<HttpClientResponse> request(
            String method,
            String url,
            HttpClient client,
            Handler<HttpClientRequest> setupHandler);

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param url          the url to send the request to
     * @param client       the vertx http client to use
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link org.vertx.java.core.http.HttpClientRequest}
     * @param writeStream  optional write stream to write response data to
     * @return a promise for the HttpClientResponse
     */
    Promise<HttpClientResponse> request(
            String method,
            String url,
            HttpClient client,
            Handler<HttpClientRequest> setupHandler,
            WriteStream<?> writeStream);

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method the http method (GET, PUT, POST etc.)
     * @param url    the {@link java.net.URI} to send the request to
     * @return a promise for the HttpClientResponseAndBody
     */
    Promise<HttpClientResponseAndBody> requestResponseBody(
            String method,
            URI url);

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param url          the absolute {@link java.net.URI} to send the request to
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link org.vertx.java.core.http.HttpClientRequest}
     * @return a promise for the HttpClientResponseAndBody
     */
    Promise<HttpClientResponseAndBody> requestResponseBody(
            String method,
            URI url,
            Handler<HttpClientRequest> setupHandler);

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method the http method (GET, PUT, POST etc.)
     * @param url    the url to send the request to
     * @param client the vertx http client to use
     * @return a promise for the HttpClientResponseAndBody
     */
    Promise<HttpClientResponseAndBody> requestResponseBody(
            String method,
            String url,
            HttpClient client);

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param url          the url to send the request to
     * @param client       the vertx http client to use
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link org.vertx.java.core.http.HttpClientRequest}
     * @return a promise for the HttpClientResponseAndBody
     */
    Promise<HttpClientResponseAndBody> requestResponseBody(
            String method,
            String url,
            HttpClient client,
            Handler<HttpClientRequest> setupHandler);

}
