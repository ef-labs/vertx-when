package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Deferred;
import com.englishtown.promises.Promise;
import com.englishtown.promises.When;
import com.englishtown.vertx.promises.HttpClientResponseAndBody;
import com.englishtown.vertx.promises.WhenHttpClient;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpClient;
import org.vertx.java.core.http.HttpClientRequest;
import org.vertx.java.core.http.HttpClientResponse;
import org.vertx.java.core.streams.Pump;
import org.vertx.java.core.streams.WriteStream;

import javax.inject.Inject;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation of {@link WhenHttpClient}
 */
public class DefaultWhenHttpClient implements WhenHttpClient {

    public static int CONNECT_TIMEOUT = 10000;

    private final Vertx vertx;
    private final When<HttpClientResponse> when = new When<>();
    private final When<HttpClientResponseAndBody> whenBody = new When<>();

    @Inject
    public DefaultWhenHttpClient(Vertx vertx) {
        this.vertx = vertx;
    }

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method the http method (GET, PUT, POST etc.)
     * @param url    the {@link java.net.URI} to send the request to
     * @return a promise for the HttpClientResponse
     */
    @Override
    public Promise<HttpClientResponse> request(String method, URI url) {
        return request(method, url, null);
    }

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param url          the {@link java.net.URI} to send the request to
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link org.vertx.java.core.http.HttpClientRequest}
     * @return a promise for the HttpClientResponse
     */
    @Override
    public Promise<HttpClientResponse> request(String method, URI url, Handler<HttpClientRequest> setupHandler) {
        return request(method, url, setupHandler, null);
    }

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param url          the absolute {@link java.net.URI} to send the request to
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link org.vertx.java.core.http.HttpClientRequest}
     * @param writeStream  optional write stream to write response data to
     * @return a promise for the HttpClientResponse
     */
    @Override
    public Promise<HttpClientResponse> request(String method, URI url, Handler<HttpClientRequest> setupHandler, WriteStream<?> writeStream) {
        return request(method, url, setupHandler, writeStream, null);
    }

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method           the http method (GET, PUT, POST etc.)
     * @param url              the absolute {@link java.net.URI} to send the request to
     * @param setupHandler     optional setup handler.  If provided it must call end() on the {@link org.vertx.java.core.http.HttpClientRequest}
     * @param writeStream      optional write stream to write response data to
     * @param expectedStatuses optional set of expected statuses that will trigger the fulfilled callback
     * @return a promise for the HttpClientResponse
     */
    @Override
    public Promise<HttpClientResponse> request(String method, URI url, Handler<HttpClientRequest> setupHandler, WriteStream<?> writeStream, Set<Integer> expectedStatuses) {

        final Deferred<HttpClientResponse> d = when.defer();

        try {
            HttpClient client = createClient(url, d);
            return request(d, method, getPath(url), client, setupHandler, writeStream, expectedStatuses);

        } catch (Throwable t) {
            d.getResolver().reject(t);
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
    public Promise<HttpClientResponse> request(String method, String url, HttpClient client) {
        return request(method, url, client, null);
    }

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param url          the url to send the request to
     * @param client       the vertx http client to use
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link org.vertx.java.core.http.HttpClientRequest}
     * @return a promise for the HttpClientResponse
     */
    @Override
    public Promise<HttpClientResponse> request(String method, String url, HttpClient client, Handler<HttpClientRequest> setupHandler) {
        return request(method, url, client, setupHandler, null);
    }

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
    @Override
    public Promise<HttpClientResponse> request(String method, String url, HttpClient client, Handler<HttpClientRequest> setupHandler, WriteStream<?> writeStream) {
        return request(method, url, client, setupHandler, writeStream, null);
    }

    /**
     * Send a Vertx http client request and returns a promise
     *
     * @param method           the http method (GET, PUT, POST etc.)
     * @param url              the url to send the request to
     * @param client           the vertx http client to use
     * @param setupHandler     optional setup handler.  If provided it must call end() on the {@link org.vertx.java.core.http.HttpClientRequest}
     * @param writeStream      optional write stream to write response data to
     * @param expectedStatuses set of expected statuses that will trigger the fulfilled callback
     * @return a promise for the HttpClientResponse
     */
    @Override
    public Promise<HttpClientResponse> request(String method, String url, HttpClient client, Handler<HttpClientRequest> setupHandler, WriteStream<?> writeStream, Set<Integer> expectedStatuses) {
        final Deferred<HttpClientResponse> d = when.defer();
        return request(d, method, url, client, setupHandler, writeStream, expectedStatuses);
    }

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method the http method (GET, PUT, POST etc.)
     * @param url    the {@link java.net.URI} to send the request to
     * @return a promise for the HttpClientResponseAndBody
     */
    @Override
    public Promise<HttpClientResponseAndBody> requestResponseBody(String method, URI url) {
        return requestResponseBody(method, url, null);
    }

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param url          the absolute {@link java.net.URI} to send the request to
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link org.vertx.java.core.http.HttpClientRequest}
     * @return a promise for the HttpClientResponseAndBody
     */
    @Override
    public Promise<HttpClientResponseAndBody> requestResponseBody(String method, URI url, Handler<HttpClientRequest> setupHandler) {
        return requestResponseBody(method, url, setupHandler, null);
    }

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method           the http method (GET, PUT, POST etc.)
     * @param url              the absolute {@link java.net.URI} to send the request to
     * @param setupHandler     optional setup handler.  If provided it must call end() on the {@link org.vertx.java.core.http.HttpClientRequest}
     * @param expectedStatuses optional set of expected statuses that will trigger the fulfilled callback
     * @return a promise for the HttpClientResponseAndBody
     */
    @Override
    public Promise<HttpClientResponseAndBody> requestResponseBody(String method, URI url, Handler<HttpClientRequest> setupHandler, Set<Integer> expectedStatuses) {
        final Deferred<HttpClientResponseAndBody> d = whenBody.defer();

        try {
            HttpClient client = createClient(url, d);
            return requestResponseBody(d, method, getPath(url), client, setupHandler, expectedStatuses);

        } catch (Throwable t) {
            d.getResolver().reject(t);
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
    public Promise<HttpClientResponseAndBody> requestResponseBody(String method, String url, HttpClient client) {
        return requestResponseBody(method, url, client, null);
    }

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method       the http method (GET, PUT, POST etc.)
     * @param url          the url to send the request to
     * @param client       the vertx http client to use
     * @param setupHandler optional setup handler.  If provided it must call end() on the {@link org.vertx.java.core.http.HttpClientRequest}
     * @return a promise for the HttpClientResponseAndBody
     */
    @Override
    public Promise<HttpClientResponseAndBody> requestResponseBody(String method, String url, HttpClient client, Handler<HttpClientRequest> setupHandler) {
        return requestResponseBody(method, url, client, setupHandler, null);
    }

    /**
     * Send a Vertx http client request and returns a promise for the response and full body
     *
     * @param method           the http method (GET, PUT, POST etc.)
     * @param url              the url to send the request to
     * @param client           the vertx http client to use
     * @param setupHandler     optional setup handler.  If provided it must call end() on the {@link org.vertx.java.core.http.HttpClientRequest}
     * @param expectedStatuses set of expected statuses that will trigger the fulfilled callback
     * @return a promise for the HttpClientResponseAndBody
     */
    @Override
    public Promise<HttpClientResponseAndBody> requestResponseBody(String method, String url, HttpClient client, Handler<HttpClientRequest> setupHandler, Set<Integer> expectedStatuses) {
        final Deferred<HttpClientResponseAndBody> d = whenBody.defer();
        return requestResponseBody(d, method, url, client, setupHandler, expectedStatuses);
    }

    protected Promise<HttpClientResponse> request(
            final Deferred<HttpClientResponse> d,
            String method,
            String url,
            HttpClient client,
            Handler<HttpClientRequest> setupHandler,
            final WriteStream<?> writeStream,
            Set<Integer> expectedStatuses) {

        if (expectedStatuses == null || expectedStatuses.isEmpty()) {
            expectedStatuses = new HashSet<>();
            expectedStatuses.add(HttpResponseStatus.OK.code());
        }

        final Set<Integer> finalExpectedStatuses = expectedStatuses;

        try {
            HttpClientRequest request = client.request(method, url, new Handler<HttpClientResponse>() {
                @Override
                public void handle(final HttpClientResponse response) {
                    // Short circuit, no need to wait for response end
                    if (writeStream == null) {
                        if (finalExpectedStatuses.contains(response.statusCode())) {
                            d.getResolver().resolve(response);
                        } else {
                            d.getResolver().reject(response);
                        }
                        return;
                    }

                    Pump.createPump(response, writeStream).start();

                    response.endHandler(new Handler<Void>() {
                        @Override
                        public void handle(Void event) {
                            if (finalExpectedStatuses.contains(response.statusCode())) {
                                d.getResolver().resolve(response);
                            } else {
                                d.getResolver().reject(response);
                            }
                        }
                    });
                }
            });

            request.exceptionHandler(new Handler<Throwable>() {
                @Override
                public void handle(Throwable t) {
                    d.getResolver().reject(t);
                }
            });

            if (setupHandler != null) {
                setupHandler.handle(request);
            } else {
                request.end();
            }

        } catch (Throwable t) {
            d.getResolver().reject(t);
        }

        return d.getPromise();

    }

    protected Promise<HttpClientResponseAndBody> requestResponseBody(
            final Deferred<HttpClientResponseAndBody> d,
            String method,
            String url,
            HttpClient client,
            Handler<HttpClientRequest> setupHandler,
            Set<Integer> expectedStatuses) {

        if (expectedStatuses == null || expectedStatuses.isEmpty()) {
            expectedStatuses = new HashSet<>();
            expectedStatuses.add(HttpResponseStatus.OK.code());
        }

        final Set<Integer> finalExpectedStatuses = expectedStatuses;

        try {
            HttpClientRequest request = client.request(method, url, new Handler<HttpClientResponse>() {
                @Override
                public void handle(final HttpClientResponse response) {
                    response.bodyHandler(new Handler<Buffer>() {
                        @Override
                        public void handle(Buffer body) {
                            HttpClientResponseAndBody responseAndBody = new DefaultHttpClientResponseAndBody(response, body);
                            if (finalExpectedStatuses.contains(response.statusCode())) {
                                d.getResolver().resolve(responseAndBody);
                            } else {
                                d.getResolver().reject(responseAndBody);
                            }
                        }
                    });
                }
            });

            request.exceptionHandler(new Handler<Throwable>() {
                @Override
                public void handle(Throwable t) {
                    d.getResolver().reject(t);
                }
            });

            if (setupHandler != null) {
                setupHandler.handle(request);
            } else {
                request.end();
            }

        } catch (Throwable t) {
            d.getResolver().reject(t);
        }

        return d.getPromise();

    }

    protected <T> HttpClient createClient(URI url, final Deferred<T> d) {

        if (url == null) throw new IllegalArgumentException("url is null");
        if (!url.isAbsolute()) throw new IllegalArgumentException("url must be absolute");

        int port = (url.getPort() > 0) ? url.getPort() : 80;

        return vertx.createHttpClient()
                .setHost(url.getHost())
                .setPort(port)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .exceptionHandler(new Handler<Throwable>() {
                    @Override
                    public void handle(Throwable t) {
                        d.getResolver().reject(t);
                    }
                });
    }

    protected String getPath(URI url) {
        final StringBuilder path = new StringBuilder();
        path.append(url.getPath());
        if (url.getQuery() != null && url.getQuery().length() > 0) {
            path.append("?");
            path.append(url.getQuery());
        }

        return path.toString();
    }

}
