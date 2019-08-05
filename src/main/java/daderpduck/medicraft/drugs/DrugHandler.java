package daderpduck.medicraft.drugs;

import daderpduck.medicraft.capabilities.DrugCapability;
import daderpduck.medicraft.capabilities.IDrug;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class DrugHandler {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			IDrug drugCap = event.player.getCapability(DrugCapability.CAP_DRUG, null);
			assert drugCap != null;

			for (Drug.DrugEffect drugEffect : drugCap.getAllDrugs()) {
				if (drugEffect.drugDelay >= 0) {
					drugEffect.drug.preDrugEffect(event.player, drugEffect.drugDelay--, drugEffect.amplifier);
					continue;
				}

				drugEffect.drug.drugEffect(event.player, drugEffect.drugDuration--, drugEffect.amplifier);

				if (drugEffect.drugDuration <= 0) drugEffect.drug.removeDrugFromPlayer(event.player);
			}
		}
	}
}
