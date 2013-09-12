package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Deferred;
import com.englishtown.promises.Promise;
import com.englishtown.promises.Value;
import com.englishtown.promises.When;
import com.englishtown.vertx.promises.WhenHttpClient;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpClient;
import org.vertx.java.core.http.HttpClientRequest;
import org.vertx.java.core.http.HttpClientResponse;

import javax.inject.Inject;
import java.net.URI;

/**
 * Default implementation of {@link WhenHttpClient}
 */
public class DefaultWhenHttpClient implements WhenHttpClient {

    public static int CONNECT_TIMEOUT = 10000;

    private final Vertx vertx;
    private final When<HttpClientResponse, Void> when = new When<>();

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
    public Promise<HttpClientResponse, Void> request(String method, URI url) {
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
    public Promise<HttpClientResponse, Void> request(String method, URI url, Handler<HttpClientRequest> setupHandler) {

        final Deferred<HttpClientResponse, Void> d = when.defer();

        try {
            HttpClient client = createClient(url, d);
            return request(d, method, getPath(url), client, setupHandler);

        } catch (Throwable t) {
            reject(d, t);
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
    public Promise<HttpClientResponse, Void> request(String method, String url, HttpClient client) {
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
    public Promise<HttpClientResponse, Void> request(String method, String url, HttpClient client, Handler<HttpClientRequest> setupHandler) {
        final Deferred<HttpClientResponse, Void> d = when.defer();
        return request(d, method, url, client, setupHandler);
    }

    protected Promise<HttpClientResponse, Void> request(
            final Deferred<HttpClientResponse, Void> d,
            String method,
            String url,
            HttpClient client,
            Handler<HttpClientRequest> setupHandler) {

        try {
            HttpClientRequest request = client.request(method, url, new Handler<HttpClientResponse>() {
                @Override
                public void handle(HttpClientResponse response) {
                    if (response.statusCode() == 200) {
                        d.getResolver().resolve(response);
                    } else {
                        d.getResolver().reject(response);
                    }
                }
            });

            request.exceptionHandler(new Handler<Throwable>() {
                @Override
                public void handle(Throwable t) {
                    reject(d, t);
                }
            });

            if (setupHandler != null) {
                setupHandler.handle(request);
            } else {
                request.end();
            }

        } catch (Throwable t) {
            reject(d, t);
        }

        return d.getPromise();

    }

    protected HttpClient createClient(URI url, final Deferred<HttpClientResponse, Void> d) {

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
                        reject(d, t);
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

    protected void reject(Deferred<HttpClientResponse, Void> d, Throwable t) {
        RuntimeException e = (t instanceof RuntimeException ? (RuntimeException) t : new RuntimeException(t));
        d.getResolver().reject(new Value<HttpClientResponse>(null, e));
    }

}
