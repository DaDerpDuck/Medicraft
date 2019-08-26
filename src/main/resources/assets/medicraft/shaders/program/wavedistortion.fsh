#version 120

#define PI 3.1415926535897932384626433832795

uniform sampler2D DiffuseSampler;
uniform float Time;

varying vec2 texCoord;

uniform vec2 Frequency;
uniform vec2 Amplitude;

void main() {
	float xOffset = cos(texCoord.y*Frequency.x + Time*PI*2.0)*Amplitude.x;
	float yOffset = cos(texCoord.x*Frequency.y + Time*PI*2.0)*Amplitude.y;
	vec2 offset = vec2(xOffset, yOffset);

	vec4 color = texture2D(DiffuseSampler, texCoord + offset);

	gl_FragColor = vec4(color.rgb, 1.0);
}