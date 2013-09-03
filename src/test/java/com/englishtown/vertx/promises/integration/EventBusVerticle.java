package com.englishtown.vertx.promises.integration;

import org.vertx.java.busmods.BusModBase;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

/**
 * Event bus verticle for integration tests
 */
public class EventBusVerticle extends BusModBase implements Handler<Message<JsonObject>> {

    public static final String ADDRESS = "et.eb.test";

    /**
     * Start the busmod
     */
    @Override
    public void start() {
        super.start();
        eb.registerHandler(ADDRESS, this);
    }

    /**
     * Something has happened, so handle it.
     */
    @Override
    public void handle(Message<JsonObject> message) {
        sendOK(message);
    }
}
