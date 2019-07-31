#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;
varying vec2 oneTexel;

uniform vec2 InSize;

uniform vec2 BlurDir;
uniform float Radius;

void main() {
    vec4 blurred = vec4(0.0);
    float totalStrength = 0.0;
    float totalAlpha = 0.0;
    float totalSamples = 0.0;
    float radius = floor(Radius);

    for(float r = -radius; r <= radius; r += 1.0) {
        vec4 color = texture2D(DiffuseSampler, texCoord + oneTexel * r * BlurDir);

		// Accumulate average alpha
        totalAlpha += color.a;
        totalSamples += 1.0;

		// Accumulate smoothed blur
        float strength = 1.0 - abs(r / radius);
        totalStrength += strength;
        blurred += color;
    }
    gl_FragColor = vec4(blurred.rgb / (radius * 2.0 + 1.0), totalAlpha);
}
