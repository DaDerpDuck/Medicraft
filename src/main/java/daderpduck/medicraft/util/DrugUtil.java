package daderpduck.medicraft.util;

import daderpduck.medicraft.drugs.Drug;
import daderpduck.medicraft.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class DrugUtil {
	public static ItemStack getSyringeFromDrug(Drug drug, int amount) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("DrugId", drug.id);

		ItemStack syringe = new ItemStack(ModItems.SYRINGE_FILLED, amount);
		syringe.setTagCompound(tag);

		return syringe;
	}

	public static ItemStack getVialFromDrug(Drug drug, int amount) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("DrugId", drug.id);

		ItemStack vial = new ItemStack(ModItems.VIAL_FILLED, amount);
		vial.setTagCompound(tag);

		return vial;
	}
}
