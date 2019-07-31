package daderpduck.medicraft.effects.shaders;

import daderpduck.medicraft.shaders.Visual;
import org.lwjgl.util.vector.Vector3f;

public class Vignette extends Visual {
	private static final String SHADERNAME = "vignette";

	public Vector3f color;
	public float strength;

	public Vignette(int lifeTime, Vector3f color, float strength) {
		super(SHADERNAME, lifeTime);

		this.color = color;
		this.strength = strength;
	}

	@Override
	public void onRender(float partialTicks) {
		changeUniform("Strength", lerp(strength, 0, getProgress()));
		changeUniform("Color", color.x, color.y, color.z);
	}

	@Override
	public boolean canRender() {
		return strength > 0;
	}
}
