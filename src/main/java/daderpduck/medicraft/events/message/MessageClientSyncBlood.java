package daderpduck.medicraft.events.message;

import daderpduck.medicraft.capabilities.BloodCapability;
import daderpduck.medicraft.capabilities.IBlood;
import daderpduck.medicraft.network.MessageBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class MessageClientSyncBlood extends MessageBase<MessageClientSyncBlood> {
	public MessageClientSyncBlood(){}

	private float blood;

	public MessageClientSyncBlood(float blood) {
		this.blood = blood;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		blood = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(blood);
	}

	@Override
	public void handleClientSide(MessageClientSyncBlood message) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		IBlood blood = player.getCapability(BloodCapability.BloodProvider.CAP_BLOOD, null);

		blood.setBlood(message.blood);
	}

	@Override
	public void handleServerSide(MessageClientSyncBlood message, EntityPlayerMP player) {

	}
}
