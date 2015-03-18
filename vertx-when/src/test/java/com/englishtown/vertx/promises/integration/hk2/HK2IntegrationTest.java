package com.englishtown.vertx.promises.integration.hk2;

import com.englishtown.promises.When;
import com.englishtown.vertx.hk2.HK2VertxBinder;
import com.englishtown.vertx.promises.WhenEventBus;
import com.englishtown.vertx.promises.WhenFileSystem;
import com.englishtown.vertx.promises.WhenHttpClient;
import com.englishtown.vertx.promises.WhenVertx;
import com.englishtown.vertx.promises.hk2.HK2WhenBinder;
import com.englishtown.vertx.promises.impl.DefaultWhenEventBus;
import com.englishtown.vertx.promises.impl.DefaultWhenHttpClient;
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
        whenVertx = locator.getService(WhenVertx.class);
        whenEventBus = locator.getService(WhenEventBus.class);
        whenHttpClient = locator.getService(WhenHttpClient.class);
        whenFileSystem = locator.getService(WhenFileSystem.class);

    }

}
