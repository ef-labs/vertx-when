package com.englishtown.vertx.promises.integration.guice;

import com.englishtown.promises.When;
import com.englishtown.vertx.guice.GuiceVertxBinder;
import com.englishtown.vertx.promises.WhenEventBus;
import com.englishtown.vertx.promises.WhenFileSystem;
import com.englishtown.vertx.promises.WhenHttpClient;
import com.englishtown.vertx.promises.WhenVertx;
import com.englishtown.vertx.promises.guice.GuiceWhenBinder;
import com.englishtown.vertx.promises.impl.DefaultWhenEventBus;
import com.englishtown.vertx.promises.impl.DefaultWhenHttpClient;
import com.englishtown.vertx.promises.impl.DefaultWhenVertx;
import com.englishtown.vertx.promises.integration.IntegrationTestBase;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Integration tests
 */
public class GuiceIntegrationTest extends IntegrationTestBase {

    @Override
    public void setUp() throws Exception {
        super.setUp();

        Injector injector = Guice.createInjector(new GuiceWhenBinder(), new GuiceVertxBinder(vertx));
        when = injector.getInstance(When.class);
        whenVertx = injector.getInstance(WhenVertx.class);
        whenEventBus = injector.getInstance(WhenEventBus.class);
        whenHttpClient = injector.getInstance(WhenHttpClient.class);
        whenFileSystem = injector.getInstance(WhenFileSystem.class);

    }

}
