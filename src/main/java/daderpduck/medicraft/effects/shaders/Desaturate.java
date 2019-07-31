package daderpduck.medicraft.effects.shaders;

import daderpduck.medicraft.shaders.Visual;

public class Desaturate extends Visual {
	private static final String SHADERNAME = "desaturate";

	public float saturation;

	public Desaturate(int lifeTime, float saturation) {
		super(SHADERNAME, lifeTime);

		this.saturation = saturation;
	}

	@Override
	public void onRender(float partialTicks) {
		changeUniform("Saturation", saturation);
	}

	@Override
	public boolean canRender() {
		return saturation != 1;
	}
}
