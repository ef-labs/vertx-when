package com.englishtown.vertx.promises;

import com.englishtown.promises.Promise;
import io.vertx.core.DeploymentOptions;
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
     * @param name The verticle identifier
     * @param options    The deployment options
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

}
