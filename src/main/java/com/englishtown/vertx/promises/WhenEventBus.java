package com.englishtown.vertx.promises;

import com.englishtown.promises.Promise;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

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
     * Send a message
     *
     * @param address The address to send it to
     * @param message The message
     */
    Promise<Message> send(String address, Object message);

    /**
     * Send an object as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    <T> Promise<Message<T>> sendWithTimeout(String address, Object message, long timeout);

    /**
     * Send a JSON object as a message
     *
     * @param address The address to send it to
     * @param message The message
     */
    <T> Promise<Message<T>> send(String address, JsonObject message);

    /**
     * Send a JSON object as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    <T> Promise<Message<T>> sendWithTimeout(String address, JsonObject message, long timeout);

    /**
     * Send a JSON array as a message
     *
     * @param address The address to send it to
     * @param message The message
     */
    <T> Promise<Message<T>> send(String address, JsonArray message);

    /**
     * Send a JSON array as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    <T> Promise<Message<T>> sendWithTimeout(String address, JsonArray message, long timeout);

    /**
     * Send a Buffer as a message
     *
     * @param address The address to send it to
     * @param message The message
     */
    <T> Promise<Message<T>> send(String address, Buffer message);

    /**
     * Send a Buffer object as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    <T> Promise<Message<T>> sendWithTimeout(String address, Buffer message, long timeout);

    /**
     * Send a byte[] as a message
     *
     * @param address The address to send it to
     * @param message The message
     */
    <T> Promise<Message<T>> send(String address, byte[] message);

    /**
     * Send a byte[] object as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    <T> Promise<Message<T>> sendWithTimeout(String address, byte[] message, long timeout);

    /**
     * Send a String as a message
     *
     * @param address The address to send it to
     * @param message The message
     */
    <T> Promise<Message<T>> send(String address, String message);

    /**
     * Send a string object as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    <T> Promise<Message<T>> sendWithTimeout(String address, String message, long timeout);

    /**
     * Send an Integer as a message
     *
     * @param address The address to send it to
     * @param message The message
     */
    <T> Promise<Message<T>> send(String address, Integer message);

    /**
     * Send an Integer as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    <T> Promise<Message<T>> sendWithTimeout(String address, Integer message, long timeout);

    /**
     * Send a Long as a message
     *
     * @param address The address to send it to
     * @param message The message
     */
    <T> Promise<Message<T>> send(String address, Long message);

    /**
     * Send a long as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    <T> Promise<Message<T>> sendWithTimeout(String address, Long message, long timeout);

    /**
     * Send a Float as a message
     *
     * @param address The address to send it to
     * @param message The message
     */
    <T> Promise<Message<T>> send(String address, Float message);

    /**
     * Send a float as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    <T> Promise<Message<T>> sendWithTimeout(String address, Float message, long timeout);

    /**
     * Send a Double as a message
     *
     * @param address The address to send it to
     * @param message The message
     */
    <T> Promise<Message<T>> send(String address, Double message);

    /**
     * Send a double as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    <T> Promise<Message<T>> sendWithTimeout(String address, Double message, long timeout);

    /**
     * Send a Boolean as a message
     *
     * @param address The address to send it to
     * @param message The message
     */
    <T> Promise<Message<T>> send(String address, Boolean message);

    /**
     * Send a boolean as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    <T> Promise<Message<T>> sendWithTimeout(String address, Boolean message, long timeout);

    /**
     * Send a Short as a message
     *
     * @param address The address to send it to
     * @param message The message
     */
    <T> Promise<Message<T>> send(String address, Short message);

    /**
     * Send a short as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    <T> Promise<Message<T>> sendWithTimeout(String address, Short message, long timeout);

    /**
     * Send a Character as a message
     *
     * @param address The address to send it to
     * @param message The message
     */
    <T> Promise<Message<T>> send(String address, Character message);

    /**
     * Send a character as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    <T> Promise<Message<T>> sendWithTimeout(String address, Character message, long timeout);

    /**
     * Send a Byte as a message
     *
     * @param address The address to send it to
     * @param message The message
     */
    <T> Promise<Message<T>> send(String address, Byte message);

    /**
     * Send a byte as a message
     *
     * @param address The address to send it to
     * @param message The message
     * @param timeout - Timeout in ms. If no reply received within the timeout then the reply handler will be unregistered
     */
    <T> Promise<Message<T>> sendWithTimeout(String address, Byte message, long timeout);

    /**
     * Unregisters a handler given the address and the handler
     *
     * @param address The address the handler was registered at
     * @param handler The handler
     */
    Promise<Void> unregisterHandler(String address, Handler<? extends Message> handler);

    /**
     * Registers a handler against the specified address
     *
     * @param address The address to register it at
     * @param handler The handler
     */
    Promise<Void> registerHandler(String address, Handler<? extends Message> handler);

}
