package com.englishtown.vertx.promises.hk2;

import com.englishtown.vertx.promises.*;
import com.englishtown.vertx.promises.impl.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;
import java.util.concurrent.Executor;

/**
 * HK2 binder for vertx-when
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

        bind(DefaultWhenVertx.class).to(WhenVertx.class).in(Singleton.class);
        bind(DefaultWhenEventBus.class).to(WhenEventBus.class).in(Singleton.class);
        bind(DefaultWhenHttpClient.class).to(WhenHttpClient.class).in(Singleton.class);
        bind(DefaultWhenFileSystem.class).to(WhenFileSystem.class).in(Singleton.class);
        bind(DefaultPromiseAdapter.class).to(PromiseAdapter.class).in(Singleton.class);

    }
}
