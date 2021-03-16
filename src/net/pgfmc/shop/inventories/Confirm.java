package net.pgfmc.shop.inventories;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Confirm {
	
	public static void confirmBuy(List<Object> listing, Player buyer)
	{
		Inventory inv = Bukkit.createInventory(this, 54, "Shop");
	}

}
