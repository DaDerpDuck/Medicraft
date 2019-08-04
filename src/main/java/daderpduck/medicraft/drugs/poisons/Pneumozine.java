package daderpduck.medicraft.drugs.poisons;

import daderpduck.medicraft.drugs.Drug;
import net.minecraft.entity.player.EntityPlayer;

public class Pneumozine extends Drug {
	public Pneumozine() {
		super(2, 60*20, 20*20, 5*20);
	}

	@Override
	public void drugEffect(EntityPlayer player, int drugDuration, int amplifier) {

	}
}
