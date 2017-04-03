package com.englishtown.vertx.promises;

import com.englishtown.promises.Promise;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;

/**
 * When.java wrapper for vert.x {@link HttpClient} methods
 */
public interface WhenHttpClient {

    /**
     * Create an HTTP request to send to the server at the specified host and port
     *
     * @param method     the HTTP method
     * @param port       the port
     * @param host       the host
     * @param requestURI the relative URI
     * @return a promise for the HTTP client response object
     */
    Promise<HttpClientResponse> request(HttpMethod method, int port, String host, String requestURI);

    /**
     * Create an HTTP request to send to the server at the specified host and port
     *
     * @param method     the HTTP method
     * @param port       the port
     * @param host       the host
     * @param requestURI the relative URI
     * @param options    the additional request options
     * @return a promise for the HTTP client response object
     */
    Promise<HttpClientResponse> request(HttpMethod method, int port, String host, String requestURI, RequestOptions options);

    /**
     * Create an HTTP request to send to the server at the specified host and default port
     *
     * @param method     the HTTP method
     * @param host       the host
     * @param requestURI the relative URI
     * @return a promise for the HTTP client response object
     */
    Promise<HttpClientResponse> request(HttpMethod method, String host, String requestURI);

    /**
     * Create an HTTP request to send to the server at the specified host and default port
     *
     * @param method     the HTTP method
     * @param host       the host
     * @param requestURI the relative URI
     * @param options    the additional request options
     * @return a promise for the HTTP client response object
     */
    Promise<HttpClientResponse> request(HttpMethod method, String host, String requestURI, RequestOptions options);

    /**
     * Create an HTTP request to send to the server at the default host and port.
     *
     * @param method     the HTTP method
     * @param requestURI the relative URI
     * @return a promise for the HTTP client response object
     */
    Promise<HttpClientResponse> request(HttpMethod method, String requestURI);

    /**
     * Create an HTTP request to send to the server at the default host and port.
     *
     * @param method     the HTTP method
     * @param requestURI the relative URI
     * @param options    the additional request options
     * @return a promise for the HTTP client response object
     */
    Promise<HttpClientResponse> request(HttpMethod method, String requestURI, RequestOptions options);

    /**
     * Create an HTTP request to send to the server at the specified host and port and return the
     *
     * @param method     the HTTP method
     * @param port       the port
     * @param host       the host
     * @param requestURI the relative URI
     * @return a promise for the HTTP client response object
     */
    Promise<HttpClientResponseAndBody> requestAndReadBody(HttpMethod method, int port, String host, String requestURI);

    /**
     * Create an HTTP request to send to the server at the specified host and port
     *
     * @param method     the HTTP method
     * @param port       the port
     * @param host       the host
     * @param requestURI the relative URI
     * @param options    the additional request options
     * @return a promise for the HTTP client response object
     */
    Promise<HttpClientResponseAndBody> requestAndReadBody(HttpMethod method, int port, String host, String requestURI, RequestOptions options);

    /**
     * Create an HTTP request to send to the server at the specified host and default port
     *
     * @param method     the HTTP method
     * @param host       the host
     * @param requestURI the relative URI
     * @return a promise for the HTTP client response object
     */
    Promise<HttpClientResponseAndBody> requestAndReadBody(HttpMethod method, String host, String requestURI);

    /**
     * Create an HTTP request to send to the server at the specified host and default port
     *
     * @param method     the HTTP method
     * @param host       the host
     * @param requestURI the relative URI
     * @param options    the additional request options
     * @return a promise for the HTTP client response object
     */
    Promise<HttpClientResponseAndBody> requestAndReadBody(HttpMethod method, String host, String requestURI, RequestOptions options);

    /**
     * Create an HTTP request to send to the server at the default host and port.
     *
     * @param method     the HTTP method
     * @param requestURI the relative URI
     * @return a promise for the HTTP client response object
     */
    Promise<HttpClientResponseAndBody> requestAndReadBody(HttpMethod method, String requestURI);

    /**
     * Create an HTTP request to send to the server at the default host and port.
     *
     * @param method     the HTTP method
     * @param requestURI the relative URI
     * @param options    the additional request options
     * @return a promise for the HTTP client response object
     */
    Promise<HttpClientResponseAndBody> requestAndReadBody(HttpMethod method, String requestURI, RequestOptions options);

    /**
     * Create an HTTP request to send to the server using an absolute URI
     * the response
     *
     * @param method      the HTTP method
     * @param absoluteURI the absolute URI
     * @return a promise for the HTTP client response object
     */
    Promise<HttpClientResponse> requestAbs(HttpMethod method, String absoluteURI);

    /**
     * Create an HTTP request to send to the server using an absolute URI
     * the response
     *
     * @param method      the HTTP method
     * @param absoluteURI the absolute URI
     * @param options     the additional request options
     * @return a promise for the HTTP client response object
     */
    Promise<HttpClientResponse> requestAbs(HttpMethod method, String absoluteURI, RequestOptions options);

    /**
     * Create an HTTP request to send to the server using an absolute URI
     * the response
     *
     * @param method      the HTTP method
     * @param absoluteURI the absolute URI
     * @return a promise for the HTTP client response object
     */
    Promise<HttpClientResponseAndBody> requestAbsAndReadBody(HttpMethod method, String absoluteURI);

    /**
     * Create an HTTP request to send to the server using an absolute URI
     * the response
     *
     * @param method      the HTTP method
     * @param absoluteURI the absolute URI
     * @param options     the additional request options
     * @return a promise for the HTTP client response object
     */
    Promise<HttpClientResponseAndBody> requestAbsAndReadBody(HttpMethod method, String absoluteURI, RequestOptions options);

    /**
     * Takes a {@link io.vertx.core.http.HttpClientResponse} and returns a promise for the body.
     * NOTE: Make sure you use {@link com.englishtown.vertx.promises.RequestOptions} to pause the response
     *
     * @param response the paused HTTP client response object
     * @return a promise for the body buffer
     */
    Promise<Buffer> body(HttpClientResponse response);

}
