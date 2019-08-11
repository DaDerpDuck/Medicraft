package daderpduck.medicraft.events;

import daderpduck.medicraft.base.DrugType;
import daderpduck.medicraft.capabilities.DrugCapability;
import daderpduck.medicraft.capabilities.IDrug;
import daderpduck.medicraft.items.SyringeFilled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SyringeAttack {
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer && event.getSource().getImmediateSource() instanceof EntityPlayer) {
			EntityPlayer attacker = (EntityPlayer) event.getSource().getTrueSource();
			EntityPlayer victim = (EntityPlayer) event.getEntityLiving();

			if (attacker != null && !attacker.getHeldItemMainhand().isEmpty() && attacker.getHeldItemMainhand().getItem() instanceof SyringeFilled) {
				ItemStack stack = attacker.getHeldItemMainhand();
				DrugType drugType = DrugType.getDrugTypeFromNBT(stack);

				if (drugType != null && drugType.getDrug() != null) {
					drugType.getDrug().drugPlayer(victim);

					IDrug drugCap = victim.getCapability(DrugCapability.CAP_DRUG, null);
					assert drugCap != null;
					drugCap.sync((EntityPlayerMP) victim);
				}

			}
		}
	}
}
