#version 330
in vec3 in_vert;
in vec3 in_normal;

uniform mat4 m_proj;
uniform mat4 m_view;
uniform mat4 m_model;

out vec3 v_normal;

void main() {
    gl_Position = m_proj * m_view * m_model * vec4(in_vert, 1.0);
    // Correct normal transformation requires normal matrix
    // For uniform scaling, m_model rotation part is enough.
    v_normal = mat3(m_model) * in_normal; 
}
