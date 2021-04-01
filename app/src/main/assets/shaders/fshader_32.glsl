//dynimic mesh 动态网格
#version 100
precision highp float;
varying vec2 v_texcoord;
uniform lowp sampler2D s_textureY;
uniform lowp sampler2D s_textureU;
uniform lowp sampler2D s_textureV;
uniform float u_offset;
uniform vec2 texSize;
vec3 rgb2hsv(vec3 c) {
	vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
	vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
	vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));
	float d = q.x - min(q.w, q.y);
	float e = 1.0e-10;
	return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}
vec4 YuvToRgb(vec2 uv) {
    float y, u, v, r, g, b;
    y = texture2D(s_textureY, uv).r;
    u = texture2D(s_textureU, uv).r;
    v = texture2D(s_textureV, uv).r;
    u = u - 0.5;
    v = v - 0.5;
    r = y + 1.403 * v;
    g = y - 0.344 * u - 0.714 * v;
    b = y + 1.770 * u;
    vec3 c1 = vec3(r, g, b);
    vec3 hsv = rgb2hsv(c1);
    if(hsv.x > 0.8 || hsv.x < 0.04) {
        return vec4(c1, 1.0);
    }
    c1 += vec3(0.3, 0.1588, 0);
    vec3 desat = vec3(0.6, 0.25, 0.15);
    float grayness = dot(c1, desat);
    float contrast = (grayness - 0.5) * 1.35 + 0.5;
    float clip = min(grayness, contrast);
    float bright = max(clip - 0.2, 0.0);
    return vec4(bright, bright, bright, 1.0);
}
void main()
{
    if(u_offset == 2.0)
    {
        gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0);
    }
    else {
        gl_FragColor = YuvToRgb(v_texcoord);
    }
}