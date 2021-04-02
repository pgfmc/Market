package net.pgfmc.shop.inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.pgfmc.shop.Listing;
import net.pgfmc.shop.Main;

public class Confirm implements InventoryHolder {
	
	Inventory inv;
	Listing lst;
	
	
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
		
		if (lst.getTrade() != null) {
			
			ItemMeta itemMeta = lst.getTrade().getItemMeta();
			
			List<String> list = new ArrayList<String>();
			list.add("Place the required item in the item slot");
			list.add("to the left, then take the item you paid for!");
			list.add("");
			
			String name = lst.getTrade().getItemMeta().getLocalizedName(); // --------- plural stuff
			if (lst.getItem().getAmount() == 1) {
				list.add("Required payment: " + String.valueOf(lst.getPrice()) + " " + lst.getTrade().getItemMeta().getLocalizedName());
			} else {
				if (name.endsWith("s")) {
					
					list.add("Required payment: " + String.valueOf(lst.getPrice()) + " " + lst.getTrade().getItemMeta().getLocalizedName() + "s");
				} else {
					list.add("Required payment: " + String.valueOf(lst.getPrice()) + " " + lst.getTrade().getItemMeta().getLocalizedName() + "'");
				}
			}
			
			list.add("Required payment: " + String.valueOf(lst.getPrice()) + " " + lst.getTrade().getItemMeta().getLocalizedName());
			list.add("Listing Posted by " + lst.getPlayer().getName());
			
			itemMeta.setLore(list);
			
			ItemStack itemStack = lst.getTrade();
			itemStack.setItemMeta(itemMeta);
			
			inv.setItem(16, itemStack);
			
		} else {
			
			ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(Material.DIAMOND);
			
			List<String> list = new ArrayList<String>();
			list.add("Place the required item in the item slot");
			list.add("to the left, then take the item you paid for!");
			list.add("");
			if (lst.getPrice() == 1) { // -------------------------------------------------------- decides wether to use plural or singular
				list.add("Required payment: " + String.valueOf(lst.getPrice()) + " Diamond");
			} else {
				list.add("Required payment: " + String.valueOf(lst.getPrice()) + " Diamonds");
			}
			list.add("Listing Posted by " + lst.getPlayer().getName());
			
			itemMeta.setLore(list);
			
			ItemStack itemStack = lst.getTrade();
			itemStack.setItemMeta(itemMeta);
			
			inv.setItem(16, itemStack);
		}
		
		inv.setItem(20, Main.createItem("", Material.NETHER_STAR));
		inv.setItem(23, Main.createItem("", Material.NETHER_STAR));
	}
	
	public void confirmBuy(List<Object> listing, Player buyer)
	{
		inv = Bukkit.createInventory(this, 54, "Shop");
	}

	@Override
	public Inventory getInventory() {
		return inv;
	}
}