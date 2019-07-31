package daderpduck.medicraft.init;

import daderpduck.medicraft.base.CustomPotion;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;

public class ModPotions {
	public static final ArrayList<CustomPotion> POTIONS = new ArrayList<>();

	public static final Potion BLEEDING = new CustomPotion("bleeding", true, 0, 0, 0xfb1d27)
			.hideParticles(true);
	public static final Potion BROKEN_LEG = new CustomPotion("broken_leg", true, 1, 0,0x000000)
			.setDamageOnJump(4F)
			.unPressKeysOnJump()
			.setAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, ModAttributeModifier.BROKEN_LEG_MODIFIER)
			.hideParticles(true);
	public static final Potion CONCUSSION = new CustomPotion("concussion", true, 2, 0, 0xFFFFFF)
			.hideParticles(true);
	public static final Potion BRAIN_SWELLING = new CustomPotion("brain_swelling", true, 3, 0, 0xFF0000)
			.hideParticles(true);
	public static final Potion BRAIN_HEMORRHAGE = new CustomPotion("brain_hemorrhage", true, 4, 0, 0xFF00000)
			.hideParticles(true);
	public static final Potion SPRAINED_ANKLE = new CustomPotion("sprained_ankle", true, 5, 0, 0xFF0000)
			.unPressKeysOnJump()
			.setAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, ModAttributeModifier.SPRAINED_ANKLE_MODIFIER)
			.hideParticles(true);

	public static void registerPotions() {
		for (Potion effect : POTIONS) {
			ForgeRegistries.POTIONS.register(effect);
		}
	}
}
