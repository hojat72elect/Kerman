package com.kerman.core

import com.kerman.core.net.HttpStatus
import com.kerman.core.net.ServerSocket
import com.kerman.core.net.ServerSocketHints
import com.kerman.core.net.Socket
import com.kerman.core.net.SocketHints
import com.kerman.core.utils.KermanRuntimeException
import com.kerman.core.utils.Pool
import java.io.InputStream
import java.io.OutputStream

/**
 * Info : This interface was inspired by "com.badlogic.gdx.Net".
 *
 * Provides functions to perform networking operations, such as simple HTTP get and post requests, and TCP server/client socket
 * communication.
 *
 * To perform an HTTP request create a [HttpRequest] with the HTTP method (see [HttpMethods] for common methods) and
 * invoke [.sendHttpRequest] with it and a [HttpResponseListener]. After the HTTP
 * request was processed, the [HttpResponseListener] is called with a [HttpResponse] with the HTTP response values and
 * an status code to determine if the request was successful or not.
 *
 * To create a TCP client socket to communicate with a remote TCP server, invoke the
 * [.newClientSocket] method. The returned [Socket] offers an [InputStream]
 * and [OutputStream] to communicate with the end point.
 *
 * To create a TCP server socket that waits for incoming connections, invoke the
 * [.newServerSocket] method. The returned [ServerSocket] offers a
 * [ServerSocket.accept] method that waits for an incoming connection.
 */
interface Net {

    /**
     * Process the specified [HttpRequest] and reports the [HttpResponse] to the specified
     * [HttpResponseListener].
     *
     * @param httpRequest The [HttpRequest] to be performed.
     * @param httpResponseListener The [HttpResponseListener] to call once the HTTP response is ready to be processed. Could
     * be null, in that case no listener is called.
     */
    fun sendHttpRequest(httpRequest: HttpRequest, httpResponseListener: HttpResponseListener?)

    fun cancelHttpRequest(httpRequest: HttpRequest)

    fun isHttpRequestPending(httpRequest: HttpRequest): Boolean

    /**
     * Creates a new server socket on the given address and port, using the given [Protocol], waiting for incoming
     * connections.
     *
     * @param hostname the hostname or ip address to bind the socket to.
     * @param port  the port to listen on.
     * @param hints  additional [ServerSocketHints] used to create the socket. Input null to use the default setting provided by the system.
     * @return the [ServerSocket]
     * @throws KermanRuntimeException in case the socket couldn't be opened.
     */
    fun newServerSocket(protocol: Protocol, hostname: String, port: Int, hints: ServerSocketHints?): ServerSocket?

    /**
     * Creates a new server socket on the given port, using the given [Protocol], waiting for incoming connections.
     *
     * @param port the port to listen on.
     * @param hints additional [ServerSocketHints] used to create the socket. Input null to use the default setting provided by the system.
     * @return the [ServerSocket]
     * @throws KermanRuntimeException in case the socket couldn't be opened.
     */
    fun newServerSocket(protocol: Protocol, port: Int, hints: ServerSocketHints?): ServerSocket?

    /**
     * Creates a new TCP client socket that connects to the given host and port.
     *
     * @param host  the host address
     * @param port  the port
     * @param hints additional [SocketHints] used to create the socket. Input null to use the default setting provided by the system.
     * @throws KermanRuntimeException in case the socket couldn't be opened
     */
    fun newClientSocket(protocol: Protocol, host: String, port: Int, hints: SocketHints?): Socket?

    /**
     * Launches the default browser to display a URI. If the default browser is not able to handle the specified URI, the
     * application registered for handling URIs of the specified type is invoked. The application is determined from the protocol
     * and path of the URI. A best effort is made to open the given URI; however, since external applications are involved, no
     * guarantee can be made as to whether the URI was actually opened. If it is known that the URI was not opened, false will be
     * returned; otherwise, true will be returned.
     *
     * @param URI the URI to be opened.
     * @return false if it is known the uri was not opened, true otherwise.
     */
    fun openURI(URI: String): Boolean

    /**
     * Protocol used by [Net.newServerSocket] and
     * [Net.newClientSocket].
     */
    enum class Protocol {
        TCP
    }

    /**
     * HTTP response interface with methods to get the response data as a byte[], a [String] or an [InputStream].
     */
    interface HttpResponse {

        /**
         * Returns the data of the HTTP response as a byte[].
         * **Note**: This method may only be called once per response.
         * @return the result as a byte[] or null in case of a timeout or if the operation was canceled/terminated abnormally. The
         * timeout is specified when creating the HTTP request, with [HttpRequest.setTimeOut()]
         */
        fun getResult(): ByteArray?

        /**
         * Returns the data of the HTTP response as a [String].
         * **Note**: This method may only be called once per response.
         * @return the result as a string or null in case of a timeout or if the operation was canceled/terminated abnormally. The
         * timeout is specified when creating the HTTP request, with [HttpRequest.setTimeOut()]
         */
        fun getResultAsString(): String?

        /**
         * Returns the data of the HTTP response as an [InputStream].
         * **Warning:** Do not store a reference to this InputStream outside of
         * [HttpResponseListener.handleHttpResponse]. The underlying HTTP connection will be closed after that
         * callback finishes executing. Reading from the InputStream after its connection has been closed will lead to exception.
         *
         * @return An [InputStream] with the [HttpResponse] data.
         */
        fun getResultAsStream(): InputStream?

        /**
         * Returns the [HttpStatus] containing the statusCode of the HTTP response.
         */
        fun getStatus(): HttpStatus

        /**
         * Returns the value of the header with the given name as a [String], or null if the header is not set. See
         * [HttpResponseHeader].
         */
        fun getHeader(name: String): String?

        /**
         * Returns a Map of the headers. The keys are Strings that represent the header name. Each values is a List of Strings that
         * represent the corresponding header values. See [HttpResponseHeader].
         */
        fun getHeaders(): MutableMap<String, MutableList<String>>
    }

    /**
     * Provides common HTTP methods to use when creating a [HttpRequest].
     *
     *  * **HEAD** Asks for a response identical to that of a GET request but without the response body.
     *  * **GET** requests a representation of the specified resource. Requests using GET should only retrieve data.
     *  * **POST** is used to submit an entity to the specified resource, often causing a change in state or side effects on
     * the server.
     *  * **PUT** replaces all current representations of the target resource with the request payload.
     *  * **PATCH** method is used to apply partial modifications to a resource.
     *  * **DELETE** deletes the specified resource.
     */
    interface HttpMethods {
        companion object {
            /**
             * The HEAD method asks for a response identical to that of a GET request, but without the response body.
             */
            const val HEAD: String = "HEAD"

            /**
             * The GET method requests a representation of the specified resource. Requests using GET should only retrieve data.
             */
            const val GET: String = "GET"

            /**
             * The POST method is used to submit an entity to the specified resource, often causing a change in state or side effects
             * on the server.
             */
            const val POST: String = "POST"

            /**
             * The PUT method replaces all current representations of the target resource with the request payload.
             */
            const val PUT: String = "PUT"

            /**
             * The PATCH method is used to apply partial modifications to a resource.
             */
            const val PATCH: String = "PATCH"

            /**
             * The DELETE method deletes the specified resource.
             */
            const val DELETE: String = "DELETE"
        }
    }

    /**
     * Listener to be able to do custom logic once the [HttpResponse] is ready to be processed, register it with
     * [Net.sendHttpRequest].
     */
    interface HttpResponseListener {
        /**
         * Called when the [HttpRequest] has been processed and there is a [HttpResponse] ready. Passing data to the
         * rendering thread should be done using [Application.postRunnable] [HttpResponse]
         * contains the [HttpStatus] and should be used to determine if the request was successful or not (see more info at
         * [HttpStatus.getStatusCode]). For example:
         *
         * <pre>
         * HttpResponseListener listener = new HttpResponseListener() {
         * public void handleHttpResponse (HttpResponse httpResponse) {
         * HttpStatus status = httpResponse.getStatus();
         * if (status.getStatusCode() >= 200 && status.getStatusCode() < 300) {
         * // it was successful
         * } else {
         * // do something else
         * }
         * }
         * }
        </pre> *
         *
         * @param httpResponse The [HttpResponse] with the HTTP response values.
         */
        fun handleHttpResponse(httpResponse: HttpResponse)

        /**
         * Called if the [HttpRequest] failed because an exception when processing the HTTP request, could be a timeout any
         * other reason (not an HTTP error).
         *
         * @param t If the HTTP request failed because an Exception, t encapsulates it to give more information.
         */
        fun failed(t: Throwable?)

        fun cancelled()
    }

    /**
     * Contains getters and setters for the following parameters:
     *
     *  * **httpMethod:** GET or POST are most common, can use [HttpMethods][Net.HttpMethods] for static
     * references
     *  * **url:** the url
     *  * **headers:** a map of the headers, setter can be called multiple times
     *  * **timeout:** time spent trying to connect before giving up
     *  * **content:** A string containing the data to be used when processing the HTTP request.
     *
     * Abstracts the concept of a HTTP Request:
     *
     * <pre>
     * Map<String></String>, String> parameters = new HashMap<String></String>, String>();
     * parameters.put("user", "myuser");
     *
     * HttpRequest httpGet = new HttpRequest(HttpMethods.Get);
     * httpGet.setUrl("http://somewhere.net");
     * httpGet.setContent(HttpParametersUtils.convertHttpParameters(parameters));
     * ...
     * Gdx.net.sendHttpRequest (httpGet, new HttpResponseListener() {
     * public void handleHttpResponse(HttpResponse httpResponse) {
     * status = httpResponse.getResultAsString();
     * //do stuff here based on response
     * }
     *
     * public void failed(Throwable t) {
     * status = "failed";
     * //do stuff here based on the failed attempt
     * }
     * });
    </pre> *
     */
    class HttpRequest(httpMethod: String? = null) : Pool.Poolable {

        var method = httpMethod // the HTTP method of the HttpRequest.
        var url: String? = null // the URL of the HTTP request.
        private val headers = HashMap<String, String>()

        var timeOut = 0 // the number of milliseconds to wait before giving up, 0 or negative to block until the operation is done
        var content: String? = null // the content string to be used for the HTTP request.
        private var contentStream: InputStream? = null
        private var contentLength = 0L
        private var followRedirects = true
        var includeCredentials = false // whether a cross-origin request will include credentials. By default false.


        /**
         * Sets a header to this HTTP request, see [HttpRequestHeader].
         *
         * @param name  the name of the header.
         * @param value the value of the header.
         */
        fun setHeader(name: String, value: String) {
            headers[name] = value
        }

        /**
         * Sets the content as a stream to be used for a POST for example, to transmit custom data.
         *
         * @param contentStream The stream with the content data.
         */
        fun setContent(contentStream: InputStream, contentLength: Long) {
            this.contentStream = contentStream
            this.contentLength = contentLength
        }

        /**
         * Returns the content stream.
         */
        fun getContentStream() = contentStream

        /**
         * Returns the content length in case content is a stream.
         */
        fun getContentLength() = contentLength

        /**
         * Returns a Map<String></String>, String> with the headers of the HTTP request.
         */
        fun getHeaders() = headers

        /**
         * Returns whether 301 and 302 redirects are followed. By default true. Whether to follow redirects.
         */
        fun getFollowRedirects() = followRedirects

        /**
         * Sets whether 301 and 302 redirects are followed. By default true.
         *
         * @param followRedirects whether to follow redirects.
         * @throws IllegalArgumentException if redirection is disabled.
         */
        @Throws(IllegalArgumentException::class)
        fun setFollowRedirects(followRedirects: Boolean) {
            if (followRedirects || Kerman.app.getType() != Application.ApplicationType.WebGL) {
                this.followRedirects = followRedirects
            } else {
                throw IllegalArgumentException("Following redirects can't be disabled!")
            }
        }

        override fun reset() {
            this.method = null
            url = null
            headers.clear()
            timeOut = 0

            content = null
            contentStream = null
            contentLength = 0

            followRedirects = true
        }
    }
}
