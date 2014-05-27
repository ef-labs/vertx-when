package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.*;
import com.englishtown.vertx.promises.HttpClientResponseAndBody;
import com.englishtown.vertx.promises.WhenHttpClientResponse;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpClientResponse;

/**
 * Default implementation of {@link WhenHttpClientResponse}
 *
 * @deprecated Use {@link com.englishtown.vertx.promises.WhenHttpClient} requestResponseBody() instead.
 */
@Deprecated
public class DefaultWhenHttpClientResponse implements WhenHttpClientResponse {

    private final When<HttpClientResponseAndBody> when = new When<>();

    /**
     * @param promise
     * @return
     * @deprecated Use {@link com.englishtown.vertx.promises.WhenHttpClient} requestResponseBody() instead.
     */
    @Deprecated
    @Override
    public Promise<HttpClientResponseAndBody> body(Promise<HttpClientResponse> promise) {

        final Deferred<HttpClientResponseAndBody> d = when.defer();

        promise.then(
                new FulfilledRunnable<HttpClientResponse>() {
                    @Override
                    public Promise<HttpClientResponse> run(final HttpClientResponse response) {
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
                new RejectedRunnable<HttpClientResponse>() {
                    @Override
                    public Promise<HttpClientResponse> run(Value<HttpClientResponse> value) {
                        reject(d, value.getCause());
                        return null;
                    }
                }
        );

        return d.getPromise();
    }

    protected void reject(Deferred<HttpClientResponseAndBody> d, Throwable t) {
        RuntimeException e = (t == null || t instanceof RuntimeException ? (RuntimeException) t : new RuntimeException(t));
        d.getResolver().reject(new Value<HttpClientResponseAndBody>(null, e));
    }

}
