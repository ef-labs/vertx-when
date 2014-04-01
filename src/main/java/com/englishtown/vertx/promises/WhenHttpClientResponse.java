package com.englishtown.vertx.promises;

import com.englishtown.promises.Promise;
import org.vertx.java.core.http.HttpClientResponse;

/**
 * @deprecated Use {@link com.englishtown.vertx.promises.WhenHttpClient} requestResponseBody() instead.
 */
@Deprecated
public interface WhenHttpClientResponse {

    /**
     * @param promise
     * @return
     * @deprecated Use {@link com.englishtown.vertx.promises.WhenHttpClient} requestResponseBody() instead.
     */
    @Deprecated()
    Promise<HttpClientResponseAndBody> body(Promise<HttpClientResponse> promise);

}
