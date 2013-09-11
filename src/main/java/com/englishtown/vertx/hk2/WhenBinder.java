package com.englishtown.vertx.hk2;

import com.englishtown.vertx.promises.WhenPlatformManager;
import com.englishtown.vertx.promises.WhenContainer;
import com.englishtown.vertx.promises.WhenEventBus;
import com.englishtown.vertx.promises.WhenHttpClient;
import com.englishtown.vertx.promises.WhenHttpClientResponse;
import com.englishtown.vertx.promises.impl.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * HK2 binder for vertx-mod-when
 */
public class WhenBinder extends AbstractBinder {
    /**
     * Implement to provide binding definitions using the exposed binding
     * methods.
     */
    @Override
    protected void configure() {

        bind(DefaultWhenPlatformManager.class).to(WhenPlatformManager.class);
        bind(DefaultWhenContainer.class).to(WhenContainer.class);
        bind(DefaultWhenEventBus.class).to(WhenEventBus.class);
        bind(DefaultWhenHttpClient.class).to(WhenHttpClient.class);
        bind(DefaultWhenHttpClientResponse.class).to(WhenHttpClientResponse.class);

    }
}
