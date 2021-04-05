package net.pgfmc.shop.inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.pgfmc.shop.Listing;
import net.pgfmc.shop.Main;

public class Confirm implements InventoryHolder {
	
	Inventory inv;
	Listing lst;
	public boolean isBought = false;
	
	public Confirm(Listing listing) { // constructor (creates menu for buying an item)
		
		lst = listing;
		inv = Bukkit.createInventory(this, 27, "");
		invBuilder();
	}
	
	private void invBuilder() {
		
		inv.setItem(0, Main.createItem("§eCancel", Material.FEATHER));
		inv.setItem(2, Main.createItem("", Material.NETHER_STAR));
		inv.setItem(5, Main.createItem("", Material.NETHER_STAR));
		inv.setItem(10, Main.createItem("", Material.NETHER_STAR));
		inv.setItem(11, lst.getItem());
		inv.setItem(12, Main.createItem("", Material.NETHER_STAR));
		inv.setItem(13, Main.createItem("", Material.NETHER_STAR));
		inv.setItem(15, Main.createItem("", Material.NETHER_STAR));
		
		ItemMeta itemMeta = lst.getTrade().getItemMeta();
		
		List<String> list = new ArrayList<String>();
		list.add("Place the required item in the item slot");
		list.add("to the left, then take the item you paid for!");
		list.add("");
		list.add("Required payment: " + lst.getPrice());
		list.add("Required payment: " + String.valueOf(lst.getPrice()) + " " + lst.getTrade().getItemMeta().getLocalizedName());
		list.add("Listing Posted by " + lst.getPlayer().getName());
		
		itemMeta.setLore(list);
		
		ItemStack itemStack = lst.getTrade();
		itemStack.setItemMeta(itemMeta);
		
		inv.setItem(16, itemStack);
		
		inv.setItem(20, Main.createItem("", Material.NETHER_STAR));
		inv.setItem(23, Main.createItem("", Material.NETHER_STAR));
	}
	
	public void confirmBuy() { // clears the inventory, and makes it to where all actions in the inventory are cancelled.
		inv.clear();
		isBought = true;
	}
	
	public boolean canBuy() { // returns if the listing can be bought
		
		if (inv.getItem(14).getType() == lst.getItem().getType() && inv.getItem(14).getAmount() == lst.getItem().getAmount()) {
			return true;
		}
		return false;
	}
	
	@Override
	public Inventory getInventory() {
		return inv;
	}
}