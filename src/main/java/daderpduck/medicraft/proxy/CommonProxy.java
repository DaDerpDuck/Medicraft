package daderpduck.medicraft.proxy;

import daderpduck.medicraft.items.SyringeFilled;
import daderpduck.medicraft.items.VialFilled;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CommonProxy {
	public void registerItemRenderer(Item item, int meta, String id) {}

	static SyringeFilled syringe;
	static VialFilled vial;

	public void PreInit() {
		//Syringe
		syringe = new SyringeFilled("syringe_filled");
		ForgeRegistries.ITEMS.register(syringe);

		//Vial
		vial = new VialFilled("vial_filled");
		ForgeRegistries.ITEMS.register(vial);
	}

	public void Init() {

	}

	public void PostInit() {

	}
}
