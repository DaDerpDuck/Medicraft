#version 120

uniform sampler2D DiffuseSampler;
uniform float Time;

varying vec2 texCoord;

uniform float Opacity;

float rand(vec2 co) {
    return fract(sin(dot(co, vec2(12.9898,78.233)))*43758.5453);
}

void main() {
    vec4 tex = texture2D(DiffuseSampler, texCoord);
    vec2 centeredCoord = texCoord-0.5;

    vec2 staticCoords = floor(texCoord*255.0) + mod(Time, 1.0);
    vec3 staticColor = vec3(rand(staticCoords));
    float distance = sqrt(dot(centeredCoord, centeredCoord));

    vec3 vignetteStatic = mix(tex.rgb, staticColor, distance*0.7);

    gl_FragColor = vec4(mix(tex.rgb, vignetteStatic, Opacity), 1.0);
}