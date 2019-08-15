package daderpduck.medicraft.proxy;

import daderpduck.medicraft.base.DrugType;
import daderpduck.medicraft.entities.EntityUnconsciousBody;
import daderpduck.medicraft.init.ModDrugTypes;
import daderpduck.medicraft.init.ModItems;
import daderpduck.medicraft.items.SyringeFilled;
import daderpduck.medicraft.items.VialFilled;
import daderpduck.medicraft.util.BodyRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import java.util.Objects;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), id));
	}

	public void PreInit() {
		super.PreInit();

		RenderingRegistry.registerEntityRenderingHandler(EntityUnconsciousBody.class, BodyRenderer::new);

		for (DrugType drugType : ModDrugTypes.DRUG_TYPES) {
			//Syringe
			ModelResourceLocation syringeModelResourceLocation = new ModelResourceLocation(Objects.requireNonNull(ModItems.SYRINGE_FILLED.getRegistryName()), "inventory");
			ModelLoader.setCustomModelResourceLocation(ModItems.SYRINGE_FILLED, drugType.getId(), syringeModelResourceLocation);

			//Vial
			ModelResourceLocation vialModelResourceLocation = new ModelResourceLocation(Objects.requireNonNull(ModItems.VIAL_FILLED.getRegistryName()), "inventory");
			ModelLoader.setCustomModelResourceLocation(ModItems.VIAL_FILLED, drugType.getId(), vialModelResourceLocation);
		}
	}

	public void Init() {
		super.Init();

	}

	public void PostInit() {
		super.PostInit();

		Minecraft mc = Minecraft.getMinecraft();

		mc.getItemColors().registerItemColorHandler(new SyringeFilled.liquidColor(), ModItems.SYRINGE_FILLED);
		mc.getItemColors().registerItemColorHandler(new VialFilled.liquidColor(), ModItems.VIAL_FILLED);
	}
}
