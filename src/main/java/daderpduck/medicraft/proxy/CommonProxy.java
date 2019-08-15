package daderpduck.medicraft.proxy;

import daderpduck.medicraft.Main;
import daderpduck.medicraft.entities.EntityUnconsciousBody;
import daderpduck.medicraft.handler.*;
import daderpduck.medicraft.init.ModBlocks;
import daderpduck.medicraft.init.ModCapabilities;
import daderpduck.medicraft.init.ModItems;
import daderpduck.medicraft.init.ModPotions;
import daderpduck.medicraft.network.NetworkHandler;
import daderpduck.medicraft.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

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
		MinecraftForge.EVENT_BUS.register(new SyringeAttack());
		MinecraftForge.EVENT_BUS.register(new RespawnEvent());
		MinecraftForge.EVENT_BUS.register(new UnconsciousHandler());
	}

	public void Init() {
		registerEntity(EntityUnconsciousBody.class);
	}

	public void PostInit() {

	}

	private static int entityIndex = 0;
	private void registerEntity(Class<? extends Entity> entityClass){
		String idName = "unconsciousbody";
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, idName), entityClass, idName, entityIndex++, Main.instance, 32, 1, true);
	}
}
