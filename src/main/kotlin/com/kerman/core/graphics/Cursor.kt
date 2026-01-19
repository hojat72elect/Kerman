package com.kerman.core.graphics

import com.kerman.core.Graphics
import com.kerman.core.utils.Disposable

/**
 * Info : This interface was inspired by "com.badlogic.gdx.graphics.Cursor".
 *
 * Represents a mouse cursor. Create a cursor via [Graphics.newCursor]. To set the cursor use
 * [Graphics.setCursor]. To use one of the system cursors, call [Graphics.setSystemCursor]
 */
interface Cursor : Disposable {
    enum class SystemCursor {
        Arrow, Ibeam, Crosshair, Hand, HorizontalResize, VerticalResize, NWSEResize, NESWResize, AllResize, NotAllowed, None
    }
}
