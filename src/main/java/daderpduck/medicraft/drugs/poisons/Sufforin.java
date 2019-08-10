package daderpduck.medicraft.drugs.poisons;

import daderpduck.medicraft.capabilities.BloodCapability;
import daderpduck.medicraft.capabilities.IBlood;
import daderpduck.medicraft.drugs.Drug;
import net.minecraft.entity.player.EntityPlayer;

public class Sufforin extends Drug {
	public Sufforin() {
		super(2);
		setInitialDuration(120*20);
		setDurationIncrement(30*20);
		setDrugDelay(30*20);
	}

	@Override
	public void drugEffect(EntityPlayer player, int drugDuration, int amplifier) {
		IBlood blood = player.getCapability(BloodCapability.CAP_BLOOD, null);
		assert blood != null;

		blood.decreaseBlood(0.8F + (0.2F*amplifier));
	}
}
