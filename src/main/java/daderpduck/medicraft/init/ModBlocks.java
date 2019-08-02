package daderpduck.medicraft.init;

import daderpduck.medicraft.blocks.YogurtBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks {
	public static final List<Block> BLOCKS = new ArrayList<>();

	//Blocks
	public static final Block YOGURT_BLOCK = new YogurtBlock("yogurt_block", Material.CLAY);
}
