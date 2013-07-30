package com.englishtown.vertx.promises;

import com.englishtown.promises.Promise;
import org.vertx.java.core.json.JsonObject;

/**
 * When.java wrapper over a vert.x {@link org.vertx.java.platform.Container}
 */
public interface WhenContainer {

    /**
     * Deploy a verticle programmatically
     *
     * @param main The main of the verticle
     */
    Promise<String, Void> deployVerticle(String main);

    /**
     * Deploy a verticle programmatically
     *
     * @param main   The main of the verticle
     * @param config JSON config to provide to the verticle
     */
    Promise<String, Void> deployVerticle(String main, JsonObject config);

    /**
     * Deploy a verticle programmatically
     *
     * @param main      The main of the verticle
     * @param instances The number of instances to deploy (defaults to 1)
     */
    Promise<String, Void> deployVerticle(String main, int instances);

    /**
     * Deploy a verticle programmatically
     *
     * @param main      The main of the verticle
     * @param config    JSON config to provide to the verticle
     * @param instances The number of instances to deploy (defaults to 1)
     */
    Promise<String, Void> deployVerticle(String main, JsonObject config, int instances);

    /**
     * Deploy a module programmatically
     *
     * @param moduleName The main of the module to deploy
     */
    Promise<String, Void> deployModule(String moduleName);

    /**
     * Deploy a module programmatically
     *
     * @param moduleName The main of the module to deploy
     * @param config     JSON config to provide to the module
     */
    Promise<String, Void> deployModule(String moduleName, JsonObject config);

    /**
     * Deploy a module programmatically
     *
     * @param moduleName The main of the module to deploy
     * @param instances  The number of instances to deploy (defaults to 1)
     */
    Promise<String, Void> deployModule(String moduleName, int instances);

    /**
     * Deploy a module programmatically
     *
     * @param moduleName The main of the module to deploy
     * @param config     JSON config to provide to the module
     * @param instances  The number of instances to deploy (defaults to 1)
     */
    Promise<String, Void> deployModule(String moduleName, JsonObject config, int instances);

    /**
     * Deploy a worker verticle programmatically
     *
     * @param main          The main of the verticle
     * @param multiThreaded if true then the verticle will be deployed as a multi-threaded worker
     */
    Promise<String, Void> deployWorkerVerticle(String main, boolean multiThreaded);

    /**
     * Deploy a worker verticle programmatically
     *
     * @param main          The main of the verticle
     * @param config        JSON config to provide to the verticle
     * @param multiThreaded if true then the verticle will be deployed as a multi-threaded worker
     */
    Promise<String, Void> deployWorkerVerticle(String main, JsonObject config, boolean multiThreaded);

    /**
     * Deploy a worker verticle programmatically
     *
     * @param main          The main of the verticle
     * @param instances     The number of instances to deploy (defaults to 1)
     * @param multiThreaded if true then the verticle will be deployed as a multi-threaded worker
     */
    Promise<String, Void> deployWorkerVerticle(String main, int instances, boolean multiThreaded);

    /**
     * Deploy a worker verticle programmatically
     *
     * @param main          The main of the verticle
     * @param config        JSON config to provide to the verticle
     * @param instances     The number of instances to deploy (defaults to 1)
     * @param multiThreaded if true then the verticle will be deployed as a multi-threaded worker
     */
    Promise<String, Void> deployWorkerVerticle(String main, JsonObject config, int instances, boolean multiThreaded);

}
