package daderpduck.medicraft.network.message;

import daderpduck.medicraft.capabilities.IUnconscious;
import daderpduck.medicraft.capabilities.UnconsciousCapability;
import daderpduck.medicraft.network.MessageBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageClientSyncUnconscious extends MessageBase<MessageClientSyncUnconscious> {
	public MessageClientSyncUnconscious() {}

	private boolean unconscious;

	public MessageClientSyncUnconscious(boolean unconscious) {
		this.unconscious = unconscious;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		unconscious = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(unconscious);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleClientSide(MessageClientSyncUnconscious message) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		IUnconscious unconscious = player.getCapability(UnconsciousCapability.CAP_UNCONSCIOUS, null);
		assert unconscious != null;

		unconscious.setUnconscious(message.unconscious);
	}

	@SideOnly(Side.SERVER)
	@Override
	public void handleServerSide(MessageClientSyncUnconscious message, EntityPlayerMP player) {

	}
}
