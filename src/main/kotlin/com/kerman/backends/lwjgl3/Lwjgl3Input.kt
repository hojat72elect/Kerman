package com.kerman.backends.lwjgl3

import com.kerman.core.Input
import com.kerman.core.utils.Disposable

interface Lwjgl3Input : Input, Disposable {
    fun windowHandleChanged(windowHandle: Long)
    fun update()
    fun prepareNext()
    fun resetPollingStates()
}
