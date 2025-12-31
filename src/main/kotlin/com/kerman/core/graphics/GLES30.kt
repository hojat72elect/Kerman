package com.kerman.core.graphics

import java.nio.Buffer
import java.nio.FloatBuffer
import java.nio.IntBuffer
import java.nio.LongBuffer

/**
 * This interface was inspired by "com.badlogic.gdx.graphics.GL30".
 *
 * A wrapper for all the methods of OpenGL ES 3.0.
 *
 * This is the modern mobile standard, and also the basis for WebGL 2.0. Most important features of this version:
 * 1 - Multiple Render Targets (MRT) : Essential for techniques like Deferred Rendering.
 * 2 - Instancing : Allows drawing thousands of identical objects (like trees or grass) efficiently.
 * 3 - Texture Compression: Standardized ETC2 compression, solving a major headache of supporting different GPU vendors (Adreno, Mali, PowerVR).
 */
interface GLES30 : GLES20 {

    fun glReadBuffer(mode: Int)
    fun glDrawRangeElements(mode: Int, start: Int, end: Int, count: Int, type: Int, indices: Buffer)
    fun glDrawRangeElements(mode: Int, start: Int, end: Int, count: Int, type: Int, offset: Int)
    fun glTexImage2D(target: Int, level: Int, internalformat: Int, width: Int, height: Int, border: Int, format: Int, type: Int, offset: Int)
    fun glTexImage3D(target: Int, level: Int, internalformat: Int, width: Int, height: Int, depth: Int, border: Int, format: Int, type: Int, pixels: Buffer?)
    fun glTexImage3D(target: Int, level: Int, internalformat: Int, width: Int, height: Int, depth: Int, border: Int, format: Int, type: Int, offset: Int)
    fun glTexSubImage2D(target: Int, level: Int, xoffset: Int, yoffset: Int, width: Int, height: Int, format: Int, type: Int, offset: Int)
    fun glTexSubImage3D(target: Int, level: Int, xoffset: Int, yoffset: Int, zoffset: Int, width: Int, height: Int, depth: Int, format: Int, type: Int, pixels: Buffer)
    fun glTexSubImage3D(target: Int, level: Int, xoffset: Int, yoffset: Int, zoffset: Int, width: Int, height: Int, depth: Int, format: Int, type: Int, offset: Int)
    fun glCopyTexSubImage3D(target: Int, level: Int, xoffset: Int, yoffset: Int, zoffset: Int, x: Int, y: Int, width: Int, height: Int)
    fun glGenQueries(n: Int, ids: IntArray, offset: Int)
    fun glGenQueries(n: Int, ids: IntBuffer)
    fun glDeleteQueries(n: Int, ids: IntArray, offset: Int)
    fun glDeleteQueries(n: Int, ids: IntBuffer)
    fun glIsQuery(id: Int): Boolean
    fun glBeginQuery(target: Int, id: Int)
    fun glEndQuery(target: Int)
    fun glGetQueryiv(target: Int, pname: Int, params: IntBuffer)
    fun glGetQueryObjectuiv(id: Int, pname: Int, params: IntBuffer)
    fun glUnmapBuffer(target: Int): Boolean
    fun glGetBufferPointerv(target: Int, pname: Int): Buffer
    fun glDrawBuffers(n: Int, bufs: IntBuffer)
    fun glUniformMatrix2x3fv(location: Int, count: Int, transpose: Boolean, value: FloatBuffer)
    fun glUniformMatrix3x2fv(location: Int, count: Int, transpose: Boolean, value: FloatBuffer)
    fun glUniformMatrix2x4fv(location: Int, count: Int, transpose: Boolean, value: FloatBuffer)
    fun glUniformMatrix4x2fv(location: Int, count: Int, transpose: Boolean, value: FloatBuffer)
    fun glUniformMatrix3x4fv(location: Int, count: Int, transpose: Boolean, value: FloatBuffer)
    fun glUniformMatrix4x3fv(location: Int, count: Int, transpose: Boolean, value: FloatBuffer)
    fun glBlitFramebuffer(srcX0: Int, srcY0: Int, srcX1: Int, srcY1: Int, dstX0: Int, dstY0: Int, dstX1: Int, dstY1: Int, mask: Int, filter: Int)
    fun glRenderbufferStorageMultisample(target: Int, samples: Int, internalformat: Int, width: Int, height: Int)
    fun glFramebufferTextureLayer(target: Int, attachment: Int, texture: Int, level: Int, layer: Int)
    fun glMapBufferRange(target: Int, offset: Int, length: Int, access: Int): Buffer
    fun glFlushMappedBufferRange(target: Int, offset: Int, length: Int)
    fun glBindVertexArray(array: Int)
    fun glDeleteVertexArrays(n: Int, arrays: IntArray, offset: Int)
    fun glDeleteVertexArrays(n: Int, arrays: IntBuffer)
    fun glGenVertexArrays(n: Int, arrays: IntArray, offset: Int)
    fun glGenVertexArrays(n: Int, arrays: IntBuffer)
    fun glIsVertexArray(array: Int): Boolean
    fun glBeginTransformFeedback(primitiveMode: Int)
    fun glEndTransformFeedback()
    fun glBindBufferRange(target: Int, index: Int, buffer: Int, offset: Int, size: Int)
    fun glBindBufferBase(target: Int, index: Int, buffer: Int)
    fun glTransformFeedbackVaryings(program: Int, varyings: Array<String>, bufferMode: Int)
    fun glVertexAttribIPointer(index: Int, size: Int, type: Int, stride: Int, offset: Int)
    fun glGetVertexAttribIiv(index: Int, pname: Int, params: IntBuffer)
    fun glGetVertexAttribIuiv(index: Int, pname: Int, params: IntBuffer)
    fun glVertexAttribI4i(index: Int, x: Int, y: Int, z: Int, w: Int)
    fun glVertexAttribI4ui(index: Int, x: Int, y: Int, z: Int, w: Int)
    fun glGetUniformuiv(program: Int, location: Int, params: IntBuffer)
    fun glGetFragDataLocation(program: Int, name: String): Int
    fun glUniform1uiv(location: Int, count: Int, value: IntBuffer)
    fun glUniform3uiv(location: Int, count: Int, value: IntBuffer)
    fun glUniform4uiv(location: Int, count: Int, value: IntBuffer)
    fun glClearBufferiv(buffer: Int, drawbuffer: Int, value: IntBuffer)
    fun glClearBufferuiv(buffer: Int, drawbuffer: Int, value: IntBuffer)
    fun glClearBufferfv(buffer: Int, drawbuffer: Int, value: FloatBuffer)
    fun glClearBufferfi(buffer: Int, drawbuffer: Int, depth: Float, stencil: Int)
    fun glGetStringi(name: Int, index: Int): String
    fun glCopyBufferSubData(readTarget: Int, writeTarget: Int, readOffset: Int, writeOffset: Int, size: Int)
    fun glGetUniformIndices(program: Int, uniformNames: Array<String>, uniformIndices: IntBuffer)
    fun glGetActiveUniformsiv(program: Int, uniformCount: Int, uniformIndices: IntBuffer, pname: Int, params: IntBuffer)
    fun glGetUniformBlockIndex(program: Int, uniformBlockName: String): Int
    fun glGetActiveUniformBlockiv(program: Int, uniformBlockIndex: Int, pname: Int, params: IntBuffer)
    fun glGetActiveUniformBlockName(program: Int, uniformBlockIndex: Int, length: Buffer, uniformBlockName: Buffer)
    fun glGetActiveUniformBlockName(program: Int, uniformBlockIndex: Int): String
    fun glUniformBlockBinding(program: Int, uniformBlockIndex: Int, uniformBlockBinding: Int)
    fun glDrawArraysInstanced(mode: Int, first: Int, count: Int, instanceCount: Int)
    fun glDrawElementsInstanced(mode: Int, count: Int, type: Int, indicesOffset: Int, instanceCount: Int)
    fun glGetInteger64v(pname: Int, params: LongBuffer)
    fun glGetBufferParameteri64v(target: Int, pname: Int, params: LongBuffer)
    fun glGenSamplers(count: Int, samplers: IntArray, offset: Int)
    fun glGenSamplers(count: Int, samplers: IntBuffer)
    fun glDeleteSamplers(count: Int, samplers: IntArray, offset: Int)
    fun glDeleteSamplers(count: Int, samplers: IntBuffer)
    fun glIsSampler(sampler: Int): Boolean
    fun glBindSampler(unit: Int, sampler: Int)
    fun glSamplerParameteri(sampler: Int, pname: Int, param: Int)
    fun glSamplerParameteriv(sampler: Int, pname: Int, param: IntBuffer)
    fun glSamplerParameterf(sampler: Int, pname: Int, param: Float)
    fun glSamplerParameterfv(sampler: Int, pname: Int, param: FloatBuffer)
    fun glGetSamplerParameteriv(sampler: Int, pname: Int, params: IntBuffer)
    fun glGetSamplerParameterfv(sampler: Int, pname: Int, params: FloatBuffer)
    fun glVertexAttribDivisor(index: Int, divisor: Int)
    fun glBindTransformFeedback(target: Int, id: Int)
    fun glDeleteTransformFeedbacks(n: Int, ids: IntArray, offset: Int)
    fun glDeleteTransformFeedbacks(n: Int, ids: IntBuffer)
    fun glGenTransformFeedbacks(n: Int, ids: IntArray, offset: Int)
    fun glGenTransformFeedbacks(n: Int, ids: IntBuffer)
    fun glIsTransformFeedback(id: Int): Boolean
    fun glPauseTransformFeedback()
    fun glResumeTransformFeedback()
    fun glProgramParameteri(program: Int, pname: Int, value: Int)
    fun glInvalidateFramebuffer(target: Int, numAttachments: Int, attachments: IntBuffer)
    fun glInvalidateSubFramebuffer(target: Int, numAttachments: Int, attachments: IntBuffer, x: Int, y: Int, width: Int, height: Int)

    @Deprecated("starting from GL ES version 3.0 this is deprecated. use the other version of this function instead")
    override fun glVertexAttribPointer(indx: Int, size: Int, type: Int, normalized: Boolean, stride: Int, ptr: Buffer)

    companion object {
        const val GL_READ_BUFFER = 0x0C02
        const val GL_UNPACK_ROW_LENGTH = 0x0CF2
        const val GL_UNPACK_SKIP_ROWS = 0x0CF3
        const val GL_UNPACK_SKIP_PIXELS = 0x0CF4
        const val GL_PACK_ROW_LENGTH = 0x0D02
        const val GL_PACK_SKIP_ROWS = 0x0D03
        const val GL_PACK_SKIP_PIXELS = 0x0D04
        const val GL_COLOR = 0x1800
        const val GL_DEPTH = 0x1801
        const val GL_STENCIL = 0x1802
        const val GL_RED = 0x1903
        const val GL_RGB8 = 0x8051
        const val GL_RGBA8 = 0x8058
        const val GL_RGB10_A2 = 0x8059
        const val GL_TEXTURE_BINDING_3D = 0x806A
        const val GL_UNPACK_SKIP_IMAGES = 0x806D
        const val GL_UNPACK_IMAGE_HEIGHT = 0x806E
        const val GL_TEXTURE_3D = 0x806F
        const val GL_TEXTURE_WRAP_R = 0x8072
        const val GL_MAX_3D_TEXTURE_SIZE = 0x8073
        const val GL_UNSIGNED_INT_2_10_10_10_REV = 0x8368
        const val GL_MAX_ELEMENTS_VERTICES = 0x80E8
        const val GL_MAX_ELEMENTS_INDICES = 0x80E9
        const val GL_TEXTURE_MIN_LOD = 0x813A
        const val GL_TEXTURE_MAX_LOD = 0x813B
        const val GL_TEXTURE_BASE_LEVEL = 0x813C
        const val GL_TEXTURE_MAX_LEVEL = 0x813D
        const val GL_MIN = 0x8007
        const val GL_MAX = 0x8008
        const val GL_DEPTH_COMPONENT24 = 0x81A6
        const val GL_MAX_TEXTURE_LOD_BIAS = 0x84FD
        const val GL_TEXTURE_COMPARE_MODE = 0x884C
        const val GL_TEXTURE_COMPARE_FUNC = 0x884D
        const val GL_CURRENT_QUERY = 0x8865
        const val GL_QUERY_RESULT = 0x8866
        const val GL_QUERY_RESULT_AVAILABLE = 0x8867
        const val GL_BUFFER_MAPPED = 0x88BC
        const val GL_BUFFER_MAP_POINTER = 0x88BD
        const val GL_STREAM_READ = 0x88E1
        const val GL_STREAM_COPY = 0x88E2
        const val GL_STATIC_READ = 0x88E5
        const val GL_STATIC_COPY = 0x88E6
        const val GL_DYNAMIC_READ = 0x88E9
        const val GL_DYNAMIC_COPY = 0x88EA
        const val GL_MAX_DRAW_BUFFERS = 0x8824
        const val GL_DRAW_BUFFER0 = 0x8825
        const val GL_DRAW_BUFFER1 = 0x8826
        const val GL_DRAW_BUFFER2 = 0x8827
        const val GL_DRAW_BUFFER3 = 0x8828
        const val GL_DRAW_BUFFER4 = 0x8829
        const val GL_DRAW_BUFFER5 = 0x882A
        const val GL_DRAW_BUFFER6 = 0x882B
        const val GL_DRAW_BUFFER7 = 0x882C
        const val GL_DRAW_BUFFER8 = 0x882D
        const val GL_DRAW_BUFFER9 = 0x882E
        const val GL_DRAW_BUFFER10 = 0x882F
        const val GL_DRAW_BUFFER11 = 0x8830
        const val GL_DRAW_BUFFER12 = 0x8831
        const val GL_DRAW_BUFFER13 = 0x8832
        const val GL_DRAW_BUFFER14 = 0x8833
        const val GL_DRAW_BUFFER15 = 0x8834
        const val GL_MAX_FRAGMENT_UNIFORM_COMPONENTS = 0x8B49
        const val GL_MAX_VERTEX_UNIFORM_COMPONENTS = 0x8B4A
        const val GL_SAMPLER_3D = 0x8B5F
        const val GL_SAMPLER_2D_SHADOW = 0x8B62
        const val GL_FRAGMENT_SHADER_DERIVATIVE_HINT = 0x8B8B
        const val GL_PIXEL_PACK_BUFFER = 0x88EB
        const val GL_PIXEL_UNPACK_BUFFER = 0x88EC
        const val GL_PIXEL_PACK_BUFFER_BINDING = 0x88ED
        const val GL_PIXEL_UNPACK_BUFFER_BINDING = 0x88EF
        const val GL_FLOAT_MAT2x3 = 0x8B65
        const val GL_FLOAT_MAT2x4 = 0x8B66
        const val GL_FLOAT_MAT3x2 = 0x8B67
        const val GL_FLOAT_MAT3x4 = 0x8B68
        const val GL_FLOAT_MAT4x2 = 0x8B69
        const val GL_FLOAT_MAT4x3 = 0x8B6A
        const val GL_SRGB = 0x8C40
        const val GL_SRGB8 = 0x8C41
        const val GL_SRGB8_ALPHA8 = 0x8C43
        const val GL_COMPARE_REF_TO_TEXTURE = 0x884E
        const val GL_MAJOR_VERSION = 0x821B
        const val GL_MINOR_VERSION = 0x821C
        const val GL_NUM_EXTENSIONS = 0x821D
        const val GL_RGBA32F = 0x8814
        const val GL_RGB32F = 0x8815
        const val GL_RGBA16F = 0x881A
        const val GL_RGB16F = 0x881B
        const val GL_VERTEX_ATTRIB_ARRAY_INTEGER = 0x88FD
        const val GL_MAX_ARRAY_TEXTURE_LAYERS = 0x88FF
        const val GL_MIN_PROGRAM_TEXEL_OFFSET = 0x8904
        const val GL_MAX_PROGRAM_TEXEL_OFFSET = 0x8905
        const val GL_MAX_VARYING_COMPONENTS = 0x8B4B
        const val GL_TEXTURE_2D_ARRAY = 0x8C1A
        const val GL_TEXTURE_BINDING_2D_ARRAY = 0x8C1D
        const val GL_R11F_G11F_B10F = 0x8C3A
        const val GL_UNSIGNED_INT_10F_11F_11F_REV = 0x8C3B
        const val GL_RGB9_E5 = 0x8C3D
        const val GL_UNSIGNED_INT_5_9_9_9_REV = 0x8C3E
        const val GL_TRANSFORM_FEEDBACK_VARYING_MAX_LENGTH = 0x8C76
        const val GL_TRANSFORM_FEEDBACK_BUFFER_MODE = 0x8C7F
        const val GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_COMPONENTS = 0x8C80
        const val GL_TRANSFORM_FEEDBACK_VARYINGS = 0x8C83
        const val GL_TRANSFORM_FEEDBACK_BUFFER_START = 0x8C84
        const val GL_TRANSFORM_FEEDBACK_BUFFER_SIZE = 0x8C85
        const val GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN = 0x8C88
        const val GL_RASTERIZER_DISCARD = 0x8C89
        const val GL_MAX_TRANSFORM_FEEDBACK_INTERLEAVED_COMPONENTS = 0x8C8A
        const val GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_ATTRIBS = 0x8C8B
        const val GL_INTERLEAVED_ATTRIBS = 0x8C8C
        const val GL_SEPARATE_ATTRIBS = 0x8C8D
        const val GL_TRANSFORM_FEEDBACK_BUFFER = 0x8C8E
        const val GL_TRANSFORM_FEEDBACK_BUFFER_BINDING = 0x8C8F
        const val GL_RGBA32UI = 0x8D70
        const val GL_RGB32UI = 0x8D71
        const val GL_RGBA16UI = 0x8D76
        const val GL_RGB16UI = 0x8D77
        const val GL_RGBA8UI = 0x8D7C
        const val GL_RGB8UI = 0x8D7D
        const val GL_RGBA32I = 0x8D82
        const val GL_RGB32I = 0x8D83
        const val GL_RGBA16I = 0x8D88
        const val GL_RGB16I = 0x8D89
        const val GL_RGBA8I = 0x8D8E
        const val GL_RGB8I = 0x8D8F
        const val GL_RED_INTEGER = 0x8D94
        const val GL_RGB_INTEGER = 0x8D98
        const val GL_RGBA_INTEGER = 0x8D99
        const val GL_SAMPLER_2D_ARRAY = 0x8DC1
        const val GL_SAMPLER_2D_ARRAY_SHADOW = 0x8DC4
        const val GL_SAMPLER_CUBE_SHADOW = 0x8DC5
        const val GL_UNSIGNED_INT_VEC2 = 0x8DC6
        const val GL_UNSIGNED_INT_VEC3 = 0x8DC7
        const val GL_UNSIGNED_INT_VEC4 = 0x8DC8
        const val GL_INT_SAMPLER_2D = 0x8DCA
        const val GL_INT_SAMPLER_3D = 0x8DCB
        const val GL_INT_SAMPLER_CUBE = 0x8DCC
        const val GL_INT_SAMPLER_2D_ARRAY = 0x8DCF
        const val GL_UNSIGNED_INT_SAMPLER_2D = 0x8DD2
        const val GL_UNSIGNED_INT_SAMPLER_3D = 0x8DD3
        const val GL_UNSIGNED_INT_SAMPLER_CUBE = 0x8DD4
        const val GL_UNSIGNED_INT_SAMPLER_2D_ARRAY = 0x8DD7
        const val GL_BUFFER_ACCESS_FLAGS = 0x911F
        const val GL_BUFFER_MAP_LENGTH = 0x9120
        const val GL_BUFFER_MAP_OFFSET = 0x9121
        const val GL_DEPTH_COMPONENT32F = 0x8CAC
        const val GL_DEPTH32F_STENCIL8 = 0x8CAD
        const val GL_FLOAT_32_UNSIGNED_INT_24_8_REV = 0x8DAD
        const val GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING = 0x8210
        const val GL_FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE = 0x8211
        const val GL_FRAMEBUFFER_ATTACHMENT_RED_SIZE = 0x8212
        const val GL_FRAMEBUFFER_ATTACHMENT_GREEN_SIZE = 0x8213
        const val GL_FRAMEBUFFER_ATTACHMENT_BLUE_SIZE = 0x8214
        const val GL_FRAMEBUFFER_ATTACHMENT_ALPHA_SIZE = 0x8215
        const val GL_FRAMEBUFFER_ATTACHMENT_DEPTH_SIZE = 0x8216
        const val GL_FRAMEBUFFER_ATTACHMENT_STENCIL_SIZE = 0x8217
        const val GL_FRAMEBUFFER_DEFAULT = 0x8218
        const val GL_FRAMEBUFFER_UNDEFINED = 0x8219
        const val GL_DEPTH_STENCIL_ATTACHMENT = 0x821A
        const val GL_DEPTH_STENCIL = 0x84F9
        const val GL_UNSIGNED_INT_24_8 = 0x84FA
        const val GL_DEPTH24_STENCIL8 = 0x88F0
        const val GL_UNSIGNED_NORMALIZED = 0x8C17
        const val GL_DRAW_FRAMEBUFFER_BINDING = GLES20.GL_FRAMEBUFFER_BINDING
        const val GL_READ_FRAMEBUFFER = 0x8CA8
        const val GL_DRAW_FRAMEBUFFER = 0x8CA9
        const val GL_READ_FRAMEBUFFER_BINDING = 0x8CAA
        const val GL_RENDERBUFFER_SAMPLES = 0x8CAB
        const val GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER = 0x8CD4
        const val GL_MAX_COLOR_ATTACHMENTS = 0x8CDF
        const val GL_COLOR_ATTACHMENT1 = 0x8CE1
        const val GL_COLOR_ATTACHMENT2 = 0x8CE2
        const val GL_COLOR_ATTACHMENT3 = 0x8CE3
        const val GL_COLOR_ATTACHMENT4 = 0x8CE4
        const val GL_COLOR_ATTACHMENT5 = 0x8CE5
        const val GL_COLOR_ATTACHMENT6 = 0x8CE6
        const val GL_COLOR_ATTACHMENT7 = 0x8CE7
        const val GL_COLOR_ATTACHMENT8 = 0x8CE8
        const val GL_COLOR_ATTACHMENT9 = 0x8CE9
        const val GL_COLOR_ATTACHMENT10 = 0x8CEA
        const val GL_COLOR_ATTACHMENT11 = 0x8CEB
        const val GL_COLOR_ATTACHMENT12 = 0x8CEC
        const val GL_COLOR_ATTACHMENT13 = 0x8CED
        const val GL_COLOR_ATTACHMENT14 = 0x8CEE
        const val GL_COLOR_ATTACHMENT15 = 0x8CEF
        const val GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE = 0x8D56
        const val GL_MAX_SAMPLES = 0x8D57
        const val GL_HALF_FLOAT = 0x140B
        const val GL_MAP_READ_BIT = 0x0001
        const val GL_MAP_WRITE_BIT = 0x0002
        const val GL_MAP_INVALIDATE_RANGE_BIT = 0x0004
        const val GL_MAP_INVALIDATE_BUFFER_BIT = 0x0008
        const val GL_MAP_FLUSH_EXPLICIT_BIT = 0x0010
        const val GL_MAP_UNSYNCHRONIZED_BIT = 0x0020
        const val GL_RG = 0x8227
        const val GL_RG_INTEGER = 0x8228
        const val GL_R8 = 0x8229
        const val GL_RG8 = 0x822B
        const val GL_R16F = 0x822D
        const val GL_R32F = 0x822E
        const val GL_RG16F = 0x822F
        const val GL_RG32F = 0x8230
        const val GL_R8I = 0x8231
        const val GL_R8UI = 0x8232
        const val GL_R16I = 0x8233
        const val GL_R16UI = 0x8234
        const val GL_R32I = 0x8235
        const val GL_R32UI = 0x8236
        const val GL_RG8I = 0x8237
        const val GL_RG8UI = 0x8238
        const val GL_RG16I = 0x8239
        const val GL_RG16UI = 0x823A
        const val GL_RG32I = 0x823B
        const val GL_RG32UI = 0x823C
        const val GL_VERTEX_ARRAY_BINDING = 0x85B5
        const val GL_R8_SNORM = 0x8F94
        const val GL_RG8_SNORM = 0x8F95
        const val GL_RGB8_SNORM = 0x8F96
        const val GL_RGBA8_SNORM = 0x8F97
        const val GL_SIGNED_NORMALIZED = 0x8F9C
        const val GL_PRIMITIVE_RESTART_FIXED_INDEX = 0x8D69
        const val GL_COPY_READ_BUFFER = 0x8F36
        const val GL_COPY_WRITE_BUFFER = 0x8F37
        const val GL_COPY_READ_BUFFER_BINDING = GL_COPY_READ_BUFFER
        const val GL_COPY_WRITE_BUFFER_BINDING = GL_COPY_WRITE_BUFFER
        const val GL_UNIFORM_BUFFER = 0x8A11
        const val GL_UNIFORM_BUFFER_BINDING = 0x8A28
        const val GL_UNIFORM_BUFFER_START = 0x8A29
        const val GL_UNIFORM_BUFFER_SIZE = 0x8A2A
        const val GL_MAX_VERTEX_UNIFORM_BLOCKS = 0x8A2B
        const val GL_MAX_FRAGMENT_UNIFORM_BLOCKS = 0x8A2D
        const val GL_MAX_COMBINED_UNIFORM_BLOCKS = 0x8A2E
        const val GL_MAX_UNIFORM_BUFFER_BINDINGS = 0x8A2F
        const val GL_MAX_UNIFORM_BLOCK_SIZE = 0x8A30
        const val GL_MAX_COMBINED_VERTEX_UNIFORM_COMPONENTS = 0x8A31
        const val GL_MAX_COMBINED_FRAGMENT_UNIFORM_COMPONENTS = 0x8A33
        const val GL_UNIFORM_BUFFER_OFFSET_ALIGNMENT = 0x8A34
        const val GL_ACTIVE_UNIFORM_BLOCK_MAX_NAME_LENGTH = 0x8A35
        const val GL_ACTIVE_UNIFORM_BLOCKS = 0x8A36
        const val GL_UNIFORM_TYPE = 0x8A37
        const val GL_UNIFORM_SIZE = 0x8A38
        const val GL_UNIFORM_NAME_LENGTH = 0x8A39
        const val GL_UNIFORM_BLOCK_INDEX = 0x8A3A
        const val GL_UNIFORM_OFFSET = 0x8A3B
        const val GL_UNIFORM_ARRAY_STRIDE = 0x8A3C
        const val GL_UNIFORM_MATRIX_STRIDE = 0x8A3D
        const val GL_UNIFORM_IS_ROW_MAJOR = 0x8A3E
        const val GL_UNIFORM_BLOCK_BINDING = 0x8A3F
        const val GL_UNIFORM_BLOCK_DATA_SIZE = 0x8A40
        const val GL_UNIFORM_BLOCK_NAME_LENGTH = 0x8A41
        const val GL_UNIFORM_BLOCK_ACTIVE_UNIFORMS = 0x8A42
        const val GL_UNIFORM_BLOCK_ACTIVE_UNIFORM_INDICES = 0x8A43
        const val GL_UNIFORM_BLOCK_REFERENCED_BY_VERTEX_SHADER = 0x8A44
        const val GL_UNIFORM_BLOCK_REFERENCED_BY_FRAGMENT_SHADER = 0x8A46

        // GL_INVALID_INDEX is defined as 0xFFFFFFFFu in C.
        const val GL_INVALID_INDEX = -1
        const val GL_MAX_VERTEX_OUTPUT_COMPONENTS = 0x9122
        const val GL_MAX_FRAGMENT_INPUT_COMPONENTS = 0x9125
        const val GL_MAX_SERVER_WAIT_TIMEOUT = 0x9111
        const val GL_OBJECT_TYPE = 0x9112
        const val GL_SYNC_CONDITION = 0x9113
        const val GL_SYNC_STATUS = 0x9114
        const val GL_SYNC_FLAGS = 0x9115
        const val GL_SYNC_FENCE = 0x9116
        const val GL_SYNC_GPU_COMMANDS_COMPLETE = 0x9117
        const val GL_UNSIGNALED = 0x9118
        const val GL_SIGNALED = 0x9119
        const val GL_ALREADY_SIGNALED = 0x911A
        const val GL_TIMEOUT_EXPIRED = 0x911B
        const val GL_CONDITION_SATISFIED = 0x911C
        const val GL_WAIT_FAILED = 0x911D
        const val GL_SYNC_FLUSH_COMMANDS_BIT = 0x00000001

        // GL_TIMEOUT_IGNORED is defined as 0xFFFFFFFFFFFFFFFFull in C.
        const val GL_TIMEOUT_IGNORED: Long = -1
        const val GL_VERTEX_ATTRIB_ARRAY_DIVISOR = 0x88FE
        const val GL_ANY_SAMPLES_PASSED = 0x8C2F
        const val GL_ANY_SAMPLES_PASSED_CONSERVATIVE = 0x8D6A
        const val GL_SAMPLER_BINDING = 0x8919
        const val GL_RGB10_A2UI = 0x906F
        const val GL_TEXTURE_SWIZZLE_R = 0x8E42
        const val GL_TEXTURE_SWIZZLE_G = 0x8E43
        const val GL_TEXTURE_SWIZZLE_B = 0x8E44
        const val GL_TEXTURE_SWIZZLE_A = 0x8E45
        const val GL_GREEN = 0x1904
        const val GL_BLUE = 0x1905
        const val GL_INT_2_10_10_10_REV = 0x8D9F
        const val GL_TRANSFORM_FEEDBACK = 0x8E22
        const val GL_TRANSFORM_FEEDBACK_PAUSED = 0x8E23
        const val GL_TRANSFORM_FEEDBACK_ACTIVE = 0x8E24
        const val GL_TRANSFORM_FEEDBACK_BINDING = 0x8E25
        const val GL_PROGRAM_BINARY_RETRIEVABLE_HINT = 0x8257
        const val GL_PROGRAM_BINARY_LENGTH = 0x8741
        const val GL_NUM_PROGRAM_BINARY_FORMATS = 0x87FE
        const val GL_PROGRAM_BINARY_FORMATS = 0x87FF
        const val GL_COMPRESSED_R11_EAC = 0x9270
        const val GL_COMPRESSED_SIGNED_R11_EAC = 0x9271
        const val GL_COMPRESSED_RG11_EAC = 0x9272
        const val GL_COMPRESSED_SIGNED_RG11_EAC = 0x9273
        const val GL_COMPRESSED_RGB8_ETC2 = 0x9274
        const val GL_COMPRESSED_SRGB8_ETC2 = 0x9275
        const val GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2 = 0x9276
        const val GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2 = 0x9277
        const val GL_COMPRESSED_RGBA8_ETC2_EAC = 0x9278
        const val GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC = 0x9279
        const val GL_TEXTURE_IMMUTABLE_FORMAT = 0x912F
        const val GL_MAX_ELEMENT_INDEX = 0x8D6B
        const val GL_NUM_SAMPLE_COUNTS = 0x9380
        const val GL_TEXTURE_IMMUTABLE_LEVELS = 0x82DF
    }
}