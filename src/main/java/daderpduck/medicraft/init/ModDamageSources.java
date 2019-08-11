package daderpduck.medicraft.init;

import daderpduck.medicraft.util.Reference;
import net.minecraft.util.DamageSource;

public class ModDamageSources {
	public static final DamageSource WOUND = MakeDamageSource("wound").setDamageBypassesArmor();
	public static final DamageSource BRAIN_SWELLING = MakeDamageSource("brain_swelling").setDamageBypassesArmor();
	public static final DamageSource BLOOD_LOSS = MakeDamageSource("blood_loss").setDamageBypassesArmor().setDamageIsAbsolute();

	private static DamageSource MakeDamageSource(String str) {
		return new DamageSource(Reference.MOD_ID + "." + str);
	}
}
