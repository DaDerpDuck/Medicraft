package daderpduck.medicraft.effects.shaders;

import daderpduck.medicraft.shaders.Visual;

public class Blobs2 extends Visual {
	private static final String SHADERNAME = "blobs2";

	public int radius;

	public Blobs2(int lifeTime, int radius) {
		super(SHADERNAME, lifeTime);

		this.radius = radius;
	}

	@Override
	public void onRender(float partialTicks) {
		changeUniform("Radius", lerp(radius, 0, getProgress()));
	}

	@Override
	public boolean canRender() {
		return radius > 0;
	}
}
