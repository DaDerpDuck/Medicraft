package daderpduck.medicraft.effects.injuries;

import daderpduck.medicraft.init.ModPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

public class Concussion {
	public Concussion(EntityPlayer player, int duration) {
		if (player.isPotionActive(ModPotions.CONCUSSION)) {
			//Getting another concussion during a concussion is bad news
			new BrainSwelling(player, 600);
		}

		PotionEffect concussion = new PotionEffect(ModPotions.CONCUSSION, duration, 0);

		player.addPotionEffect(concussion);
	}
}
