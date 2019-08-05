package daderpduck.medicraft.events.message;

import daderpduck.medicraft.capabilities.BloodCapability;
import daderpduck.medicraft.capabilities.IBlood;
import daderpduck.medicraft.network.MessageBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageClientSyncBlood extends MessageBase<MessageClientSyncBlood> {
	public MessageClientSyncBlood() {}

	private float blood;
	private float maxBlood;

	public MessageClientSyncBlood(float blood, float maxBlood) {
		this.blood = blood;
		this.maxBlood = maxBlood;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		blood = buf.readFloat();
		maxBlood = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(blood);
		buf.writeFloat(maxBlood);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleClientSide(MessageClientSyncBlood message) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		IBlood blood = player.getCapability(BloodCapability.CAP_BLOOD, null);
		assert blood != null;

		blood.setBlood(message.blood);
		blood.setMaxBlood(message.maxBlood);
	}

	@SideOnly(Side.SERVER)
	@Override
	public void handleServerSide(MessageClientSyncBlood message, EntityPlayerMP player) {

	}
}
