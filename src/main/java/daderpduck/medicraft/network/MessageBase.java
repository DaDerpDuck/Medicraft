package daderpduck.medicraft.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//Credit to https://www.youtube.com/watch?v=DhuCk0H71Ks (MineMaarten)
public abstract class MessageBase<REQ extends IMessage> implements IMessage, IMessageHandler<REQ, REQ> {

	@Override
	public REQ onMessage(REQ message, MessageContext ctx) {
		if (ctx.side == Side.SERVER) {
			//Run on server thread
			ctx.getServerHandler().player.getServerWorld().addScheduledTask(() -> handleServerSide(message, ctx.getServerHandler().player));
		} else {
			//Run on main thread
			Minecraft.getMinecraft().addScheduledTask(() -> handleClientSide(message));
		}
		return null;
	}

	//Handle packet on the client side
	@SideOnly(Side.CLIENT)
	public abstract void handleClientSide(REQ message);

	//Handle packet on the server side
	@SideOnly(Side.SERVER)
	public abstract void handleServerSide(REQ message, EntityPlayerMP player);
}
