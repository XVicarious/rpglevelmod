package us.xvicario.rpglevel;

import java.util.ArrayList;
import java.util.List;

import us.xvicario.rpglevel.util.PlayerInformation;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

public class CheckLevelsCommand implements ICommand {

	private ArrayList<String> aliases;
	
	public CheckLevelsCommand() {
		this.aliases = new ArrayList<String>();
		this.aliases.add("checkLevels");
		this.aliases.add("clv");
	}
	
	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCommandName() {
		return this.aliases.get(0);
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return this.aliases.get(0);
	}

	@Override
	public List getCommandAliases() {
		return this.aliases;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		PlayerInformation pi = PlayerInformation.forPlayer((EntityPlayer)icommandsender);
		((EntityPlayer)icommandsender).addChatMessage("Your §4Mining§F Level is: " + EnumChatFormatting.GREEN + pi.getMiningLevel());
		((EntityPlayer)icommandsender).addChatMessage("Your §4Attack§F Level is: " + EnumChatFormatting.GREEN + pi.getAttackLevel());
		((EntityPlayer)icommandsender).addChatMessage("Your §4Defense§F Level is: " + EnumChatFormatting.GREEN + pi.getDefenceLevel());
		((EntityPlayer)icommandsender).addChatMessage("Your §4Woodcutting§F Level is: " + EnumChatFormatting.GREEN + pi.getWoodcuttingLevel());
		((EntityPlayer)icommandsender).addChatMessage("Your §4Archery§F Level is: " + EnumChatFormatting.GREEN + "0");
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] astring, int i) {
		// TODO Auto-generated method stub
		return false;
	}

}
