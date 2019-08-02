package daderpduck.medicraft.network;

import daderpduck.medicraft.events.message.MessageClientSyncBlood;
import daderpduck.medicraft.events.message.MessageExplodeDamage;
import daderpduck.medicraft.events.message.MessageHurt;
import daderpduck.medicraft.events.message.MessagePain;
import daderpduck.medicraft.util.Reference;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

//Credit to https://www.youtube.com/watch?v=DhuCk0H71Ks (MineMaarten)
public class NetworkHandler {
	private static SimpleNetworkWrapper INSTANCE;
	private static int id = 0;

	@SuppressWarnings({"unchecked", "SameParameterValue"})
	private static void registerMessage(Class c, Side side) {
		INSTANCE.registerMessage(c, c, id, side);
		id++;
	}

	public static void init() {
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

		registerMessage(MessageHurt.class, Side.CLIENT);
		registerMessage(MessageExplodeDamage.class, Side.CLIENT);
		registerMessage(MessagePain.class, Side.CLIENT);
		registerMessage(MessageClientSyncBlood.class, Side.CLIENT);
	}


	@SuppressWarnings({"unused"})
	public static void FireServer(IMessage message) {
		INSTANCE.sendToServer(message);
	}

	public static void FireClient(IMessage message, EntityPlayerMP player) {
		INSTANCE.sendTo(message, player);
	}

	@SuppressWarnings({"unused"})
	public static void FireAllClients(IMessage message) {
		INSTANCE.sendToAll(message);
	}
}
