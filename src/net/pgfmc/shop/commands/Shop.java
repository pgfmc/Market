package net.pgfmc.shop.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.pgfmc.shop.inventories.Base;

public class Shop implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player))
		{
			sender.sendMessage("§cYou cannot execute this command.");
			return true;
		}
		
		Player p = (Player) sender;
		
		Base gui = new Base();
		p.openInventory(gui.getInventory());
		
		return true;
	}
	
	

}
