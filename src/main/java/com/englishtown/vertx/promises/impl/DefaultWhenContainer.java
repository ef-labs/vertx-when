package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Deferred;
import com.englishtown.promises.Promise;
import com.englishtown.promises.Value;
import com.englishtown.promises.When;
import com.englishtown.vertx.promises.WhenContainer;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Container;

import javax.inject.Inject;

/**
 * Default implementation of {@link WhenContainer}
 */
public class DefaultWhenContainer implements WhenContainer {

    private final Container container;
    private final When<String, Void> when = new When<>();

    @Inject
    public DefaultWhenContainer(Container container) {
        this.container = container;
    }

    /**
     * Deploy a verticle programmatically
     *
     * @param main The main of the verticle
     */
    @Override
    public Promise<String, Void> deployVerticle(String main) {
        return deployVerticle(main, null, 1);
    }

    /**
     * Deploy a verticle programmatically
     *
     * @param main   The main of the verticle
     * @param config JSON config to provide to the verticle
     */
    @Override
    public Promise<String, Void> deployVerticle(String main, JsonObject config) {
        return deployVerticle(main, config, 1);
    }

    /**
     * Deploy a verticle programmatically
     *
     * @param main      The main of the verticle
     * @param instances The number of instances to deploy (defaults to 1)
     */
    @Override
    public Promise<String, Void> deployVerticle(String main, int instances) {
        return deployVerticle(main, null, instances);
    }

    /**
     * Deploy a verticle programmatically
     *
     * @param main      The main of the verticle
     * @param config    JSON config to provide to the verticle
     * @param instances The number of instances to deploy (defaults to 1)
     */
    @Override
    public Promise<String, Void> deployVerticle(String main, JsonObject config, int instances) {
        final Deferred<String, Void> d = when.defer();

        container.deployVerticle(main, config, instances, new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> result) {
                if (result.succeeded()) {
                    d.getResolver().resolve(result.result());
                } else {
                    reject(d, result.result(), result.cause());
                }
            }
        });

        return d.getPromise();
    }

    /**
     * Deploy a module programmatically
     *
     * @param moduleName The main of the module to deploy
     */
    @Override
    public Promise<String, Void> deployModule(String moduleName) {
        return deployModule(moduleName, null, 1);
    }

    /**
     * Deploy a module programmatically
     *
     * @param moduleName The main of the module to deploy
     * @param config     JSON config to provide to the module
     */
    @Override
    public Promise<String, Void> deployModule(String moduleName, JsonObject config) {
        return deployModule(moduleName, config, 1);
    }

    /**
     * Deploy a module programmatically
     *
     * @param moduleName The main of the module to deploy
     * @param instances  The number of instances to deploy (defaults to 1)
     */
    @Override
    public Promise<String, Void> deployModule(String moduleName, int instances) {
        return deployModule(moduleName, null, instances);
    }

    /**
     * Deploy a module programmatically
     *
     * @param moduleName The main of the module to deploy
     * @param config     JSON config to provide to the module
     * @param instances  The number of instances to deploy (defaults to 1)
     */
    @Override
    public Promise<String, Void> deployModule(String moduleName, JsonObject config, int instances) {
        final Deferred<String, Void> d = when.defer();

        container.deployModule(moduleName, config, instances, new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> result) {
                if (result.succeeded()) {
                    d.getResolver().resolve(result.result());
                } else {
                    reject(d, result.result(), result.cause());
                }
            }
        });

        return d.getPromise();
    }

    /**
     * Deploy a worker verticle programmatically
     *
     * @param main          The main of the verticle
     * @param multiThreaded if true then the verticle will be deployed as a multi-threaded worker
     */
    @Override
    public Promise<String, Void> deployWorkerVerticle(String main, boolean multiThreaded) {
        return deployWorkerVerticle(main, null, 1, multiThreaded);
    }

    /**
     * Deploy a worker verticle programmatically
     *
     * @param main          The main of the verticle
     * @param config        JSON config to provide to the verticle
     * @param multiThreaded if true then the verticle will be deployed as a multi-threaded worker
     */
    @Override
    public Promise<String, Void> deployWorkerVerticle(String main, JsonObject config, boolean multiThreaded) {
        return deployWorkerVerticle(main, config, 1, multiThreaded);
    }

    /**
     * Deploy a worker verticle programmatically
     *
     * @param main          The main of the verticle
     * @param instances     The number of instances to deploy (defaults to 1)
     * @param multiThreaded if true then the verticle will be deployed as a multi-threaded worker
     */
    @Override
    public Promise<String, Void> deployWorkerVerticle(String main, int instances, boolean multiThreaded) {
        return deployWorkerVerticle(main, null, instances, multiThreaded);
    }

    /**
     * Deploy a worker verticle programmatically
     *
     * @param main          The main of the verticle
     * @param config        JSON config to provide to the verticle
     * @param instances     The number of instances to deploy (defaults to 1)
     * @param multiThreaded if true then the verticle will be deployed as a multi-threaded worker
     */
    @Override
    public Promise<String, Void> deployWorkerVerticle(String main, JsonObject config, int instances, boolean multiThreaded) {
        final Deferred<String, Void> d = when.defer();

        container.deployWorkerVerticle(main, config, instances, multiThreaded, new Handler<AsyncResult<String>>() {
            @Override
            public void handle(AsyncResult<String> result) {
                if (result.succeeded()) {
                    d.getResolver().resolve(result.result());
                } else {
                    reject(d, result.result(), result.cause());
                }
            }
        });

        return d.getPromise();
    }

    protected void reject(Deferred<String, Void> d, String result, Throwable t) {
        RuntimeException e = (t == null || t instanceof RuntimeException ? (RuntimeException) t : new RuntimeException(t));
        d.getResolver().reject(new Value<>(result, e));
    }

}
