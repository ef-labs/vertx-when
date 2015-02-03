package com.englishtown.vertx.promises.impl;

import io.vertx.core.Vertx;

import javax.inject.Inject;
import java.util.concurrent.Executor;

/**
 * When starter helper.  Sets the when nextTick to run on the vert.x event loop
 */
public class VertxExecutor implements Executor {

    private final Vertx vertx;

    @Inject
    public VertxExecutor(Vertx vertx) {
        this.vertx = vertx;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(Runnable command) {
        vertx.runOnContext(aVoid -> command.run());
    }
}
