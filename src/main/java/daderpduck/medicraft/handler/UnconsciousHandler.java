package daderpduck.medicraft.handler;

import daderpduck.medicraft.base.CustomPotion;
import daderpduck.medicraft.capabilities.*;
import daderpduck.medicraft.effects.injuries.shaders.UnconsciousShaders;
import daderpduck.medicraft.entities.EntityUnconsciousBody;
import daderpduck.medicraft.init.ModPotions;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.LinkedList;

/**
 * Handles events when player is unconscious
 */
public class UnconsciousHandler {
	private boolean isUnconscious(EntityPlayer player) {
		IUnconscious unconscious = player.getCapability(UnconsciousCapability.CAP_UNCONSCIOUS, null);
		assert unconscious != null;

		return unconscious.getUnconscious();
	}

	@SuppressWarnings("WeakerAccess")
	public static void setUnconscious(EntityPlayerMP player) {
		IUnconscious unconscious = player.getCapability(UnconsciousCapability.CAP_UNCONSCIOUS, null);
		assert unconscious != null;
		unconscious.setUnconscious(true);
		unconscious.setLastPos(player.posX, player.posY, player.posZ);
		unconscious.sync(player);

		IBlood blood = player.getCapability(BloodCapability.CAP_BLOOD, null);
		assert blood != null;
		blood.setOxygen(blood.getMaxOxygen());
		blood.setBlood(blood.getMaxBlood());
		blood.sync(player);

		IDrug drug = player.getCapability(DrugCapability.CAP_DRUG, null);
		assert drug != null;
		drug.setDrugs(new LinkedList<>());
		drug.sync(player);

		MinecraftServer server = player.getServer();
		assert server != null;

		for (CustomPotion potion : ModPotions.POTIONS) {

			//Remove modifiers
			if (potion.getAttribute() != null && potion.getAttributeModifier() != null) {
				IAttributeInstance attribute = player.getEntityAttribute(potion.getAttribute());

				if (attribute.getModifier(potion.getAttributeModifier().getID()) != null)
					attribute.removeModifier(potion.getAttributeModifier());
			}
		}

		World world = player.getEntityWorld();

		player.setHealth(player.getMaxHealth());
		player.clearActivePotions();
		player.setEntityInvulnerable(true);

		EntityUnconsciousBody unconsciousBody = new EntityUnconsciousBody(player);
		unconsciousBody.forceSpawn = true;
		unconsciousBody.rotationYaw = player.rotationYaw;
		unconsciousBody.setCustomNameTag(player.getName());
		world.spawnEntity(unconsciousBody);
		world.updateEntityWithOptionalForce(unconsciousBody, false);

		player.getServerWorld().getEntityTracker().removePlayerFromTrackers(player);
		player.getServerWorld().getEntityTracker().untrack(player);
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onAttackEntity(AttackEntityEvent event) {
		if (isUnconscious(event.getEntityPlayer())) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onBreak(BlockEvent.BreakEvent event) {
		if (isUnconscious(event.getPlayer())) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		if (isUnconscious(event.getEntityPlayer())) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
		if (isUnconscious(event.getEntityPlayer())) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
		if (isUnconscious(event.getEntityPlayer())) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
		if (isUnconscious(event.getEntityPlayer())) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onPickupItem(EntityItemPickupEvent event) {
		if (isUnconscious(event.getEntityPlayer())) {
			event.setCanceled(true);
		}
	}

	//Make player invisible to mobs
	@SubscribeEvent
	public void onVisibilityPlayer(PlayerEvent.Visibility event) {
		if (isUnconscious(event.getEntityPlayer())) {
			event.modifyVisibility(0D);
		}
	}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {

		if (event.side == Side.SERVER) {
			if (isUnconscious(event.player)) {
				FoodStats foodStats = event.player.getFoodStats();
				foodStats.setFoodLevel(20);
				foodStats.setFoodSaturationLevel(5F);
				event.player.timeUntilPortal = Integer.MAX_VALUE;
			}
		} else if (event.side == Side.CLIENT) {
			if (isUnconscious(event.player)) {
				UnconsciousShaders.STATIC_OVERLAY.opacity = 0.4F;
				UnconsciousShaders.DESATURATE.saturation = 0.4F;
			} else {
				UnconsciousShaders.STATIC_OVERLAY.opacity = 0;
				UnconsciousShaders.DESATURATE.saturation = 1;
			}
		}
	}

	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer && isUnconscious((EntityPlayer) event.getEntityLiving())) {
			if (event.getAmount() >= Float.MAX_VALUE) return;
			event.setCanceled(true);
		}
	}

	//Mobs (that uses the AI system) will not target the player, in case player went unconscious while being chased
	@SubscribeEvent
	public void entityTarget(LivingSetAttackTargetEvent event) {
		if (event.getEntity() instanceof EntityLiving && event.getTarget() instanceof EntityPlayer && isUnconscious((EntityPlayer) event.getTarget())) {
			((EntityLiving) event.getEntity()).setAttackTarget(null);
		}
	}

	@SubscribeEvent
	public void onPlayerLoggedIn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
		if (isUnconscious(event.player)) {
			event.player.setDead();
		}
	}
}
