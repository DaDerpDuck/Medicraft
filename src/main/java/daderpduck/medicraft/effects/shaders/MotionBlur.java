package daderpduck.medicraft.effects.shaders;

import daderpduck.medicraft.shaders.Visual;

public class MotionBlur extends Visual {
	private static final String SHADERNAME = "motionblur";

	public float intensity;

	public MotionBlur(int lifeTime, float intensity) {
		super(SHADERNAME, lifeTime);

		this.intensity = intensity;
	}

	@Override
	public void onRender(float partialTicks) {
		changeUniform("Intensity", lerp(intensity, 0, getProgress()));
	}

	@Override
	public boolean canRender() {
		return intensity > 0;
	}
}
