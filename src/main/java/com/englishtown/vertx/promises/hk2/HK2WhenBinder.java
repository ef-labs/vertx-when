package com.englishtown.vertx.promises.hk2;

import com.englishtown.vertx.promises.WhenContainer;
import com.englishtown.vertx.promises.WhenEventBus;
import com.englishtown.vertx.promises.WhenHttpClient;
import com.englishtown.vertx.promises.WhenPlatformManager;
import com.englishtown.vertx.promises.impl.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;
import java.util.concurrent.Executor;

/**
 * HK2 binder for vertx-mod-when
 */
public class HK2WhenBinder extends AbstractBinder {
    /**
     * Implement to provide binding definitions using the exposed binding
     * methods.
     */
    @Override
    protected void configure() {

        install(new com.englishtown.promises.hk2.WhenBinder());

        bind(VertxExecutor.class).to(Executor.class).in(Singleton.class).ranked(10);

        bind(DefaultWhenPlatformManager.class).to(WhenPlatformManager.class);
        bind(DefaultWhenContainer.class).to(WhenContainer.class);
        bind(DefaultWhenEventBus.class).to(WhenEventBus.class);
        bind(DefaultWhenHttpClient.class).to(WhenHttpClient.class);

    }
}