package daderpduck.medicraft.init;

import daderpduck.medicraft.base.AttributeModifierBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;

public class ModAttributeModifier {
	public static final AttributeModifier BROKEN_LEG_MODIFIER = new AttributeModifierBase("9C8C4010-71BF-403F-A875-CE6AC829C585", "Broken Leg Modifier", -0.95)
			.excludeFromFOV()
			.setSaved(false);
	public static final AttributeModifier SPRAINED_ANKLE_MODIFIER = new AttributeModifierBase("041CE8D7-B612-4EAE-B05E-F96F8DD203DB", "Sprained Ankle Modifier", -0.5)
			.excludeFromFOV()
			.setSaved(false);
}
