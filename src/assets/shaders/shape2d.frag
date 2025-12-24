#version 330
in vec2 v_uv;

uniform vec4 u_color;
uniform int u_shape_type; // 0=rect, 1=circle, 2=text
uniform sampler2D u_texture;

out vec4 f_color;

void main() {
    if (u_shape_type == 1) {
        // Circle logic
        vec2 center = vec2(0.5, 0.5);
        float dist = distance(v_uv, center);
        if (dist > 0.5) {
            discard;
        }
        f_color = u_color;
    } else if (u_shape_type == 2) {
        // Text/Texture logic
        // For text, typically alpha channel holds the shape (if using standard font atlas)
        vec4 tex_color = texture(u_texture, v_uv);
        // Assuming white text on transparent background, or just using alpha
        // If texture is just alpha (common for fonts), use u_color * alpha
        // But if texture is RGBA (emoji?), use texture.
        
        // Let's assume standard white text atlas implies:
        f_color = vec4(u_color.rgb, u_color.a * tex_color.r); // Using R channel as accumulation for simple atlases or A. 
        // For standard PIL image (L or RGBA), let's look at alpha.
        f_color = vec4(u_color.rgb, u_color.a * tex_color.a);
        
    } else {
        // Rect / Default
        f_color = u_color;
    }
}
