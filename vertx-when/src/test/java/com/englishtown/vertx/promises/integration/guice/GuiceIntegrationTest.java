package com.englishtown.vertx.promises.integration.guice;

import com.englishtown.promises.When;
import com.englishtown.vertx.guice.GuiceVertxBinder;
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
        whenVertx = new DefaultWhenVertx(vertx, when);
        whenEventBus = new DefaultWhenEventBus(vertx, when);
        whenHttpClient = new DefaultWhenHttpClient(vertx, when);
    }

}
