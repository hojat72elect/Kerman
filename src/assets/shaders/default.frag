#version 330
in vec3 v_normal;
out vec4 f_color;

void main() {
    float ambient = 0.2;
    vec3 normal = normalize(v_normal);
    vec3 light_dir = normalize(vec3(0.5, 1.0, 0.5));
    float diff = max(dot(normal, light_dir), 0.0);
    
    vec3 color = vec3(0.8, 0.8, 0.8);
    vec3 final_color = color * (diff + ambient);
    
    f_color = vec4(final_color, 1.0);
}
