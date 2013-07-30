package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.*;
import com.englishtown.promises.Runnable;
import com.englishtown.vertx.promises.HttpClientResponseAndBody;
import com.englishtown.vertx.promises.WhenHttpClientResponse;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpClientResponse;

/**
 * Default implementation of {@link WhenHttpClientResponse}
 */
public class DefaultWhenHttpClientResponse implements WhenHttpClientResponse {

    private final When<HttpClientResponseAndBody, Void> when = new When<>();

    @Override
    public Promise<HttpClientResponseAndBody, Void> body(Promise<HttpClientResponse, Void> promise) {

        final Deferred<HttpClientResponseAndBody, Void> d = when.defer();

        promise.then(
                new Runnable<Promise<HttpClientResponse, Void>, HttpClientResponse>() {
                    @Override
                    public Promise<HttpClientResponse, Void> run(final HttpClientResponse response) {
                        try {

                            response.exceptionHandler(new Handler<Throwable>() {
                                @Override
                                public void handle(Throwable t) {
                                    reject(d, t);
                                }
                            });

                            response.bodyHandler(new Handler<Buffer>() {
                                @Override
                                public void handle(Buffer body) {
                                    d.getResolver().resolve(new DefaultHttpClientResponseAndBody(response, body));
                                }
                            });

                        } catch (RuntimeException e) {
                            reject(d, e);
                        }
                        return null;
                    }
                },
                new Runnable<Promise<HttpClientResponse, Void>, Value<HttpClientResponse>>() {
                    @Override
                    public Promise<HttpClientResponse, Void> run(Value<HttpClientResponse> response) {
                        reject(d, response.error);
                        return null;
                    }
                }
        );

        return d.getPromise();
    }

    protected void reject(Deferred<HttpClientResponseAndBody, Void> d, Throwable t) {
        RuntimeException e = (t == null || t instanceof RuntimeException ? (RuntimeException) t : new RuntimeException(t));
        d.getResolver().reject(new Value<HttpClientResponseAndBody>(null, e));
    }

}
