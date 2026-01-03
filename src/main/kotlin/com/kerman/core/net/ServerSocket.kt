package com.kerman.core.net

import com.kerman.core.Net
import com.kerman.core.utils.Disposable
import com.kerman.core.utils.KermanRuntimeException

/**
 * Info : This interface was inspired by "com.badlogic.gdx.net.ServerSocket".
 *
 * A server socket that accepts new incoming connections, returning [Socket] instances. The [.accept]
 * method should preferably be called in a separate thread as it is blocking.
 */
interface ServerSocket : Disposable {

    /**
     * @return the Protocol used by this socket
     */
    fun getProtocol(): Net.Protocol

    /**
     * Accepts a new incoming connection from a client [Socket]. The given hints will be applied to the accepted socket.
     * Blocking, call on a separate thread.
     *
     * @param hints additional [SocketHints] applied to the accepted [Socket]. Input null to use the default setting
     * provided by the system.
     * @return the accepted [Socket]
     * @throws KermanRuntimeException in case an error occurred
     */
    fun accept(hints: SocketHints): Socket
}
