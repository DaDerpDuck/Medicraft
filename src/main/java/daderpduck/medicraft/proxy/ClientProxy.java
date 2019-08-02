package daderpduck.medicraft.proxy;

import daderpduck.medicraft.base.Medicine;
import daderpduck.medicraft.init.ModMedicines;
import daderpduck.medicraft.items.SyringeFilled;
import daderpduck.medicraft.items.VialFilled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

import java.util.Objects;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), id));
	}

	public void PreInit() {
		super.PreInit();

		//Syringe
		for (Medicine medicine : ModMedicines.MEDICINES) {
			ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(Objects.requireNonNull(syringe.getRegistryName()), "inventory");
			ModelLoader.setCustomModelResourceLocation(CommonProxy.syringe, medicine.getId(), itemModelResourceLocation);
		}

		//Vial
		for (Medicine medicine : ModMedicines.MEDICINES) {
			ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(Objects.requireNonNull(vial.getRegistryName()), "inventory");
			ModelLoader.setCustomModelResourceLocation(CommonProxy.vial, medicine.getId(), itemModelResourceLocation);
		}
	}

	public void Init() {
		super.Init();

	}

	public void PostInit() {
		super.PostInit();

		Minecraft mc = Minecraft.getMinecraft();

		mc.getItemColors().registerItemColorHandler(new SyringeFilled.liquidColor(), CommonProxy.syringe);
		mc.getItemColors().registerItemColorHandler(new VialFilled.liquidColor(), CommonProxy.vial);
	}
}
