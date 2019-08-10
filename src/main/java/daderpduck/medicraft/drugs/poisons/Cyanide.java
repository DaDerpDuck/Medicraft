package daderpduck.medicraft.drugs.poisons;

import daderpduck.medicraft.capabilities.BloodCapability;
import daderpduck.medicraft.capabilities.IBlood;
import daderpduck.medicraft.drugs.Drug;
import net.minecraft.entity.player.EntityPlayer;

public class Cyanide extends Drug {
	public Cyanide() {
		super(2);
		setInitialDuration(60*20);
		setDurationIncrement(20*20);
		setDrugDelay(5*20);
	}

	@Override
	public void drugEffect(EntityPlayer player, int drugDuration, int amplifier) {
		IBlood blood = player.getCapability(BloodCapability.CAP_BLOOD, null);
		assert blood != null;

		blood.decreaseOxygen(.045D + (.07D*amplifier));
	}
}
