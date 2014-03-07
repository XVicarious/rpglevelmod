package us.xvicario.rpglevel.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerInformation implements IExtendedEntityProperties {
	public static final String IDENTIFIER = "rpglevel_data";
	private int miningLevel;
	private int archeryLevel;
	private int attackLevel;
	private int defenceLevel;
	private int woodcuttingLevel;

	public static PlayerInformation forPlayer(Entity player) {
		return (PlayerInformation) player.getExtendedProperties("rpglevel_data");
	}

	public PlayerInformation(EntityPlayer player) {
	}

	@Override
	public void init(Entity entity, World world) {
		this.miningLevel = 1;
		this.archeryLevel = 1;
		this.attackLevel = 1;
		this.defenceLevel = 1;
		this.woodcuttingLevel = 1;
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = compound.getCompoundTag("rpglevel_data");
		this.miningLevel = nbt.getInteger("miningLevel");
		this.attackLevel = nbt.getInteger("attackLevel");
		this.defenceLevel = nbt.getInteger("defenceLevel");
		this.archeryLevel = nbt.getInteger("archeryLevel");
		this.woodcuttingLevel = nbt.getInteger("woodcuttingLevel");
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("miningLevel", this.miningLevel);
		nbt.setInteger("archeryLevel", this.archeryLevel);
		nbt.setInteger("attackLevel", this.attackLevel);
		nbt.setInteger("defenceLevel", this.defenceLevel);
		nbt.setInteger("woodcuttingLevel", this.woodcuttingLevel);
		compound.setCompoundTag("rpglevel_data", nbt);
	}

	public int getMiningLevel() {
		return this.miningLevel;
	}

	public int setMiningLevel() {
		this.miningLevel += 1;
		return this.miningLevel;
	}

	public int setAttackLevel() {
		this.attackLevel += 1;
		return this.attackLevel;
	}

	public int setDefenceLevel() {
		this.defenceLevel += 1;
		return this.defenceLevel;
	}

	public int setArcheryLevel() {
		this.archeryLevel += 1;
		return this.archeryLevel;
	}

	public int getAttackLevel() {
		return this.attackLevel;
	}

	public int getDefenceLevel() {
		return this.defenceLevel;
	}
	
	public int getWoodcuttingLevel() {
		return this.woodcuttingLevel;
	}
	
	public int setWoodcuttingLevel() {
		this.woodcuttingLevel += 1;
		return this.woodcuttingLevel;
	}
}
