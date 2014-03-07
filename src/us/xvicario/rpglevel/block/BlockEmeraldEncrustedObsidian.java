package us.xvicario.rpglevel.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockEmeraldEncrustedObsidian extends Block {
	public BlockEmeraldEncrustedObsidian(int par1, Material par2Material) {
		super(par1, par2Material);
		setHardness(10.0F);
		setStepSound(Block.soundStoneFootstep);
		setUnlocalizedName("emeraldEncrustedObsidian");
		setCreativeTab(CreativeTabs.tabBlock);
	}
}
