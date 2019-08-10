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
	private double oxygenLevel;

	public MessageClientSyncBlood(float blood, float maxBlood, double oxygenLevel) {
		this.blood = blood;
		this.maxBlood = maxBlood;
		this.oxygenLevel = oxygenLevel;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		blood = buf.readFloat();
		maxBlood = buf.readFloat();
		oxygenLevel = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(blood);
		buf.writeFloat(maxBlood);
		buf.writeDouble(oxygenLevel);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleClientSide(MessageClientSyncBlood message) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		IBlood blood = player.getCapability(BloodCapability.CAP_BLOOD, null);
		assert blood != null;

		blood.setBlood(message.blood);
		blood.setMaxBlood(message.maxBlood);
		blood.setOxygen(message.oxygenLevel);
	}

	@SideOnly(Side.SERVER)
	@Override
	public void handleServerSide(MessageClientSyncBlood message, EntityPlayerMP player) {

	}
}
