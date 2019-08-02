package daderpduck.medicraft.blood;

import daderpduck.medicraft.capabilities.BloodCapability;
import daderpduck.medicraft.capabilities.IBlood;
import daderpduck.medicraft.init.ModBloodRegenModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

@Mod.EventBusSubscriber
public class BloodRegen {
	private static final float baseRegen = .2F;
	private static final HashMap<EntityPlayer, LinkedList<BloodRegenModifier>> bloodModifiers = new HashMap<>();

	public static void addModifier(EntityPlayer player, BloodRegenModifier modifier) {
		bloodModifiers.get(player).add(modifier);
	}

	public static void removeModifier(EntityPlayer player, UUID id) {
		for (BloodRegenModifier modifier : bloodModifiers.get(player)) {
			if (modifier.getId() == id) {
				bloodModifiers.get(player).remove(modifier);
				break;
			}
		}
	}

	@Nullable
	public static BloodRegenModifier getBloodRegenModifier(EntityPlayer player, UUID id) {
		System.out.println(bloodModifiers.get(player));
		for (BloodRegenModifier modifier : bloodModifiers.get(player)) {
			if (modifier.getId() == id) {
				return modifier;
			}
		}
		return null;
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;
		IBlood blood = player.getCapability(BloodCapability.CAP_BLOOD, null);

		if (bloodModifiers.get(player) == null) return;

		float toRegen = baseRegen;
		for (BloodRegenModifier modifier : bloodModifiers.get(player)) {
			toRegen += modifier.getModifier();
		}

		assert blood != null;
		blood.increase(toRegen);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		bloodModifiers.put(event.player, ModBloodRegenModifier.MODIFIERS);
	}

	@SubscribeEvent
	public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
		bloodModifiers.remove(event.player);
	}
}
