package com.englishtown.vertx.promises.guice;

import com.englishtown.vertx.promises.WhenContainer;
import com.englishtown.vertx.promises.WhenEventBus;
import com.englishtown.vertx.promises.WhenHttpClient;
import com.englishtown.vertx.promises.WhenPlatformManager;
import com.englishtown.vertx.promises.impl.*;
import com.google.inject.AbstractModule;

import javax.inject.Singleton;
import java.util.concurrent.Executor;

/**
 * Created by adriangonzalez on 9/11/14.
 */
public class GuiceWhenBinder extends AbstractModule {
    /**
     * Configures a {@link com.google.inject.Binder} via the exposed methods.
     */
    @Override
    protected void configure() {

        install(new com.englishtown.promises.guice.WhenBinder());

        bind(Executor.class).to(VertxExecutor.class).in(Singleton.class);

        bind(WhenPlatformManager.class).to(DefaultWhenPlatformManager.class);
        bind(WhenContainer.class).to(DefaultWhenContainer.class);
        bind(WhenEventBus.class).to(DefaultWhenEventBus.class);
        bind(WhenHttpClient.class).to(DefaultWhenHttpClient.class);

    }
}
