package daderpduck.medicraft.items;

import daderpduck.medicraft.Main;
import daderpduck.medicraft.init.ModItems;
import daderpduck.medicraft.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class SyringeEmpty extends Item implements IHasModel {

	public SyringeEmpty() {
		setUnlocalizedName("syringe_empty");
		setRegistryName("syringe_empty");
		setMaxStackSize(16);
		setCreativeTab(CreativeTabs.MATERIALS);

		ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this,0,"inventory");
	}
}
