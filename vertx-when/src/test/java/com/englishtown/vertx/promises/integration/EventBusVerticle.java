package com.englishtown.vertx.promises.integration;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;

/**
 * Event bus verticle for integration tests
 */
public class EventBusVerticle extends AbstractVerticle implements Handler<Message<JsonObject>> {

    public static final String ADDRESS = "et.eb.test";

    private MessageConsumer<JsonObject> consumer;

    /**
     * Start the busmod
     */
    @Override
    public void start() throws Exception {
        super.start();

        consumer = vertx.eventBus().consumer(ADDRESS);
        consumer.handler(this);

    }

    @Override
    public void stop() throws Exception {
        consumer.unregister();
        super.stop();
    }

    /**
     * Something has happened, so handle it.
     */
    @Override
    public void handle(Message<JsonObject> message) {
        message.reply(new JsonObject().put("status", "ok"));
    }
}
