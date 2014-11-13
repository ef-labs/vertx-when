package com.englishtown.vertx.promises;

import com.englishtown.promises.Promise;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * Wraps the vert.x event bus using when.java promises rather than handlers
 */
public interface WhenEventBus {

    /**
     * Returns the underlying event bus being wrapped over
     *
     * @return
     */
    EventBus getEventBus();

    /**
     * Close the EventBus and release all resources.
     *
     * @return A promise for the event bus close completion
     */
    Promise<Void> close();

    /**
     * Send a message
     * @param address The address to send it to
     * @param message The message, may be {@code null}
     * @return A promise for a reply
     */
    <T> Promise<Message<T>> send(String address, Object message);

    /**
     * Send a message
     * @param address The address to send it to
     * @param message The message, may be {@code null}
     * @return A promise for a reply
     */
    <T> Promise<Message<T>> send(String address, Object message, DeliveryOptions options);

}
