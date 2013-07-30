package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Deferred;
import com.englishtown.promises.Promise;
import com.englishtown.promises.Value;
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
    private final Vertx vertx;
    private final Container container;

    @Inject
    public DefaultWhenEventBus(Vertx vertx, Container container) {
        this.vertx = vertx;
        this.container = container;
        eventBus = vertx.eventBus();
    }

    @Override
    public Promise<Message, Void> send(String address, Object message) {
        final Deferred<Message, Void> d = new When<Message, Void>().defer();
        eventBus.send(address, message, new Handler<Message>() {
            @Override
            public void handle(Message reply) {
                d.getResolver().resolve(reply);
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>, Void> send(String address, JsonObject message) {
        final Deferred<Message<T>, Void> d = new When<Message<T>, Void>().defer();
        eventBus.send(address, message, new Handler<Message<T>>() {
            @Override
            public void handle(Message<T> reply) {
                d.getResolver().resolve(reply);
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>, Void> send(String address, JsonArray message) {
        final Deferred<Message<T>, Void> d = new When<Message<T>, Void>().defer();
        eventBus.send(address, message, new Handler<Message<T>>() {
            @Override
            public void handle(Message<T> reply) {
                d.getResolver().resolve(reply);
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>, Void> send(String address, Buffer message) {
        final Deferred<Message<T>, Void> d = new When<Message<T>, Void>().defer();
        eventBus.send(address, message, new Handler<Message<T>>() {
            @Override
            public void handle(Message<T> reply) {
                d.getResolver().resolve(reply);
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>, Void> send(String address, byte[] message) {
        final Deferred<Message<T>, Void> d = new When<Message<T>, Void>().defer();
        eventBus.send(address, message, new Handler<Message<T>>() {
            @Override
            public void handle(Message<T> reply) {
                d.getResolver().resolve(reply);
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>, Void> send(String address, String message) {
        final Deferred<Message<T>, Void> d = new When<Message<T>, Void>().defer();
        eventBus.send(address, message, new Handler<Message<T>>() {
            @Override
            public void handle(Message<T> reply) {
                d.getResolver().resolve(reply);
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>, Void> send(String address, Integer message) {
        final Deferred<Message<T>, Void> d = new When<Message<T>, Void>().defer();
        eventBus.send(address, message, new Handler<Message<T>>() {
            @Override
            public void handle(Message<T> reply) {
                d.getResolver().resolve(reply);
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>, Void> send(String address, Long message) {
        final Deferred<Message<T>, Void> d = new When<Message<T>, Void>().defer();
        eventBus.send(address, message, new Handler<Message<T>>() {
            @Override
            public void handle(Message<T> reply) {
                d.getResolver().resolve(reply);
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>, Void> send(String address, Float message) {
        final Deferred<Message<T>, Void> d = new When<Message<T>, Void>().defer();
        eventBus.send(address, message, new Handler<Message<T>>() {
            @Override
            public void handle(Message<T> reply) {
                d.getResolver().resolve(reply);
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>, Void> send(String address, Double message) {
        final Deferred<Message<T>, Void> d = new When<Message<T>, Void>().defer();
        eventBus.send(address, message, new Handler<Message<T>>() {
            @Override
            public void handle(Message<T> reply) {
                d.getResolver().resolve(reply);
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>, Void> send(String address, Boolean message) {
        final Deferred<Message<T>, Void> d = new When<Message<T>, Void>().defer();
        eventBus.send(address, message, new Handler<Message<T>>() {
            @Override
            public void handle(Message<T> reply) {
                d.getResolver().resolve(reply);
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>, Void> send(String address, Short message) {
        final Deferred<Message<T>, Void> d = new When<Message<T>, Void>().defer();
        eventBus.send(address, message, new Handler<Message<T>>() {
            @Override
            public void handle(Message<T> reply) {
                d.getResolver().resolve(reply);
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>, Void> send(String address, Character message) {
        final Deferred<Message<T>, Void> d = new When<Message<T>, Void>().defer();
        eventBus.send(address, message, new Handler<Message<T>>() {
            @Override
            public void handle(Message<T> reply) {
                d.getResolver().resolve(reply);
            }
        });
        return d.getPromise();
    }

    @Override
    public <T> Promise<Message<T>, Void> send(String address, Byte message) {
        final Deferred<Message<T>, Void> d = new When<Message<T>, Void>().defer();
        eventBus.send(address, message, new Handler<Message<T>>() {
            @Override
            public void handle(Message<T> reply) {
                d.getResolver().resolve(reply);
            }
        });
        return d.getPromise();
    }

    @Override
    public Promise<Void, Void> unregisterHandler(String address, Handler<? extends Message> handler) {
        final Deferred<Void, Void> d = new When<Void, Void>().defer();
        eventBus.unregisterHandler(address, handler, new Handler<AsyncResult<Void>>() {
            @Override
            public void handle(AsyncResult<Void> result) {
                if (result.succeeded()) {
                    d.getResolver().resolve((Void) null);
                } else {
                    d.getResolver().reject(new Value<Void>(null, new RuntimeException(result.cause())));
                }
            }
        });
        return d.getPromise();
    }

    @Override
    public Promise<Void, Void> registerHandler(String address, Handler<? extends Message> handler) {
        final Deferred<Void, Void> d = new When<Void, Void>().defer();
        eventBus.registerHandler(address, handler, new Handler<AsyncResult<Void>>() {
            @Override
            public void handle(AsyncResult<Void> result) {
                if (result.succeeded()) {
                    d.getResolver().resolve((Void) null);
                } else {
                    d.getResolver().reject(new Value<Void>(null, new RuntimeException(result.cause())));
                }
            }
        });
        return d.getPromise();
    }

}
