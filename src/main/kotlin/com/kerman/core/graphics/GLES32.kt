package com.kerman.core.graphics

import java.nio.Buffer
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.nio.IntBuffer

/**
 * This interface was inspired by "com.badlogic.gdx.graphics.GL32".
 *
 * This was the last and final version of OpenGL ES API. released in 2015.
 * Most important new features :
 * 1 - Tessellation Shaders : They allow you to take a low-detail model and smooth it out dynamically as the camera gets
 * closer (saves memory while keeping objects looking detailed).
 * 2 - Geometry Shaders : They allow the GPU to create or destroy geometry on the fly.
 * 3 - More blending features : Added powerful blending modes (like Multiply, Screen, Overlay) that previously required expensive custom shader math.
 *
 * OpenGL ES version 3.2 is fully supported on almost all Android devices above 7.0.
 */
interface GLES32 : GLES31 {

    fun glBlendBarrier()
    fun glCopyImageSubData(
        srcName: Int,
        srcTarget: Int,
        srcLevel: Int,
        srcX: Int,
        srcY: Int,
        srcZ: Int,
        dstName: Int,
        dstTarget: Int,
        dstLevel: Int,
        dstX: Int,
        dstY: Int,
        dstZ: Int,
        srcWidth: Int,
        srcHeight: Int,
        srcDepth: Int
    )

    fun glDebugMessageControl(source: Int, type: Int, severity: Int, ids: IntBuffer, enabled: Boolean)
    fun glDebugMessageInsert(source: Int, type: Int, id: Int, severity: Int, buf: String)
    fun glDebugMessageCallback(callback: DebugProc)
    fun glGetDebugMessageLog(count: Int, sources: IntBuffer, types: IntBuffer, ids: IntBuffer, severities: IntBuffer, lengths: IntBuffer, messageLog: ByteBuffer): Int
    fun glPushDebugGroup(source: Int, id: Int, message: String)
    fun glPopDebugGroup()
    fun glObjectLabel(identifier: Int, name: Int, label: String)
    fun glGetObjectLabel(identifier: Int, name: Int): String
    fun glGetPointerv(pname: Int): Long
    fun glEnablei(target: Int, index: Int)
    fun glDisablei(target: Int, index: Int)
    fun glBlendEquationi(buf: Int, mode: Int)
    fun glBlendEquationSeparatei(buf: Int, modeRGB: Int, modeAlpha: Int)
    fun glBlendFunci(buf: Int, src: Int, dst: Int)
    fun glBlendFuncSeparatei(buf: Int, srcRGB: Int, dstRGB: Int, srcAlpha: Int, dstAlpha: Int)
    fun glColorMaski(index: Int, r: Boolean, g: Boolean, b: Boolean, a: Boolean)
    fun glIsEnabledi(target: Int, index: Int): Boolean
    fun glDrawElementsBaseVertex(mode: Int, count: Int, type: Int, indices: Buffer, basevertex: Int)
    fun glDrawRangeElementsBaseVertex(mode: Int, start: Int, end: Int, count: Int, type: Int, indices: Buffer, basevertex: Int)
    fun glDrawElementsInstancedBaseVertex(mode: Int, count: Int, type: Int, indices: Buffer, instanceCount: Int, basevertex: Int)
    fun glDrawElementsInstancedBaseVertex(mode: Int, count: Int, type: Int, indicesOffset: Int, instanceCount: Int, basevertex: Int)
    fun glFramebufferTexture(target: Int, attachment: Int, texture: Int, level: Int)
    fun glGetGraphicsResetStatus(): Int
    fun glReadnPixels(x: Int, y: Int, width: Int, height: Int, format: Int, type: Int, bufSize: Int, data: Buffer)
    fun glGetnUniformfv(program: Int, location: Int, params: FloatBuffer)
    fun glGetnUniformiv(program: Int, location: Int, params: IntBuffer)
    fun glGetnUniformuiv(program: Int, location: Int, params: IntBuffer)
    fun glMinSampleShading(value: Float)
    fun glPatchParameteri(pname: Int, value: Int)
    fun glTexParameterIiv(target: Int, pname: Int, params: IntBuffer)
    fun glTexParameterIuiv(target: Int, pname: Int, params: IntBuffer)
    fun glGetTexParameterIiv(target: Int, pname: Int, params: IntBuffer)
    fun glGetTexParameterIuiv(target: Int, pname: Int, params: IntBuffer)
    fun glSamplerParameterIiv(sampler: Int, pname: Int, param: IntBuffer)
    fun glSamplerParameterIuiv(sampler: Int, pname: Int, param: IntBuffer)
    fun glGetSamplerParameterIiv(sampler: Int, pname: Int, params: IntBuffer)
    fun glGetSamplerParameterIuiv(sampler: Int, pname: Int, params: IntBuffer)
    fun glTexBuffer(target: Int, internalformat: Int, buffer: Int)
    fun glTexBufferRange(target: Int, internalformat: Int, buffer: Int, offset: Int, size: Int)
    fun glTexStorage3DMultisample(target: Int, samples: Int, internalformat: Int, width: Int, height: Int, depth: Int, fixedsamplelocations: Boolean)

    interface DebugProc {
        fun onMessage(source: Int, type: Int, id: Int, severity: Int, message: String)
    }

    companion object {
        const val GL_CONTEXT_FLAG_DEBUG_BIT = 0x00000002
        const val GL_CONTEXT_FLAG_ROBUST_ACCESS_BIT = 0x00000004
        const val GL_GEOMETRY_SHADER_BIT = 0x00000004
        const val GL_TESS_CONTROL_SHADER_BIT = 0x00000008
        const val GL_TESS_EVALUATION_SHADER_BIT = 0x00000010
        const val GL_QUADS = 0x0007
        const val GL_LINES_ADJACENCY = 0x000A
        const val GL_LINE_STRIP_ADJACENCY = 0x000B
        const val GL_TRIANGLES_ADJACENCY = 0x000C
        const val GL_TRIANGLE_STRIP_ADJACENCY = 0x000D
        const val GL_PATCHES = 0x000E
        const val GL_STACK_OVERFLOW = 0x0503
        const val GL_STACK_UNDERFLOW = 0x0504
        const val GL_CONTEXT_LOST = 0x0507
        const val GL_TEXTURE_BORDER_COLOR = 0x1004
        const val GL_VERTEX_ARRAY = 0x8074
        const val GL_CLAMP_TO_BORDER = 0x812D
        const val GL_CONTEXT_FLAGS = 0x821E
        const val GL_PRIMITIVE_RESTART_FOR_PATCHES_SUPPORTED = 0x8221
        const val GL_DEBUG_OUTPUT_SYNCHRONOUS = 0x8242
        const val GL_DEBUG_NEXT_LOGGED_MESSAGE_LENGTH = 0x8243
        const val GL_DEBUG_CALLBACK_FUNCTION = 0x8244
        const val GL_DEBUG_CALLBACK_USER_PARAM = 0x8245
        const val GL_DEBUG_SOURCE_API = 0x8246
        const val GL_DEBUG_SOURCE_WINDOW_SYSTEM = 0x8247
        const val GL_DEBUG_SOURCE_SHADER_COMPILER = 0x8248
        const val GL_DEBUG_SOURCE_THIRD_PARTY = 0x8249
        const val GL_DEBUG_SOURCE_APPLICATION = 0x824A
        const val GL_DEBUG_SOURCE_OTHER = 0x824B
        const val GL_DEBUG_TYPE_ERROR = 0x824C
        const val GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR = 0x824D
        const val GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR = 0x824E
        const val GL_DEBUG_TYPE_PORTABILITY = 0x824F
        const val GL_DEBUG_TYPE_PERFORMANCE = 0x8250
        const val GL_DEBUG_TYPE_OTHER = 0x8251
        const val GL_LOSE_CONTEXT_ON_RESET = 0x8252
        const val GL_GUILTY_CONTEXT_RESET = 0x8253
        const val GL_INNOCENT_CONTEXT_RESET = 0x8254
        const val GL_UNKNOWN_CONTEXT_RESET = 0x8255
        const val GL_RESET_NOTIFICATION_STRATEGY = 0x8256
        const val GL_LAYER_PROVOKING_VERTEX = 0x825E
        const val GL_UNDEFINED_VERTEX = 0x8260
        const val GL_NO_RESET_NOTIFICATION = 0x8261
        const val GL_DEBUG_TYPE_MARKER = 0x8268
        const val GL_DEBUG_TYPE_PUSH_GROUP = 0x8269
        const val GL_DEBUG_TYPE_POP_GROUP = 0x826A
        const val GL_DEBUG_SEVERITY_NOTIFICATION = 0x826B
        const val GL_MAX_DEBUG_GROUP_STACK_DEPTH = 0x826C
        const val GL_DEBUG_GROUP_STACK_DEPTH = 0x826D
        const val GL_BUFFER = 0x82E0
        const val GL_SHADER = 0x82E1
        const val GL_PROGRAM = 0x82E2
        const val GL_QUERY = 0x82E3
        const val GL_PROGRAM_PIPELINE = 0x82E4
        const val GL_SAMPLER = 0x82E6
        const val GL_MAX_LABEL_LENGTH = 0x82E8
        const val GL_MAX_TESS_CONTROL_INPUT_COMPONENTS = 0x886C
        const val GL_MAX_TESS_EVALUATION_INPUT_COMPONENTS = 0x886D
        const val GL_GEOMETRY_SHADER_INVOCATIONS = 0x887F
        const val GL_GEOMETRY_VERTICES_OUT = 0x8916
        const val GL_GEOMETRY_INPUT_TYPE = 0x8917
        const val GL_GEOMETRY_OUTPUT_TYPE = 0x8918
        const val GL_MAX_GEOMETRY_UNIFORM_BLOCKS = 0x8A2C
        const val GL_MAX_COMBINED_GEOMETRY_UNIFORM_COMPONENTS = 0x8A32
        const val GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS = 0x8C29
        const val GL_TEXTURE_BUFFER = 0x8C2A
        const val GL_TEXTURE_BUFFER_BINDING = 0x8C2A
        const val GL_MAX_TEXTURE_BUFFER_SIZE = 0x8C2B
        const val GL_TEXTURE_BINDING_BUFFER = 0x8C2C
        const val GL_TEXTURE_BUFFER_DATA_STORE_BINDING = 0x8C2D
        const val GL_SAMPLE_SHADING = 0x8C36
        const val GL_MIN_SAMPLE_SHADING_VALUE = 0x8C37
        const val GL_PRIMITIVES_GENERATED = 0x8C87
        const val GL_FRAMEBUFFER_ATTACHMENT_LAYERED = 0x8DA7
        const val GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS = 0x8DA8
        const val GL_SAMPLER_BUFFER = 0x8DC2
        const val GL_INT_SAMPLER_BUFFER = 0x8DD0
        const val GL_UNSIGNED_INT_SAMPLER_BUFFER = 0x8DD8
        const val GL_GEOMETRY_SHADER = 0x8DD9
        const val GL_MAX_GEOMETRY_UNIFORM_COMPONENTS = 0x8DDF
        const val GL_MAX_GEOMETRY_OUTPUT_VERTICES = 0x8DE0
        const val GL_MAX_GEOMETRY_TOTAL_OUTPUT_COMPONENTS = 0x8DE1
        const val GL_MAX_COMBINED_TESS_CONTROL_UNIFORM_COMPONENTS = 0x8E1E
        const val GL_MAX_COMBINED_TESS_EVALUATION_UNIFORM_COMPONENTS = 0x8E1F
        const val GL_FIRST_VERTEX_CONVENTION = 0x8E4D
        const val GL_LAST_VERTEX_CONVENTION = 0x8E4E
        const val GL_MAX_GEOMETRY_SHADER_INVOCATIONS = 0x8E5A
        const val GL_MIN_FRAGMENT_INTERPOLATION_OFFSET = 0x8E5B
        const val GL_MAX_FRAGMENT_INTERPOLATION_OFFSET = 0x8E5C
        const val GL_FRAGMENT_INTERPOLATION_OFFSET_BITS = 0x8E5D
        const val GL_PATCH_VERTICES = 0x8E72
        const val GL_TESS_CONTROL_OUTPUT_VERTICES = 0x8E75
        const val GL_TESS_GEN_MODE = 0x8E76
        const val GL_TESS_GEN_SPACING = 0x8E77
        const val GL_TESS_GEN_VERTEX_ORDER = 0x8E78
        const val GL_TESS_GEN_POINT_MODE = 0x8E79
        const val GL_ISOLINES = 0x8E7A
        const val GL_FRACTIONAL_ODD = 0x8E7B
        const val GL_FRACTIONAL_EVEN = 0x8E7C
        const val GL_MAX_PATCH_VERTICES = 0x8E7D
        const val GL_MAX_TESS_GEN_LEVEL = 0x8E7E
        const val GL_MAX_TESS_CONTROL_UNIFORM_COMPONENTS = 0x8E7F
        const val GL_MAX_TESS_EVALUATION_UNIFORM_COMPONENTS = 0x8E80
        const val GL_MAX_TESS_CONTROL_TEXTURE_IMAGE_UNITS = 0x8E81
        const val GL_MAX_TESS_EVALUATION_TEXTURE_IMAGE_UNITS = 0x8E82
        const val GL_MAX_TESS_CONTROL_OUTPUT_COMPONENTS = 0x8E83
        const val GL_MAX_TESS_PATCH_COMPONENTS = 0x8E84
        const val GL_MAX_TESS_CONTROL_TOTAL_OUTPUT_COMPONENTS = 0x8E85
        const val GL_MAX_TESS_EVALUATION_OUTPUT_COMPONENTS = 0x8E86
        const val GL_TESS_EVALUATION_SHADER = 0x8E87
        const val GL_TESS_CONTROL_SHADER = 0x8E88
        const val GL_MAX_TESS_CONTROL_UNIFORM_BLOCKS = 0x8E89
        const val GL_MAX_TESS_EVALUATION_UNIFORM_BLOCKS = 0x8E8A
        const val GL_TEXTURE_CUBE_MAP_ARRAY = 0x9009
        const val GL_TEXTURE_BINDING_CUBE_MAP_ARRAY = 0x900A
        const val GL_SAMPLER_CUBE_MAP_ARRAY = 0x900C
        const val GL_SAMPLER_CUBE_MAP_ARRAY_SHADOW = 0x900D
        const val GL_INT_SAMPLER_CUBE_MAP_ARRAY = 0x900E
        const val GL_UNSIGNED_INT_SAMPLER_CUBE_MAP_ARRAY = 0x900F
        const val GL_IMAGE_BUFFER = 0x9051
        const val GL_IMAGE_CUBE_MAP_ARRAY = 0x9054
        const val GL_INT_IMAGE_BUFFER = 0x905C
        const val GL_INT_IMAGE_CUBE_MAP_ARRAY = 0x905F
        const val GL_UNSIGNED_INT_IMAGE_BUFFER = 0x9067
        const val GL_UNSIGNED_INT_IMAGE_CUBE_MAP_ARRAY = 0x906A
        const val GL_MAX_TESS_CONTROL_IMAGE_UNIFORMS = 0x90CB
        const val GL_MAX_TESS_EVALUATION_IMAGE_UNIFORMS = 0x90CC
        const val GL_MAX_GEOMETRY_IMAGE_UNIFORMS = 0x90CD
        const val GL_MAX_GEOMETRY_SHADER_STORAGE_BLOCKS = 0x90D7
        const val GL_MAX_TESS_CONTROL_SHADER_STORAGE_BLOCKS = 0x90D8
        const val GL_MAX_TESS_EVALUATION_SHADER_STORAGE_BLOCKS = 0x90D9
        const val GL_TEXTURE_2D_MULTISAMPLE_ARRAY = 0x9102
        const val GL_TEXTURE_BINDING_2D_MULTISAMPLE_ARRAY = 0x9105
        const val GL_SAMPLER_2D_MULTISAMPLE_ARRAY = 0x910B
        const val GL_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 0x910C
        const val GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 0x910D
        const val GL_MAX_GEOMETRY_INPUT_COMPONENTS = 0x9123
        const val GL_MAX_GEOMETRY_OUTPUT_COMPONENTS = 0x9124
        const val GL_MAX_DEBUG_MESSAGE_LENGTH = 0x9143
        const val GL_MAX_DEBUG_LOGGED_MESSAGES = 0x9144
        const val GL_DEBUG_LOGGED_MESSAGES = 0x9145
        const val GL_DEBUG_SEVERITY_HIGH = 0x9146
        const val GL_DEBUG_SEVERITY_MEDIUM = 0x9147
        const val GL_DEBUG_SEVERITY_LOW = 0x9148
        const val GL_TEXTURE_BUFFER_OFFSET = 0x919D
        const val GL_TEXTURE_BUFFER_SIZE = 0x919E
        const val GL_TEXTURE_BUFFER_OFFSET_ALIGNMENT = 0x919F
        const val GL_MULTIPLY = 0x9294
        const val GL_SCREEN = 0x9295
        const val GL_OVERLAY = 0x9296
        const val GL_DARKEN = 0x9297
        const val GL_LIGHTEN = 0x9298
        const val GL_COLORDODGE = 0x9299
        const val GL_COLORBURN = 0x929A
        const val GL_HARDLIGHT = 0x929B
        const val GL_SOFTLIGHT = 0x929C
        const val GL_DIFFERENCE = 0x929E
        const val GL_EXCLUSION = 0x92A0
        const val GL_HSL_HUE = 0x92AD
        const val GL_HSL_SATURATION = 0x92AE
        const val GL_HSL_COLOR = 0x92AF
        const val GL_HSL_LUMINOSITY = 0x92B0
        const val GL_PRIMITIVE_BOUNDING_BOX = 0x92BE
        const val GL_MAX_TESS_CONTROL_ATOMIC_COUNTER_BUFFERS = 0x92CD
        const val GL_MAX_TESS_EVALUATION_ATOMIC_COUNTER_BUFFERS = 0x92CE
        const val GL_MAX_GEOMETRY_ATOMIC_COUNTER_BUFFERS = 0x92CF
        const val GL_MAX_TESS_CONTROL_ATOMIC_COUNTERS = 0x92D3
        const val GL_MAX_TESS_EVALUATION_ATOMIC_COUNTERS = 0x92D4
        const val GL_MAX_GEOMETRY_ATOMIC_COUNTERS = 0x92D5
        const val GL_DEBUG_OUTPUT = 0x92E0
        const val GL_IS_PER_PATCH = 0x92E7
        const val GL_REFERENCED_BY_TESS_CONTROL_SHADER = 0x9307
        const val GL_REFERENCED_BY_TESS_EVALUATION_SHADER = 0x9308
        const val GL_REFERENCED_BY_GEOMETRY_SHADER = 0x9309
        const val GL_FRAMEBUFFER_DEFAULT_LAYERS = 0x9312
        const val GL_MAX_FRAMEBUFFER_LAYERS = 0x9317
        const val GL_MULTISAMPLE_LINE_WIDTH_RANGE = 0x9381
        const val GL_MULTISAMPLE_LINE_WIDTH_GRANULARITY = 0x9382
        const val GL_COMPRESSED_RGBA_ASTC_4x4 = 0x93B0
        const val GL_COMPRESSED_RGBA_ASTC_5x4 = 0x93B1
        const val GL_COMPRESSED_RGBA_ASTC_5x5 = 0x93B2
        const val GL_COMPRESSED_RGBA_ASTC_6x5 = 0x93B3
        const val GL_COMPRESSED_RGBA_ASTC_6x6 = 0x93B4
        const val GL_COMPRESSED_RGBA_ASTC_8x5 = 0x93B5
        const val GL_COMPRESSED_RGBA_ASTC_8x6 = 0x93B6
        const val GL_COMPRESSED_RGBA_ASTC_8x8 = 0x93B7
        const val GL_COMPRESSED_RGBA_ASTC_10x5 = 0x93B8
        const val GL_COMPRESSED_RGBA_ASTC_10x6 = 0x93B9
        const val GL_COMPRESSED_RGBA_ASTC_10x8 = 0x93BA
        const val GL_COMPRESSED_RGBA_ASTC_10x10 = 0x93BB
        const val GL_COMPRESSED_RGBA_ASTC_12x10 = 0x93BC
        const val GL_COMPRESSED_RGBA_ASTC_12x12 = 0x93BD
        const val GL_COMPRESSED_SRGB8_ALPHA8_ASTC_4x4 = 0x93D0
        const val GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x4 = 0x93D1
        const val GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x5 = 0x93D2
        const val GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x5 = 0x93D3
        const val GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x6 = 0x93D4
        const val GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x5 = 0x93D5
        const val GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x6 = 0x93D6
        const val GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x8 = 0x93D7
        const val GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x5 = 0x93D8
        const val GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x6 = 0x93D9
        const val GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x8 = 0x93DA
        const val GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x10 = 0x93DB
        const val GL_COMPRESSED_SRGB8_ALPHA8_ASTC_12x10 = 0x93DC
        const val GL_COMPRESSED_SRGB8_ALPHA8_ASTC_12x12 = 0x93DD
    }

}