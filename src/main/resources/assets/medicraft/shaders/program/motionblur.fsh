#version 120

uniform sampler2D DiffuseSampler;
uniform sampler2D PrevSampler;

varying vec2 texCoord;
varying vec2 oneTexel;

uniform float Intensity;

void main() {
    vec4 CurrTexel = texture2D(DiffuseSampler, texCoord);
    vec4 PrevTexel = texture2D(PrevSampler, texCoord);

    if (PrevTexel.a == 0) {
        PrevTexel = CurrTexel;
    }

    gl_FragColor = vec4(mix(CurrTexel.rgb, PrevTexel.rgb, Intensity), 1.0);
}