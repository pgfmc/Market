package net.pgfmc.shop.commands;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.pgfmc.shop.inventories.Base;

public class Shop implements CommandExecutor {
	
	public static ArrayList<Base> SHOPPERS = new ArrayList<Base>(); // This holds a list of Base for, one for each player that opens the shop, saves in memory

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		if (!(sender instanceof Player) || ((Player) sender).getGameMode() != GameMode.SURVIVAL)
		{
			sender.sendMessage("§cYou cannot execute this command."); // lol
			return true;
		}
		
		Player p = (Player) sender; // gets Base (all of the listings) and then opens the gui for them
		
		if (SHOPPERS != null)
		{
			for (Base shopper : SHOPPERS)
			{
				if (shopper.matches(p))
				{
					p.openInventory(shopper.getInventory());
					return true;
				}
			}
		}
		
		Base gui = new Base(p);
		p.openInventory(gui.getInventory());
		SHOPPERS.add(gui);
		
		return true;
	}
}