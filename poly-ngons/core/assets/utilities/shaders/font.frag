#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture;
uniform float u_spread;
uniform float u_scale;

varying vec4 v_color;
varying vec2 v_texCoord;

float smoothing = 0.25 / (u_spread * u_scale);

void main() {
    float distance = texture2D(u_texture, v_texCoord).a;
    float alpha = smoothstep(0.5 - smoothing, 0.5 + smoothing, distance);
    gl_FragColor = vec4(v_color.rgb, alpha);
}