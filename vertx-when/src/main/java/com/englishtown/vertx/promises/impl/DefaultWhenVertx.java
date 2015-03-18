package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Deferred;
import com.englishtown.promises.Promise;
import com.englishtown.promises.When;
import com.englishtown.vertx.promises.WhenVertx;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;

import javax.inject.Inject;

/**
 * Default implementation of {@link com.englishtown.vertx.promises.WhenVertx}
 */
public class DefaultWhenVertx implements WhenVertx {

    private final Vertx vertx;
    private final When when;

    @Inject
    public DefaultWhenVertx(Vertx vertx, When when) {
        this.vertx = vertx;
        this.when = when;
    }

    /**
     * Deploy a verticle programmatically
     *
     * @param verticle The verticle instance to deploy
     * @return A promise for the deployment id
     */
    @Override
    public Promise<String> deployVerticle(Verticle verticle) {
        Deferred<String> d = when.defer();

        vertx.deployVerticle(verticle, result -> {
            if (result.succeeded()) {
                d.resolve(result.result());
            } else {
                d.reject(result.cause());
            }
        });

        return d.getPromise();
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
        Deferred<String> d = when.defer();

        vertx.deployVerticle(verticle, options, result -> {
            if (result.succeeded()) {
                d.resolve(result.result());
            } else {
                d.reject(result.cause());
            }
        });

        return d.getPromise();
    }

    /**
     * Deploy a verticle programmatically
     *
     * @param name The verticle identifier
     * @return A promise for the deployment id
     */
    @Override
    public Promise<String> deployVerticle(String name) {
        Deferred<String> d = when.defer();

        vertx.deployVerticle(name, result -> {
            if (result.succeeded()) {
                d.resolve(result.result());
            } else {
                d.reject(result.cause());
            }
        });

        return d.getPromise();
    }

    /**
     * Deploy a verticle programmatically
     *
     * @param name The verticle identifier
     * @param options  The deployment options
     * @return A promise for the deployment id
     */
    @Override
    public Promise<String> deployVerticle(String name, DeploymentOptions options) {
        Deferred<String> d = when.defer();

        vertx.deployVerticle(name, options, result -> {
            if (result.succeeded()) {
                d.resolve(result.result());
            } else {
                d.reject(result.cause());
            }
        });

        return d.getPromise();
    }

    /**
     * Undeploy a verticle
     *
     * @param deploymentID The deployment ID
     * @return A promise for undeployment completion
     */
    @Override
    public Promise<Void> undeploy(String deploymentID) {
        Deferred<Void> d = when.defer();

        vertx.undeploy(deploymentID, result -> {
            if (result.succeeded()) {
                d.resolve(result.result());
            } else {
                d.reject(result.cause());
            }
        });

        return d.getPromise();
    }
}
