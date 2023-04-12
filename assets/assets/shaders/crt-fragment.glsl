#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;

uniform float flicker;
uniform float flickerStrength;
uniform float distortionAmount;
uniform float chromaticAberration;
uniform float scanlineBrightness;
uniform float scanlineAmount;

vec4 get(vec2 texCoords, vec4 mask, float zoom, inout vec2 uv) {
    vec2 point = texCoords - vec2(0.5, 0.5);
    float radius = length(point);
    vec2 corr = point * ((1.0 + distortionAmount * radius * radius) / (1.0 - zoom)) / 1.1;
    uv = corr + vec2(0.5, 0.5);
    if (uv.x < 0 || uv.y < 0 || uv.x > 1 || uv.y > 1) return vec4(0, 0, 0, 1) * mask;
    return texture2D(u_texture, uv) * mask;
}
vec4 getColor(vec4 texColor, vec2 texCoords) {
    vec2 uvR = vec2(0, 0);
    vec2 uvG = vec2(0, 0);
    vec2 uvB = vec2(0, 0);
    vec4 col = get(texCoords, vec4(1, 0, 0, 1), -chromaticAberration, uvR) + get(texCoords, vec4(0, 1, 0, 1), 0, uvG) + get(texCoords, vec4(0, 0, 1, 1), chromaticAberration, uvB);
    float multiplier = flicker * flickerStrength + (1 - flickerStrength);
    if (uint(uvR.y * scanlineAmount) % 2 == 1) multiplier *= scanlineBrightness;
    return col * multiplier;
}

void main() {
    vec4 texColor = texture2D(u_texture, v_texCoords);
    gl_FragColor = getColor(texColor, v_texCoords);
}