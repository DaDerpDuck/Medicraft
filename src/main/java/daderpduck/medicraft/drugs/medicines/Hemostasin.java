package daderpduck.medicraft.drugs.medicines;

import daderpduck.medicraft.drugs.Drug;
import daderpduck.medicraft.init.ModPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

public class Hemostasin extends Drug {
	public Hemostasin() {
		super();
		setInitialDuration(10*20);
		setDurationIncrement(10*20);
	}

	@Override
	public void drugEffect(EntityPlayer player, int drugDuration, int amplifier) {
		PotionEffect bleedingEffect = player.getActivePotionEffect(ModPotions.BLEEDING);

		if (bleedingEffect != null) {

			try {
				Field duration = ReflectionHelper.findField(PotionEffect.class, "duration", "field_76460_b");
				duration.setInt(bleedingEffect, duration.getInt(bleedingEffect)-10);
			} catch (IllegalAccessException ignored) {}

		}
	}
}
