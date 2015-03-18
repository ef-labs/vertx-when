package com.englishtown.vertx.promises.integration.simple;

import com.englishtown.promises.WhenFactory;
import com.englishtown.vertx.promises.impl.*;
import com.englishtown.vertx.promises.integration.IntegrationTestBase;

/**
 * Integration tests not using dependency injection
 */
public class NoDIIntegrationTest extends IntegrationTestBase {

    @Override
    public void setUp() throws Exception {
        super.setUp();

        // Create the vert.x executor for callbacks to run on the vert.x event loop
        VertxExecutor executor = new VertxExecutor(vertx);
        when = WhenFactory.createFor(() -> executor);

        whenVertx = new DefaultWhenVertx(vertx, when);
        whenEventBus = new DefaultWhenEventBus(vertx, when);
        whenHttpClient = new DefaultWhenHttpClient(vertx, when);
        whenFileSystem = new DefaultWhenFileSystem(vertx, when);

    }
}
