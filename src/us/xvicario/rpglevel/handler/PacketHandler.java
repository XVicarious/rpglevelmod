package us.xvicario.rpglevel.handler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.common.DimensionManager;
import us.xvicario.rpglevel.RPGLevel;
import us.xvicario.rpglevel.tileentity.TileEntityLevelUpStation;
import us.xvicario.rpglevel.util.PlayerInformation;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {
	private EntityPlayer player;

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		this.player = ((EntityPlayer) player);
		if (packet.channel.equals("RPGLEVEL")) {
			handlePacket(packet, player);
		}
	}

	private void handlePacket(Packet250CustomPayload packet, Player player) {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			int id = dis.readInt();
			PlayerInformation pi = PlayerInformation.forPlayer((Entity) player);
			if (this.player.getHeldItem() != null) {
				if (this.player.getHeldItem().getItem() == Item.pickaxeIron) {
					pi.setMiningLevel();
					((EntityPlayer)player).addChatMessage("Your MINING level is now " + pi.getMiningLevel());
				} else if (this.player.getHeldItem().getItem() == Item.axeIron) {
					pi.setWoodcuttingLevel();
					((EntityPlayer)player).addChatMessage("You WOODCUTTING level is now " + pi.getWoodcuttingLevel());
				}
			}
			this.player.destroyCurrentEquippedItem();
			this.player.addExperienceLevel(-RPGLevel.requiredLevels);
		} catch (IOException e) {
			RPGLevel.log.info("Failed to send last packet! This isn't good!");
		}
	}
}
