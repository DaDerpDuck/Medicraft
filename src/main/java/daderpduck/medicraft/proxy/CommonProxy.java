package daderpduck.medicraft.proxy;

import daderpduck.medicraft.base.DrugType;
import daderpduck.medicraft.init.ModDrugTypes;
import daderpduck.medicraft.init.ModItems;
import daderpduck.medicraft.items.SyringeFilled;
import daderpduck.medicraft.items.VialFilled;
import daderpduck.medicraft.util.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	public void registerItemRenderer(Item item, int meta, String id) {
	}

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
		//Automatically create recipes for filled syringes
		for (DrugType drugType : ModDrugTypes.DRUG_TYPES) {
			//Filling a syringe
			GameRegistry.addShapelessRecipe(
					new ResourceLocation(Reference.MOD_ID, "recipe_"+drugType.getName()),
					new ResourceLocation(""),
					new ItemStack(syringe,1, drugType.getId()),

					Ingredient.fromStacks(new ItemStack(ModItems.SYRINGE_EMPTY)),
					Ingredient.fromStacks(new ItemStack(vial, 1, drugType.getId()))
			);
			//Emptying contents of a syringe
			GameRegistry.addShapelessRecipe(
					new ResourceLocation(Reference.MOD_ID, "recipe_"+drugType.getName()+"_empty"),
					new ResourceLocation("emptysyringegroup"),
					new ItemStack(ModItems.SYRINGE_EMPTY),

					Ingredient.fromStacks(new ItemStack(syringe,1, drugType.getId()))
			);
		}
	}

	public void PostInit() {

	}
}
