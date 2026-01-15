package com.kerman.core.utils

import com.kerman.core.files.FileHandle
import java.io.InputStream

/**
 * This interface was inspired by "com.badlogic.gdx.utils.BaseJsonReader".
 */
interface BaseJsonReader {

    fun parse(input: InputStream): JsonValue
    fun parse(file: FileHandle): JsonValue
}
