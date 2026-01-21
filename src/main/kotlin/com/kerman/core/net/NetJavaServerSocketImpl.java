package com.kerman.core.net;

import com.kerman.core.Net.Protocol;
import com.kerman.core.utils.KermanRuntimeException;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;

/**
 * Server socket implementation using java.net.ServerSocket.
 */
public class NetJavaServerSocketImpl implements ServerSocket {

    private final Protocol protocol;

    /**
     * Our server or null for disposed, aka closed.
     */
    private java.net.ServerSocket server;

    public NetJavaServerSocketImpl(Protocol protocol, int port, ServerSocketHints hints) {
        this(protocol, null, port, hints);
    }

    public NetJavaServerSocketImpl(Protocol protocol, String hostname, int port, ServerSocketHints hints) {
        this.protocol = protocol;

        // create the server socket
        try {
            // initialize
            server = new java.net.ServerSocket();
            if (hints != null) {
                server.setPerformancePreferences(hints.performancePrefConnectionTime, hints.performancePrefLatency,
                        hints.performancePrefBandwidth);
                server.setReuseAddress(hints.reuseAddress);
                server.setSoTimeout(hints.acceptTimeout);
                server.setReceiveBufferSize(hints.receiveBufferSize);
            }

            // and bind the server...
            InetSocketAddress address;
            if (hostname != null) {
                address = new InetSocketAddress(hostname, port);
            } else {
                address = new InetSocketAddress(port);
            }

            if (hints != null) {
                server.bind(address, hints.backlog);
            } else {
                server.bind(address);
            }
        } catch (Exception e) {
            throw new KermanRuntimeException("Cannot create a server socket at port " + port + ".", e);
        }
    }

    @NotNull
    @Override
    public Protocol getProtocol() {
        return protocol;
    }

    @NotNull
    @Override
    public Socket accept(@NotNull SocketHints hints) {
        try {
            return new NetJavaSocketImpl(server.accept(), hints);
        } catch (Exception e) {
            throw new KermanRuntimeException("Error accepting socket.", e);
        }
    }

    @Override
    public void dispose() {
        if (server != null) {
            try {
                server.close();
                server = null;
            } catch (Exception e) {
                throw new KermanRuntimeException("Error closing server.", e);
            }
        }
    }
}
