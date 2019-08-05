package daderpduck.medicraft.events.message;

import daderpduck.medicraft.network.MessageBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageHurt extends MessageBase<MessageHurt> {
	public MessageHurt() {}

	private float damage;

	public MessageHurt(float damage) {
		this.damage = damage;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		damage = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(damage);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleClientSide(MessageHurt message) {

	}

	@SideOnly(Side.SERVER)
	@Override
	public void handleServerSide(MessageHurt message, EntityPlayerMP player) {

	}

}
