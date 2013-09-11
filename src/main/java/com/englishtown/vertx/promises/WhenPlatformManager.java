package com.englishtown.vertx.promises;

import com.englishtown.promises.Promise;
import org.vertx.java.core.Handler;
import org.vertx.java.core.json.JsonObject;

import java.net.URL;
import java.util.Map;

/**
 * <p>Represents the Vert.x platform.</p>
 * <p>It's the role of a PlatformManager to deploy and undeploy modules and verticles. It's also used to install modules, and for various other tasks.</p>
 * <p>The Platform Manager basically represents the Vert.x container in which verticles and modules run.</p>
 */
public interface WhenPlatformManager {

	/**
	 * Deploy a verticle
	 *
	 * @param main      The main, e.g. app.js, foo.rb, org.mycompany.MyMain, etc
	 * @param config    Any JSON config to pass to the verticle, or null if none
	 * @param classpath The classpath for the verticle
	 * @param instances The number of instances to deploy
	 * @param includes  Comma separated list of modules to include, or null if none
	 * @return Promise of deployment
	 */
	Promise<String, Void> deployVerticle(String main, JsonObject config, URL[] classpath, int instances, String includes);

	/**
	 * Deploy a worker verticle
	 *
	 * @param multiThreaded Is it a multi-threaded worker verticle?
	 * @param main          The main, e.g. app.js, foo.rb, org.mycompany.MyMain, etc
	 * @param config        Any JSON config to pass to the verticle, or null if none
	 * @param classpath     The classpath for the verticle
	 * @param instances     The number of instances to deploy
	 * @param includes      Comma separated list of modules to include, or null if none
	 * @return Promise of deployment
	 */
	Promise<String, Void> deployWorkerVerticle(boolean multiThreaded, String main, JsonObject config, URL[] classpath, int instances, String includes);

	/**
	 * Deploy a module
	 *
	 * @param moduleName The name of the module to deploy
	 * @param config     Any JSON config to pass to the verticle, or null if none
	 * @param instances  The number of instances to deploy
	 * @return Promise of deployment
	 */
	Promise<String, Void> deployModule(String moduleName, JsonObject config, int instances);

	/**
	 * Deploy a module from a zip file. The zip must contain a valid Vert.x module. Vert.x will automatically install the module from the zip into the local mods dir or the system mods dir (if it's a system module), or VERTX_MODS if set, and then deploy the module
	 *
	 * @param zipFileName The name of the zip file that contains the module
	 * @param config      Any JSON config to pass to the verticle, or null if none
	 * @param instances   The number of instances to deploy
	 * @return Promise of deployment
	 */
	Promise<String, Void> deployModuleFromZip(String zipFileName, JsonObject config, int instances);

	/**
	 * Deploy a module from the classpath. The classpath must contain a single mod.json and the resources for that module only.
	 *
	 * @param moduleName The name of the module to deploy
	 * @param config     Any JSON config to pass to the verticle, or null if none
	 * @param instances  The number of instances to deploy
	 * @param classpath  Array of URLS corresponding to the classpath for the module
	 * @return Promise of deployment
	 */
	Promise<String, Void> deployModuleFromClasspath(String moduleName, JsonObject config, int instances, URL[] classpath);

	/**
	 * Undeploy a deployment
	 *
	 * @param deploymentID The ID of the deployment to undeploy, as given in the doneHandler when deploying
	 * @return Promise of undeployment
	 */
	Promise<Void, Void> undeploy(String deploymentID);

	/**
	 * Undeploy all verticles and modules
	 *
	 * @return Promise of undeployment
	 */
	Promise<Void, Void> undeployAll();

	/**
	 * List all deployments, with deployment ID and number of instances
	 *
	 * @return map of instances
	 */
	Map<String, Integer> listInstances();

	/**
	 * Install a module into the filesystem Vert.x will search in the configured repos to locate the module
	 *
	 * @param moduleName The name of the module
	 * @return Promise of installation
	 */
	Promise<Void, Void> installModule(String moduleName);

	/**
	 * Uninstall a module from the filesystem
	 *
	 * @param moduleName The name of the module
	 * @return Promise of uninstallation
	 */
	Promise<Void, Void> uninstallModule(String moduleName);

	/**
	 * Pull in all the dependencies (the 'includes' and the 'deploys' fields in mod.json) and copy them into an internal mods directory in the module. This allows a self contained module to be created.
	 *
	 * @param moduleName The name of the module
	 * @return Promise of pull
	 */
	Promise<Void, Void> pullInDependencies(String moduleName);

	/**
	 * Register a handler that will be called when the platform exits because of a verticle calling container.exit()
	 *
	 * @param handler The handler
	 */
	void registerExitHandler(Handler<Void> handler);

	/**
	 * Stop the platform manager
	 */
	void stop();
}
