package daderpduck.medicraft.effects.shaders;

import daderpduck.medicraft.shaders.Visual;

public class StaticOverlay extends Visual {
	private static final String SHADERNAME = "staticoverlay";

	public float opacity;

	public StaticOverlay(int lifeTime, float opacity) {
		super(SHADERNAME, lifeTime);

		this.opacity = opacity;
	}

	@Override
	public void onRender(float partialTicks) {
		changeUniform("Opacity", opacity);
	}

	@Override
	public boolean canRender() {
		return opacity > 0;
	}
}
