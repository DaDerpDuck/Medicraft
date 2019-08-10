//Taken from Psychedelicaft
#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;

uniform float Intensity;
uniform float Distance;

void main() {
    vec3 col = texture2D(DiffuseSampler, texCoord).rgb;
    vec3 newColor = col*0.35;

    newColor += texture2D(DiffuseSampler, vec2(0.5 + (texCoord.s - 0.5)/(Intensity + 1.0) + Distance, texCoord.t)).rgb*0.325;
    newColor += texture2D(DiffuseSampler, vec2(0.5 + (texCoord.s - 0.5)/(Intensity + 1.0) - Distance, texCoord.t)).rgb*0.325;

    gl_FragColor = vec4(mix(col, newColor, Intensity), 1.0);
}