package com.englishtown.vertx.promises;

import com.englishtown.promises.WhenProgress;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;

import javax.inject.Inject;
import java.util.concurrent.Executor;

/**
 * When starter helper.  Sets the when nextTick to run on the vert.x event loop
 */
public class WhenStarter {

    private final Vertx vertx;

    @Inject
    public WhenStarter(Vertx vertx) {
        this.vertx = vertx;
    }

    public void run() {

        WhenProgress.setNextTick(new Executor() {
            @Override
            public void execute(final Runnable command) {
                vertx.runOnContext(new Handler<Void>() {
                    @Override
                    public void handle(Void aVoid) {
                        command.run();
                    }
                });
            }
        });

    }

}
