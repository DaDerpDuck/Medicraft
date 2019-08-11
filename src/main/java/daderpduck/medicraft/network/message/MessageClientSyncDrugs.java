package daderpduck.medicraft.network.message;

import daderpduck.medicraft.capabilities.DrugCapability;
import daderpduck.medicraft.capabilities.IDrug;
import daderpduck.medicraft.drugs.Drug;
import daderpduck.medicraft.network.MessageBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.List;

public class MessageClientSyncDrugs extends MessageBase<MessageClientSyncDrugs> {
	public MessageClientSyncDrugs() {}

	private List<Drug.DrugEffect> drugEffects;

	public MessageClientSyncDrugs(List<Drug.DrugEffect> drugEffects) {
		this.drugEffects = drugEffects;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		List<Drug.DrugEffect> drugEffects1 = new LinkedList<>();
		int length = buf.readInt();

		for (int i = 1; i <= length; i++) {
			Drug drug = Drug.getDrugById(buf.readInt());
			if (drug == null) continue;

			drugEffects1.add(new Drug.DrugEffect(
					drug,
					buf.readInt(),
					buf.readInt(),
					drug.maxAmplifier,
					buf.readInt()
			));
		}

		drugEffects = drugEffects1;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(drugEffects.size());

		for (Drug.DrugEffect drugEffect : drugEffects) {
			buf.writeInt(drugEffect.drug.id);
			buf.writeInt(drugEffect.drugDuration);
			buf.writeInt(drugEffect.amplifier);
			buf.writeInt(drugEffect.drugDelay);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleClientSide(MessageClientSyncDrugs message) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		IDrug drug = player.getCapability(DrugCapability.CAP_DRUG, null);
		assert drug != null;

		drug.setDrugs(message.drugEffects);
	}

	@SideOnly(Side.SERVER)
	@Override
	public void handleServerSide(MessageClientSyncDrugs message, EntityPlayerMP player) {

	}
}
