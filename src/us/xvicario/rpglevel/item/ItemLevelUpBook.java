package us.xvicario.rpglevel.item;

import java.util.List;

import us.xvicario.rpglevel.util.PlayerInformation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemLevelUpBook extends Item {
	public static final String[] names = { "mining", "attack", "defence", "archery", "woodcutting" };
	private static String subName;
	private boolean flag;

	public ItemLevelUpBook(int id) {
		super(id);
		this.maxStackSize = 1;
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("levelUpBook");
		setHasSubtypes(true);
		setMaxDamage(0);
		flag = false;
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		PlayerInformation pi = PlayerInformation.forPlayer(par2EntityPlayer);
		if (subName == "mining") {
			if (flag == false) {
				pi.setMiningLevel();
				par2EntityPlayer.addChatMessage("You now have level " + pi.getMiningLevel() + " mining!");
				flag = true;
			}
		} else if (subName == "attack") {
			if (flag == false) {
				pi.setAttackLevel();
				par2EntityPlayer.addChatMessage("You now have level " + pi.getAttackLevel() + " attack!");
				flag = true;
			}
		} else if (subName == "defence") {
			pi.setDefenceLevel();
		} else if (subName == "archery") {
			pi.setArcheryLevel();
		} else if (subName == "woodcutting") {
			pi.setWoodcuttingLevel();
		} else {
			par2EntityPlayer.addChatMessage("Nope?");
		}
		par2EntityPlayer.destroyCurrentEquippedItem();
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int x = 0; x < 4; x++) {
			par3List.add(new ItemStack(this, 1, x));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 15);
		subName = names[i];
		return super.getUnlocalizedName() + "." + names[i];
	}
}
