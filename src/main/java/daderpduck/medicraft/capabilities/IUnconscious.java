package daderpduck.medicraft.capabilities;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;

public interface IUnconscious {
	void setUnconscious(boolean flag);
	boolean getUnconscious();

	void setLastPos(double posX, double posY, double posZ);
	Vec3d getLastPos();

	void sync(EntityPlayerMP player);
}
