package com.englishtown.vertx.promises;

import com.englishtown.promises.Promise;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;

/**
 *
 */
public interface WhenEventBus {

    Promise<Message, Void> send(String address, Object message);

    <T> Promise<Message<T>, Void> send(String address, JsonObject message);

    <T> Promise<Message<T>, Void> send(String address, JsonArray message);

    <T> Promise<Message<T>, Void> send(String address, Buffer message);

    <T> Promise<Message<T>, Void> send(String address, byte[] message);

    <T> Promise<Message<T>, Void> send(String address, String message);

    <T> Promise<Message<T>, Void> send(String address, Integer message);

    <T> Promise<Message<T>, Void> send(String address, Long message);

    <T> Promise<Message<T>, Void> send(String address, Float message);

    <T> Promise<Message<T>, Void> send(String address, Double message);

    <T> Promise<Message<T>, Void> send(String address, Boolean message);

    <T> Promise<Message<T>, Void> send(String address, Short message);

    <T> Promise<Message<T>, Void> send(String address, Character message);

    <T> Promise<Message<T>, Void> send(String address, Byte message);

    Promise<Void, Void> unregisterHandler(String address, Handler<? extends Message> handler);

    Promise<Void, Void> registerHandler(String address, Handler<? extends Message> handler);

}
