package daderpduck.medicraft.capabilities;

import net.minecraft.entity.player.EntityPlayerMP;

public interface IUnconscious {
	void setUnconscious(boolean flag);
	boolean getUnconscious();

	void sync(EntityPlayerMP player);
}
