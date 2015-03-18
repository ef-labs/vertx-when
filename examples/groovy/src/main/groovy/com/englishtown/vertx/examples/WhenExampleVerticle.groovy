package com.englishtown.vertx.examples

import com.englishtown.promises.When
import com.englishtown.promises.WhenFactory
import com.englishtown.vertx.promises.WhenEventBus
import com.englishtown.vertx.promises.WhenHttpClient
import com.englishtown.vertx.promises.WhenVertx
import com.englishtown.vertx.promises.impl.DefaultWhenEventBus
import com.englishtown.vertx.promises.impl.DefaultWhenHttpClient
import com.englishtown.vertx.promises.impl.DefaultWhenVertx
import com.englishtown.vertx.promises.impl.VertxExecutor
import io.vertx.lang.groovy.GroovyVerticle

/**
 * Groovy vertx-when example verticle
 */
public class WhenExampleVerticle extends GroovyVerticle {

    private When when;
    private WhenVertx whenVertx;
    private WhenEventBus whenEventBus;
    private WhenHttpClient whenHttpClient;

    public static final String ADDRESS = "when.eb.example";

    @Override
    void start() throws Exception {

        initWhen();

        vertx.eventBus().consumer(ADDRESS, { msg -> msg.reply("pong") });
        vertx.createHttpServer([host: "localhost", port: 8080]).requestHandler({ req -> req.response().end("pong") }).listen()

        super.start()
    }

    private void initWhen() {

        // Get a reference the the java io.vertx.core.Vertx instance
        io.vertx.core.Vertx jVertx = vertx.getDelegate() as io.vertx.core.Vertx;

        // Use the vert.x executor to queue callbacks on the vert.x event loop
        when = WhenFactory.createFor({ new VertxExecutor(jVertx) })

        // Instantiate when.java vert.x wrappers
        whenVertx = new DefaultWhenVertx(jVertx, when);
        whenEventBus = new DefaultWhenEventBus(jVertx, when);
        whenHttpClient = new DefaultWhenHttpClient(jVertx, when);

    }
}
