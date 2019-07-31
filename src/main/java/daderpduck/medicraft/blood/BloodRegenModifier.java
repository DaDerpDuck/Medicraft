package daderpduck.medicraft.blood;

import java.util.UUID;

public class BloodRegenModifier {
	private final UUID id;
	private float modifier;

	public BloodRegenModifier(UUID id, float modifier) {
		this.id = id;
		this.modifier = modifier;
	}

	public void setModifier(float newValue) {
		modifier = newValue;
	}

	public float getModifier() {
		return modifier;
	}

	public UUID getId() {
		return id;
	}
}
