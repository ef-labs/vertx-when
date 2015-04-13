package com.englishtown.vertx.promises;

import com.englishtown.promises.Promise;
import com.englishtown.promises.Resolver;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

import java.util.function.Consumer;

/**
 * Converts vert.x class to promises
 */
public interface PromiseAdapter {

    /**
     * Converts a {@link Future} to a {@link Promise}
     *
     * @param future
     * @param <T>
     * @return
     */
    default <T> Promise<T> toPromise(Future<T> future) {
        return toPromise(future::setHandler);
    }

    /**
     * Converts a {@link Consumer} for an {@link AsyncResult} {@link Handler} to a {@link Promise}
     *
     * @param consumer
     * @param <T>
     * @return
     */
    <T> Promise<T> toPromise(Consumer<Handler<AsyncResult<T>>> consumer);

    /**
     * Creates a vert.x {@link AsyncResult} handler for a {@link Resolver}
     *
     * @param resolver
     * @param <T>
     * @return
     */
    <T> Handler<AsyncResult<T>> toHandler(Resolver<T> resolver);

}
