package daderpduck.medicraft.effects.injuries;

import daderpduck.medicraft.init.ModPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;

public class SprainedAnkle {

	public SprainedAnkle(EntityPlayer player, int duration) {
		PotionEffect sprainedAnkleEffect = new PotionEffect(ModPotions.SPRAINED_ANKLE, duration, -1);
		sprainedAnkleEffect.setCurativeItems(new ArrayList<>());

		player.addPotionEffect(sprainedAnkleEffect);
	}
}
