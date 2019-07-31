package daderpduck.medicraft.base;

import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.ArrayList;
import java.util.UUID;

public class AttributeModifierBase extends AttributeModifier {
	private static final ArrayList<UUID> excludeFromFOVList = new ArrayList<>();

	public static ArrayList<UUID> getExcludeFromFOV() {
		return excludeFromFOVList;
	}

	public AttributeModifierBase(String uuid, String name, double value) {
		super(UUID.fromString(uuid), name, value, 2);
	}
	public AttributeModifierBase(String uuid, String name, double value, int operation) {
		super(UUID.fromString(uuid), name, value, operation);
	}

	public AttributeModifierBase excludeFromFOV() {
		excludeFromFOVList.add(this.getID());

		return this;
	}
}
