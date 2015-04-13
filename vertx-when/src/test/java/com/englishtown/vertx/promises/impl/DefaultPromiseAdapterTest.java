package com.englishtown.vertx.promises.impl;

import com.englishtown.promises.Deferred;
import com.englishtown.promises.Resolver;
import com.englishtown.promises.When;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Consumer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link DefaultPromiseAdapter}
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultPromiseAdapterTest {

    private DefaultPromiseAdapter adapter;
    private String success = "success!";
    private Throwable cause = new Throwable();

    @Mock
    private When when;
    @Mock
    private AsyncResult<String> result;
    @Mock
    private Deferred<String> deferred;
    @Mock
    private Resolver<String> resolver;
    @Mock
    private Consumer<Handler<AsyncResult<String>>> consumer;
    @Mock
    private Future<String> future;

    @Before
    public void setUp() throws Exception {

        Mockito.when(result.succeeded()).thenReturn(true).thenReturn(false);
        Mockito.when(result.result()).thenReturn(success);
        Mockito.when(result.cause()).thenReturn(cause);

        Mockito.when(when.<String>defer()).thenReturn(deferred);

        adapter = new DefaultPromiseAdapter(when);

    }

    @Test
    public void testToHandler() throws Exception {

        Handler<AsyncResult<String>> handler = adapter.toHandler(resolver);

        handler.handle(result);
        verify(resolver).resolve(eq(success));

        handler.handle(result);
        verify(resolver).reject(eq(cause));

    }

    @Test
    public void testToPromise() throws Exception {

        adapter.toPromise(consumer);
        verify(when).defer();
        verify(consumer).accept(any());

    }

    @Test
    public void testToPromise1() throws Exception {

        adapter.toPromise(future);
        future.complete();

        verify(when).defer();
        verify(future).setHandler(any());

    }
}