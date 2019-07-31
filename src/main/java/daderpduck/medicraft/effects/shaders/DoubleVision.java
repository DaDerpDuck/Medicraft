package daderpduck.medicraft.effects.shaders;

import daderpduck.medicraft.shaders.Visual;

public class DoubleVision extends Visual {
	private static final String SHADERNAME = "doublevision";

	public float distance;
	public float intensity;

	public DoubleVision(int lifeTime, float distance, float intensity) {
		super(SHADERNAME, lifeTime);

		this.distance = distance;
		this.intensity = intensity;
	}

	@Override
	public void onRender(float partialTicks) {
		changeUniform("Distance", lerp(distance, 0, getProgress()));
		changeUniform("Intensity", lerp(intensity, 0, getProgress()));
	}

	@Override
	public boolean canRender() {
		return distance != 0 && intensity != 0;
	}
}
