package net.pgfmc.shop.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.pgfmc.shop.inventories.Base;

public class Shop implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (!(sender instanceof Player) || ((Player) sender).getGameMode() != GameMode.SURVIVAL)
		{
			sender.sendMessage("§cYou cannot execute this command."); // lol
			return true;
		}
		
		Player p = (Player) sender; // gets Base (all of the listings) and then opens the gui for them
		
		Base gui = new Base();
		p.openInventory(gui.getInventory());
		
		return true;
	}
}