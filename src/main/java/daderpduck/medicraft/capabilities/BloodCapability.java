package daderpduck.medicraft.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BloodCapability {
	@CapabilityInject(IBlood.class)
	public static Capability<IBlood> CAP_BLOOD;

	public static void register(CapabilityManager INSTANCE) {
		INSTANCE.register(IBlood.class, new BloodStorage(), ImplementationBlood::new);
	}

	/**
	 * Default implementation of blood
	 */
	public static class ImplementationBlood implements IBlood, INBTSerializable<NBTTagCompound> {
		private float maxBloodLevel = 2000F;
		private float bloodLevel = 2000F;

		@Override
		public void increase(float amount) {
			bloodLevel += amount;
			bloodLevel = Math.min(bloodLevel, maxBloodLevel);
		}

		@Override
		public void decrease(float amount) {
			bloodLevel -= amount;
			bloodLevel = Math.min(bloodLevel, maxBloodLevel);
		}

		@Override
		public void setBlood(float amount) {
			bloodLevel = Math.min(amount, maxBloodLevel);
		}

		@Override
		public float getBlood() {
			return bloodLevel;
		}

		@Override
		public void setMaxBlood(float amount) {
			maxBloodLevel = amount;
			bloodLevel = Math.min(bloodLevel, maxBloodLevel);
		}

		@Override
		public float getMaxBlood() {
			return maxBloodLevel;
		}

		@Override
		public NBTTagCompound serializeNBT() {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setFloat("bloodLevel", bloodLevel);
			nbt.setFloat("maxBloodLevel", maxBloodLevel);
			return nbt;
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			bloodLevel = nbt.getFloat("bloodLevel");
			maxBloodLevel = nbt.getFloat("maxBloodLevel");
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
			return nbt;
		}

		@Override
		public void readNBT(Capability<IBlood> capability, IBlood instance, EnumFacing side, NBTBase nbt) {

		}
	}

	/**
	 * Blood provider
	 */
	public static class BloodProvider implements ICapabilityProvider {
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
	}
}
