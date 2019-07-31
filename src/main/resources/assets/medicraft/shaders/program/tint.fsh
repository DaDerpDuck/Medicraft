#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;

uniform float Opacity;
uniform vec3 Color;

void main() {
    vec4 tex = texture2D(DiffuseSampler, texCoord);

    gl_FragColor = vec4(mix(tex.rgb, Color, Opacity), 1.0);
}