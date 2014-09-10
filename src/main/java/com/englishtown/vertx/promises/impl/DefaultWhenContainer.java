package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Deferred;
import com.englishtown.promises.Promise;
import com.englishtown.promises.When;
import com.englishtown.vertx.promises.WhenContainer;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Container;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of {@link WhenContainer}
 */
public class DefaultWhenContainer implements WhenContainer {

    private final Container container;
    private final When when;

    @Inject
    public DefaultWhenContainer(Container container, When when) {
        this.container = container;
        this.when = when;
    }

    /**
     * Deploy a verticle programmatically
     *
     * @param main The main of the verticle
     */
    @Override
    public Promise<String> deployVerticle(String main) {
        return deployVerticle(main, null, 1);
    }

    /**
     * Deploy a verticle programmatically
     *
     * @param main   The main of the verticle
     * @param config JSON config to provide to the verticle
     */
    @Override
    public Promise<String> deployVerticle(String main, JsonObject config) {
        return deployVerticle(main, config, 1);
    }

    /**
     * Deploy a verticle programmatically
     *
     * @param main      The main of the verticle
     * @param instances The number of instances to deploy (defaults to 1)
     */
    @Override
    public Promise<String> deployVerticle(String main, int instances) {
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
    public Promise<String> deployVerticle(String main, JsonObject config, int instances) {
        final Deferred<String> d = when.defer();

        container.deployVerticle(main, config, instances, result -> {
            if (result.succeeded()) {
                d.resolve(result.result());
            } else {
                d.reject(result.cause());
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
    public Promise<String> deployModule(String moduleName) {
        return deployModule(moduleName, null, 1);
    }

    /**
     * Deploy a module programmatically
     *
     * @param moduleName The main of the module to deploy
     * @param config     JSON config to provide to the module
     */
    @Override
    public Promise<String> deployModule(String moduleName, JsonObject config) {
        return deployModule(moduleName, config, 1);
    }

    /**
     * Deploy a module programmatically
     *
     * @param moduleName The main of the module to deploy
     * @param instances  The number of instances to deploy (defaults to 1)
     */
    @Override
    public Promise<String> deployModule(String moduleName, int instances) {
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
    public Promise<String> deployModule(String moduleName, JsonObject config, int instances) {
        final Deferred<String> d = when.defer();

        container.deployModule(moduleName, config, instances, result -> {
            if (result.succeeded()) {
                d.resolve(result.result());
            } else {
                d.reject(result.cause());
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
    public Promise<String> deployWorkerVerticle(String main, boolean multiThreaded) {
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
    public Promise<String> deployWorkerVerticle(String main, JsonObject config, boolean multiThreaded) {
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
    public Promise<String> deployWorkerVerticle(String main, int instances, boolean multiThreaded) {
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
    public Promise<String> deployWorkerVerticle(String main, JsonObject config, int instances, boolean multiThreaded) {
        final Deferred<String> d = when.defer();

        container.deployWorkerVerticle(main, config, instances, multiThreaded, result -> {
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
     */
    @Override
    public Promise<Void> undeployVerticle(String deploymentID) {
        final Deferred<Void> d = when.defer();
        container.undeployVerticle(deploymentID, result -> {
            if (result.succeeded()) {
                d.resolve((Void) null);
            } else {
                d.reject(result.cause());
            }
        });
        return d.getPromise();
    }

    /**
     * Undeploy a module
     *
     * @param deploymentID The deployment ID
     */
    @Override
    public Promise<Void> undeployModule(String deploymentID) {
        final Deferred<Void> d = when.defer();
        container.undeployModule(deploymentID, result -> {
            if (result.succeeded()) {
                d.resolve((Void) null);
            } else {
                d.reject(result.cause());
            }
        });
        return d.getPromise();

    }

    /**
     * Deploys one or more modules.  The Json is of the structure:
     * [ {
     * "name": "groupId~artifactId~version",
     * "instances": 1,
     * "config": {}
     * } ]
     *
     * @param modules
     * @return
     */
    @Override
    public List<Promise<String>> deployModules(JsonArray modules) {
        List<Promise<String>> promises = new ArrayList<>();

        if (modules == null) {
            return promises;
        }

        for (int i = 0; i < modules.size(); i++) {
            JsonObject module = modules.get(i);
            String name = module.getString("name");

            if (name != null && !name.isEmpty()) {
                int instances = module.getInteger("instances", 1);
                JsonObject config = module.getObject("config", new JsonObject());
                promises.add(deployModule(name, config, instances));
            }
        }

        return promises;
    }

}
