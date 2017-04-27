package com.englishtown.vertx.promises;

import com.englishtown.promises.Promise;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.CaseInsensitiveHeaders;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Options for http client requests
 */
public class RequestOptions {

    private HttpClient client;
    private HttpClientOptions clientOptions;
    private Buffer data;
    private MultiMap headers;
    private boolean chunked;
    private long timeoutMs = -1;
    private int writeQueueMaxSize = -1;
    private boolean pauseResponse;
    private Function<HttpClientRequest, Promise<Void>> setupHandler;

    public RequestOptions setClient(HttpClient client) {
        this.client = client;
        return this;
    }

    public HttpClient getClient() {
        return client;
    }

    public RequestOptions setClientOptions(HttpClientOptions options) {
        this.clientOptions = options;
        return this;
    }

    public HttpClientOptions getClientOptions() {
        return clientOptions;
    }

    public RequestOptions setData(Buffer data) {
        this.data = data;
        return this;
    }

    public RequestOptions setData(String data) {
        this.data = data == null ? null : Buffer.buffer(data);
        return this;
    }

    public Buffer getData() {
        return data;
    }

    /**
     * Add a header to the request.  Can be called multiple times to add multiple headers
     *
     * @param name
     * @param value
     * @return
     */
    public RequestOptions addHeader(String name, String value) {
        if (headers == null) {
            headers = new CaseInsensitiveHeaders();
        }
        headers.add(name, value);
        return this;
    }


    /**
     * @deprecated: Use the {@link #setHeaders} overload using a {@link MultiMap} instead.
     */
    @Deprecated
    public RequestOptions setHeaders(Map<String, String> headers) {
        headers.forEach((k, v) -> this.headers.add(k, v));
        return this;
    }

    public RequestOptions setHeaders(MultiMap headers) {
        this.headers = headers;
        return this;
    }

    public MultiMap getMultiMapHeaders() {
        return this.headers;
    }

    /**
     * @deprecated: Use {@link #getMultiMapHeaders} instead.
     */
    @Deprecated
    public Map<String, String> getHeaders() {
        if (this.headers == null) {
            return null;
        }

        Map<String, String> map = new HashMap<>();
        this.headers.entries()
                .forEach(entry -> map.putIfAbsent(entry.getKey(), entry.getValue()));

        return map;
    }

    public RequestOptions setChunked(boolean chunked) {
        this.chunked = chunked;
        return this;
    }

    public boolean getChunked() {
        return this.chunked;
    }

    public RequestOptions setTimeout(long timeoutMs) {
        this.timeoutMs = timeoutMs;
        return this;
    }

    public long getTimeout() {
        return this.timeoutMs;
    }

    public RequestOptions setWriteQueueMaxSize(int maxSize) {
        this.writeQueueMaxSize = maxSize;
        return this;
    }

    public int getWriteQueueMaxSize() {
        return this.writeQueueMaxSize;
    }

    /**
     * Flag to pause the {@link io.vertx.core.http.HttpClientResponse}.  This must be called if you want to add a response or body handler when the promise resolves.
     *
     * @return
     */
    public RequestOptions setPauseResponse(boolean pause) {
        this.pauseResponse = pause;
        return this;
    }

    public boolean getPauseResponse() {
        return this.pauseResponse;
    }

    /**
     * Optional additional setupHandler function.  The client request is provided to allow additional initialization or writes.
     *
     * @param setup
     * @return
     */
    public RequestOptions setSetupHandler(Function<HttpClientRequest, Promise<Void>> setup) {
        this.setupHandler = setup;
        return this;
    }

    public Function<HttpClientRequest, Promise<Void>> getSetupHandler() {
        return this.setupHandler;
    }

}
