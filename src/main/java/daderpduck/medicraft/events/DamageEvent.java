package daderpduck.medicraft.events;

import daderpduck.medicraft.capabilities.IUnconscious;
import daderpduck.medicraft.capabilities.UnconsciousCapability;
import daderpduck.medicraft.effects.injuries.BloodLoss;
import daderpduck.medicraft.effects.injuries.BrokenLeg;
import daderpduck.medicraft.effects.injuries.Concussion;
import daderpduck.medicraft.effects.injuries.SprainedAnkle;
import daderpduck.medicraft.init.ModPotions;
import daderpduck.medicraft.network.NetworkHandler;
import daderpduck.medicraft.network.message.MessageClientSyncUnconscious;
import daderpduck.medicraft.network.message.MessageExplodeDamage;
import daderpduck.medicraft.network.message.MessageHurt;
import daderpduck.medicraft.network.message.MessagePain;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DamageEvent {
	/**
	 * Deals with damage..
	 */
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onDamageEvent(LivingDamageEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		float damage = event.getAmount();

		if (entity instanceof EntityPlayerMP) {
			if (entity.getHealth() - damage <= 0) {
				onFatalDamage(event);
				return;
			}

			calculateInjury(event);
		}
	}

	/**
	 * TODO: Simulate unconsciousness
	 */
	private void onFatalDamage(LivingDamageEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		float damage = event.getAmount();

		//Damage was too much; let them rest in peace
		if (entity.getHealth() - damage <= -20) return;

		IUnconscious unconscious = entity.getCapability(UnconsciousCapability.CAP_UNCONSCIOUS, null);
		assert unconscious != null;

		unconscious.setUnconscious(true);
		NetworkHandler.FireClient(new MessageClientSyncUnconscious(unconscious.getUnconscious()), (EntityPlayerMP) entity);

		entity.setHealth(entity.getMaxHealth());

		event.setCanceled(true);
	}

	/**
	 * Gives the player injuries depending on the damage source and amount
	 */
	private static void calculateInjury(LivingDamageEvent event) {
		EntityPlayerMP player = (EntityPlayerMP) event.getEntityLiving();
		DamageSource damageSource = event.getSource();
		float damage = event.getAmount();

		//Hurt
		NetworkHandler.FireClient(new MessageHurt(damage), player);

		//Explosion
		if (damageSource.isExplosion()) {
			NetworkHandler.FireClient(new MessageExplodeDamage(damage), player);

			float f = damage / player.getMaxHealth() / 2F;
			float rand = player.world.rand.nextFloat();

			if (f > rand) {
				new Concussion(player, 8400);
			}
		}

		//Fall damage
		if (damageSource == DamageSource.FALL) {
			float f = damage / player.getMaxHealth();
			float rand = player.world.rand.nextFloat();

			if (player.isPotionActive(ModPotions.BROKEN_LEG)) {
				//Player fell with broken legs (idiot)
				event.setAmount(damage * 2);

				NetworkHandler.FireClient(new MessagePain(2), player);
			} else {
				//Player fell without broken legs
				if (f >= 0.5F && f > rand) {
					player.sendMessage(new TextComponentTranslation("injury.medicraft.broken_leg"));
					new BrokenLeg(player, (int) (f * 12000));
					new BloodLoss(player, (int) (f * 3000));

					NetworkHandler.FireClient(new MessagePain(2), player);
				}
			}

			if (player.isPotionActive(ModPotions.SPRAINED_ANKLE)) {
				//Player fell with sprained ankle
				event.setAmount(damage * 1.1F);

				NetworkHandler.FireClient(new MessagePain(0.9F), player);
			} else {
				//Player fell without sprained ankle
				if (f < 0.5F && f > rand) {
					player.sendMessage(new TextComponentTranslation("injury.medicraft.sprained_ankle"));
					new SprainedAnkle(player, (int) (f * 6000));

					NetworkHandler.FireClient(new MessagePain(1.2F), player);
				}
			}
		}
	}
}
