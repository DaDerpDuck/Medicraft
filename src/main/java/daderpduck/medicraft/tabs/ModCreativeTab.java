package daderpduck.medicraft.tabs;

import daderpduck.medicraft.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class ModCreativeTab extends CreativeTabs {
	public ModCreativeTab(String label, String texture) {
		super(label);
		setBackgroundImageName(texture);
	}

	@Nonnull
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.BANDAGE);
	}
}
