package daderpduck.medicraft.drugs.antidotes;

import daderpduck.medicraft.capabilities.DrugCapability;
import daderpduck.medicraft.capabilities.IDrug;
import daderpduck.medicraft.drugs.Drug;
import daderpduck.medicraft.init.ModDrugs;
import net.minecraft.entity.player.EntityPlayer;

public class CyanideAntidote extends Drug {
	public CyanideAntidote() {
		super();
		setDrugDelay(2*20);
		setInitialDuration(10*20);
		setDurationIncrement(10*20);
	}

	@Override
	public void drugEffect(EntityPlayer player, int drugDuration, int amplifier) {
		IDrug drugCap = player.getCapability(DrugCapability.CAP_DRUG, null);
		assert drugCap != null;

		DrugEffect cyanideEffect = drugCap.getActiveDrug(ModDrugs.CYANIDE);
		if (cyanideEffect != null) {
			cyanideEffect.drugDuration -= 4;
		}
	}
}
