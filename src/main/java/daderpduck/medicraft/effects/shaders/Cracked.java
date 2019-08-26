package daderpduck.medicraft.effects.shaders;

import daderpduck.medicraft.shaders.Visual;

import javax.vecmath.Vector2f;

public class Cracked extends Visual {
	private static final String SHADERNAME = "cracked";

	public Vector2f center;
	public float angleOffset;
	public float size;
	public float crackScale;
	public float lineOpacity;

	public Cracked(int lifeTime, Vector2f center, float angleOffset, float size, float crackScale, float lineOpacity) {
		super(SHADERNAME, lifeTime);

		this.center = center;
		this.angleOffset = angleOffset;
		this.size = size;
		this.crackScale = crackScale;
		this.lineOpacity = lineOpacity;
	}

	@Override
	public void onRender(float partialTicks) {
		changeUniform("Center", center.x, center.y);
		changeUniform("AngleOffset", angleOffset);
		changeUniform("Size", size);
		changeUniform("CrackScale", crackScale);
		changeUniform("LineOpacity", lineOpacity);
	}

	@Override
	public boolean canRender() {
		return crackScale > 0 && lineOpacity > 0;
	}
}
