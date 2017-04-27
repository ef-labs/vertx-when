package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Deferred;
import com.englishtown.promises.Promise;
import com.englishtown.promises.When;
import com.englishtown.vertx.promises.HttpClientResponseAndBody;
import com.englishtown.vertx.promises.RequestOptions;
import com.englishtown.vertx.promises.WhenHttpClient;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Function;

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
    public Promise<HttpClientResponse> request(HttpMethod method, int port, String host, String requestURI) {
        return request(method, port, host, requestURI, null);
    }

    @Override
    public Promise<HttpClientResponse> request(HttpMethod method, int port, String host, String requestURI, RequestOptions options) {
        RequestFunction func = client -> client.request(method, port, host, requestURI);
        return doRequest(options, func);
    }

    @Override
    public Promise<HttpClientResponse> request(HttpMethod method, String host, String requestURI) {
        return request(method, host, requestURI, null);
    }

    @Override
    public Promise<HttpClientResponse> request(HttpMethod method, String host, String requestURI, RequestOptions options) {
        RequestFunction func = client -> client.request(method, host, requestURI);
        return doRequest(options, func);
    }

    @Override
    public Promise<HttpClientResponse> request(HttpMethod method, String requestURI) {
        return request(method, requestURI, (RequestOptions) null);
    }

    @Override
    public Promise<HttpClientResponse> request(HttpMethod method, String requestURI, RequestOptions options) {
        RequestFunction func = client -> client.request(method, requestURI);
        return doRequest(options, func);
    }

    @Override
    public Promise<HttpClientResponseAndBody> requestAndReadBody(HttpMethod method, int port, String host, String requestURI) {
        return requestAndReadBody(method, port, host, requestURI, null);
    }

    @Override
    public Promise<HttpClientResponseAndBody> requestAndReadBody(HttpMethod method, int port, String host, String requestURI, RequestOptions options) {
        RequestFunction func = client -> client.request(method, port, host, requestURI);
        return doRequestAndReadBody(options, func);
    }

    @Override
    public Promise<HttpClientResponseAndBody> requestAndReadBody(HttpMethod method, String host, String requestURI) {
        return requestAndReadBody(method, host, requestURI, null);
    }

    @Override
    public Promise<HttpClientResponseAndBody> requestAndReadBody(HttpMethod method, String host, String requestURI, RequestOptions options) {
        RequestFunction func = client -> client.request(method, host, requestURI);
        return doRequestAndReadBody(options, func);
    }

    @Override
    public Promise<HttpClientResponseAndBody> requestAndReadBody(HttpMethod method, String requestURI) {
        return requestAndReadBody(method, requestURI, (RequestOptions) null);
    }

    @Override
    public Promise<HttpClientResponseAndBody> requestAndReadBody(HttpMethod method, String requestURI, RequestOptions options) {
        RequestFunction func = client -> client.request(method, requestURI);
        return doRequestAndReadBody(options, func);
    }

    @Override
    public Promise<HttpClientResponse> requestAbs(HttpMethod method, String absoluteURI) {
        return requestAbs(method, absoluteURI, null);
    }

    @Override
    public Promise<HttpClientResponse> requestAbs(HttpMethod method, String absoluteURI, RequestOptions options) {
        RequestFunction func = client -> client.requestAbs(method, absoluteURI);
        return doRequest(options, func);
    }

    @Override
    public Promise<HttpClientResponseAndBody> requestAbsAndReadBody(HttpMethod method, String absoluteURI) {
        return requestAbsAndReadBody(method, absoluteURI, null);
    }

    @Override
    public Promise<HttpClientResponseAndBody> requestAbsAndReadBody(HttpMethod method, String absoluteURI, RequestOptions options) {
        RequestFunction func = client -> client.requestAbs(method, absoluteURI);
        return doRequestAndReadBody(options, func);
    }

    private Promise<HttpClientResponse> doRequest(RequestOptions options, RequestFunction requestFunction) {

        if (options == null) {
            options = new RequestOptions();
        }

        Deferred<HttpClientResponse> d = when.defer();
        RequestOptions finalOptions = options;

        Handler<HttpClientRequestContext> handler = context -> {
            HttpClientResponse response = context.getResponse();

            if (context.shouldCloseClient()) {
                HttpClient client = context.getClient();

                response.endHandler(aVoid -> {
                    client.close();
                    context.setCloseClient(false);
                });
            }

            if (finalOptions.getPauseResponse()) {
                response.pause();
            }

            d.resolve(HttpClientResponseProxy.newInstance(response, context));
        };

        return innerDoRequest(options, requestFunction, handler, d::reject, d.getPromise());
    }

    private Promise<HttpClientResponseAndBody> doRequestAndReadBody(RequestOptions options, RequestFunction requestFunction) {

        if (options == null) {
            options = new RequestOptions();
        }

        if (options.getPauseResponse()) {
            throw new IllegalStateException("Cannot pause response when reading body");
        }

        Deferred<HttpClientResponseAndBody> d = when.defer();

        Handler<HttpClientRequestContext> handler = context -> {
            HttpClientResponse response = context.getResponse();
            response.bodyHandler(body -> {
                if (context.shouldCloseClient()) {
                    context.getClient().close();
                    context.setCloseClient(false);
                }
                context.setBody(body);
                d.resolve(context);
            });
        };

        return innerDoRequest(options, requestFunction, handler, d::reject, d.getPromise());
    }

    private <T> Promise<T> innerDoRequest(
            RequestOptions options,
            RequestFunction requestFunction,
            Handler<HttpClientRequestContext> responseHandler,
            Handler<Throwable> exceptionHandler,
            Promise<T> promise) {

        Objects.requireNonNull(options);
        Objects.requireNonNull(requestFunction);
        Objects.requireNonNull(responseHandler);

        HttpClientRequestContext context = getContext(options, requestFunction);
        HttpClientRequest request = context.getRequest();

        Objects.requireNonNull(request);

        request
                .handler(response -> {
                    context.setResponse(response);
                    responseHandler.handle(context);
                })
                .exceptionHandler(exceptionHandler);

        if (options.getChunked()) {
            request.setChunked(true);
        }
        if (options.getMultiMapHeaders() != null) {
            request.headers().addAll(options.getMultiMapHeaders());
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
                        return promise;
                    });
        } else {
            end(request, options);
            return promise;
        }

    }

    private HttpClientRequestContext getContext(RequestOptions options, RequestFunction requestFunction) {

        HttpClientRequestContext context = new HttpClientRequestContext();
        HttpClient client;
        boolean closeClient;

        if (options.getClient() != null) {
            closeClient = false;
            client = options.getClient();
        } else {
            closeClient = true;
            if (options.getClientOptions() != null) {
                client = vertx.createHttpClient(options.getClientOptions());
            } else {
                client = vertx.createHttpClient();
            }
        }

        HttpClientRequest request = requestFunction.apply(client);

        return context
                .setOptions(options)
                .setCloseClient(closeClient)
                .setClient(client)
                .setRequest(request);
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
        Promise<Buffer> p = d.getPromise();

        // Set the body handler and resume the response
        response.bodyHandler(d::resolve).resume();

        if (response instanceof HttpClientResponseContext) {
            HttpClientRequestContext context = ((HttpClientResponseContext) response).getContext();
            if (context.shouldCloseClient()) {
                p = p.then(body -> {
                    if (context.shouldCloseClient()) {
                        context.getClient().close();
                        context.setCloseClient(false);
                    }
                    return when.resolve(body);
                });
            }
        }

        return p;
    }

    private static class HttpClientRequestContext implements HttpClientResponseAndBody {

        private RequestOptions options;
        private HttpClient client;
        private boolean closeClient;
        private HttpClientRequest request;
        private HttpClientResponse response;
        private Buffer body;

        public HttpClient getClient() {
            return client;
        }

        public HttpClientRequestContext setClient(HttpClient client) {
            this.client = client;
            return this;
        }

        public boolean shouldCloseClient() {
            return closeClient;
        }

        public HttpClientRequestContext setCloseClient(boolean closeClient) {
            this.closeClient = closeClient;
            return this;
        }


        public RequestOptions getOptions() {
            return options;
        }

        public HttpClientRequestContext setOptions(RequestOptions options) {
            this.options = options;
            return this;
        }

        public HttpClientRequest getRequest() {
            return request;
        }

        public HttpClientRequestContext setRequest(HttpClientRequest request) {
            this.request = request;
            return this;
        }

        @Override
        public HttpClientResponse getResponse() {
            return response;
        }

        public HttpClientRequestContext setResponse(HttpClientResponse response) {
            this.response = response;
            return this;
        }

        @Override
        public Buffer getBody() {
            return body;
        }

        public HttpClientRequestContext setBody(Buffer body) {
            this.body = body;
            return this;
        }
    }

    private interface RequestFunction extends Function<HttpClient, HttpClientRequest> {
    }

    private interface HttpClientResponseContext {

        HttpClientRequestContext getContext();

    }

    private static class HttpClientResponseProxy implements java.lang.reflect.InvocationHandler {

        private HttpClientResponse response;
        private HttpClientRequestContext context;

        public static HttpClientResponse newInstance(HttpClientResponse response, HttpClientRequestContext context) {

            Object proxy = java.lang.reflect.Proxy.newProxyInstance(
                    response.getClass().getClassLoader(),
                    new Class[]{HttpClientResponse.class, HttpClientResponseContext.class},
                    new HttpClientResponseProxy(response, context));

            return (HttpClientResponse) proxy;
        }

        private HttpClientResponseProxy(HttpClientResponse response, HttpClientRequestContext context) {
            this.response = response;
            this.context = context;
        }

        public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
            try {
                if (m.getDeclaringClass() == HttpClientResponseContext.class) {
                    return context;
                } else {
                    return m.invoke(response, args);
                }
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
        }
    }

}
