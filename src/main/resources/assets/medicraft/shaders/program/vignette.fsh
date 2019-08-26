#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;

uniform vec3 Color;
uniform float Strength;

void main() {
	vec4 tex = texture2D(DiffuseSampler, texCoord);
	vec2 centeredCoord = texCoord - 0.5;

	float distance = sqrt(dot(centeredCoord, centeredCoord));

	gl_FragColor = vec4(mix(tex.rgb, Color, distance*Strength), 1.0);
}