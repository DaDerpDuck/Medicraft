package daderpduck.medicraft.capabilities;

import daderpduck.medicraft.events.message.MessageClientSyncBlood;
import daderpduck.medicraft.network.NetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BloodCapability {
	@CapabilityInject(IBlood.class)
	public static Capability<IBlood> CAP_BLOOD;

	public static void register() {
		CapabilityManager.INSTANCE.register(IBlood.class, new BloodStorage(), ImplementationBlood::new);
		CapabilityAttach.addCapability("blood", new BloodProvider(), new BloodSyncFunction());
	}

	/**
	 * Default implementation of blood
	 */
	public static class ImplementationBlood implements IBlood {
		private float maxBloodLevel = 2000F;
		private float bloodLevel = 2000F;
		private double oxygenLevel = 100D;
		private final double maxOxygenLevel = 100D;

		@Override
		public void setBlood(float amount) {
			bloodLevel = MathHelper.clamp(amount, -1, maxBloodLevel);
		}

		@Override
		public float getBlood() {
			return bloodLevel;
		}

		@Override
		public void increaseBlood(float amount) {
			bloodLevel = MathHelper.clamp(bloodLevel + amount, -1, maxBloodLevel);
		}

		@Override
		public void decreaseBlood(float amount) {
			bloodLevel = MathHelper.clamp(bloodLevel - amount, -1, maxBloodLevel);
		}

		@Override
		public void setMaxBlood(float amount) {
			maxBloodLevel = amount;
			bloodLevel = MathHelper.clamp(bloodLevel, -1, maxBloodLevel);
		}

		@Override
		public float getMaxBlood() {
			return maxBloodLevel;
		}

		@Override
		public void setOxygen(double amount) {
			oxygenLevel = MathHelper.clamp(amount, -1, maxOxygenLevel);
		}

		@Override
		public double getOxygen() {
			return oxygenLevel;
		}

		@Override
		public void increaseOxygen(double amount) {
			oxygenLevel = MathHelper.clamp(oxygenLevel + amount, -1, maxOxygenLevel);
		}

		@Override
		public void decreaseOxygen(double amount) {
			oxygenLevel = MathHelper.clamp(oxygenLevel - amount, -1, maxOxygenLevel);
		}

		@Override
		public double getMaxOxygen() {
			return maxOxygenLevel;
		}
	}

	/**
	 * Blood storage
	 */
	public static class BloodStorage implements Capability.IStorage<IBlood> {

		@Nullable
		@Override
		public NBTBase writeNBT(Capability<IBlood> capability, IBlood instance, EnumFacing side) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setFloat("bloodLevel", instance.getBlood());
			nbt.setFloat("maxBloodLevel", instance.getMaxBlood());
			nbt.setDouble("oxygenLevel", instance.getOxygen());
			return nbt;
		}

		@Override
		public void readNBT(Capability<IBlood> capability, IBlood instance, EnumFacing side, NBTBase nbt) {
			NBTTagCompound nbtTagCompound = (NBTTagCompound) nbt;
			instance.setBlood(nbtTagCompound.getFloat("bloodLevel"));
			instance.setMaxBlood(nbtTagCompound.getFloat("maxBloodLevel"));
			instance.setOxygen(nbtTagCompound.getDouble("oxygenLevel"));
		}
	}

	/**
	 * Blood provider
	 */
	public static class BloodProvider implements ICapabilitySerializable<NBTBase> {
		private final IBlood instance = CAP_BLOOD.getDefaultInstance();

		@Override
		public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
			return capability == CAP_BLOOD;
		}

		@Nullable
		@Override
		public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
			return capability == CAP_BLOOD ? CAP_BLOOD.cast(instance) : null;
		}

		@Override
		public NBTBase serializeNBT() {
			return CAP_BLOOD.getStorage().writeNBT(CAP_BLOOD, instance, null);
		}

		@Override
		public void deserializeNBT(NBTBase nbt) {
			CAP_BLOOD.getStorage().readNBT(CAP_BLOOD, instance, null, nbt);
		}
	}

	/**
	 * Sync implementation of blood
	 */
	static class BloodSyncFunction implements CapabilityAttach.RunnableSyncFunction {
		@Override
		public void run(EntityPlayer player) {
			IBlood blood = player.getCapability(BloodCapability.CAP_BLOOD, null);
			assert blood != null;
			NetworkHandler.FireClient(new MessageClientSyncBlood(blood.getBlood(), blood.getMaxBlood(), blood.getOxygen()), (EntityPlayerMP) player);
		}
	}
}
