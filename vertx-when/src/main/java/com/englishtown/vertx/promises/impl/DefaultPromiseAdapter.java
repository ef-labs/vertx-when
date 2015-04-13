package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Deferred;
import com.englishtown.promises.Promise;
import com.englishtown.promises.Resolver;
import com.englishtown.promises.When;
import com.englishtown.vertx.promises.PromiseAdapter;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import javax.inject.Inject;
import java.util.function.Consumer;

/**
 * Default implementation of {@link PromiseAdapter}
 */
public class DefaultPromiseAdapter implements PromiseAdapter {

    private final When when;

    @Inject
    public DefaultPromiseAdapter(When when) {
        this.when = when;
    }

    /**
     * Converts a {@link Consumer} for an {@link AsyncResult} {@link Handler} to a {@link Promise}
     *
     * @param consumer
     * @param <T>
     * @return
     */
    @Override
    public <T> Promise<T> toPromise(Consumer<Handler<AsyncResult<T>>> consumer) {
        Deferred<T> d = when.defer();
        consumer.accept(toHandler(d));
        return d.getPromise();
    }

    /**
     * Creates a vert.x {@link AsyncResult} handler for a {@link Resolver}
     *
     * @param resolver
     * @param <T>
     * @return
     */
    @Override
    public <T> Handler<AsyncResult<T>> toHandler(Resolver<T> resolver) {
        return result -> {
            if (result.succeeded()) {
                resolver.resolve(result.result());
            } else {
                resolver.reject(result.cause());
            }
        };
    }

}
