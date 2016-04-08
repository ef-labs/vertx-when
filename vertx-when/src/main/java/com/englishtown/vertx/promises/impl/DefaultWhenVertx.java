package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Promise;
import com.englishtown.promises.When;
import com.englishtown.vertx.promises.PromiseAdapter;
import com.englishtown.vertx.promises.WhenVertx;
import io.vertx.core.*;

import javax.inject.Inject;

/**
 * Default implementation of {@link com.englishtown.vertx.promises.WhenVertx}
 */
public class DefaultWhenVertx implements WhenVertx {

    private final Vertx vertx;
    private final PromiseAdapter adapter;

    @Inject
    public DefaultWhenVertx(Vertx vertx, PromiseAdapter adapter) {
        this.vertx = vertx;
        this.adapter = adapter;
    }

    public DefaultWhenVertx(Vertx vertx, When when) {
        this(vertx, new DefaultPromiseAdapter(when));
    }

    /**
     * Deploy a verticle programmatically
     *
     * @param verticle The verticle instance to deploy
     * @return A promise for the deployment id
     */
    @Override
    public Promise<String> deployVerticle(Verticle verticle) {
        return adapter.toPromise(handler -> vertx.deployVerticle(verticle, handler));
    }

    /**
     * Deploy a verticle programmatically
     *
     * @param verticle The verticle instance to deploy
     * @param options  The deployment options
     * @return A promise for the deployment id
     */
    @Override
    public Promise<String> deployVerticle(Verticle verticle, DeploymentOptions options) {
        return adapter.toPromise(handler -> vertx.deployVerticle(verticle, options, handler));
    }

    /**
     * Deploy a verticle programmatically
     *
     * @param name The verticle identifier
     * @return A promise for the deployment id
     */
    @Override
    public Promise<String> deployVerticle(String name) {
        return adapter.toPromise(handler -> vertx.deployVerticle(name, handler));
    }

    /**
     * Deploy a verticle programmatically
     *
     * @param name    The verticle identifier
     * @param options The deployment options
     * @return A promise for the deployment id
     */
    @Override
    public Promise<String> deployVerticle(String name, DeploymentOptions options) {
        return adapter.toPromise(handler -> vertx.deployVerticle(name, options, handler));
    }

    /**
     * Undeploy a verticle
     *
     * @param deploymentID The deployment ID
     * @return A promise for undeployment completion
     */
    @Override
    public Promise<Void> undeploy(String deploymentID) {
        return adapter.toPromise(handler -> vertx.undeploy(deploymentID, handler));
    }

    /**
     * Safely execute some blocking code.
     * <p>
     * Executes the blocking code in the handler {@code blockingCodeHandler} using a thread from the worker pool.
     * <p>
     * When the code is complete the promise will resolve with the result on the original context
     * (e.g. on the original event loop of the caller).
     * <p>
     * A {@code Future} instance is passed into {@code blockingCodeHandler}. When the blocking code successfully completes,
     * the handler should call the {@link Future#complete} or {@link Future#complete(Object)} method, or the {@link Future#fail}
     * method if it failed.
     *
     * @param blockingCodeHandler handler representing the blocking code to run
     * @param ordered             if true then if executeBlocking is called several times on the same context, the executions
     *                            for that context will be executed serially, not in parallel. if false then they will be no ordering
     *                            guarantees
     */
    @Override
    public <T> Promise<T> executeBlocking(Handler<Future<T>> blockingCodeHandler, boolean ordered) {
        return adapter.toPromise(handler -> vertx.executeBlocking(blockingCodeHandler, ordered, handler));
    }

}
