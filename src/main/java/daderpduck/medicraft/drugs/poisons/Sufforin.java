package daderpduck.medicraft.drugs.poisons;

import daderpduck.medicraft.capabilities.BloodCapability;
import daderpduck.medicraft.capabilities.IBlood;
import daderpduck.medicraft.drugs.Drug;
import net.minecraft.entity.player.EntityPlayer;

public class Sufforin extends Drug {
	public Sufforin() {
		super(0);
		setInitialDuration(300*20);
		setDurationIncrement(30*20);
		setDrugDelay(300*20);
	}

	@Override
	public void drugEffect(EntityPlayer player, int drugDuration, int amplifier) {
		IBlood blood = player.getCapability(BloodCapability.CAP_BLOOD, null);
		assert blood != null;

		//Injection kills in 319 seconds
		blood.decreaseOxygen(1/3D);
	}
}
