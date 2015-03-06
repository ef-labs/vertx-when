package com.englishtown.vertx.promises.guice;

import com.englishtown.promises.guice.WhenBinder;
import com.englishtown.vertx.promises.WhenVertx;
import com.englishtown.vertx.promises.WhenEventBus;
import com.englishtown.vertx.promises.WhenHttpClient;
import com.englishtown.vertx.promises.impl.*;
import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;

import javax.inject.Singleton;

/**
 * Guice vertx-when binder
 */
public class GuiceWhenBinder extends AbstractModule {
    /**
     * Configures a {@link com.google.inject.Binder} via the exposed methods.
     */
    @Override
    protected void configure() {

        install(Modules.override(new WhenBinder()).with(new OverridesGuiceWhenBinder()));

        bind(WhenVertx.class).to(DefaultWhenVertx.class).in(Singleton.class);
        bind(WhenEventBus.class).to(DefaultWhenEventBus.class).in(Singleton.class);
        bind(WhenHttpClient.class).to(DefaultWhenHttpClient.class).in(Singleton.class);

    }
}
