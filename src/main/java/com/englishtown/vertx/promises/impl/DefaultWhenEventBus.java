package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Deferred;
import com.englishtown.promises.Promise;
import com.englishtown.promises.When;
import com.englishtown.vertx.promises.WhenEventBus;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Container;

import javax.inject.Inject;

/**
 * Default implementation of {@link WhenEventBus}
 */
public class DefaultWhenEventBus implements WhenEventBus {

    private final EventBus eventBus;
    private final When when;

    /**
     * Takes the event bus off the provided vert.x instance
     *
     * @param vertx     the vertx instance with the event bus to wrap
     * @param container the container instance
     */
    @Inject
    public DefaultWhenEventBus(Vertx vertx, Container container, When when) {
        this(vertx.eventBus(), when);
    }

    /**
     * Directly provide the event bus instance to use
     *
     * @param eventBus the event bus instance to wrap
     */
    public DefaultWhenEventBus(EventBus eventBus, When when) {
        this.eventBus = eventBus;
        this.when = when;
    }

    /**
     * Returns the underlying event bus being wrapped over
     *
     * @return
     */
    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public Promise<Message> send(String address, Object message) {
        final Deferred<Message> d = when.defer();
        eventBus.send(address, message, reply -> d.resolve(reply));
        return d.getPromise();
    }

    /**
     * Send an object as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    @Override
    public <T> Promise<Message<T>> sendWithTimeout(String address, Object message, long timeout) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.sendWithTimeout(address, message, timeout, (AsyncResult<Message<T>> result) -> {
            if (result.succeeded()) {
                d.resolve(result.result());
            } else {
                d.reject(result.cause());
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>> send(String address, JsonObject message) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.send(address, message, (Message<T> reply) -> d.resolve(reply));
        return d.getPromise();
    }

    /**
     * Send a JSON object as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    @Override
    public <T> Promise<Message<T>> sendWithTimeout(String address, JsonObject message, long timeout) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.sendWithTimeout(address, message, timeout, (AsyncResult<Message<T>> result) -> {
            if (result.succeeded()) {
                d.resolve(result.result());
            } else {
                d.reject(result.cause());
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>> send(String address, JsonArray message) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.send(address, message, (Message<T> reply) -> d.resolve(reply));
        return d.getPromise();
    }

    /**
     * Send a JSON array as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    @Override
    public <T> Promise<Message<T>> sendWithTimeout(String address, JsonArray message, long timeout) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.sendWithTimeout(address, message, timeout, (AsyncResult<Message<T>> result) -> {
            if (result.succeeded()) {
                d.resolve(result.result());
            } else {
                d.reject(result.cause());
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>> send(String address, Buffer message) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.send(address, message, (Message<T> reply) -> d.resolve(reply));
        return d.getPromise();
    }

    /**
     * Send a Buffer object as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    @Override
    public <T> Promise<Message<T>> sendWithTimeout(String address, Buffer message, long timeout) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.sendWithTimeout(address, message, timeout, (AsyncResult<Message<T>> result) -> {
            if (result.succeeded()) {
                d.resolve(result.result());
            } else {
                d.reject(result.cause());
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>> send(String address, byte[] message) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.send(address, message, (Message<T> reply) -> d.resolve(reply));
        return d.getPromise();
    }

    /**
     * Send a byte[] object as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    @Override
    public <T> Promise<Message<T>> sendWithTimeout(String address, byte[] message, long timeout) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.sendWithTimeout(address, message, timeout, new Handler<AsyncResult<Message<T>>>() {
            @Override
            public void handle(AsyncResult<Message<T>> result) {
                if (result.succeeded()) {
                    d.resolve(result.result());
                } else {
                    d.reject(result.cause());
                }
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>> send(String address, String message) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.send(address, message, (Message<T> reply) -> d.resolve(reply));
        return d.getPromise();
    }

    /**
     * Send a string object as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    @Override
    public <T> Promise<Message<T>> sendWithTimeout(String address, String message, long timeout) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.sendWithTimeout(address, message, timeout, (AsyncResult<Message<T>> result) -> {
            if (result.succeeded()) {
                d.resolve(result.result());
            } else {
                d.reject(result.cause());
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>> send(String address, Integer message) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.send(address, message, (Message<T> reply) -> d.resolve(reply));
        return d.getPromise();
    }

    /**
     * Send an Integer as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    @Override
    public <T> Promise<Message<T>> sendWithTimeout(String address, Integer message, long timeout) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.sendWithTimeout(address, message, timeout, (AsyncResult<Message<T>> result) -> {
            if (result.succeeded()) {
                d.resolve(result.result());
            } else {
                d.reject(result.cause());
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>> send(String address, Long message) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.send(address, message, (Message<T> reply) -> d.resolve(reply));
        return d.getPromise();
    }

    /**
     * Send a long as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    @Override
    public <T> Promise<Message<T>> sendWithTimeout(String address, Long message, long timeout) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.sendWithTimeout(address, message, timeout, (AsyncResult<Message<T>> result) -> {
            if (result.succeeded()) {
                d.resolve(result.result());
            } else {
                d.reject(result.cause());
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>> send(String address, Float message) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.send(address, message, (Message<T> reply) -> d.resolve(reply));
        return d.getPromise();
    }

    /**
     * Send a float as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    @Override
    public <T> Promise<Message<T>> sendWithTimeout(String address, Float message, long timeout) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.sendWithTimeout(address, message, timeout, (AsyncResult<Message<T>> result) -> {
            if (result.succeeded()) {
                d.resolve(result.result());
            } else {
                d.reject(result.cause());
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>> send(String address, Double message) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.send(address, message, (Message<T> reply) -> d.resolve(reply));
        return d.getPromise();
    }

    /**
     * Send a double as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    @Override
    public <T> Promise<Message<T>> sendWithTimeout(String address, Double message, long timeout) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.sendWithTimeout(address, message, timeout, (AsyncResult<Message<T>> result) -> {
            if (result.succeeded()) {
                d.resolve(result.result());
            } else {
                d.reject(result.cause());
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>> send(String address, Boolean message) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.send(address, message, (Message<T> reply) -> d.resolve(reply));
        return d.getPromise();
    }

    /**
     * Send a boolean as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    @Override
    public <T> Promise<Message<T>> sendWithTimeout(String address, Boolean message, long timeout) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.sendWithTimeout(address, message, timeout, (AsyncResult<Message<T>> result) -> {
            if (result.succeeded()) {
                d.resolve(result.result());
            } else {
                d.reject(result.cause());
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>> send(String address, Short message) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.send(address, message, (Message<T> reply) -> d.resolve(reply));
        return d.getPromise();
    }

    /**
     * Send a short as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    @Override
    public <T> Promise<Message<T>> sendWithTimeout(String address, Short message, long timeout) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.sendWithTimeout(address, message, timeout, (AsyncResult<Message<T>> result) -> {
            if (result.succeeded()) {
                d.resolve(result.result());
            } else {
                d.reject(result.cause());
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>> send(String address, Character message) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.send(address, message, (Message<T> reply) -> d.resolve(reply));
        return d.getPromise();
    }

    /**
     * Send a character as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    @Override
    public <T> Promise<Message<T>> sendWithTimeout(String address, Character message, long timeout) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.sendWithTimeout(address, message, timeout, (AsyncResult<Message<T>> result) -> {
            if (result.succeeded()) {
                d.resolve(result.result());
            } else {
                d.reject(result.cause());
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>> send(String address, Byte message) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.send(address, message, (Message<T> reply) -> d.resolve(reply));
        return d.getPromise();
    }

    /**
     * Send a byte as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    @Override
    public <T> Promise<Message<T>> sendWithTimeout(String address, Byte message, long timeout) {
        final Deferred<Message<T>> d = when.defer();
        eventBus.sendWithTimeout(address, message, timeout, (AsyncResult<Message<T>> result) -> {
            if (result.succeeded()) {
                d.resolve(result.result());
            } else {
                d.reject(result.cause());
            }
        });
        return d.getPromise();
    }

    @Override
    public Promise<Void> unregisterHandler(String address, Handler<? extends Message> handler) {
        final Deferred<Void> d = when.defer();
        eventBus.unregisterHandler(address, handler, result -> {
            if (result.succeeded()) {
                d.resolve((Void) null);
            } else {
                d.reject(result.cause());
            }
        });
        return d.getPromise();
    }

    @Override
    public Promise<Void> registerHandler(String address, Handler<? extends Message> handler) {
        final Deferred<Void> d = when.defer();
        eventBus.registerHandler(address, handler, (AsyncResult<Void> result) -> {
            if (result.succeeded()) {
                d.resolve((Void) null);
            } else {
                d.reject(result.cause());
            }
        });
        return d.getPromise();
    }

}
