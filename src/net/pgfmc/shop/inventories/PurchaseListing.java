package net.pgfmc.shop.inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.pgfmc.shop.Database;
import net.pgfmc.shop.Listing;
import net.pgfmc.shop.Main;

public class PurchaseListing implements InventoryHolder {
	
	Inventory inv;
	Listing lst;
	public boolean isBought = false;
	public boolean closing = false;
	
	public PurchaseListing(Listing listing) { // constructor (creates menu for buying an item)
		
		lst = listing;
		inv = Bukkit.createInventory(this, 27, "");
		
		inv.setItem(0, Main.createItem("§eCancel", Material.FEATHER));
		
		ItemMeta itemMeta = lst.getItem().getItemMeta();
		itemMeta.setLore(new ArrayList<String>());
		ItemStack itemStack = lst.getItem().clone();
		itemStack.setItemMeta(itemMeta);
		
		inv.setItem(11, itemStack);
		
		itemMeta = lst.getTrade().getItemMeta();
		
		List<String> list = new ArrayList<String>();
		list.add("§r§9Place the required item in the item slot");
		list.add("§r§9to the left, then take the item you paid for!");
		list.add("");
		list.add("§r§9Required payment: " + lst.getPrice());
		list.add("§r§9Listing Posted by " + lst.getPlayer().getName());
		
		itemMeta.setLore(list);
		
		itemStack = lst.getTrade();
		itemStack.setItemMeta(itemMeta);
		
		inv.setItem(16, itemStack);
		setInventoryState(Material.RED_CONCRETE);
	}
	
	public void setInventoryState(Material material) {
		int[] list = {2, 5, 10, 12, 13, 15, 20, 23};
		for (int slot : list) {
			inv.setItem(slot, Main.createItem(" ", material));
		}
	}
	
	public void confirmBuy(Player player) { // clears the inventory, and makes it to where all actions in the inventory are cancelled.
		
		ItemStack item = inv.getItem(14);
		Integer lstItem = lst.getItem().getAmount();
		
		if (item.getAmount() == lstItem) {
			inv.setItem(14, new ItemStack(Material.AIR));
		} else if (item.getAmount() > lstItem) {
			item.setAmount(item.getAmount() - lstItem);
			player.getInventory().addItem(item);
			inv.setItem(14, new ItemStack(Material.AIR));
		} else {
			System.out.println("ERROR: PurchaseListing.confirmBuy was run, while the listing wasn't able to buy from.");
			return;
		}
		
		Database.addMoneytoPlayer(lst.getPlayer(), lst.getTrade());
		lst.deleteListing();
		if (lst.getPlayer() instanceof Player) {
			((Player) lst.getPlayer()).sendMessage("Someone has bought from you on the shop!");
			((Player) lst.getPlayer()).playSound(((Player) lst.getPlayer()).getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
		}
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
		isBought = true;
		
		ItemStack ironBars = Main.createItem("§r§7Item has been taken!", Material.IRON_BARS);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() { // in-inventory animations
            @Override
            public void run() {
            	ItemStack[] itemList = {ironBars, ironBars, ironBars, ironBars, ironBars, ironBars, ironBars, ironBars};
            	int index = 1;
        		for (ItemStack item : itemList) {
        			inv.setItem(index, item);
        			index++;
        		}
            }
        }, 1);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() { // in-inventory animations
            @Override
            public void run() {
            	ItemStack[] itemList = {ironBars, ironBars, ironBars, ironBars, ironBars, ironBars, ironBars, ironBars, ironBars};
            	int index = 9;
        		for (ItemStack item : itemList) {
        			inv.setItem(index, item);
        			index++;
        		}
            }
        }, 4);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            @Override
            public void run() {
            	ItemStack[] itemList = {ironBars, ironBars, ironBars, ironBars, ironBars, ironBars, ironBars, ironBars, ironBars};
            	int index = 18;
        		for (ItemStack item : itemList) {
        			inv.setItem(index, item);
        			index++;
        		}
            }
        }, 7);
	}
	
	public boolean canBuy() { // returns if the listing can be bought

		if (inv.getItem(14) != null && inv.getItem(14).getType() == lst.getTrade().getType() && inv.getItem(14).getAmount() >= lst.getTrade().getAmount()) {
			return true;
		}
		return false;
	}
	
	
	
	public void setClosing(boolean closing) {
		this.closing = closing;
	}
	
	public boolean getClosing() {
		return closing;
	}
	
	public Listing getListing() {
		return lst;
	}
	
	@Override
	public Inventory getInventory() {
		return inv;
	}
}