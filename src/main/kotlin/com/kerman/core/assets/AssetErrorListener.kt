package com.kerman.core.assets

/**
 * Info : This interface was inspired by "com.badlogic.gdx.assets.AssetErrorListener".
 */
interface AssetErrorListener {
    fun error(asset: AssetDescriptor<*>, throwable: Throwable)
}
