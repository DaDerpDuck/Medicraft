//https://www.shadertoy.com/view/XdBSzW
#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;

uniform vec2 Center;
uniform float AngleOffset;
uniform float Size;
uniform float CrackScale;
uniform float LineOpacity;

float rand(vec2 co) {
	return fract(sin(dot(co, vec2(12.9898, 78.233)))*43758.5453);
}

void main() {
	vec2 resolution = gl_FragCoord.xy/texCoord.xy;
	vec2 p = (gl_FragCoord.xy*2. - resolution.xy)/resolution.x;

	vec2 v = vec2(1E3);
	vec2 v2 = vec2(1E4);
	vec2 center = Center - .5;
	for(int c = 0; c < 90; c++) {
		float angle = floor(rand(vec2(float(c), 387.44))*16.)*3.1415*.4 - AngleOffset;
		float dist = pow(rand(vec2(float(c), 78.21)), 2.)*Size;
		vec2 vc = vec2(center.x + cos(angle)*dist + rand(vec2(float(c), 349.3))*7E-3, center.y + sin(angle)*dist + rand(vec2(float(c), 912.7))*7E-3);
		if (length(vc - p) < length(v - p)) {
			v2 = v;
			v = vc;
		} else if (length(vc - p) < length(v2 - p)) {
			v2 = vc;
		}
	}

	float col = abs(length(dot(p - v, normalize(v - v2))) - length(dot(p - v2, normalize(v - v2)))) + .002*length(p - center);
	col = 7E-4*LineOpacity/col;
	if (length(v - v2) < 4E-3) col = 0.;
	if (col < .3) col = 0.;
	vec4 tex = texture2D(DiffuseSampler, texCoord + rand(v)*(CrackScale/100.));
	gl_FragColor = vec4((col*vec4(vec3(1. - tex.xyz), 1.) + (1. - col)*tex).xyz, 1.);
}