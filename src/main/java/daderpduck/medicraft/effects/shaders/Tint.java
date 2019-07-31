package daderpduck.medicraft.effects.shaders;

import daderpduck.medicraft.shaders.Visual;
import org.lwjgl.util.vector.Vector3f;

public class Tint extends Visual {
	private static final String SHADERNAME = "tint";

	public Vector3f color;
	public float opacity;

	public Tint(int lifeTime, Vector3f color, float opacity) {
		super(SHADERNAME, lifeTime);

		this.color = color;
		this.opacity = opacity;
	}

	@Override
	public void onRender(float partialTicks) {
		changeUniform("Opacity", lerp(opacity, 0, getProgress()));
		changeUniform("Color", color.x, color.y, color.z);
	}

	@Override
	public boolean canRender() {
		return opacity > 0;
	}
}
