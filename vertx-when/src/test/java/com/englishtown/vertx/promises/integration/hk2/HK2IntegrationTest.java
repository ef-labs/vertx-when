package com.englishtown.vertx.promises.integration.hk2;

import com.englishtown.promises.When;
import com.englishtown.vertx.hk2.HK2VertxBinder;
import com.englishtown.vertx.promises.hk2.HK2WhenBinder;
import com.englishtown.vertx.promises.impl.DefaultWhenEventBus;
import com.englishtown.vertx.promises.impl.DefaultWhenHttpClient;
import com.englishtown.vertx.promises.impl.DefaultWhenVertx;
import com.englishtown.vertx.promises.integration.IntegrationTestBase;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

/**
 * Integration tests
 */
public class HK2IntegrationTest extends IntegrationTestBase {

    @Override
    public void setUp() throws Exception {
        super.setUp();

        ServiceLocator locator = ServiceLocatorFactory.getInstance().create(null);
        ServiceLocatorUtilities.bind(locator, new HK2WhenBinder(), new HK2VertxBinder(vertx));

        when = locator.getService(When.class);
        whenVertx = new DefaultWhenVertx(vertx, when);
        whenEventBus = new DefaultWhenEventBus(vertx, when);
        whenHttpClient = new DefaultWhenHttpClient(vertx, when);

    }

}
