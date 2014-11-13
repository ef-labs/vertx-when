package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Deferred;
import com.englishtown.promises.Promise;
import com.englishtown.promises.When;
import com.englishtown.promises.exceptions.RejectException;
import com.englishtown.vertx.promises.HttpClientResponseAndBody;
import com.englishtown.vertx.promises.WhenHttpClient;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.*;
import io.vertx.core.streams.Pump;
import io.vertx.core.streams.WriteStream;

import javax.inject.Inject;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation of {@link WhenHttpClient}
 */
public class DefaultWhenHttpClient implements WhenHttpClient {

    private final Vertx vertx;
    private final When when;

    @Inject
    public DefaultWhenHttpClient(Vertx vertx, When when) {
        this.vertx = vertx;
        this.when = when;
    }

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method the http method (GET, PUT, POST etc.)
     * @param absoluteUri    the {@link java.net.URI} to send the request to
     * @return a promise for the HttpClientResponse
     */
    @Override
    public Promise<HttpClientResponse> request(HttpMethod method, String absoluteUri, HttpClientOptions options) {
        return request(method, absoluteUri, options, null);
    }

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param absoluteUri          the {@link java.net.URI} to send the request to
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link io.vertx.core.http.HttpClientRequest}
     * @return a promise for the HttpClientResponse
     */
    @Override
    public Promise<HttpClientResponse> request(HttpMethod method, String absoluteUri, HttpClientOptions options, Handler<HttpClientRequest> setupHandler) {
        return request(method, absoluteUri, options, setupHandler, null);
    }

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param absoluteUri          the absolute {@link java.net.URI} to send the request to
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link io.vertx.core.http.HttpClientRequest}
     * @param writeStream  optional write stream to write response data to
     * @return a promise for the HttpClientResponse
     */
    @Override
    public Promise<HttpClientResponse> request(HttpMethod method, String absoluteUri, HttpClientOptions options, Handler<HttpClientRequest> setupHandler, WriteStream<Buffer> writeStream) {
        return request(method, absoluteUri, options, setupHandler, writeStream, null);
    }

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
    @Override
    public Promise<HttpClientResponse> request(HttpMethod method, String absoluteUri, HttpClientOptions options, Handler<HttpClientRequest> setupHandler, WriteStream<Buffer> writeStream, Set<Integer> expectedStatuses) {

        final Deferred<HttpClientResponse> d = when.defer();

        try {
            HttpClient client = createClient(options, d);
            return request(d, method, absoluteUri, client, setupHandler, writeStream, expectedStatuses);

        } catch (Throwable t) {
            d.reject(t);
            return d.getPromise();
        }

    }

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method the http method (GET, PUT, POST etc.)
     * @param url    the url to send the request to
     * @param client the vertx http client to use
     * @return a promise for the HttpClientResponse
     */
    @Override
    public Promise<HttpClientResponse> request(HttpMethod method, String url, HttpClient client) {
        return request(method, url, client, null);
    }

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param url          the url to send the request to
     * @param client       the vertx http client to use
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link io.vertx.core.http.HttpClientRequest}
     * @return a promise for the HttpClientResponse
     */
    @Override
    public Promise<HttpClientResponse> request(HttpMethod method, String url, HttpClient client, Handler<HttpClientRequest> setupHandler) {
        return request(method, url, client, setupHandler, null);
    }

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param url          the url to send the request to
     * @param client       the vertx http client to use
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link io.vertx.core.http.HttpClientRequest}
     * @param writeStream  optional write stream to write response data to
     * @return a promise for the HttpClientResponse
     */
    @Override
    public Promise<HttpClientResponse> request(HttpMethod method, String url, HttpClient client, Handler<HttpClientRequest> setupHandler, WriteStream<Buffer> writeStream) {
        return request(method, url, client, setupHandler, writeStream, null);
    }

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method           the http method (GET, PUT, POST etc.)
     * @param absoluteUri              the url to send the request to
     * @param client           the vertx http client to use
     * @param setupHandler     optional setup handler.  If provided it must call end() on the {@link io.vertx.core.http.HttpClientRequest}
     * @param writeStream      optional write stream to write response data to
     * @param expectedStatuses set of expected statuses that will trigger the fulfilled callback
     * @return a promise for the HttpClientResponse
     */
    @Override
    public Promise<HttpClientResponse> request(HttpMethod method, String absoluteUri, HttpClient client, Handler<HttpClientRequest> setupHandler, WriteStream<Buffer> writeStream, Set<Integer> expectedStatuses) {
        final Deferred<HttpClientResponse> d = when.defer();
        return request(d, method, absoluteUri, client, setupHandler, writeStream, expectedStatuses);
    }

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method the http method (GET, PUT, POST etc.)
     * @param absoluteUri    the {@link java.net.URI} to send the request to
     * @return a promise for the HttpClientResponseAndBody
     */
    @Override
    public Promise<HttpClientResponseAndBody> requestResponseBody(HttpMethod method, String absoluteUri, HttpClientOptions options) {
        return requestResponseBody(method, absoluteUri, options, null);
    }

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param absoluteUri          the absolute {@link java.net.URI} to send the request to
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link io.vertx.core.http.HttpClientRequest}
     * @return a promise for the HttpClientResponseAndBody
     */
    @Override
    public Promise<HttpClientResponseAndBody> requestResponseBody(HttpMethod method, String absoluteUri, HttpClientOptions options, Handler<HttpClientRequest> setupHandler) {
        return requestResponseBody(method, absoluteUri, options, setupHandler, null);
    }

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method           the http method (GET, PUT, POST etc.)
     * @param absoluteUri      the absolute {@link java.net.URI} to send the request to
     * @param setupHandler     optional setup handler.  If provided it must call end() on the {@link io.vertx.core.http.HttpClientRequest}
     * @param expectedStatuses optional set of expected statuses that will trigger the fulfilled callback
     * @return a promise for the HttpClientResponseAndBody
     */
    @Override
    public Promise<HttpClientResponseAndBody> requestResponseBody(HttpMethod method, String absoluteUri, HttpClientOptions options, Handler<HttpClientRequest> setupHandler, Set<Integer> expectedStatuses) {
        final Deferred<HttpClientResponseAndBody> d = when.defer();

        try {
            HttpClient client = createClient(options, d);
            return requestResponseBody(d, method, absoluteUri, client, setupHandler, expectedStatuses);

        } catch (Throwable t) {
            d.reject(t);
            return d.getPromise();
        }
    }

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method the http method (GET, PUT, POST etc.)
     * @param url    the url to send the request to
     * @param client the vertx http client to use
     * @return a promise for the HttpClientResponseAndBody
     */
    @Override
    public Promise<HttpClientResponseAndBody> requestResponseBody(HttpMethod method, String url, HttpClient client) {
        return requestResponseBody(method, url, client, null);
    }

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param url          the url to send the request to
     * @param client       the vertx http client to use
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link io.vertx.core.http.HttpClientRequest}
     * @return a promise for the HttpClientResponseAndBody
     */
    @Override
    public Promise<HttpClientResponseAndBody> requestResponseBody(HttpMethod method, String url, HttpClient client, Handler<HttpClientRequest> setupHandler) {
        return requestResponseBody(method, url, client, setupHandler, null);
    }

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method           the http method (GET, PUT, POST etc.)
     * @param url              the url to send the request to
     * @param client           the vertx http client to use
     * @param setupHandler     optional setup handler.  If provided it must call end() on the {@link io.vertx.core.http.HttpClientRequest}
     * @param expectedStatuses set of expected statuses that will trigger the fulfilled callback
     * @return a promise for the HttpClientResponseAndBody
     */
    @Override
    public Promise<HttpClientResponseAndBody> requestResponseBody(HttpMethod method, String url, HttpClient client, Handler<HttpClientRequest> setupHandler, Set<Integer> expectedStatuses) {
        final Deferred<HttpClientResponseAndBody> d = when.defer();
        return requestResponseBody(d, method, url, client, setupHandler, expectedStatuses);
    }

    protected Promise<HttpClientResponse> request(
            final Deferred<HttpClientResponse> d,
            HttpMethod method,
            String absoluteUri,
            HttpClient client,
            Handler<HttpClientRequest> setupHandler,
            final WriteStream<Buffer> writeStream,
            Set<Integer> expectedStatuses) {

        if (expectedStatuses == null || expectedStatuses.isEmpty()) {
            expectedStatuses = new HashSet<>();
            expectedStatuses.add(HttpResponseStatus.OK.code());
        }

        final Set<Integer> finalExpectedStatuses = expectedStatuses;

        try {
            HttpClientRequest request = client.request(method, absoluteUri, response -> {
                // Short circuit, no need to wait for response end
                if (writeStream == null) {
                    if (finalExpectedStatuses.contains(response.statusCode())) {
                        d.resolve(response);
                    } else {
                        d.reject(new RejectException().setValue(response));
                    }
                    return;
                }

                Pump.pump(response, writeStream).start();

                response.endHandler(event -> {
                    if (finalExpectedStatuses.contains(response.statusCode())) {
                        d.resolve(response);
                    } else {
                        d.reject(new RejectException().setValue(response));
                    }
                });
            });

            request.exceptionHandler(t -> d.reject(t));

            if (setupHandler != null) {
                setupHandler.handle(request);
            } else {
                request.end();
            }

        } catch (Throwable t) {
            d.reject(t);
        }

        return d.getPromise();

    }

    protected Promise<HttpClientResponseAndBody> requestResponseBody(
            final Deferred<HttpClientResponseAndBody> d,
            HttpMethod method,
            String absoluteUri,
            HttpClient client,
            Handler<HttpClientRequest> setupHandler,
            Set<Integer> expectedStatuses) {

        if (expectedStatuses == null || expectedStatuses.isEmpty()) {
            expectedStatuses = new HashSet<>();
            expectedStatuses.add(HttpResponseStatus.OK.code());
        }

        final Set<Integer> finalExpectedStatuses = expectedStatuses;

        try {
            HttpClientRequest request = client.request(method, absoluteUri,
                    response -> response.bodyHandler(
                            body -> {
                                HttpClientResponseAndBody responseAndBody = new DefaultHttpClientResponseAndBody(response, body);
                                if (finalExpectedStatuses.contains(response.statusCode())) {
                                    d.resolve(responseAndBody);
                                } else {
                                    d.reject(new RejectException().setValue(responseAndBody));
                                }
                            }
                    )
            );

            request.exceptionHandler(t -> d.reject(t));

            if (setupHandler != null) {
                setupHandler.handle(request);
            } else {
                request.end();
            }

        } catch (Throwable t) {
            d.reject(t);
        }

        return d.getPromise();

    }

    protected <T> HttpClient createClient(HttpClientOptions options, final Deferred<T> d) {

        return vertx.createHttpClient(options)
                .exceptionHandler(t -> d.reject(t));

    }

}
