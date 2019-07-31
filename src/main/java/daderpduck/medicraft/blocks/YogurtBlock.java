package daderpduck.medicraft.blocks;

import daderpduck.medicraft.base.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class YogurtBlock extends BlockBase {

	public YogurtBlock(String name, Material material) {
		super(name, material);
		
		setSoundType(SoundType.SLIME);
		setHardness(0.5F);
		setResistance(3.0F);
		//setLightLevel
		//setLightOpacity
		//setBlockUnbreakable
	}
}
