package com.englishtown.vertx.promises.guice;

import com.englishtown.vertx.promises.impl.*;
import com.google.inject.AbstractModule;

import javax.inject.Singleton;
import java.util.concurrent.Executor;

/**
 * Internal binder for vertx-when that overrides the when.java binder
 */
class OverridesGuiceWhenBinder extends AbstractModule {
    /**
     * Configures a {@link com.google.inject.Binder} via the exposed methods.
     */
    @Override
    protected void configure() {

        bind(Executor.class).to(VertxExecutor.class).in(Singleton.class);

    }
}
