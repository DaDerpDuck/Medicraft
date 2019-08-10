package daderpduck.medicraft.blood;

import daderpduck.medicraft.capabilities.BloodCapability;
import daderpduck.medicraft.capabilities.IBlood;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class BloodRegen {
	private static final float baseBloodRegen = .1F;
	private static final double baseOxygenRegen = .02D;

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;
		IBlood blood = player.getCapability(BloodCapability.CAP_BLOOD, null);
		assert blood != null;

		blood.increaseBlood(baseBloodRegen);
		blood.increaseOxygen(baseOxygenRegen);
	}
}
