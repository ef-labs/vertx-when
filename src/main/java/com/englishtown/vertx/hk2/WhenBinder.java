package com.englishtown.vertx.hk2;

import com.englishtown.vertx.promises.*;
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
        bind(WhenStarter.class).to(WhenStarter.class);

    }
}
