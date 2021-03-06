package daderpduck.medicraft.util.handlers;

import daderpduck.medicraft.commands.CommandBloodLevel;
import daderpduck.medicraft.commands.CommandMaxBloodLevel;
import daderpduck.medicraft.commands.CommandOxygenLevel;
import daderpduck.medicraft.init.ModBlocks;
import daderpduck.medicraft.init.ModItems;
import daderpduck.medicraft.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class RegistryHandler {
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		for (Item item : ModItems.ITEMS) {
			if (item instanceof IHasModel) {
				((IHasModel) item).registerModels();
			}
		}

		for (Block block : ModBlocks.BLOCKS) {
			if (block instanceof IHasModel) {
				((IHasModel) block).registerModels();
			}
		}
	}

	public static void serverRegistries(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandBloodLevel());
		event.registerServerCommand(new CommandMaxBloodLevel());
		event.registerServerCommand(new CommandOxygenLevel());
	}
}
