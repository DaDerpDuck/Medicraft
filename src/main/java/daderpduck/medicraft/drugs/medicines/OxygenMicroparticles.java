package daderpduck.medicraft.drugs.medicines;

import daderpduck.medicraft.capabilities.BloodCapability;
import daderpduck.medicraft.capabilities.DrugCapability;
import daderpduck.medicraft.capabilities.IBlood;
import daderpduck.medicraft.capabilities.IDrug;
import daderpduck.medicraft.drugs.Drug;
import net.minecraft.entity.player.EntityPlayer;

public class OxygenMicroparticles extends Drug {
	public OxygenMicroparticles() {
		super();
	}

	@Override
	public void drugEffect(EntityPlayer player, int drugDuration, int amplifier) {
		IDrug drug = player.getCapability(DrugCapability.CAP_DRUG, null);
		assert drug != null;

		IBlood blood = player.getCapability(BloodCapability.CAP_BLOOD, null);
		assert blood != null;

		blood.increaseOxygen(15);

		if (player.getAir() < 300) {
			player.setAir(Math.min(player.getAir() + 150, 300));
		}
	}
}
