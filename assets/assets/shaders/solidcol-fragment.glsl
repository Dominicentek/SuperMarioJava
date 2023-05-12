varying vec2 v_texCoords;
uniform sampler2D u_texture;

uniform vec4 color;

void main() {
    vec4 texColor = texture2D(u_texture, v_texCoords);
    gl_FragColor = vec4(color.r, color.g, color.b, texColor.a * color.a);
}