varying vec2 v_texCoords;
uniform sampler2D u_texture;

uniform vec2 screenSize;
uniform float rngValue = 1;
uniform float flickerStrength = 0.05;
uniform float distortionAmount = 0.5;
uniform float chromaticAberration = 0.015;
uniform float scanlineBrightness = 0.8;
uniform float scanlineAmount = 480;
uniform float fuzziness = 0.5;

float random(inout float seed1, float seed2) {
    float value = sin(dot(vec2(seed1, seed2), vec2(12.9898, 78.233))) * 43758.5453;
    seed1 = fract(value);
    return seed1;
}
vec4 get(vec2 texCoords, vec4 mask, float scale, inout vec2 uv) {
    vec2 coords = vec2(texCoords);
    float s = float(uint(gl_FragCoord.y * screenSize.x) + uint(gl_FragCoord.x)) / float(screenSize.x * screenSize.y);
    coords.x += int(random(rngValue, s) * (fuzziness * 2 + 1) - fuzziness) / screenSize.x;
    coords.y += int(random(rngValue, s) * (fuzziness * 2 + 1) - fuzziness) / screenSize.y;
    vec2 center = vec2(0.5, 0.5);
    vec2 point = coords - center;
    float radius = length(point);
    vec2 distortion = point * ((1.0 + distortionAmount * radius * radius) / (1.0 - scale)) / ((0.25 * distortionAmount) + 1);
    uv = distortion + center;
    if (uv.x < 0 || uv.y < 0 || uv.x > 1 || uv.y > 1) return vec4(0, 0, 0, 1) * mask;
    return texture2D(u_texture, uv) * mask;
}
vec4 getColor(vec4 texColor, vec2 texCoords) {
    float flicker = rngValue;
    vec2 uvR = vec2(0, 0);
    vec2 uvG = vec2(0, 0);
    vec2 uvB = vec2(0, 0);
    vec4 col = get(texCoords, vec4(1, 0, 0, 1), -chromaticAberration, uvR) + get(texCoords, vec4(0, 1, 0, 1), 0, uvG) + get(texCoords, vec4(0, 0, 1, 1), chromaticAberration, uvB);
    float multiplier = flicker * flickerStrength + (1 - flickerStrength);
    if (uint(uvG.y * scanlineAmount) % uint(2) == uint(1)) multiplier *= scanlineBrightness;
    return col * multiplier;
}
void main() {
    vec4 texColor = texture2D(u_texture, v_texCoords);
    gl_FragColor = getColor(texColor, v_texCoords);
}