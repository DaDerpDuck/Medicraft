package daderpduck.medicraft.proxy;

import daderpduck.medicraft.items.SyringeFilled;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CommonProxy {
	public void registerItemRenderer(Item item, int meta, String id) {}

	static SyringeFilled syringe;
	public void PreInit() {
		syringe = (SyringeFilled) new SyringeFilled().setUnlocalizedName("syringe_filled");
		syringe.setRegistryName("syringe_filled");
		ForgeRegistries.ITEMS.register(syringe);
	}

	public void Init() {

	}

	public void PostInit() {

	}
}
