package com.englishtown.vertx.promises;

import com.englishtown.promises.Deferred;
import com.englishtown.promises.FulfilledRunnable;
import com.englishtown.promises.Promise;
import com.englishtown.promises.When;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Unit tests for {@link com.englishtown.vertx.promises.WhenStarter}
 */
@RunWith(MockitoJUnitRunner.class)
public class WhenStarterTest {

    WhenStarter starter;

    @Mock
    Vertx vertx;
    @Captor
    ArgumentCaptor<Handler<Void>> handlerCaptor;

    @Before
    public void setUp() throws Exception {
        starter = new WhenStarter(vertx);
    }

    @Test
    public void testRun() throws Exception {

        // Run the starter
        starter.run();

        When<String> when = new When<>();
        Deferred<String> d = when.defer();

        // Verify vertx hasn't been touched yet
        verifyZeroInteractions(vertx);

        // After a resolve operation, the nextTick should be executed on the vert.x event loop
        d.getResolver().resolve("ok");
        verify(vertx).runOnContext(handlerCaptor.capture());

        handlerCaptor.getValue().handle(null);

    }
}
