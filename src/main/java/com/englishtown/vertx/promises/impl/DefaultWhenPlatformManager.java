package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Deferred;
import com.englishtown.promises.Promise;
import com.englishtown.promises.Value;
import com.englishtown.promises.When;
import com.englishtown.vertx.promises.WhenPlatformManager;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.PlatformManager;

import javax.inject.Inject;
import java.net.URL;
import java.util.Map;

/**
 * Default implementation of {@link com.englishtown.vertx.promises.WhenPlatformManager}
 */
public class DefaultWhenPlatformManager implements WhenPlatformManager {
	private final PlatformManager manager;
	private final When<String, Void> when = new When<>();

	@Inject
	public DefaultWhenPlatformManager(PlatformManager manager) {
		this.manager = manager;
	}

	protected void reject(Deferred<String, Void> d, String result, Throwable t) {
		RuntimeException e = (t == null || t instanceof RuntimeException ? (RuntimeException) t : new RuntimeException(t));
		d.getResolver().reject(new Value<>(result, e));
	}

	protected void reject(Deferred<Void, Void> d, Void result, Throwable t) {
		RuntimeException e = (t == null || t instanceof RuntimeException ? (RuntimeException) t : new RuntimeException(t));
		d.getResolver().reject(new Value<>(result, e));
	}

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
	@Override
	public Promise<String, Void> deployVerticle(String main, JsonObject config, URL[] classpath, int instances, String includes) {
		final Deferred<String, Void> d = when.defer();

		manager.deployVerticle(main, config, classpath, instances, includes, new Handler<AsyncResult<String>>() {
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
	@Override
	public Promise<String, Void> deployWorkerVerticle(boolean multiThreaded, String main, JsonObject config, URL[] classpath, int instances, String includes) {
		final Deferred<String, Void> d = when.defer();

		manager.deployWorkerVerticle(multiThreaded, main, config, classpath, instances, includes, new Handler<AsyncResult<String>>() {
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
	 * Deploy a module
	 *
	 * @param moduleName The name of the module to deploy
	 * @param config     Any JSON config to pass to the verticle, or null if none
	 * @param instances  The number of instances to deploy
	 * @return Promise of deployment
	 */
	@Override
	public Promise<String, Void> deployModule(String moduleName, JsonObject config, int instances) {
		final Deferred<String, Void> d = when.defer();

		manager.deployModule(moduleName, config, instances, new Handler<AsyncResult<String>>() {
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
	 * Deploy a module from a zip file. The zip must contain a valid Vert.x module. Vert.x will automatically install the module from the zip into the local mods dir or the system mods dir (if it's a system module), or VERTX_MODS if set, and then deploy the module
	 *
	 * @param zipFileName The name of the zip file that contains the module
	 * @param config      Any JSON config to pass to the verticle, or null if none
	 * @param instances   The number of instances to deploy
	 * @return Promise of deployment
	 */
	@Override
	public Promise<String, Void> deployModuleFromZip(String zipFileName, JsonObject config, int instances) {
		final Deferred<String, Void> d = when.defer();

		manager.deployModuleFromZip(zipFileName, config, instances, new Handler<AsyncResult<String>>() {
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
	 * Deploy a module from the classpath. The classpath must contain a single mod.json and the resources for that module only.
	 *
	 * @param moduleName The name of the module to deploy
	 * @param config     Any JSON config to pass to the verticle, or null if none
	 * @param instances  The number of instances to deploy
	 * @param classpath  Array of URLS corresponding to the classpath for the module
	 * @return Promise of deployment
	 */
	@Override
	public Promise<String, Void> deployModuleFromClasspath(String moduleName, JsonObject config, int instances, URL[] classpath) {
		final Deferred<String, Void> d = when.defer();

		manager.deployModuleFromClasspath(moduleName, config, instances, classpath, new Handler<AsyncResult<String>>() {
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
	 * Undeploy a deployment
	 *
	 * @param deploymentID The ID of the deployment to undeploy, as given in the doneHandler when deploying
	 * @return Promise of undeployment
	 */
	@Override
	public Promise<Void, Void> undeploy(String deploymentID) {
		final Deferred<Void, Void> d = new When<Void, Void>().defer();

		manager.undeploy(deploymentID, new Handler<AsyncResult<Void>>() {
			@Override
			public void handle(AsyncResult<Void> result) {
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
	 * Undeploy all verticles and modules
	 *
	 * @return Promise of undeployment
	 */
	@Override
	public Promise<Void, Void> undeployAll() {
		final Deferred<Void, Void> d = new When<Void, Void>().defer();

		manager.undeployAll(new Handler<AsyncResult<Void>>() {
			@Override
			public void handle(AsyncResult<Void> result) {
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
	 * List all deployments, with deployment ID and number of instances
	 *
	 * @return map of instances
	 */
	@Override
	public Map<String, Integer> listInstances() {
		return manager.listInstances();
	}

	/**
	 * Install a module into the filesystem Vert.x will search in the configured repos to locate the module
	 *
	 * @param moduleName The name of the module
	 * @return Promise of installation
	 */
	@Override
	public Promise<Void, Void> installModule(String moduleName) {
		final Deferred<Void, Void> d = new When<Void, Void>().defer();

		manager.installModule(moduleName, new Handler<AsyncResult<Void>>() {
			@Override
			public void handle(AsyncResult<Void> result) {
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
	 * Uninstall a module from the filesystem
	 *
	 * @param moduleName The name of the module
	 * @return Promise of uninstallation
	 */
	@Override
	public Promise<Void, Void> uninstallModule(String moduleName) {
		final Deferred<Void, Void> d = new When<Void, Void>().defer();

		manager.uninstallModule(moduleName, new Handler<AsyncResult<Void>>() {
			@Override
			public void handle(AsyncResult<Void> result) {
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
	 * Pull in all the dependencies (the 'includes' and the 'deploys' fields in mod.json) and copy them into an internal mods directory in the module. This allows a self contained module to be created.
	 *
	 * @param moduleName The name of the module
	 * @return Promise of pull
	 */
	@Override
	public Promise<Void, Void> pullInDependencies(String moduleName) {
		final Deferred<Void, Void> d = new When<Void, Void>().defer();

		manager.pullInDependencies(moduleName, new Handler<AsyncResult<Void>>() {
			@Override
			public void handle(AsyncResult<Void> result) {
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
	 * Register a handler that will be called when the platform exits because of a verticle calling container.exit()
	 *
	 * @param handler The handler
	 */
	@Override
	public void registerExitHandler(Handler<Void> handler) {
		manager.registerExitHandler(handler);
	}

	/**
	 * Stop the platform manager
	 */
	@Override
	public void stop() {
		manager.stop();
	}
}
