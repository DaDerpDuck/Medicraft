package daderpduck.medicraft.events.message;

import daderpduck.medicraft.effects.shaders.Blur;
import daderpduck.medicraft.effects.shaders.MotionBlur;
import daderpduck.medicraft.network.MessageBase;
import daderpduck.medicraft.shaders.VisualHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public class MessageHurt extends MessageBase<MessageHurt> {
	public MessageHurt(){}

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

	@Override
	public void handleClientSide(MessageHurt message) {

	}

	@Override
	public void handleServerSide(MessageHurt message, EntityPlayerMP player) {

	}
	
}
