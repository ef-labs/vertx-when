package com.englishtown.vertx.promises;

import com.englishtown.promises.Promise;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Verticle;

/**
 * When.java wrapper over {@link io.vertx.core.Vertx} async operations
 */
public interface WhenVertx {

    /**
     * Deploy a verticle programmatically
     *
     * @param verticle The verticle instance to deploy
     * @return A promise for the deployment id
     */
    Promise<String> deployVerticle(Verticle verticle);

    /**
     * Deploy a verticle programmatically
     *
     * @param verticle The verticle instance to deploy
     * @param options  The deployment options
     * @return A promise for the deployment id
     */
    Promise<String> deployVerticle(Verticle verticle, DeploymentOptions options);

    /**
     * Deploy a verticle programmatically
     *
     * @param name The verticle identifier
     * @return A promise for the deployment id
     */
    Promise<String> deployVerticle(String name);

    /**
     * Deploy a verticle programmatically
     *
     * @param name    The verticle identifier
     * @param options The deployment options
     * @return A promise for the deployment id
     */
    Promise<String> deployVerticle(String name, DeploymentOptions options);

    /**
     * Undeploy a verticle
     *
     * @param deploymentID The deployment ID
     * @return A promise for undeployment completion
     */
    Promise<Void> undeploy(String deploymentID);

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
     * @param <T>                 the type of the result
     */
    <T> Promise<T> executeBlocking(Handler<Future<T>> blockingCodeHandler, boolean ordered);

    /**
     * Like {@link #executeBlocking(Handler, boolean)} called with ordered = true.
     */
    default <T> Promise<T> executeBlocking(Handler<Future<T>> blockingCodeHandler) {
        return executeBlocking(blockingCodeHandler, true);
    }

}
