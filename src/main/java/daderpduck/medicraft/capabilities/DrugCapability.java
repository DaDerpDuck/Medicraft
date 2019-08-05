package daderpduck.medicraft.capabilities;

import daderpduck.medicraft.drugs.Drug;
import daderpduck.medicraft.events.message.MessageClientSyncDrugs;
import daderpduck.medicraft.network.NetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class DrugCapability {
	@CapabilityInject(IDrug.class)
	public static Capability<IDrug> CAP_DRUG;

	public static void register() {
		CapabilityManager.INSTANCE.register(IDrug.class, new DrugStorage(), ImplementationPoison::new);
		CapabilityAttach.addCapability("drug", new DrugProvider(), new DrugSyncFunction());
	}

	/**
	 * Default implementation of blood
	 */
	public static class ImplementationPoison implements IDrug {
		private List<Drug.DrugEffect> drugEffects = new LinkedList<>();

		@Override
		public void setDrugs(@Nonnull List<Drug.DrugEffect> drugEffects) {
			this.drugEffects = drugEffects;
		}

		@Override
		public List<Drug.DrugEffect> getAllDrugs() {
			return drugEffects;
		}

		@Override
		public void addDrug(Drug.DrugEffect drugEffect) {
			//Cleanup duplicates
			for (Drug.DrugEffect drugEffect1 : drugEffects) {
				if (drugEffect1 == drugEffect) drugEffects.remove(drugEffect1);
			}

			drugEffects.add(drugEffect);
		}

		@Nullable
		@Override
		public Drug.DrugEffect getActiveDrug(Drug drug) {
			for (Drug.DrugEffect drugEffect : drugEffects) {
				if (drugEffect.drug == drug) return drugEffect;
			}

			return null;
		}

		@Override
		public void removeDrug(Drug.DrugEffect drugEffect) {
			drugEffects.remove(drugEffect);
		}
	}

	/**
	 * Drug storage
	 */
	public static class DrugStorage implements Capability.IStorage<IDrug> {

		@Nullable
		@Override
		public NBTBase writeNBT(Capability<IDrug> capability, IDrug instance, EnumFacing side) {
			NBTTagList nbtTagList = new NBTTagList();

			for (Drug.DrugEffect drugEffect : instance.getAllDrugs()) {
				NBTTagCompound nbtTagCompound = new NBTTagCompound();
				nbtTagCompound.setInteger("drugId", drugEffect.drug.id);
				nbtTagCompound.setInteger("drugDuration", drugEffect.drugDuration);
				nbtTagCompound.setInteger("amplifier", drugEffect.amplifier);
				nbtTagCompound.setInteger("drugDelay", drugEffect.drugDelay);

				nbtTagList.appendTag(nbtTagCompound);
			}

			return nbtTagList;
		}

		@Override
		public void readNBT(Capability<IDrug> capability, IDrug instance, EnumFacing side, NBTBase nbt) {
			NBTTagList nbtTagList = (NBTTagList) nbt;

			for (NBTBase nbtBase : nbtTagList) {
				NBTTagCompound nbtTagCompound = (NBTTagCompound) nbtBase;
				Drug drug = Drug.getDrugById(nbtTagCompound.getInteger("drugId"));
				if (drug == null) continue;

				instance.addDrug(new Drug.DrugEffect(
						drug,
						nbtTagCompound.getInteger("drugDuration"),
						nbtTagCompound.getInteger("amplifier"),
						drug.maxAmplifier,
						nbtTagCompound.getInteger("drugDelay")
				));
			}
		}
	}

	/**
	 * Poison provider
	 */
	public static class DrugProvider implements ICapabilitySerializable<NBTBase> {
		private final IDrug instance = CAP_DRUG.getDefaultInstance();

		@Override
		public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
			return capability == CAP_DRUG;
		}

		@Nullable
		@Override
		public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
			return capability == CAP_DRUG ? CAP_DRUG.cast(instance) : null;
		}

		@Override
		public NBTBase serializeNBT() {
			return CAP_DRUG.getStorage().writeNBT(CAP_DRUG, instance, null);
		}

		@Override
		public void deserializeNBT(NBTBase nbt) {
			CAP_DRUG.getStorage().readNBT(CAP_DRUG, instance, null, nbt);
		}
	}

	/**
	 * Sync implementation of poison
	 */
	static class DrugSyncFunction implements CapabilityAttach.RunnableSyncFunction {
		@Override
		public void run(EntityPlayer player) {
			IDrug drug = player.getCapability(DrugCapability.CAP_DRUG, null);
			assert drug != null;
			NetworkHandler.FireClient(new MessageClientSyncDrugs(drug.getAllDrugs()), (EntityPlayerMP) player);
		}
	}
}
