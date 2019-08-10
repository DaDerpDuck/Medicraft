package daderpduck.medicraft.proxy;

import daderpduck.medicraft.base.DrugType;
import daderpduck.medicraft.events.DamageEvent;
import daderpduck.medicraft.events.EntityJoinEvent;
import daderpduck.medicraft.events.InjuryHandler;
import daderpduck.medicraft.events.PlayerTick;
import daderpduck.medicraft.init.*;
import daderpduck.medicraft.network.NetworkHandler;
import daderpduck.medicraft.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	public void registerItemRenderer(Item item, int meta, String id) {
	}

	public void PreInit() {
		ForgeRegistries.BLOCKS.registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
		ForgeRegistries.ITEMS.registerAll(ModItems.ITEMS.toArray(new Item[0]));

		NetworkHandler.init();
		ModPotions.registerPotions();
		ModCapabilities.registerCapabilities();

		MinecraftForge.EVENT_BUS.register(new DamageEvent());
		MinecraftForge.EVENT_BUS.register(new EntityJoinEvent());
		MinecraftForge.EVENT_BUS.register(new InjuryHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerTick());
	}

	public void Init() {
		//Automatically create recipes for filled syringes
		for (DrugType drugType : ModDrugTypes.DRUG_TYPES) {
			//Filling a syringe
			GameRegistry.addShapelessRecipe(
					new ResourceLocation(Reference.MOD_ID, "recipe_"+drugType.getName()),
					new ResourceLocation(""),
					new ItemStack(ModItems.SYRINGE_FILLED,1, drugType.getId()),

					Ingredient.fromStacks(new ItemStack(ModItems.SYRINGE_EMPTY)),
					Ingredient.fromStacks(new ItemStack(ModItems.VIAL_FILLED, 1, drugType.getId()))
			);
			//Emptying contents of a syringe
			GameRegistry.addShapelessRecipe(
					new ResourceLocation(Reference.MOD_ID, "recipe_"+drugType.getName()+"_empty"),
					new ResourceLocation("emptysyringegroup"),
					new ItemStack(ModItems.SYRINGE_EMPTY),

					Ingredient.fromStacks(new ItemStack(ModItems.SYRINGE_FILLED,1, drugType.getId()))
			);
		}
	}

	public void PostInit() {

	}
}
