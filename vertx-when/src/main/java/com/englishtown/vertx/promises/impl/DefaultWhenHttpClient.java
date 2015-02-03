package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Deferred;
import com.englishtown.promises.Promise;
import com.englishtown.promises.Resolver;
import com.englishtown.promises.When;
import com.englishtown.vertx.promises.RequestOptions;
import com.englishtown.vertx.promises.WhenHttpClient;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.*;

import javax.inject.Inject;

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

    @Override
    public Promise<HttpClientResponse> request(HttpMethod method, String absoluteURI) {
        return request(method, absoluteURI, null);
    }

    @Override
    public Promise<HttpClientResponse> request(HttpMethod method, String absoluteURI, RequestOptions options) {

        if (options == null) {
            options = new RequestOptions();
        }

        Deferred<HttpClientResponse> d = when.defer();

        HttpClientRequest request = getClient(options)
                .request(method, absoluteURI, getResponseHandler(options, d))
                .exceptionHandler(d::reject);

        return doRequest(request, options, d);
    }

    @Override
    public Promise<HttpClientResponse> request(HttpMethod method, int port, String host, String requestURI) {
        return request(method, port, host, requestURI, null);
    }

    @Override
    public Promise<HttpClientResponse> request(HttpMethod method, int port, String host, String requestURI, RequestOptions options) {

        if (options == null) {
            options = new RequestOptions();
        }

        Deferred<HttpClientResponse> d = when.defer();

        HttpClientRequest request = getClient(options)
                .request(method, port, host, requestURI, getResponseHandler(options, d))
                .exceptionHandler(d::reject);

        return doRequest(request, options, d);
    }

    private HttpClient getClient(RequestOptions options) {
        if (options.getClient() != null) {
            return options.getClient();
        }
        if (options.getClientOptions() != null) {
            return vertx.createHttpClient(options.getClientOptions());
        }
        return vertx.createHttpClient(new HttpClientOptions());
    }

    private Handler<HttpClientResponse> getResponseHandler(RequestOptions options, Resolver<HttpClientResponse> resolver) {
        return response -> {
            if (options.getPauseResponse()) {
                response.pause();
            }
            resolver.resolve(response);
        };
    }

    private Promise<HttpClientResponse> doRequest(HttpClientRequest request, RequestOptions options, Deferred<HttpClientResponse> d) {

        if (options.getChunked()) {
            request.setChunked(true);
        }
        if (options.getHeaders() != null) {
            request.headers().addAll(options.getHeaders());
        }
        if (options.getTimeout() > 0) {
            request.setTimeout(options.getTimeout());
        }
        if (options.getWriteQueueMaxSize() > 0) {
            request.setWriteQueueMaxSize(options.getWriteQueueMaxSize());
        }

        if (options.getSetupHandler() != null) {
            return when.resolve(options.getSetupHandler().apply(request))
                    .then(aVoid -> {
                        end(request, options);
                        return d.getPromise();
                    });
        } else {
            end(request, options);
            return d.getPromise();
        }

    }

    private void end(HttpClientRequest request, RequestOptions options) {
        if (options.getData() != null) {
            request.end(options.getData());
        } else {
            request.end();
        }
    }

    @Override
    public Promise<Buffer> body(HttpClientResponse response) {
        Deferred<Buffer> d = when.defer();

        // Set the body handler and resume the response
        response.bodyHandler(d::resolve).resume();

        return d.getPromise();
    }

}
