package daderpduck.medicraft.poisons;

import daderpduck.medicraft.capabilities.IPoison;
import daderpduck.medicraft.capabilities.PoisonCapability;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class PoisonHandler {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			IPoison poisonCap = event.player.getCapability(PoisonCapability.CAP_POISON, null);
			assert poisonCap != null;

			for (Poison.PoisonEffect poisonEffect : poisonCap.getPoisons()) {
				if (poisonEffect.poisonDelay >= 0) {
					poisonEffect.poisonDelay--;
					continue;
				}

				poisonEffect.poison.poisonEffect(event.player, poisonEffect.poisonDuration--, poisonEffect.amplifier);
			}
		}
	}
}
