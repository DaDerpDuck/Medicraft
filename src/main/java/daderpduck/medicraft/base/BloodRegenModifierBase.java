package daderpduck.medicraft.base;

import daderpduck.medicraft.blood.BloodRegenModifier;
import daderpduck.medicraft.init.ModBloodRegenModifier;

import java.util.UUID;

public class BloodRegenModifierBase extends BloodRegenModifier {

	public BloodRegenModifierBase(UUID id, float modifier) {
		super(id, modifier);

		ModBloodRegenModifier.MODIFIERS.add(this);
	}

	public BloodRegenModifierBase(String id, float modifier) {
		this(UUID.fromString(id), modifier);
	}


}
