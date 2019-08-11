package daderpduck.medicraft.network.message;

import daderpduck.medicraft.effects.shaders.Blur;
import daderpduck.medicraft.effects.shaders.MotionBlur;
import daderpduck.medicraft.network.MessageBase;
import daderpduck.medicraft.shaders.VisualHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageExplodeDamage extends MessageBase<MessageExplodeDamage> {
	public MessageExplodeDamage() {}

	private float damage;

	public MessageExplodeDamage(float damage) {
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
	public void handleClientSide(MessageExplodeDamage message) {
		if (message.damage <= 2) return;

		float f = message.damage / 3F;

		VisualHandler.setSingleShader(this, new Blur((int) f * 3000, (int) f));
		VisualHandler.setSingleShader(this, new MotionBlur((int) (f * 3000), Math.min(f / 6, 1)));
	}

	@SideOnly(Side.SERVER)
	@Override
	public void handleServerSide(MessageExplodeDamage message, EntityPlayerMP player) {

	}

}
