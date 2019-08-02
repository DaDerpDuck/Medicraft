package daderpduck.medicraft.items;

import daderpduck.medicraft.base.Medicine;
import daderpduck.medicraft.init.ModMedicines;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.awt.*;

public class SyringeFilled extends Item implements IItemColor {

	public SyringeFilled(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(1);

		setCreativeTab(CreativeTabs.MATERIALS);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> subItems) {
		for (Medicine medicine : ModMedicines.MEDICINES) {
			int metadata = medicine.getId();
			ItemStack subItemStack = new ItemStack(this, 1, metadata);
			subItems.add(subItemStack);
		}
	}

	@Nonnull
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int metadata = stack.getMetadata();

		for (Medicine medicine : ModMedicines.MEDICINES) {
			if (medicine.getId() == metadata)
				return super.getUnlocalizedName() + "." + medicine.getName();
		}

		return super.getUnlocalizedName();
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public int colorMultiplier(@Nonnull ItemStack stack, int tintIndex) {
		switch(tintIndex) {
			case 0: return Color.WHITE.getRGB();
			case 1: {
				int metadata = stack.getMetadata();
				for (Medicine medicine : ModMedicines.MEDICINES) {
					if (medicine.getId() == metadata) return medicine.getColor();
				}
			}
			default: return Color.BLACK.getRGB();
		}
	}
}
