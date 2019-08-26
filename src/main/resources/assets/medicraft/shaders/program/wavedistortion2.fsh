#version 120

uniform sampler2D DiffuseSampler;
uniform float Time;

varying vec2 texCoord;

uniform vec2 Frequency;
uniform vec2 Amplitude;

void main() {
	float X = texCoord.x*Frequency.x + Time;
	float Y = texCoord.y*Frequency.y + Time;
	float xOffset = cos(X + Y)*Amplitude.x;
	float yOffset = sin(X - Y)*Amplitude.y;
	vec2 offset = vec2(xOffset, yOffset);

	vec4 color = texture2D(DiffuseSampler, texCoord + offset);

	gl_FragColor = vec4(color.rgb, 1.0);
}