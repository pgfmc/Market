package net.pgfmc.shop.inventories;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class Confirm implements InventoryHolder{
	
	Inventory inv;
	
	public void confirmBuy(List<Object> listing, Player buyer)
	{
		inv = Bukkit.createInventory(this, 54, "Shop");
	}

	@Override
	public Inventory getInventory() {
		return inv;
	}
}