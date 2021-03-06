package daderpduck.medicraft.items;

import daderpduck.medicraft.Main;
import daderpduck.medicraft.init.ModItems;
import daderpduck.medicraft.util.IHasModel;
import net.minecraft.item.Item;

public class VialEmpty extends Item implements IHasModel {
	public VialEmpty(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxStackSize(64);
		setCreativeTab(Main.MEDICRAFT_TAB);

		ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
