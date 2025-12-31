package com.kerman.core.graphics

import java.nio.FloatBuffer
import java.nio.IntBuffer

/**
 * This interface was inspired by "com.badlogic.gdx.graphics.GL31".
 *
 * This version was released in 2014. The most important feature of this version was "Compute Shaders".
 * Compute Shaders allows the GPU to perform general mathematical calculations, not just graphics rendering.
 * It can be used in situations such as :
 * 1 - Physics simulations (particles, cloth) running entirely on the GPU.
 * 2 - Image processing filters (blur, edge detection) in real-time.
 * 3 - AI/ML inference on mobile chips.
 *
 * This version also introduced "Indirect Draw". It allows the GPU to issue its own draw commands without
 * waiting for the CPU, reducing the overhead.
 */
interface GLES31: GLES30 {

    fun glDispatchCompute(num_groups_x: Int, num_groups_y: Int, num_groups_z: Int)
    fun glDispatchComputeIndirect(indirect: Long)
    fun glDrawArraysIndirect(mode: Int, indirect: Long)
    fun glDrawElementsIndirect(mode: Int, type: Int, indirect: Long)
    fun glFramebufferParameteri(target: Int, pname: Int, param: Int)
    fun glGetFramebufferParameteriv(target: Int, pname: Int, params: IntBuffer)
    fun glGetProgramInterfaceiv(program: Int, programInterface: Int, pname: Int, params: IntBuffer)
    fun glGetProgramResourceIndex(program: Int, programInterface: Int, name: String): Int
    fun glGetProgramResourceName(program: Int, programInterface: Int, index: Int): String
    fun glGetProgramResourceiv(program: Int, programInterface: Int, index: Int, props: IntBuffer, length: IntBuffer, params: IntBuffer)
    fun glGetProgramResourceLocation(program: Int, programInterface: Int, name: String): Int
    fun glUseProgramStages(pipeline: Int, stages: Int, program: Int)
    fun glActiveShaderProgram(pipeline: Int, program: Int)
    fun glCreateShaderProgramv(type: Int, strings: Array<String>): Int
    fun glBindProgramPipeline(pipeline: Int)
    fun glDeleteProgramPipelines(n: Int, pipelines: IntBuffer)
    fun glGenProgramPipelines(n: Int, pipelines: IntBuffer)
    fun glIsProgramPipeline(pipeline: Int): Boolean
    fun glGetProgramPipelineiv(pipeline: Int, pname: Int, params: IntBuffer)
    fun glProgramUniform1i(program: Int, location: Int, v0: Int)
    fun glProgramUniform2i(program: Int, location: Int, v0: Int, v1: Int)
    fun glProgramUniform3i(program: Int, location: Int, v0: Int, v1: Int, v2: Int)
    fun glProgramUniform4i(program: Int, location: Int, v0: Int, v1: Int, v2: Int, v3: Int)
    fun glProgramUniform1ui(program: Int, location: Int, v0: Int)
    fun glProgramUniform2ui(program: Int, location: Int, v0: Int, v1: Int)
    fun glProgramUniform3ui(program: Int, location: Int, v0: Int, v1: Int, v2: Int)
    fun glProgramUniform4ui(program: Int, location: Int, v0: Int, v1: Int, v2: Int, v3: Int)
    fun glProgramUniform1f(program: Int, location: Int, v0: Float)
    fun glProgramUniform2f(program: Int, location: Int, v0: Float, v1: Float)
    fun glProgramUniform3f(program: Int, location: Int, v0: Float, v1: Float, v2: Float)
    fun glProgramUniform4f(program: Int, location: Int, v0: Float, v1: Float, v2: Float, v3: Float)
    fun glProgramUniform1iv(program: Int, location: Int, value: IntBuffer)
    fun glProgramUniform2iv(program: Int, location: Int, value: IntBuffer)
    fun glProgramUniform3iv(program: Int, location: Int, value: IntBuffer)
    fun glProgramUniform4iv(program: Int, location: Int, value: IntBuffer)
    fun glProgramUniform1uiv(program: Int, location: Int, value: IntBuffer)
    fun glProgramUniform2uiv(program: Int, location: Int, value: IntBuffer)
    fun glProgramUniform3uiv(program: Int, location: Int, value: IntBuffer)
    fun glProgramUniform4uiv(program: Int, location: Int, value: IntBuffer)
    fun glProgramUniform1fv(program: Int, location: Int, value: FloatBuffer)
    fun glProgramUniform2fv(program: Int, location: Int, value: FloatBuffer)
    fun glProgramUniform3fv(program: Int, location: Int, value: FloatBuffer)
    fun glProgramUniform4fv(program: Int, location: Int, value: FloatBuffer)
    fun glProgramUniformMatrix2fv(program: Int, location: Int, transpose: Boolean, value: FloatBuffer)
    fun glProgramUniformMatrix3fv(program: Int, location: Int, transpose: Boolean, value: FloatBuffer)
    fun glProgramUniformMatrix4fv(program: Int, location: Int, transpose: Boolean, value: FloatBuffer)
    fun glProgramUniformMatrix2x3fv(program: Int, location: Int, transpose: Boolean, value: FloatBuffer)
    fun glProgramUniformMatrix3x2fv(program: Int, location: Int, transpose: Boolean, value: FloatBuffer)
    fun glProgramUniformMatrix2x4fv(program: Int, location: Int, transpose: Boolean, value: FloatBuffer)
    fun glProgramUniformMatrix4x2fv(program: Int, location: Int, transpose: Boolean, value: FloatBuffer)
    fun glProgramUniformMatrix3x4fv(program: Int, location: Int, transpose: Boolean, value: FloatBuffer)
    fun glProgramUniformMatrix4x3fv(program: Int, location: Int, transpose: Boolean, value: FloatBuffer)
    fun glValidateProgramPipeline(pipeline: Int)
    fun glGetProgramPipelineInfoLog(program: Int): String
    fun glBindImageTexture(unit: Int, texture: Int, level: Int, layered: Boolean, layer: Int, access: Int, format: Int)
    fun glGetBooleani_v(target: Int, index: Int, data: IntBuffer)
    fun glMemoryBarrier(barriers: Int)
    fun glMemoryBarrierByRegion(barriers: Int)
    fun glTexStorage2DMultisample(target: Int, samples: Int, internalformat: Int, width: Int, height: Int, fixedsamplelocations: Boolean)
    fun glGetMultisamplefv(pname: Int, index: Int, `val`: FloatBuffer)
    fun glSampleMaski(maskNumber: Int, mask: Int)
    fun glGetTexLevelParameteriv(target: Int, level: Int, pname: Int, params: IntBuffer)
    fun glGetTexLevelParameterfv(target: Int, level: Int, pname: Int, params: FloatBuffer)
    fun glBindVertexBuffer(bindingindex: Int, buffer: Int, offset: Long, stride: Int)
    fun glVertexAttribFormat(attribindex: Int, size: Int, type: Int, normalized: Boolean, relativeoffset: Int)
    fun glVertexAttribIFormat(attribindex: Int, size: Int, type: Int, relativeoffset: Int)
    fun glVertexAttribBinding(attribindex: Int, bindingindex: Int)
    fun glVertexBindingDivisor(bindingindex: Int, divisor: Int)

    companion object {
        const val GL_VERTEX_SHADER_BIT = 0x00000001
        const val GL_FRAGMENT_SHADER_BIT = 0x00000002
        const val GL_COMPUTE_SHADER_BIT = 0x00000020
        const val GL_ALL_SHADER_BITS = -1

        const val GL_VERTEX_ATTRIB_ARRAY_BARRIER_BIT = 0x00000001
        const val GL_ELEMENT_ARRAY_BARRIER_BIT = 0x00000002
        const val GL_UNIFORM_BARRIER_BIT = 0x00000004
        const val GL_TEXTURE_FETCH_BARRIER_BIT = 0x00000008
        const val GL_SHADER_IMAGE_ACCESS_BARRIER_BIT = 0x00000020
        const val GL_COMMAND_BARRIER_BIT = 0x00000040
        const val GL_PIXEL_BUFFER_BARRIER_BIT = 0x00000080
        const val GL_TEXTURE_UPDATE_BARRIER_BIT = 0x00000100
        const val GL_BUFFER_UPDATE_BARRIER_BIT = 0x00000200
        const val GL_FRAMEBUFFER_BARRIER_BIT = 0x00000400
        const val GL_TRANSFORM_FEEDBACK_BARRIER_BIT = 0x00000800
        const val GL_ATOMIC_COUNTER_BARRIER_BIT = 0x00001000
        const val GL_SHADER_STORAGE_BARRIER_BIT = 0x00002000
        const val GL_ALL_BARRIER_BITS = -1 // 0xFFFFFFFF

        const val GL_TEXTURE_WIDTH = 0x1000
        const val GL_TEXTURE_HEIGHT = 0x1001
        const val GL_TEXTURE_INTERNAL_FORMAT = 0x1003
        const val GL_STENCIL_INDEX = 0x1901
        const val GL_TEXTURE_RED_SIZE = 0x805C
        const val GL_TEXTURE_GREEN_SIZE = 0x805D
        const val GL_TEXTURE_BLUE_SIZE = 0x805E
        const val GL_TEXTURE_ALPHA_SIZE = 0x805F
        const val GL_TEXTURE_DEPTH = 0x8071
        const val GL_PROGRAM_SEPARABLE = 0x8258
        const val GL_ACTIVE_PROGRAM = 0x8259
        const val GL_PROGRAM_PIPELINE_BINDING = 0x825A
        const val GL_MAX_COMPUTE_SHARED_MEMORY_SIZE = 0x8262
        const val GL_MAX_COMPUTE_UNIFORM_COMPONENTS = 0x8263
        const val GL_MAX_COMPUTE_ATOMIC_COUNTER_BUFFERS = 0x8264
        const val GL_MAX_COMPUTE_ATOMIC_COUNTERS = 0x8265
        const val GL_MAX_COMBINED_COMPUTE_UNIFORM_COMPONENTS = 0x8266
        const val GL_COMPUTE_WORK_GROUP_SIZE = 0x8267
        const val GL_MAX_UNIFORM_LOCATIONS = 0x826E
        const val GL_VERTEX_ATTRIB_BINDING = 0x82D4
        const val GL_VERTEX_ATTRIB_RELATIVE_OFFSET = 0x82D5
        const val GL_VERTEX_BINDING_DIVISOR = 0x82D6
        const val GL_VERTEX_BINDING_OFFSET = 0x82D7
        const val GL_VERTEX_BINDING_STRIDE = 0x82D8
        const val GL_MAX_VERTEX_ATTRIB_RELATIVE_OFFSET = 0x82D9
        const val GL_MAX_VERTEX_ATTRIB_BINDINGS = 0x82DA
        const val GL_MAX_VERTEX_ATTRIB_STRIDE = 0x82E5
        const val GL_TEXTURE_COMPRESSED = 0x86A1
        const val GL_TEXTURE_DEPTH_SIZE = 0x884A
        const val GL_READ_ONLY = 0x88B8
        const val GL_WRITE_ONLY = 0x88B9
        const val GL_READ_WRITE = 0x88BA
        const val GL_TEXTURE_STENCIL_SIZE = 0x88F1
        const val GL_TEXTURE_RED_TYPE = 0x8C10
        const val GL_TEXTURE_GREEN_TYPE = 0x8C11
        const val GL_TEXTURE_BLUE_TYPE = 0x8C12
        const val GL_TEXTURE_ALPHA_TYPE = 0x8C13
        const val GL_TEXTURE_DEPTH_TYPE = 0x8C16
        const val GL_TEXTURE_SHARED_SIZE = 0x8C3F
        const val GL_SAMPLE_POSITION = 0x8E50
        const val GL_SAMPLE_MASK = 0x8E51
        const val GL_SAMPLE_MASK_VALUE = 0x8E52
        const val GL_MAX_SAMPLE_MASK_WORDS = 0x8E59
        const val GL_MIN_PROGRAM_TEXTURE_GATHER_OFFSET = 0x8E5E
        const val GL_MAX_PROGRAM_TEXTURE_GATHER_OFFSET = 0x8E5F
        const val GL_MAX_IMAGE_UNITS = 0x8F38
        const val GL_MAX_COMBINED_SHADER_OUTPUT_RESOURCES = 0x8F39
        const val GL_IMAGE_BINDING_NAME = 0x8F3A
        const val GL_IMAGE_BINDING_LEVEL = 0x8F3B
        const val GL_IMAGE_BINDING_LAYERED = 0x8F3C
        const val GL_IMAGE_BINDING_LAYER = 0x8F3D
        const val GL_IMAGE_BINDING_ACCESS = 0x8F3E
        const val GL_DRAW_INDIRECT_BUFFER = 0x8F3F
        const val GL_DRAW_INDIRECT_BUFFER_BINDING = 0x8F43
        const val GL_VERTEX_BINDING_BUFFER = 0x8F4F
        const val GL_IMAGE_2D = 0x904D
        const val GL_IMAGE_3D = 0x904E
        const val GL_IMAGE_CUBE = 0x9050
        const val GL_IMAGE_2D_ARRAY = 0x9053
        const val GL_INT_IMAGE_2D = 0x9058
        const val GL_INT_IMAGE_3D = 0x9059
        const val GL_INT_IMAGE_CUBE = 0x905B
        const val GL_INT_IMAGE_2D_ARRAY = 0x905E
        const val GL_UNSIGNED_INT_IMAGE_2D = 0x9063
        const val GL_UNSIGNED_INT_IMAGE_3D = 0x9064
        const val GL_UNSIGNED_INT_IMAGE_CUBE = 0x9066
        const val GL_UNSIGNED_INT_IMAGE_2D_ARRAY = 0x9069
        const val GL_IMAGE_BINDING_FORMAT = 0x906E
        const val GL_IMAGE_FORMAT_COMPATIBILITY_TYPE = 0x90C7
        const val GL_IMAGE_FORMAT_COMPATIBILITY_BY_SIZE = 0x90C8
        const val GL_IMAGE_FORMAT_COMPATIBILITY_BY_CLASS = 0x90C9
        const val GL_MAX_VERTEX_IMAGE_UNIFORMS = 0x90CA
        const val GL_MAX_FRAGMENT_IMAGE_UNIFORMS = 0x90CE
        const val GL_MAX_COMBINED_IMAGE_UNIFORMS = 0x90CF
        const val GL_SHADER_STORAGE_BUFFER = 0x90D2
        const val GL_SHADER_STORAGE_BUFFER_BINDING = 0x90D3
        const val GL_SHADER_STORAGE_BUFFER_START = 0x90D4
        const val GL_SHADER_STORAGE_BUFFER_SIZE = 0x90D5
        const val GL_MAX_VERTEX_SHADER_STORAGE_BLOCKS = 0x90D6
        const val GL_MAX_FRAGMENT_SHADER_STORAGE_BLOCKS = 0x90DA
        const val GL_MAX_COMPUTE_SHADER_STORAGE_BLOCKS = 0x90DB
        const val GL_MAX_COMBINED_SHADER_STORAGE_BLOCKS = 0x90DC
        const val GL_MAX_SHADER_STORAGE_BUFFER_BINDINGS = 0x90DD
        const val GL_MAX_SHADER_STORAGE_BLOCK_SIZE = 0x90DE
        const val GL_SHADER_STORAGE_BUFFER_OFFSET_ALIGNMENT = 0x90DF
        const val GL_DEPTH_STENCIL_TEXTURE_MODE = 0x90EA
        const val GL_MAX_COMPUTE_WORK_GROUP_INVOCATIONS = 0x90EB
        const val GL_DISPATCH_INDIRECT_BUFFER = 0x90EE
        const val GL_DISPATCH_INDIRECT_BUFFER_BINDING = 0x90EF
        const val GL_TEXTURE_2D_MULTISAMPLE = 0x9100
        const val GL_TEXTURE_BINDING_2D_MULTISAMPLE = 0x9104
        const val GL_TEXTURE_SAMPLES = 0x9106
        const val GL_TEXTURE_FIXED_SAMPLE_LOCATIONS = 0x9107
        const val GL_SAMPLER_2D_MULTISAMPLE = 0x9108
        const val GL_INT_SAMPLER_2D_MULTISAMPLE = 0x9109
        const val GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE = 0x910A
        const val GL_MAX_COLOR_TEXTURE_SAMPLES = 0x910E
        const val GL_MAX_DEPTH_TEXTURE_SAMPLES = 0x910F
        const val GL_MAX_INTEGER_SAMPLES = 0x9110
        const val GL_COMPUTE_SHADER = 0x91B9
        const val GL_MAX_COMPUTE_UNIFORM_BLOCKS = 0x91BB
        const val GL_MAX_COMPUTE_TEXTURE_IMAGE_UNITS = 0x91BC
        const val GL_MAX_COMPUTE_IMAGE_UNIFORMS = 0x91BD
        const val GL_MAX_COMPUTE_WORK_GROUP_COUNT = 0x91BE
        const val GL_MAX_COMPUTE_WORK_GROUP_SIZE = 0x91BF
        const val GL_ATOMIC_COUNTER_BUFFER = 0x92C0
        const val GL_ATOMIC_COUNTER_BUFFER_BINDING = 0x92C1
        const val GL_ATOMIC_COUNTER_BUFFER_START = 0x92C2
        const val GL_ATOMIC_COUNTER_BUFFER_SIZE = 0x92C3
        const val GL_MAX_VERTEX_ATOMIC_COUNTER_BUFFERS = 0x92CC
        const val GL_MAX_FRAGMENT_ATOMIC_COUNTER_BUFFERS = 0x92D0
        const val GL_MAX_COMBINED_ATOMIC_COUNTER_BUFFERS = 0x92D1
        const val GL_MAX_VERTEX_ATOMIC_COUNTERS = 0x92D2
        const val GL_MAX_FRAGMENT_ATOMIC_COUNTERS = 0x92D6
        const val GL_MAX_COMBINED_ATOMIC_COUNTERS = 0x92D7
        const val GL_MAX_ATOMIC_COUNTER_BUFFER_SIZE = 0x92D8
        const val GL_ACTIVE_ATOMIC_COUNTER_BUFFERS = 0x92D9
        const val GL_UNSIGNED_INT_ATOMIC_COUNTER = 0x92DB
        const val GL_MAX_ATOMIC_COUNTER_BUFFER_BINDINGS = 0x92DC
        const val GL_UNIFORM = 0x92E1
        const val GL_UNIFORM_BLOCK = 0x92E2
        const val GL_PROGRAM_INPUT = 0x92E3
        const val GL_PROGRAM_OUTPUT = 0x92E4
        const val GL_BUFFER_VARIABLE = 0x92E5
        const val GL_SHADER_STORAGE_BLOCK = 0x92E6
        const val GL_TRANSFORM_FEEDBACK_VARYING = 0x92F4
        const val GL_ACTIVE_RESOURCES = 0x92F5
        const val GL_MAX_NAME_LENGTH = 0x92F6
        const val GL_MAX_NUM_ACTIVE_VARIABLES = 0x92F7
        const val GL_NAME_LENGTH = 0x92F9
        const val GL_TYPE = 0x92FA
        const val GL_ARRAY_SIZE = 0x92FB
        const val GL_OFFSET = 0x92FC
        const val GL_BLOCK_INDEX = 0x92FD
        const val GL_ARRAY_STRIDE = 0x92FE
        const val GL_MATRIX_STRIDE = 0x92FF
        const val GL_IS_ROW_MAJOR = 0x9300
        const val GL_ATOMIC_COUNTER_BUFFER_INDEX = 0x9301
        const val GL_BUFFER_BINDING = 0x9302
        const val GL_BUFFER_DATA_SIZE = 0x9303
        const val GL_NUM_ACTIVE_VARIABLES = 0x9304
        const val GL_ACTIVE_VARIABLES = 0x9305
        const val GL_REFERENCED_BY_VERTEX_SHADER = 0x9306
        const val GL_REFERENCED_BY_FRAGMENT_SHADER = 0x930A
        const val GL_REFERENCED_BY_COMPUTE_SHADER = 0x930B
        const val GL_TOP_LEVEL_ARRAY_SIZE = 0x930C
        const val GL_TOP_LEVEL_ARRAY_STRIDE = 0x930D
        const val GL_LOCATION = 0x930E
        const val GL_FRAMEBUFFER_DEFAULT_WIDTH = 0x9310
        const val GL_FRAMEBUFFER_DEFAULT_HEIGHT = 0x9311
        const val GL_FRAMEBUFFER_DEFAULT_SAMPLES = 0x9313
        const val GL_FRAMEBUFFER_DEFAULT_FIXED_SAMPLE_LOCATIONS = 0x9314
        const val GL_MAX_FRAMEBUFFER_WIDTH = 0x9315
        const val GL_MAX_FRAMEBUFFER_HEIGHT = 0x9316
        const val GL_MAX_FRAMEBUFFER_SAMPLES = 0x9318
    }
}