package daderpduck.medicraft.drugs.poisons;

import daderpduck.medicraft.capabilities.BloodCapability;
import daderpduck.medicraft.capabilities.IBlood;
import daderpduck.medicraft.drugs.Drug;
import daderpduck.medicraft.init.ModDrugs;
import net.minecraft.entity.player.EntityPlayer;

public class Cyanide extends Drug {
	public Cyanide() {
		super(-1);
		setInitialDuration(80*20);
		setDurationIncrement(20*20);
		setDrugDelay(40*20);
	}

	@Override
	public void drugEffect(EntityPlayer player, int drugDuration, int amplifier) {
		IBlood blood = player.getCapability(BloodCapability.CAP_BLOOD, null);
		assert blood != null;

		// 1 injection will kill in 76 seconds
		blood.decreaseOxygen((4/3D) + (.35D*amplifier));
	}

	@Override
	public boolean otherDrugEffect(EntityPlayer player, Drug drug) {
		return drug != ModDrugs.OXYGEN_MICROPARTICLES;
	}
}
