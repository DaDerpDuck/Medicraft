package daderpduck.medicraft;

import daderpduck.medicraft.init.ModCapabilities;
import daderpduck.medicraft.init.ModPotions;
import daderpduck.medicraft.network.NetworkHandler;
import daderpduck.medicraft.proxy.CommonProxy;
import daderpduck.medicraft.util.Reference;
import daderpduck.medicraft.util.handlers.RegistryHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class Main {
	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) {
		NetworkHandler.init();
		ModPotions.registerPotions();
		ModCapabilities.registerCapabilities();
	}
	
	@EventHandler
	public static void Init(FMLInitializationEvent event) {
		
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event) {
		
	}

	@EventHandler
	public static void ServerInit(FMLServerStartingEvent event) {
		RegistryHandler.serverRegistries(event);
	}
}
