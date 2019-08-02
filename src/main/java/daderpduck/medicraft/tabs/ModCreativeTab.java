package daderpduck.medicraft.tabs;

import daderpduck.medicraft.init.ModItems;
import daderpduck.medicraft.util.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class ModCreativeTab extends CreativeTabs {
	public ModCreativeTab(String label) {
		super(label);
		setBackgroundImageName(Reference.MOD_ID + ".png");
	}

	@Nonnull
	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.BANDAGE);
	}
}
