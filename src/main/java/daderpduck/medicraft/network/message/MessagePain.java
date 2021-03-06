package daderpduck.medicraft.network.message;

import daderpduck.medicraft.effects.shaders.Vignette;
import daderpduck.medicraft.network.MessageBase;
import daderpduck.medicraft.shaders.VisualHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.vector.Vector3f;

public class MessagePain extends MessageBase<MessagePain> {
	public MessagePain() {}

	private float pain;

	public MessagePain(float pain) {
		this.pain = pain;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pain = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(pain);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleClientSide(MessagePain message) {
		VisualHandler.addShader(new Vignette((int) (message.pain * 5000), new Vector3f(0.9F, 0, 0), message.pain));
	}

	@SideOnly(Side.SERVER)
	@Override
	public void handleServerSide(MessagePain message, EntityPlayerMP player) {

	}
}
